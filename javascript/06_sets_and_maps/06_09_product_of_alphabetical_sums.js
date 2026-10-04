// 6.9 - Product of Alphabetical Sums
// Run: node 06_09_product_of_alphabetical_sums.js

function alphabeticSumProduct(words, target) {

  function alphabeticalSum(word) {
    return [...word].reduce(
      (sum, c) => sum + c.charCodeAt(0) - "a".charCodeAt(0) + 1,
      0,
    );
  }
  const sums = new Set();
  for (const word of words) {
    sums.add(alphabeticalSum(word));
  }

  for (const i of sums) {
    if (target % i !== 0) {
      continue;
    }
    for (const j of sums) {
      const k = target / (i * j);
      if (sums.has(k)) {
        return true;
      }
    }
  }
  return false;
}


function runTests() {
  const tests = [
    // Example 1
    [["abc", "fg", "hij", "klm", "nop", "qrs", "vwx"], 1620, true],
    // Example 2
    [["a", "b"], 2, true],
    // Additional test cases
    [[], 1, false],
    [["a"], 1, true],
    [["a", "b", "c"], 6, true],
    [["a", "b", "c"], 7, false],
  ];

  for (const [words, target, want] of tests) {
    const got = alphabeticSumProduct(words, target);
    if (got !== want) {
      throw new Error(
        `\nalphabeticSumProduct(${JSON.stringify(words)}, ${target}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
