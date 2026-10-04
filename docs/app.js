const state = {
  topics: null,
  currentTopicSlug: null,
  currentProblem: null,
  currentLang: localStorage.getItem("lastLang") || "python",
};

const LANGS = [
  { key: "python", label: "Python" },
  { key: "javascript", label: "JavaScript" },
  { key: "java", label: "Java" },
];

const topicListEl = document.getElementById("topic-list");
const placeholderEl = document.getElementById("placeholder");
const problemViewEl = document.getElementById("problem-view");
const titleEl = document.getElementById("problem-title");
const descriptionEl = document.getElementById("problem-description");
const solutionToggleEl = document.getElementById("solution-toggle");
const solutionPanelEl = document.getElementById("solution-panel");
const langSelectEl = document.getElementById("lang-select");
const codeContentEl = document.getElementById("code-content");
const complexityEl = document.getElementById("complexity-box");
const testsSectionEl = document.getElementById("tests-section");
const testsContentEl = document.getElementById("tests-content");
const sidebarEl = document.getElementById("sidebar");
const menuToggleEl = document.getElementById("menu-toggle");
const sidebarCollapseEl = document.getElementById("sidebar-collapse");
const sidebarExpandEl = document.getElementById("sidebar-expand");

async function init() {
  const res = await fetch("data/topics.json");
  state.topics = await res.json();
  renderTopicList();

  // open the first topic and select its first problem by default
  const firstTopic = state.topics.topics[0];
  if (firstTopic) {
    const firstTopicEl = topicListEl.querySelector(".topic");
    if (firstTopicEl) firstTopicEl.classList.add("open");
    const firstProblem = firstTopic.problems[0];
    if (firstProblem) {
      const firstBtn = topicListEl.querySelector(
        `.problem-item[data-topic="${firstTopic.slug}"][data-problem="${firstProblem.slug}"]`,
      );
      selectProblem(firstTopic.slug, firstProblem.slug, firstBtn);
    }
  }
}

function renderTopicList() {
  topicListEl.innerHTML = "";
  state.topics.topics.forEach((topic, idx) => {
    const topicEl = document.createElement("div");
    topicEl.className = "topic";

    const header = document.createElement("div");
    header.className = "topic-header";
    header.innerHTML = `<span>${idx + 1}. ${topic.title}</span><span class="arrow">&#9656;</span>`;
    header.addEventListener("click", () => {
      topicEl.classList.toggle("open");
    });

    const list = document.createElement("div");
    list.className = "problem-list";

    for (const problem of topic.problems) {
      const btn = document.createElement("button");
      btn.className = "problem-item";
      btn.textContent = `${problem.num} ${problem.title}`;
      btn.dataset.topic = topic.slug;
      btn.dataset.problem = problem.slug;
      btn.addEventListener("click", () => selectProblem(topic.slug, problem.slug, btn));
      list.appendChild(btn);
    }

    topicEl.appendChild(header);
    topicEl.appendChild(list);
    topicListEl.appendChild(topicEl);
  });
}

async function selectProblem(topicSlug, problemSlug, btnEl) {
  // lazy-fetch only this problem's data, nothing upfront
  const res = await fetch(`data/${topicSlug}/${problemSlug}.json`);
  const problem = await res.json();

  state.currentTopicSlug = topicSlug;
  state.currentProblem = problem;

  document.querySelectorAll(".problem-item.active").forEach((el) => el.classList.remove("active"));
  if (btnEl) btnEl.classList.add("active");

  placeholderEl.hidden = true;
  problemViewEl.hidden = false;

  titleEl.textContent = `${problem.num} - ${problem.title}`;
  descriptionEl.innerHTML = renderDescription(problem.description, topicSlug);

  solutionPanelEl.hidden = true;
  solutionToggleEl.classList.remove("expanded");
  solutionToggleEl.querySelector("span").textContent = "Show Solution";
  renderLangTabs();
  renderComplexity();
  renderCode();

  if (window.innerWidth <= 820) {
    sidebarEl.classList.remove("open");
  }
}

function renderDescription(desc, topicSlug) {
  const imageRe = /!\[[^\]]*\]\((image[^)]*\.png)\)/g;
  const parts = [];

  if (desc.intro) {
    parts.push(renderProseBlock(desc.intro, topicSlug, imageRe));
  }

  if (desc.examples && desc.examples.length) {
    for (const ex of desc.examples) {
      parts.push(`<pre class="example-block">${escapeHtml(ex)}</pre>`);
    }
  }

  if (desc.constraints && desc.constraints.length) {
    const items = desc.constraints.map((c) => `<li>${escapeHtml(c)}</li>`).join("");
    parts.push(`<div class="constraints"><strong>Constraints</strong><ul>${items}</ul></div>`);
  }

  if (desc.approach) {
    parts.push(renderProseBlock(desc.approach, topicSlug, imageRe));
  }

  return parts.join("");
}

