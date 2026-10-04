#!/usr/bin/env python3
"""
Parses the numbered topic folders ("01. Dynamic Arrays", ...) of .md files and
emits:
  - python/<topic>/<NN>_<NN>_<name>.py          solution + tests, runnable
  - javascript/<topic>/<NN>_<NN>_<name>.js      solution + tests, runnable
  - java/<topic>/P<NN>_<NN>_<Name>.java         solution + tests, runnable
  - docs/data/topics.json                       sidebar manifest for the site
  - docs/data/<topic-slug>/<problem-slug>.json  per-problem data for the site
  - docs/data/<topic-slug>/images/*.png         images, shared by the site only

Also writes tools/report.txt with anomalies (missing languages, duplicates).
"""
import json
import re
import ast
import os
import shutil
import subprocess
import tempfile
import textwrap
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
TOPIC_DIRS = sorted(
    (p.name for p in ROOT.iterdir() if p.is_dir() and re.match(r"^\d+\. ", p.name)),
    key=lambda n: int(n.split(".", 1)[0]),
)

LANG_START = {
    "python": re.compile(r"^[ \t]*def run_tests\(\):", re.M),
    "javascript": re.compile(r"^[ \t]*function run_?[tT]ests\(\)", re.M),
    "java": re.compile(r"^class RunTests\b", re.M),
}

PY_END = re.compile(r"^[ \t]*run_tests\(\)\s*$", re.M)
JS_END = re.compile(r"^[ \t]*run_?[tT]ests\(\);?\s*$", re.M)
JAVA_END = re.compile(r"new RunTests\(\)\.runTests\(\);\s*\n\s*\}\s*\n\}", re.M)

CUE = re.compile(r"[Hh]ere(?:'s| is| are) the [^\n:]{0,60}?implementation[^\n:]{0,40}:[ \t]*\n+")

UNESCAPED_QUOTE = re.compile(r'(?<!\\)"')


def rejoin_broken_strings(text: str) -> str:
    """Some long f-strings/template literals got hard-wrapped mid-expression
    when originally pasted (e.g. a line break landed inside `f"...{expr}"`),
    leaving an unterminated string literal. Rejoin any line left with an odd
    number of unescaped double-quotes with the following line(s)."""
    lines = text.split("\n")
    out = []
    i = 0
    while i < len(lines):
        line = lines[i]
        while line.endswith("$") and i + 1 < len(lines) and lines[i + 1].lstrip().startswith("{"):
            i += 1
            line += lines[i].lstrip()
        joins = 0
        while (
            len(UNESCAPED_QUOTE.findall(line)) % 2 == 1
            and i + 1 < len(lines)
            and joins < 2
            and re.search(r'f"|assert|print\(|Error\(|throw|got:', line)
        ):
            i += 1
            joins += 1
            line += lines[i]
        out.append(line)
        i += 1
    return "\n".join(out)


# boilerplate / trailing commentary that trails each implementation, should
# not be shown as "code" - cut at whichever of these appears first
TRAILING_BOILERPLATE = re.compile(
    r"\n?(?:Time & Space Analysis\b"
    r"|To verify the solution is correct\b"
    r"|When the two pointers point to the same element\b).*",
    re.S,
)
# stray language-label lines left over from manual pasting (e.g. "JS:", "Java")
LEADING_LABEL = re.compile(r"^(?:JavaScript|Python|Java|JS)\s*:?\s*\n+", re.I)
# linking sentence between two code blocks (e.g. extension class + base class)
# should be dropped, not break the surrounding code
INLINE_LABEL = re.compile(r"\n?Here is the basic array class:\n*")


CODE_CHARS = re.compile(r"[{};=<>\[\]]")
CALL = re.compile(r"\w\(")
HEADING_TOKEN = re.compile(r"^[A-Za-z0-9'\-]+$")


