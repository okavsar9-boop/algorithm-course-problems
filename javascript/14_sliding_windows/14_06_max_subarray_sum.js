// 14.6 - Max Subarray Sum
// Run: node 14_06_max_subarray_sum.js

function maxSubarraySum(arr) {
  const maxVal = Math.max(...arr);
  if (maxVal <= 0) {
    // Edge case without positive values
    return maxVal;
  }

  let r = 0; // We don't need the l pointer
  let windowSum = 0;
  let curMax = 0;
  while (r < arr.length) {
    const canGrow = windowSum + arr[r] >= 0;
    if (canGrow) {
      windowSum += arr[r];
      r++;
      curMax = Math.max(curMax, windowSum);
    } else {
      windowSum = 0;
      r = r + 1;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[1, 2, 3, -2, 1], 6],
    // Example 2 from the book
    [[1, 2, 3, -2, 7], 11],
    // Example 3 from the book
    [[1, 2, 3, -8, 7], 7],
    // Example 4 from the book
    [[-2, -3, -4], -2],
    // Edge case - single element
    [[5], 5],
    // Edge case - all positive
    [[1, 2, 3], 6],
  ];
  for (const [arr, want] of tests) {
    const got = maxSubarraySum(arr);
    if (got !== want) {
      throw new Error(
        `\nmaxSubarraySum(${JSON.stringify(arr)}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
