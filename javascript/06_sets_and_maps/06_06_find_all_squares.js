// 6.6 - Find All Squares
// Run: node 06_06_find_all_squares.js

function findSquared(arr) {
  const numToIndex = new Map();
  for (let i = 0; i < arr.length; i++) {
    numToIndex.set(arr[i], i);
  }

  const res = [];
  for (let i = 0; i < arr.length; i++) {
    const square = arr[i] ** 2;
    if (numToIndex.has(square)) {
      res.push([i, numToIndex.get(square)]);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example
    [
      [4, 10, 3, 100, 5, 2, 10000],
      [
        [5, 0],
        [1, 3],
        [3, 6],
      ],
    ],
    // Additional test cases
    [[], []],
    [[1], [[0, 0]]],
    [[2, 4], [[0, 1]]],
  ];

  for (const [arr, want] of tests) {
    const got = findSquared(arr);
    // Sort both lists to compare them regardless of order
    got.sort((a, b) => a[0] - b[0] || a[1] - b[1]);
    want.sort((a, b) => a[0] - b[0] || a[1] - b[1]);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nfindSquared(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