CODE_START = re.compile(
    r"^(def|class|if|elif|else|for|while|return|import|from|try|except|finally|with|pass|break|continue|"
    r"raise|yield|lambda|print|assert|del|global|nonlocal|async|await|public|private|protected|static|final|"
    r"void|int|long|double|boolean|char|String|const|let|var|function|switch|case|default|do|throw|new|"
    r"export|extends|implements|interface|enum|this|self|super|not)\b"
)
PSEUDO = re.compile(r"^\s*(for \w+ from |define |let .* be )")


def is_prose_line(line: str) -> bool:
    """Explanatory sentences / headings / pseudocode pasted between code blocks."""
    text = line.strip()
    if not text:
        return False
    if re.match(r"^!\[[^\]]*\]\([^)]*\)", text) or re.match(r"^Example\s*\d+", text) or PSEUDO.match(text):
        return True
    if re.fullmatch(r"[\s^|]+", text) and ("^" in text or "|" in text):
        return True
    if text.startswith(("//", "#", "/*", "*", "@", "}", ")", "]")):
        return False
    if CODE_START.match(text):
        return False
    tokens = text.split()
    words = sum(1 for t in tokens if re.fullmatch(r"[A-Za-z][A-Za-z'’,.;:()\-]*", t))
    is_code_ish = re.search(r"#|//|^[\w.\[\]]+\s*=[^=]|[{};]\s*$|^[\[({\"']|[\"'`]", text)
    if len(tokens) >= 10 and words / len(tokens) >= 0.7 and not is_code_ish:
        return True
    if text.endswith(":") and len(tokens) >= 2 and all(re.fullmatch(r"[A-Za-z]+", t[:-1] if t.endswith(":") else t) for t in tokens):
        return True
    if CODE_CHARS.search(text):
        return False
    ends_sentence = text[-1] in ".:!?"
    if text[0].isupper() and len(tokens) >= 3 and (ends_sentence or not CALL.search(text)):
        return True
    quoted = '"' in text or "`" in text or text[0] == "'"
    if len(tokens) >= 8 and words / len(tokens) >= 0.6 and not CALL.search(text) and not quoted:
        return True
    if text[0].islower() and len(tokens) >= 6 and text[-1] in ".,:" and not CALL.search(text):
        return True
    if line[0] not in " \t" and text[0].isupper() and len(tokens) <= 5 and all(HEADING_TOKEN.match(t) for t in tokens):
        return True
    return False


def strip_prose(code: str) -> str:
    lines = [l for l in code.split("\n") if not is_prose_line(l)]
    return re.sub(r"\n{3,}", "\n\n", "\n".join(lines)).strip()


def clean_impl(code: str) -> str:
    code = TRAILING_BOILERPLATE.sub("", code).strip()
    code = LEADING_LABEL.sub("", code).strip()
    m = INLINE_LABEL.search(code)
    if m:
        # base class first so the file runs top to bottom
        code = code[m.end():].strip() + "\n\n" + code[: m.start()].strip()
    # undo markdown escaping of underscores/asterisks pasted as plain text
    code = code.replace("\\_", "_").replace("\\*", "*")
    code = code.replace("**init**", "__init__")
    return strip_prose(code)


SEE_SOLUTION_BLOCK = re.compile(
    r"\n?See Solution\nProblem [\d.]+ - .+?: Solution\n(?:Python|JavaScript|Java)\n?"
)


def clean_description(text: str) -> str:
    return SEE_SOLUTION_BLOCK.sub("\n\n", text).strip()


FORCE_BREAK = re.compile(r"(?<!\n)\n(?=(?:Example\s*\d+|Constraints:))")
EXAMPLE_HEADER = re.compile(r"^Example\s*\d+", re.I)
CONSTRAINTS_HEADER = re.compile(r"^Constraints:?\s*$", re.I)
CONSTRAINT_LIKE = re.compile(r"[≤≥<>]=?|\^|10\^")


