// 3.1 - Palindrome Check
// Run: node 03_01_palindrome_check.js

function palindrome(s) {
  let l = 0,
  r = s.length - 1;
  while (l < r) {
    if (s[l] !== s[r]) {
      return false;
    }
    l++;
    r--;
  }
  return true;
}


function runTests() {
  const tests = [
  // Example from the book
  ["level", true],
  ["naan", true],
  // Additional test cases
  ["", true],
  ["a", true],
  ["ab", false],
  ["abc", false],
  ["abba", true],
  ["abcba", true],
  ];
  for (const [s, want] of tests) {
    const got = palindrome(s);
    if (got !== want) {
      throw new Error(`\npalindrome(${s}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
