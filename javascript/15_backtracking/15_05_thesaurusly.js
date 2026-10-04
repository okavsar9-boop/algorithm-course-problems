// 15.5 - Thesaurusly
// Run: node 15_05_thesaurusly.js

function generateSentences(sentence, synonyms) {
  const words = sentence.split(" ");
  const res = [];
  const curSentence = [];

  function visit(i) {
    if (i === words.length) {
      res.push(curSentence.join(" "));
      return;
    }

    const choices = synonyms[words[i]] || [words[i]];

    for (const choice of choices) {
      curSentence.push(choice);
      visit(i + 1);
      curSentence.pop(); // Undo change.
    }
  }

  visit(0);
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      "one does not simply walk into mordor",
      {
        walk: ["stroll", "hike", "wander"],
        simply: ["just", "merely"],
      },
      [
        "one does not just stroll into mordor",
        "one does not just hike into mordor",
        "one does not just wander into mordor",
        "one does not merely stroll into mordor",
        "one does not merely hike into mordor",
        "one does not merely wander into mordor",
      ],
    ],
    // Edge case - no synonyms
    ["hello world", {}, ["hello world"]],
    // Single word with synonyms
    ["walk", { walk: ["stroll", "hike"] }, ["stroll", "hike"]],
    // Multiple words, some with synonyms
    [
      "I walk to the park",
      { walk: ["stroll", "hike"] },
      ["I stroll to the park", "I hike to the park"],
    ],
  ];
  for (const [sentence, synonyms, want] of tests) {
    const got = generateSentences(sentence, synonyms);
    got.sort();
    want.sort();
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ngenerateSentences(${sentence}, ${JSON.stringify(synonyms)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
