// 3.9 - Sort Valley-Shaped Array
// Run: node 03_09_sort_valley_shaped_array.js

function sortValleyArray(arr) {
  if (arr.length === 0) {
    return [];
  }
  let l = 0,
  r = arr.length - 1;
  const res = new Array(arr.length).fill(0);
  let i = arr.length - 1;
  while (l < r) {
    if (arr[l] >= arr[r]) {
      res[i] = arr[l];
      l++;
      i--;
    } else {
      res[i] = arr[r];
      r--;
      i--;
    }
  }
  res[0] = arr[l];
  return res;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [
  [8, 4, 2, 6],
  [2, 4, 6, 8],
  ],
  // Example 2 from the book
  [
  [1, 2],
  [1, 2],
  ],
  // Example 3 from the book
  [
  [2, 2, 1, 1],
  [1, 1, 2, 2],
  ],
  // Additional test cases
  [[], []],
  [[1], [1]],
  [
  [3, 2, 1, 4],
  [1, 2, 3, 4],
  ],
  [
  [5, 4, 3, 2, 1, 2, 3],
  [1, 2, 2, 3, 3, 4, 5],
  ],
  [
  [1, 1, 1, 1],
  [1, 1, 1, 1],
  ],
  ];
  for (const [arr, want] of tests) {
    const got = sortValleyArray(arr);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
      `\nsortValleyArray(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