def structure_description(desc: str) -> dict:
    """Splits the free-form description into an intro, a list of examples,
    a bullet list of constraints, and the remaining approach/explanation
    prose, so the site can render each part with appropriate formatting."""
    desc = FORCE_BREAK.sub("\n\n", desc.strip())
    paragraphs = re.split(r"\n{2,}", desc)

    intro, examples, constraints, approach = [], [], [], []
    mode = "intro"
    for p in paragraphs:
        p = p.strip()
        if not p:
            continue
        first_line = p.split("\n", 1)[0].strip()

        if EXAMPLE_HEADER.match(first_line):
            mode = "examples"
            examples.append(p)
            continue

        if CONSTRAINTS_HEADER.match(first_line) or first_line.lower().startswith("constraints:"):
            mode = "constraints"
            rest = p.split("\n", 1)[1] if "\n" in p else ""
            constraints.extend(l.strip() for l in rest.split("\n") if l.strip())
            continue

        if mode == "intro":
            intro.append(p)
        elif mode == "examples":
            examples.append(p)
        elif mode == "constraints":
            if CONSTRAINT_LIKE.search(p) and len(p) < 160:
                constraints.extend(l.strip() for l in p.split("\n") if l.strip())
            else:
                mode = "approach"
                approach.append(p)
        else:
            approach.append(p)

    return {
        "intro": "\n\n".join(intro),
        "examples": examples,
        "constraints": constraints,
        "approach": "\n\n".join(approach),
    }


def has_flattened_indentation(code: str) -> bool:
    """Heuristic: a line ending in ':' (python block opener) immediately
    followed by a line with no extra indentation signals stripped indentation."""
    lines = code.split("\n")
    for i, line in enumerate(lines[:-1]):
        stripped = line.rstrip()
        if not stripped.endswith(":") or stripped.lstrip().startswith("#"):
            continue
        indent = len(line) - len(line.lstrip(" "))
        nxt = lines[i + 1]
        if nxt.strip() == "":
            continue
        nxt_indent = len(nxt) - len(nxt.lstrip(" "))
        if nxt_indent <= indent:
            return True
    return False


BARE_CALL = re.compile(r"^[a-zA-Z_]\w*\(\)$")


def reindent_python(code: str, indent_size: int = 2) -> str:
    """Reconstructs indentation for simple, flattened python test harnesses.
    Lines inside an open bracket ([{ are passed through at a fixed indent,
    since Python's grammar doesn't care about whitespace there; only lines
    outside brackets need a real indentation level, tracked via a depth
    counter that increases after a line ending in ':' and resets to 0 for a
    bare top-level call (e.g. "run_tests()") following a blank line."""

    def net_brackets(s):
        return sum(s.count(c) for c in "([{") - sum(s.count(c) for c in ")]}")

    out = []
    depth = 0
    bracket_depth = 0
    prev_blank = False
    for raw in code.split("\n"):
        stripped = raw.strip()
        if not stripped:
            out.append("")
            prev_blank = True
            continue

        if bracket_depth > 0:
            out.append(" " * (indent_size * (depth + 1)) + stripped)
            bracket_depth = max(bracket_depth + net_brackets(stripped), 0)
            prev_blank = False
            continue

        this_depth = depth
        if prev_blank and (BARE_CALL.match(stripped) or stripped.startswith(("def ", "class "))):
            depth = 0
            this_depth = 0

        out.append(" " * (indent_size * this_depth) + stripped)
        bracket_depth = max(bracket_depth + net_brackets(stripped), 0)
        if bracket_depth == 0 and stripped.endswith(":"):
            depth += 1
        prev_blank = False

    return "\n".join(out)


FALLBACK_CODE_START = {
    "python": re.compile(r"^(?:def|class) \w+", re.M),
    "javascript": re.compile(r"^(?:function \w+\(|class \w+)", re.M),
    "java": re.compile(r"^(?:public )?class \w+", re.M),
}


