// 15.4 - To Be or Not to Be
// Run: node 15_04_to_be_or_not_to_be.js

function shakespearify(sentence) {
  const words = sentence ? sentence.split(" ") : [];
  const res = [];
  const currentSentence = [];

  function visit(i) {
    if (i === words.length) {
      res.push(currentSentence.join(" "));
      return;
    }
    // Choice 1: include the word
    currentSentence.push(words[i]);
    visit(i + 1);
    currentSentence.pop(); // Cleanup work: undo choice 1
    // Choice 2: exclude the word
    visit(i + 1);
  }

  visit(0);
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      "I love dogs",
      ["", "I", "love", "dogs", "I love", "I dogs", "love dogs", "I love dogs"],
    ],
    // Edge case - empty sentence
    ["", [""]],
    // Single word
    ["hello", ["", "hello"]],
    // Two words
    ["hello world", ["", "hello", "world", "hello world"]],
  ];
  for (const [sentence, want] of tests) {
    const got = shakespearify(sentence);
    got.sort();
    want.sort();
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nshakespearify(${JSON.stringify(sentence)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
