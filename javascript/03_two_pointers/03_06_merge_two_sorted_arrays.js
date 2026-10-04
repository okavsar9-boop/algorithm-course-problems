// 3.6 - Merge Two Sorted Arrays
// Run: node 03_06_merge_two_sorted_arrays.js

function merge(arr1, arr2) {
  let p1 = 0,
    p2 = 0;
  const res = [];
  while (p1 < arr1.length && p2 < arr2.length) {
    if (arr1[p1] < arr2[p2]) {
      res.push(arr1[p1]);
      p1++;
    } else {
      res.push(arr2[p2]);
      p2++;
    }
  }
  while (p1 < arr1.length) {
    res.push(arr1[p1]);
    p1++;
  }
  while (p2 < arr2.length) {
    res.push(arr2[p2]);
    p2++;
  }
  return res;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [1, 3, 4, 5],
      [2, 4, 4],
      [1, 2, 3, 4, 4, 4, 5],
    ],
    // Example 2 from the book
    [[-1], [], [-1]],
    // Additional test cases
    [[], [], []],
    [[1], [], [1]],
    [[], [1], [1]],
    [
      [1, 3, 5],
      [2, 4, 6],
      [1, 2, 3, 4, 5, 6],
    ],
    [
      [1, 1, 1],
      [1, 1, 1],
      [1, 1, 1, 1, 1, 1],
    ],
  ];
  for (const [arr1, arr2, want] of tests) {
    const got = merge(arr1, arr2);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nmerge(${JSON.stringify(arr1)}, ${JSON.stringify(arr2)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