def find_end(lang, start_pos, text):
    pattern = {"python": PY_END, "javascript": JS_END, "java": JAVA_END}[lang]
    m = pattern.search(text, start_pos)
    if not m:
        return len(text)
    return m.end()


def has_flattened_braces(code: str) -> bool:
    """Heuristic: a line ending in '{' immediately followed by a line with no
    extra indentation signals stripped indentation (same artifact as Python)."""
    lines = code.split("\n")
    for i, line in enumerate(lines[:-1]):
        stripped = line.rstrip()
        if not stripped.endswith("{"):
            continue
        indent = len(line) - len(line.lstrip(" "))
        nxt = lines[i + 1]
        if nxt.strip() == "":
            continue
        nxt_indent = len(nxt) - len(nxt.lstrip(" "))
        if nxt_indent <= indent:
            return True
    return False


def reindent_braces(code: str, indent_size: int = 2) -> str:
    """Reconstructs indentation for brace-delimited code (JS/Java) using net
    brace balance per line, since nesting is unambiguous from braces alone."""
    out = []
    depth = 0
    for raw_line in code.split("\n"):
        stripped = raw_line.strip()
        if not stripped:
            out.append("")
            continue
        this_depth = depth - 1 if stripped.startswith("}") else depth
        this_depth = max(this_depth, 0)
        out.append(" " * (indent_size * this_depth) + stripped)
        depth += stripped.count("{") - stripped.count("}")
        depth = max(depth, 0)
    return "\n".join(out)


TSA_RE = re.compile(r"Time & Space Analysis\b")
TESTS_INTRO_RE = re.compile(r"\nTests\s*\nHere are some test cases to verify the solution:\s*\n?")


def extract_complexity(region: str) -> str:
    m_tsa = TSA_RE.search(region)
    if not m_tsa:
        return ""
    m_tests = TESTS_INTRO_RE.search(region, m_tsa.start())
    end = m_tests.start() if m_tests else len(region)
    return region[m_tsa.start():end].strip()


TESTS_INTRO_SENTENCE = re.compile(r"\n?Here are some test cases to verify the solution:\s*")
HELPER_START = {
    "python": re.compile(r"^[ \t]*def (?!run_tests\b)\w+\(", re.M),
    "javascript": re.compile(r"^[ \t]*function (?!run_?[tT]ests\b)\w+\(", re.M),
    "java": re.compile(r"^[ \t]*(?:private|public) \w.*\bis[A-Z]\w*\(|^[ \t]*class Test\w+\s*\{", re.M),
}


def extract_stranded_helper(region: str, lang: str) -> tuple:
    """A validation helper (e.g. is_valid_partition) sometimes sits between
    the complexity write-up and the test-start anchor, getting discarded as
    if it were prose. Recover it so it isn't silently missing from the tests."""
    m_tsa = TSA_RE.search(region)
    if not m_tsa:
        return region, ""
    search_from = m_tsa.end()
    helper_re = HELPER_START[lang]
    m_helper = helper_re.search(region, search_from)
    if not m_helper:
        return region, ""
    helper_code = strip_prose(TESTS_INTRO_SENTENCE.sub("", region[m_helper.start():]))
    return region[: m_helper.start()], helper_code


PRE_CODE_START = re.compile(r"^(class |def |function |public class |abstract class |interface )")


def preamble_code(segment: str) -> str:
    """Helper types (e.g. Node) are shown before the implementation cue, outside
    the solution region. Recover top-level class/function blocks from there."""
    out, in_code = [], False
    for line in segment.split("\n"):
        if not in_code:
            if PRE_CODE_START.match(line):
                in_code = True
                out.append(line)
            continue
        if not line.strip() or line[0] in " \t" or line.startswith(("}", ")", "]")):
            out.append(line)
        elif PRE_CODE_START.match(line):
            out.append(line)
        else:
            in_code = False
    return "\n".join(out).strip()


