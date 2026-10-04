// 19.6 - Count Subarrays With Sum K
// Run: node 19_06_count_subarrays_with_sum_k.js

function countSubarrays(arr, k) {
  const prefixSum = new Array(arr.length).fill(0);
  prefixSum[0] = arr[0];
  for (let i = 1; i < arr.length; i++) {
    prefixSum[i] = prefixSum[i - 1] + arr[i];
  }

  const prefixSumToCount = new Map([[0, 1]]); // For the empty prefix.
  let count = 0;
  for (const val of prefixSum) {
    if (prefixSumToCount.has(val - k)) {
      count += prefixSumToCount.get(val - k);
    }
    prefixSumToCount.set(val, (prefixSumToCount.get(val) || 0) + 1);
  }
  return count;
}


function runTests() {
  const tests = [
    // Example from the book
    [[1, 2, 3, 2, 1], 3, 3],
    [[-1, -2, -3, 2, 1], -3, 4],
    // Edge case: All zeros
    [[0, 0, 0], 0, 6],
    // Edge case: No subarray with sum k
    [[1, 2, 3], 10, 0],
  ];

  for (const [arr, k, want] of tests) {
    const got = countSubarrays(arr, k);
    if (got !== want) {
      throw new Error(
        `\ncountSubarrays(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
