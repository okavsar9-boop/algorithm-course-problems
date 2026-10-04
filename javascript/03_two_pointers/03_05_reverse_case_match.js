// 3.5 - Reverse Case Match
// Run: node 03_05_reverse_case_match.js

function reverseCaseMatch(s) {
  let l = 0,
    r = s.length - 1;
  while (l < s.length && r >= 0) {
    if (!/[a-z]/.test(s[l])) {
      l++;
    } else if (!/[A-Z]/.test(s[r])) {
      r--;
    } else {
      if (s[l] !== s[r].toLowerCase()) {
        return false;
      }
      l++;
      r--;
    }
  }
  return true;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    ["haDrRAHd", true],
    // Example 2 from the book
    ["haHrARDd", false],
    // Additional test cases
    ["", true],
    ["aA", true],
    ["Aa", true],
    ["BbbB", true],
    ["abAB", false],
    ["abBA", true],
    ["helloworldHELLOWORLD", false],
  ];
  for (const [s, want] of tests) {
    const got = reverseCaseMatch(s);
    if (got !== want) {
      throw new Error(`\nreverseCaseMatch(${s}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