JS_CHUNK_START = re.compile(
    r"(function\b|class\b|const\b|let\b|var\b|async\b|export\b|import\b|//|/\*|\(function|new\b|"
    r"[A-Za-z_$][\w$.]*\([^\n]*\);?\s*$)"
)


def parses(code: str, lang: str) -> bool:
    if lang == "python":
        try:
            compile(code, "<chunk>", "exec")
        except (SyntaxError, ValueError):
            return False
        return any(l.strip() and not l.strip().startswith("#") for l in code.split("\n"))
    if lang == "javascript":
        if not JS_CHUNK_START.match(code.lstrip()):
            return False
        with tempfile.NamedTemporaryFile("w", suffix=".js", delete=False) as f:
            f.write(code)
        try:
            return subprocess.run(["node", "--check", f.name], capture_output=True).returncode == 0
        finally:
            os.unlink(f.name)
    return True


CONTINUATION = re.compile(r"^(else\b|elif\b|except\b|finally\b|[})\]])")


def top_level_chunks(code: str) -> list:
    chunks, cur = [], []
    for line in code.split("\n"):
        starts_new = line.strip() and line[0] not in " \t" and not CONTINUATION.match(line)
        if starts_new and cur and not cur[-1].startswith("@"):
            chunks.append("\n".join(cur).rstrip())
            cur = []
        cur.append(line)
    if cur:
        chunks.append("\n".join(cur).rstrip())
    return [c for c in chunks if c.strip()]


STRAY_CALL = re.compile(r"^(?!run_?[tT]ests\b)[A-Za-z_]\w*\([^\n]*\);?[ \t]*$", re.M)


def drop_invalid_chunks(code: str, lang: str) -> str:
    """Pseudocode and stray prose pasted among the code are removed by keeping
    only the top-level chunks that parse on their own."""
    if lang == "java" or not code.strip():
        return code
    if parses(code, lang) and not STRAY_CALL.search(code):
        return code
    kept = [
        c for c in top_level_chunks(code)
        if parses(c, lang) and not STRAY_CALL.fullmatch(c.strip())
    ]
    return "\n\n".join(kept)


OPENER = re.compile(r"^(\s+)(def|class) \w.*:\s*$")


def fix_overindented_openers(code: str) -> str:
    """A def/class line pasted with extra indentation while its body is at that
    same indentation: dedent the opener so the body is nested under it."""
    lines = code.split("\n")
    for i, line in enumerate(lines):
        m = OPENER.match(line)
        if not m:
            continue
        nxt = next((l for l in lines[i + 1:] if l.strip()), None)
        if nxt is None:
            continue
        opener_indent = len(m.group(1))
        nxt_indent = len(nxt) - len(nxt.lstrip(" "))
        if nxt_indent <= opener_indent:
            lines[i] = " " * max(nxt_indent - 2, 0) + line.lstrip(" ")
    return "\n".join(lines)


