// 3.4 - Palindromic Sentence
// Run: node 03_04_palindromic_sentence.js

function palindromicSentence(s) {
  let l = 0,
  r = s.length - 1;
  while (l < r) {
    if (!/[a-zA-Z]/.test(s[l])) {
      l++;
    } else if (!/[a-zA-Z]/.test(s[r])) {
      r--;
    } else {
      if (s[l].toLowerCase() !== s[r].toLowerCase()) {
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
  // Example from the book
  ["Bob wondered, 'Now, Bob?'", true],
  // Additional test cases
  ["", true],
  ["a", true],
  ["A man, a plan, a canal: Panama", true],
  ["race a car", false],
  ["Was it a car or a cat I saw?", true],
  ["hello", false],
  [".,?!'", true],
  ];
  for (const [s, want] of tests) {
    const got = palindromicSentence(s);
    if (got !== want) {
      throw new Error(
      `\npalindromicSentence(${s}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
