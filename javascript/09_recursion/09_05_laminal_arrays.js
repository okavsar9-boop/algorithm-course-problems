// 9.5 - Laminal Arrays
// Run: node 09_05_laminal_arrays.js

function maxLaminalSumInefficient(arr) {
  // O(n log n)

  // Returns the max sum for a laminal array in arr[l:r].
  function maxLaminalSumRec(l, r) {
    if (r - l === 1) {
      return arr[l];
    }
    const mid = Math.floor((l + r) / 2);
    const option1 = maxLaminalSumRec(l, mid);
    const option2 = maxLaminalSumRec(mid, r);
    const option3 = arr.slice(l, r).reduce((sum, val) => sum + val, 0);
    return Math.max(option1, option2, option3);
  }

  return maxLaminalSumRec(0, arr.length);
}

function maxLaminalSum(arr) {
  // O(n)

  // Returns the max sum for a laminal array in arr[l:r] and the sum of arr[l:r].
  function maxLaminalSumRec(l, r) {
    if (r - l === 1) {
      return [arr[l], arr[l]];
    }
    const mid = Math.floor((l + r) / 2);
    const [option1, leftSum] = maxLaminalSumRec(l, mid);
    const [option2, rightSum] = maxLaminalSumRec(mid, r);
    const option3 = leftSum + rightSum;
    return [Math.max(option1, option2, option3), option3];
  }

  const [res, _] = maxLaminalSumRec(0, arr.length);
  return res;
}


function runTests() {
  const tests = [
    // Example 1 from book
    [[3, -9, 2, 4, -1, 5, 5, -4], 6],
    // Example 2 from book
    [[1], 1],
    // Example 3 from book
    [[-1, -2], -1],
    // Additional test case
    [[1, 2, 3, 4], 10],
    // Additional test case with all negatives
    [[-2, -1, -4, -3], -1],
    // Large test case
    [[1, -2, 3, -4, 5, -6, 7, -8, 9, -10, 11, -12, 13, -14, 15, -16], 15],
  ];
  for (const [arr, want] of tests) {
    const got = maxLaminalSum(arr);
    if (got !== want) {
      throw new Error(
        `\nmaxLaminalSum(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
    const gotInefficient = maxLaminalSumInefficient(arr);
    if (gotInefficient !== want) {
      throw new Error(
        `\nmaxLaminalSumInefficient(${JSON.stringify(arr)}): got: ${gotInefficient}, want: ${want}\n`,
      );
    }
  }
}

runTests();
