// 19.7 - Longest Subarray With Sum K
// Run: node 19_07_longest_subarray_with_sum_k.js

function longestSubarrayWithSumK(arr, k) {
  const prefixSum = new Array(arr.length).fill(0);
  prefixSum[0] = arr[0];
  for (let i = 1; i < arr.length; i++) {
    prefixSum[i] = prefixSum[i - 1] + arr[i];
  }

  const prefixSumToIndex = new Map([[0, -1]]); // For the empty prefix.
  let res = -1;
  for (let r = 0; r < prefixSum.length; r++) {
    const val = prefixSum[r];
    if (prefixSumToIndex.has(val - k)) {
      const l = prefixSumToIndex.get(val - k);
      res = Math.max(res, r - l);
    }
    if (!prefixSumToIndex.has(val)) {
      prefixSumToIndex.set(val, r);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [[1, 2, 3, 2, 1], 3, 2],
    [[-1, -2, -3, 2, 1], -3, 5],
    // Edge case: All zeros
    [[0, 0, 0], 0, 3],
    // Edge case: No subarray with sum k
    [[1, 2, 3], 10, -1],
  ];

  for (const [arr, k, want] of tests) {
    const got = longestSubarrayWithSumK(arr, k);
    if (got !== want) {
      throw new Error(
        `\nlongestSubarrayWithSumK(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