def parse_file(path: Path):
    text = path.read_text(encoding="utf-8")
    text = rejoin_broken_strings(text)

    anchors = []
    for lang, pattern in LANG_START.items():
        m = pattern.search(text)
        if m:
            anchors.append((m.start(), lang))
    anchors.sort(key=lambda x: x[0])

    issues = []
    if len(anchors) < 3:
        found = [a[1] for a in anchors]
        issues.append(f"only found languages: {found} (expected python, javascript, java)")

    sections = {}
    complexity = ""
    prev_end = None
    first_impl_start = None
    for idx, (start_pos, lang) in enumerate(anchors):
        end_pos = find_end(lang, start_pos, text)
        if idx == 0:
            # find where this language's implementation code begins
            marker = SEE_SOLUTION_BLOCK.search(text)
            search_from = marker.end() if marker and marker.end() < start_pos else 0
            cue_m = CUE.search(text, search_from, start_pos)
            if cue_m:
                impl_start = cue_m.end()
                description = text[: cue_m.start()].strip()
            else:
                fb = FALLBACK_CODE_START[lang]
                last = fb.search(text, search_from, start_pos)
                impl_start = last.start() if last else 0
                if last is None:
                    issues.append(f"could not locate start of {lang} implementation (no cue, no fallback match)")
                description = text[:impl_start].strip()
            first_impl_start = impl_start
            description = clean_description(description)
            region = text[impl_start:start_pos]
            region, helper_code = extract_stranded_helper(region, lang)
            complexity = extract_complexity(region)
            impl = clean_impl(region)
            if not impl and helper_code:
                impl, helper_code = clean_impl(helper_code), ""
            pre_code = clean_impl(preamble_code(text[search_from:impl_start]))
            if pre_code and pre_code not in impl:
                impl = pre_code + "\n\n" + impl
        else:
            region = text[prev_end:start_pos]
            region, helper_code = extract_stranded_helper(region, lang)
            impl = clean_impl(region)
            if not impl and helper_code:
                impl, helper_code = clean_impl(helper_code), ""
        tests = strip_prose(textwrap.dedent(text[start_pos:end_pos].lstrip("\n")))
        if lang == "javascript":
            tests = tests.replace("run_tests", "runTests")

        if lang == "python":
            if has_flattened_indentation(tests):
                tests = reindent_python(tests)
            if helper_code and has_flattened_indentation(helper_code):
                helper_code = reindent_python(helper_code)
        if lang in ("javascript", "java"):
            if has_flattened_braces(impl):
                impl = reindent_braces(impl)
            if has_flattened_braces(tests):
                tests = reindent_braces(tests)
            if helper_code and has_flattened_braces(helper_code):
                helper_code = reindent_braces(helper_code)

        if helper_code:
            tests = helper_code + "\n\n" + tests
        if lang == "python":
            impl = fix_overindented_openers(impl)
            tests = fix_overindented_openers(tests)
        impl = drop_invalid_chunks(impl, lang)
        tests = drop_invalid_chunks(tests, lang)
        if not impl.strip() and lang in ("python", "javascript"):
            # the solution was parsed as a test helper; split it back out
            parts = top_level_chunks(tests)
            is_test = lambda c: re.match(r"(def run_tests|function run_?[tT]ests|run_?[tT]ests\()", c)
            impl = "\n\n".join(c for c in parts if not is_test(c))
            tests = "\n\n".join(c for c in parts if is_test(c))
        sections[lang] = {"impl": impl, "tests": tests}
        prev_end = end_pos

    if not anchors:
        description = text.strip()

    return {
        "description": description if anchors else text.strip(),
        "complexity": complexity,
        "sections": sections,
        "issues": issues,
        "raw": text,
    }


def slugify(name: str) -> str:
    name = name.strip().lower()
    name = re.sub(r"[^a-z0-9]+", "-", name)
    return name.strip("-")


def topic_slug(dirname: str) -> str:
    name = re.sub(r"^\d+\.\s*", "", dirname)
    return slugify(name)


def snake(text: str) -> str:
    return re.sub(r"[^a-z0-9]+", "_", text.replace("'", "").lower()).strip("_")


def pascal(text: str) -> str:
    words = re.findall(r"[A-Za-z0-9]+", text.replace("'", ""))
    return "".join(w[:1].upper() + w[1:] for w in words)


JAVA_IMPORTS = "import java.util.*;\nimport java.util.function.*;\n"


PY_IMPORTS = [
    (r"\bdeque\b", "from collections import deque"),
    (r"\bdefaultdict\b", "from collections import defaultdict"),
    (r"\bCounter\b", "from collections import Counter"),
    (r"\bOrderedDict\b", "from collections import OrderedDict"),
    (r"\bheapq\.", "import heapq"),
    (r"\bmath\.", "import math"),
    (r"\bbisect\.", "import bisect"),
    (r"\brandom\.", "import random"),
    (r"\bitertools\.", "import itertools"),
    (r"\blru_cache\b", "from functools import lru_cache"),
    (r"\bcmp_to_key\b", "from functools import cmp_to_key"),
]
PY_FROM_IMPORTS = {
    "heapq": ["heappush", "heappop", "heapify", "heapreplace", "heappushpop", "nlargest", "nsmallest"],
    "math": ["sqrt", "gcd", "ceil", "floor", "factorial", "comb", "isqrt"],
    "bisect": ["bisect_left", "bisect_right", "insort"],
}


