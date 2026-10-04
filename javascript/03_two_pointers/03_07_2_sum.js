// 3.7 - 2-Sum
// Run: node 03_07_2_sum.js

function twoSum(arr) {
  let l = 0,
  r = arr.length - 1;
  while (l < r) {
    if (arr[l] + arr[r] > 0) {
      r--;
    } else if (arr[l] + arr[r] < 0) {
      l++;
    } else {
      return true;
    }
  }
  return false;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [[-5, -2, -1, 1, 1, 10], true],
  // Example 2 from the book
  [[-3, 0, 0, 1, 2], true],
  // Example 3 from the book
  [[-5, -3, -1, 0, 2, 4, 6], false],
  // Additional test cases
  [[], false],
  [[0], false],
  [[-1, 1], true],
  [[-2, -1, 0, 1], true],
  [[1, 2, 3, 4], false],
  ];
  for (const [arr, want] of tests) {
    const got = twoSum(arr);
    if (got !== want) {
      throw new Error(
      `\ntwoSum(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
