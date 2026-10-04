// 6.7 - Word Expansion Class
// Run: node 06_07_word_expansion_class.js

class Checker {
  constructor(s) {
    this.s = s;
  }

  expandsInto(s2) {
    if (s2.length !== this.s.length + 1) {
      return false;
    }

    // Create frequency maps for both strings
    const freq = new Map();
    for (const c of s2) {
      freq.set(c, (freq.get(c) || 0) + 1);
    }

    for (const c of this.s) {
      if (!freq.has(c)) {
        return false;
      }
      freq.set(c, freq.get(c) - 1);
      if (freq.get(c) === 0) {
        freq.delete(c);
      }
    }

    // Should have exactly one character with frequency 1
    return freq.size === 1 && [...freq.values()][0] === 1;
  }
}


function runTests() {
  const tests = [
    // Example 1
    [
      "tea",
      [
        ["tea", false],
        ["team", true],
        ["seam", false],
      ],
    ],
    // Example 2
    [
      "on",
      [
        ["nooo", false],
        ["not", true],
        ["now", true],
      ],
    ],
    // Additional test cases
    [
      "",
      [
        ["a", true],
        ["", false],
        ["ab", false],
      ],
    ],
    [
      "xyz",
      [
        ["wxyz", true],
        ["xyzw", true],
        ["xyza", true],
        ["xyz", false],
      ],
    ],
  ];

  for (const [s, checks] of tests) {
    const checker = new Checker(s);
    for (const [s2, want] of checks) {
      const got = checker.expandsInto(s2);
      if (got !== want) {
        throw new Error(
          `\nChecker(${JSON.stringify(s)}).expandsInto(${JSON.stringify(s2)}): got: ${got}, want: ${want}\n`,
        );
      }
    }
  }
}

runTests();
