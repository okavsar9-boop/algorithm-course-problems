// 14.16 - Smallest Range With k Elements
// Run: node 14_16_smallest_range_with_k_elements.js

function smallestRangeWithKElements(arr, k) {
  arr.sort((a, b) => a - b);
  let l = 0,
    r = 0;
  let bestLow = 0,
    bestHigh = Infinity;
  while (true) {
    const mustGrow = r - l < k;
    if (mustGrow) {
      if (r === arr.length) {
        break;
      }
      r++;
    } else {
      if (arr[r - 1] - arr[l] < bestHigh - bestLow) {
        bestLow = arr[l];
        bestHigh = arr[r - 1];
      }
      l++;
    }
  }
  return [bestLow, bestHigh];
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[1, 2, 5, 7, 8], 3, [5, 8]],
    // Example 2 from the book - both [2,5] and [5,8] are valid
    [[5, 5, 2, 2, 8, 8], 3, [2, 5]],
    // Example 3 from the book
    [[0], 1, [0, 0]],
    // Edge case - k=len(arr)
    [[1, 5, 10], 3, [1, 10]],
    // Edge case - all same number
    [[5, 5, 5], 2, [5, 5]],
  ];
  for (const [arr, k, want] of tests) {
    const got = smallestRangeWithKElements([...arr], k);
    // For this problem, there might be multiple valid answers
    // We check if the range contains at least k elements and is minimal
    const count = arr.filter((x) => want[0] <= x && x <= want[1]).length;
    if (!(count >= k)) {
      throw new Error(
        `\nsmallestRangeWithKElements(${JSON.stringify(arr)}, ${k}): range ${got} contains fewer than ${k} elements\n`,
      );
    }
    if (!(got[1] - got[0] <= want[1] - want[0])) {
      throw new Error(
        `\nsmallestRangeWithKElements(${JSON.stringify(arr)}, ${k}): range ${got} is larger than ${want}\n`,
      );
    }
  }
}

runTests();
