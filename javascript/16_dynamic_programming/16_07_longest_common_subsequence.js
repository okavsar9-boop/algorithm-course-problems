// 16.7 - Longest Common Subsequence
// Run: node 16_07_longest_common_subsequence.js

function longestCommonSubsequence(s1, s2) {
  const memo = new Map();

  function lcs(i1, i2) {
    if (i1 === s1.length || i2 === s2.length) {
      return 0;
    }

    const key = `${i1},${i2}`;
    if (memo.has(key)) {
      return memo.get(key);
    }

    let result;
    if (s1[i1] === s2[i2]) {
      result = 1 + lcs(i1 + 1, i2 + 1);
    } else {
      result = Math.max(lcs(i1 + 1, i2), lcs(i1, i2 + 1));
    }
    memo.set(key, result);
    return result;
  }

  return lcs(0, 0);
}


function runTests() {
  const tests = [
    ["HAHAH", "AAAAHH", 3],
    ["", "AA", 0],
    ["ABC", "BCA", 2],
    ["ABCD", "ACBAD", 3],
    ["", "", 0],
    ["ABCDEFGHIJ", "ACBDEFGHIK", 8],
    ["AAAAAAAAAAAAAAA", "AAAAAAAAAAAAA", 13],
    ["THEQUICKBROWNFOX", "THESLOWREDFOX", 8],
    ["AAAAABBBBBCCCCCDDDDD", "BBBBBCCCCCDDDDDEEEEE", 15],
  ];

  for (const [s1, s2, want] of tests) {
    const got = longestCommonSubsequence(s1, s2);
    if (got !== want) {
      throw new Error(
        `\nlongestCommonSubsequence(${s1}, ${s2}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