def python_imports(body: str) -> str:
    found = [stmt for pat, stmt in PY_IMPORTS if re.search(pat, body) and stmt not in body]
    for module, names in PY_FROM_IMPORTS.items():
        used = [
            n for n in names
            if re.search(r"(?<![.\w])%s\(" % n, body) and not re.search(r"def %s\b" % n, body)
        ]
        if used:
            found.append(f"from {module} import {', '.join(used)}")
    return "\n".join(found) + "\n\n" if found else ""


JS_DEQUE = """class DequeNode {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

class Deque {
  constructor() {
    this.head = null;
    this.tail = null;
    this._size = 0;
  }

  empty() {
    return !this.head;
  }

  size() {
    return this._size;
  }

  peekFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.head.val;
  }

  peekBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.tail.val;
  }

  pushBack(val) {
    const newNode = new DequeNode(val);
    if (this.tail) {
      this.tail.next = newNode;
      newNode.prev = this.tail;
    }
    this.tail = newNode;
    if (!this.head) {
      this.head = newNode;
    }
    this._size++;
  }

  pushFront(val) {
    const newNode = new DequeNode(val);
    if (this.head) {
      this.head.prev = newNode;
      newNode.next = this.head;
    }
    this.head = newNode;
    if (!this.tail) {
      this.tail = newNode;
    }
    this._size++;
  }

  popBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.tail.val;
    this.tail = this.tail.prev;
    if (this.tail) {
      this.tail.next = null;
    } else {
      this.head = null;
    }
    this._size--;
    return val;
  }

  popFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.head.val;
    this.head = this.head.next;
    if (this.head) {
      this.head.prev = null;
    } else {
      this.tail = null;
    }
    this._size--;
    return val;
  }
}

"""


def render_code_file(lang, num, title, stem, sect):
    """Builds the runnable source file (solution then tests) and its name."""
    body = sect["impl"] + "\n\n\n" + sect["tests"] + "\n"
    if lang == "python":
        name = f"{stem}_{snake(title)}.py"
        header = f"# {num} - {title}\n# Run: python3 {name}\n\n" + python_imports(body)
        return header + body, name
    if lang == "javascript":
        name = f"{stem}_{snake(title)}.js"
        header = f"// {num} - {title}\n// Run: node {name}\n\n"
        if re.search(r"new Deque\(", body) and not re.search(r"class Deque\b", body):
            header += "// JS has no built-in deque, so a linked-list one is included.\n" + JS_DEQUE
        return header + body, name
    cls = f"P{stem}_{pascal(title)}"
    name = f"{cls}.java"
    if re.search(r"public\s+class\s+Solution\b", body):
        body = re.sub(r"\bSolution\b", cls, body)
    # only the file-named class may be public
    body = re.sub(r"public\s+class\s+(?!%s\b)" % re.escape(cls), "class ", body)
    header = f"// {num} - {title}\n// Run: javac {name} && java {cls}\n\n{JAVA_IMPORTS}\n"
    return header + body, name


IMAGE_RE = re.compile(r"!\[[^\]]*\]\((image[^)]*\.png)\)")


