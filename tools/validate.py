#!/usr/bin/env python3
"""Runs every generated solution file (python, node, javac+java) and lists failures."""
import subprocess
import sys
import tempfile
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent


def run(cmd, cwd=None):
    try:
        r = subprocess.run(cmd, capture_output=True, text=True, timeout=30, cwd=cwd)
    except subprocess.TimeoutExpired:
        return False, "timeout"
    if r.returncode == 0:
        return True, ""
    lines = (r.stderr or r.stdout).strip().splitlines()
    return False, " | ".join(lines[-2:])[:200]


def main():
    failures = []
    total = 0
    for f in sorted((ROOT / "python").rglob("*.py")):
        total += 1
        ok, err = run([sys.executable, str(f)])
        if not ok:
            failures.append((f.relative_to(ROOT), err))
    for f in sorted((ROOT / "javascript").rglob("*.js")):
        total += 1
        ok, err = run(["node", str(f)])
        if not ok:
            failures.append((f.relative_to(ROOT), err))
    has_java = subprocess.run(["javac", "-version"], capture_output=True).returncode == 0
    if not has_java:
        print("javac not available: skipping java files")
    for f in sorted((ROOT / "java").rglob("*.java") if has_java else []):
        total += 1
        with tempfile.TemporaryDirectory() as tmp:
            ok, err = run(["javac", "-d", tmp, str(f)])
            if ok:
                ok, err = run(["java", "-cp", tmp, f.stem])
        if not ok:
            failures.append((f.relative_to(ROOT), err))
    for path, err in failures:
        print(f"FAIL {path}: {err}")
    print(f"{total - len(failures)}/{total} passed")
    return 1 if failures else 0


if __name__ == "__main__":
    sys.exit(main())
