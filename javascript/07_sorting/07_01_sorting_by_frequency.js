// 7.1 - Sorting by Frequency
// Run: node 07_01_sorting_by_frequency.js

function letterOccurrences(word) {
  const letterToCount = new Map();
  for (const c of word) {
    letterToCount.set(c, (letterToCount.get(c) || 0) + 1);
  }

  const tuples = [];
  for (const [letter, count] of letterToCount) {
    tuples.push([letter, count]);
  }
  tuples.sort((a, b) => {
    if (a[1] !== b[1]) {
      return b[1] - a[1]; // Sort by frequency descending
    }
    return a[0] < b[0] ? -1 : 1; // Break ties by letter ascending
  });
  const res = [];
  for (const [letter, _] of tuples) {
    res.push(letter);
  }
  return res;
}

function letterOccurrencesLambda(word) {
  const letterToCount = new Map();
  const res = [];
  for (const c of word) {
    if (!letterToCount.has(c)) {
      letterToCount.set(c, 0);
      res.push(c);
    }
    letterToCount.set(c, letterToCount.get(c) + 1);
  }
  res.sort((a, b) => {
    const countA = letterToCount.get(a);
    const countB = letterToCount.get(b);
    if (countA !== countB) return countB - countA;
    return a < b ? -1 : 1;
  });
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      "supercalifragilisticexpialidocious",
      [
        "i",
        "a",
        "c",
        "l",
        "s",
        "e",
        "o",
        "p",
        "r",
        "u",
        "d",
        "f",
        "g",
        "t",
        "x",
      ],
    ],
    // Edge case - empty string
    ["", []],
    // Edge case - single character
    ["a", ["a"]],
    // Edge case - all same frequency
    ["abc", ["a", "b", "c"]],
    // Multiple frequencies with ties
    ["aabbbcccc", ["c", "b", "a"]],
    // All same character
    ["zzzzz", ["z"]],
    // Alternating characters
    ["ababab", ["a", "b"]],
    // Reverse alphabetical order but same frequency
    ["zyxwv", ["v", "w", "x", "y", "z"]],
    // Long string with many frequencies
    ["aaaaabbbbbbbcccccccccdddddddddddeeeeeeeeeeee", ["e", "d", "c", "b", "a"]],
  ];

  for (const [word, want] of tests) {
    const got1 = letterOccurrences(word);
    if (JSON.stringify(got1) !== JSON.stringify(want)) {
      throw new Error(
        `\nletterOccurrences(${word}): got: ${got1}, want: ${want}\n`,
      );
    }
    const got2 = letterOccurrencesLambda(word);
    if (JSON.stringify(got2) !== JSON.stringify(want)) {
      throw new Error(
        `\nletterOccurrencesLambda(${word}): got: ${got2}, want: ${want}\n`,
      );
    }
  }
}

runTests();