def main():
    report_lines = []
    manifest = {"topics": []}

    out_java = ROOT / "java"
    out_js = ROOT / "javascript"
    out_py = ROOT / "python"
    out_docs_data = ROOT / "docs" / "data"
    for p in (out_java, out_js, out_py, out_docs_data):
        if p.exists():
            shutil.rmtree(p)
        p.mkdir(parents=True, exist_ok=True)

    all_impls = {}  # (lang) -> list of (problem_id, code) to check duplicates

    for topic_dir in TOPIC_DIRS:
        tdir = ROOT / topic_dir
        if not tdir.exists():
            continue
        tslug = topic_slug(topic_dir)
        topic_title = re.sub(r"^\d+\.\s*", "", topic_dir)

        md_files = sorted(
            [p for p in tdir.glob("*.md")],
            key=lambda p: [int(x) if x.isdigit() else x for x in re.split(r"(\d+)", p.stem)],
        )

        (out_docs_data / tslug / "images").mkdir(parents=True, exist_ok=True)

        topic_manifest = {"slug": tslug, "title": topic_title, "problems": []}

        for md in md_files:
            problem_num_title = md.stem  # e.g. "4.8 - Matrix Operations"
            m = re.match(r"^([\d.]+)\s*-\s*(.+)$", problem_num_title)
            num = m.group(1) if m else ""
            title = m.group(2) if m else problem_num_title
            pslug = slugify(title)

            parsed = parse_file(md)
            if parsed["issues"]:
                report_lines.append(f"[{topic_dir}/{md.name}] " + "; ".join(parsed["issues"]))

            for lang, data in parsed["sections"].items():
                code = data["impl"]
                key = (topic_dir, md.name)
                all_impls.setdefault(lang, []).append((key, code))

            # copy referenced images
            imgs = IMAGE_RE.findall(parsed["raw"])
            for img in imgs:
                src = tdir / img
                if src.exists():
                    dst = out_docs_data / tslug / "images" / img
                    if not dst.exists():
                        shutil.copy2(src, dst)

            struct = structure_description(parsed["description"])

            # one runnable file per problem and language: solution + tests
            topic_folder = f"{int(topic_dir.split('.', 1)[0]):02d}_{snake(topic_title)}"
            idx = num.split(".")[1].zfill(2) if "." in num else "00"
            stem = f"{topic_folder.split('_', 1)[0]}_{idx}"
            for lang, outdir in (("python", out_py), ("javascript", out_js), ("java", out_java)):
                sect = parsed["sections"].get(lang)
                if not sect:
                    continue
                folder = outdir / topic_folder
                folder.mkdir(parents=True, exist_ok=True)
                content, fname = render_code_file(lang, num, title, stem, sect)
                (folder / fname).write_text(content, encoding="utf-8")

            problem_json = {
                "num": num,
                "title": title,
                "slug": pslug,
                "description": struct,
                "complexity": parsed["complexity"],
                "solutions": {
                    lang: parsed["sections"][lang]["impl"]
                    for lang in ("python", "javascript", "java")
                    if lang in parsed["sections"]
                },
                "tests": {
                    lang: parsed["sections"][lang]["tests"]
                    for lang in ("python", "javascript", "java")
                    if lang in parsed["sections"]
                },
            }
            (out_docs_data / tslug / f"{pslug}.json").write_text(
                json.dumps(problem_json, indent=2), encoding="utf-8"
            )
            topic_manifest["problems"].append({"num": num, "title": title, "slug": pslug})

        manifest["topics"].append(topic_manifest)

    (out_docs_data / "topics.json").write_text(json.dumps(manifest, indent=2), encoding="utf-8")

    # duplicate-code detection (possible copy-paste mistakes)
    for lang, items in all_impls.items():
        seen = {}
        for key, code in items:
            norm = re.sub(r"\s+", " ", code).strip()
            if not norm:
                continue
            if norm in seen and seen[norm] != key:
                report_lines.append(
                    f"DUPLICATE {lang} code between {seen[norm]} and {key}"
                )
            else:
                seen[norm] = key

    report_path = ROOT / "tools" / "report.txt"
    report_path.write_text("\n".join(report_lines) if report_lines else "No issues found.", encoding="utf-8")
    print(f"Wrote {len(report_lines)} issue(s) to {report_path}")
    for line in report_lines:
        print(" -", line)


if __name__ == "__main__":
    main()