function renderProseBlock(text, topicSlug, imageRe) {
  const blocks = text.split(/\n{2,}/);
  return blocks
    .map((block) => {
      const trimmed = block.trim();
      if (!trimmed) return "";
      if (imageRe.test(trimmed)) {
        imageRe.lastIndex = 0;
        return trimmed.replace(imageRe, (_, file) => `<img src="data/${topicSlug}/images/${file}" alt="diagram" />`);
      }

      const lines = trimmed.split("\n").map((l) => l.trim()).filter(Boolean);
      const isList = lines.length > 1 && lines.every((l) => l.length < 160);
      if (isList) {
        const items = lines.map((l) => `<li>${formatInline(l)}</li>`).join("");
        return `<ul>${items}</ul>`;
      }
      return `<p>${formatInline(trimmed)}</p>`;
    })
    .join("");
}

function formatInline(text) {
  let s = escapeHtml(text);
  // bold method/function-call style tokens, e.g. append(x), pop_back()
  s = s.replace(/\b([a-zA-Z_][a-zA-Z0-9_]*\([^()]*\))/g, "<strong>$1</strong>");
  return s;
}

function escapeHtml(s) {
  return s
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;");
}

function renderLangTabs() {
  const available = LANGS.filter((l) => state.currentProblem.solutions[l.key]);
  if (!available.find((l) => l.key === state.currentLang)) {
    state.currentLang = available[0]?.key || "python";
  }
  langSelectEl.innerHTML = available
    .map((lang) => `<option value="${lang.key}">${lang.label}</option>`)
    .join("");
  langSelectEl.value = state.currentLang;
}

langSelectEl.addEventListener("change", () => {
  state.currentLang = langSelectEl.value;
  localStorage.setItem("lastLang", state.currentLang);
  renderCode();
});

function renderCode() {
  const code = state.currentProblem.solutions[state.currentLang] || "";
  setCodeBlock(codeContentEl, code, state.currentLang);
  renderTests();
}

function renderTests() {
  const tests = (state.currentProblem.tests || {})[state.currentLang] || "";
  testsSectionEl.hidden = !tests;
  if (!tests) return;
  setCodeBlock(testsContentEl, tests, state.currentLang);
}

function setCodeBlock(el, code, lang) {
  const prismLang = lang === "javascript" ? "javascript" : lang;
  el.className = `language-${prismLang}`;
  el.textContent = code;
  if (window.Prism) {
    Prism.highlightElement(el);
  }
}

function renderComplexity() {
  const text = state.currentProblem.complexity || "";
  if (!text) {
    complexityEl.hidden = true;
    return;
  }
  complexityEl.hidden = false;
  const lines = text.split("\n");
  const heading = lines[0];
  const rest = lines.slice(1).join("\n").trim();
  const items = rest
    .split(/\n{2,}/)
    .map((p) => p.trim())
    .filter(Boolean)
    .flatMap((paragraph) => paragraph.split("\n").map((l) => l.trim()).filter(Boolean))
    .map((p) => `<li>${formatComplexityLine(p)}</li>`)
    .join("");
  complexityEl.innerHTML = `<strong class="complexity-heading">${escapeHtml(heading)}</strong><ul>${items}</ul>`;
}

function formatComplexityLine(line) {
  // bold a leading label like "Time:", "Extra space:", "n:" before the colon
  const m = line.match(/^([A-Za-z][A-Za-z0-9 ]{0,20}):\s*(.*)$/s);
  if (m) {
    return `<strong>${escapeHtml(m[1])}:</strong> ${formatInline(m[2])}`;
  }
  return formatInline(line);
}

solutionToggleEl.addEventListener("click", () => {
  const show = solutionPanelEl.hidden;
  solutionPanelEl.hidden = !show;
  solutionToggleEl.classList.toggle("expanded", show);
  solutionToggleEl.querySelector("span").textContent = show ? "Hide Solution" : "Show Solution";
});

document.querySelectorAll(".copy-btn").forEach((btn) => {
  btn.addEventListener("click", async () => {
    const target = document.getElementById(btn.dataset.copyTarget);
    await navigator.clipboard.writeText(target.textContent);
    const original = btn.textContent;
    btn.textContent = "Copied!";
    setTimeout(() => (btn.textContent = original), 1200);
  });
});

menuToggleEl.addEventListener("click", () => {
  sidebarEl.classList.toggle("open");
});

sidebarCollapseEl.addEventListener("click", () => {
  document.body.classList.add("sidebar-collapsed");
  sidebarExpandEl.hidden = false;
});

sidebarExpandEl.addEventListener("click", () => {
  document.body.classList.remove("sidebar-collapsed");
  sidebarExpandEl.hidden = true;
});


init();

