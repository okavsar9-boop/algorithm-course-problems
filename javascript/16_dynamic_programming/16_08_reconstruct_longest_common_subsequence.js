// 16.8 - Reconstruct Longest Common Subsequence
// Run: node 16_08_reconstruct_longest_common_subsequence.js

function lcsReconstruction(s1, s2) {
  const memo = new Map();

  function lcsRec(i1, i2) {
    if (i1 === s1.length || i2 === s2.length) {
      return "";
    }
    const key = `${i1},${i2}`;
    if (memo.has(key)) {
      return memo.get(key);
    }
    let res;
    if (s1[i1] === s2[i2]) {
      res = s1[i1] + lcsRec(i1 + 1, i2 + 1);
    } else {
      const opt1 = lcsRec(i1 + 1, i2);
      const opt2 = lcsRec(i1, i2 + 1);
      res = opt1.length >= opt2.length ? opt1 : opt2;
    }
    memo.set(key, res);
    return res;
  }

  return lcsRec(0, 0);
}

function lcsReconstructionOptimal(s1, s2) {
  const memo = new Map();

  function lcsRec(i1, i2) {
    if (i1 === s1.length || i2 === s2.length) {
      return 0;
    }
    const key = `${i1},${i2}`;
    if (memo.has(key)) {
      return memo.get(key);
    }
    if (s1[i1] === s2[i2]) {
      memo.set(key, 1 + lcsRec(i1 + 1, i2 + 1));
    } else {
      memo.set(key, Math.max(lcsRec(i1 + 1, i2), lcsRec(i1, i2 + 1)));
    }
    return memo.get(key);
  }

  let i1 = 0,
    i2 = 0;
  const res = [];
  while (i1 < s1.length && i2 < s2.length) {
    if (s1[i1] === s2[i2]) {
      res.push(s1[i1]);
      i1++;
      i2++;
    } else if (lcsRec(i1 + 1, i2) >= lcsRec(i1, i2 + 1)) {
      i1++;
    } else {
      i2++;
    }
  }
  return res.join("");
}


function runTests() {
  const tests = [
    // s1, s2, length of the LCS
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

  function isSubsequence(subseq, s) {
    let i = 0;
    for (const char of s) {
      if (i < subseq.length && char === subseq[i]) {
        i++;
      }
    }
    return i === subseq.length;
  }

  for (const [s1, s2, wantLength] of tests) {
    const got = lcsReconstructionOptimal(s1, s2);
    if (got.length !== wantLength) {
      throw new Error(
        `\nlcsReconstructionOptimal(${s1}, ${s2}): got length: ${got.length}, want length: ${wantLength}\n`,
      );
    }
    if (!isSubsequence(got, s1)) {
      throw new Error(
        `\nlcsReconstructionOptimal(${s1}, ${s2}): result '${got}' is not a subsequence of '${s1}'\n`,
      );
    }
    if (!isSubsequence(got, s2)) {
      throw new Error(
        `\nlcsReconstructionOptimal(${s1}, ${s2}): result '${got}' is not a subsequence of '${s2}'\n`,
      );
    }

    const got2 = lcsReconstruction(s1, s2);
    if (got2.length !== wantLength) {
      throw new Error(
        `\nlcsReconstruction(${s1}, ${s2}): got length: ${got2.length}, want length: ${wantLength}\n`,
      );
    }
    if (!isSubsequence(got2, s1)) {
      throw new Error(
        `\nlcsReconstruction(${s1}, ${s2}): result '${got2}' is not a subsequence of '${s1}'\n`,
      );
    }
    if (!isSubsequence(got2, s2)) {
      throw new Error(
        `\nlcsReconstruction(${s1}, ${s2}): result '${got2}' is not a subsequence of '${s2}'\n`,
      );
    }
  }
}

runTests();
