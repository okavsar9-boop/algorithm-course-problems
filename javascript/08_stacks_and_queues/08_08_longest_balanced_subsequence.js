// 8.8 - Longest Balanced Subsequence
// Run: node 08_08_longest_balanced_subsequence.js

function longestBalancedSubsequence(s) {
  const invalidIndices = new Set();
  const stack = [];
  for (let i = 0; i < s.length; i++) {
    const c = s[i];
    if (c === "(") {
      stack.push(i);
    } else if (!stack.length) {
      invalidIndices.add(i);
    } else {
      stack.pop();
    }
  }

  while (stack.length) {
    invalidIndices.add(stack.pop());
  }

  const res = [];
  for (let i = 0; i < s.length; i++) {
    if (!invalidIndices.has(i)) {
      res.push(s[i]);
    }
  }
  return res.join("");
}


function runTests() {
  const tests = [
    ["))(())(()", ["(())()"]],
    ["(()()", ["()()", "(())"]],
    ["(()(()(", ["()()", "(())"]],
    ["())(()", ["()()"]],
    ["(", [""]],
    ["", [""]],
  ];
  for (const [s, want] of tests) {
    const got = longestBalancedSubsequence(s);
    if (!want.includes(got)) {
      throw new Error(
        `\nlongestBalancedSubsequence(${s}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
