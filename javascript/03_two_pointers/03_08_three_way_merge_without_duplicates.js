// 3.8 - Three-Way Merge Without Duplicates
// Run: node 03_08_three_way_merge_without_duplicates.js

function threeWayMerge(arr1, arr2, arr3) {
  let p1 = 0,
  p2 = 0,
  p3 = 0;
  const res = [];
  while (p1 < arr1.length || p2 < arr2.length || p3 < arr3.length) {
    // Find the smallest value among current positions
    let minVal = Infinity;
    if (p1 < arr1.length) {
      minVal = Math.min(minVal, arr1[p1]);
    }
    if (p2 < arr2.length) {
      minVal = Math.min(minVal, arr2[p2]);
    }
    if (p3 < arr3.length) {
      minVal = Math.min(minVal, arr3[p3]);
    }

    // Skip duplicates of minVal in all arrays
    if (p1 < arr1.length && arr1[p1] === minVal) {
      p1++;
    }
    if (p2 < arr2.length && arr2[p2] === minVal) {
      p2++;
    }
    if (p3 < arr3.length && arr3[p3] === minVal) {
      p3++;
    }

    // Only add if we haven't added this value before
    if (res.length === 0 || res[res.length - 1] !== minVal) {
      res.push(minVal);
    }

  }
  return res;
}


function runTests() {
  const tests = [
  // Example from the book
  [
  [2, 3, 3, 4, 5, 7],
  [3, 3, 9],
  [3, 3, 9],
  [2, 3, 4, 5, 7, 9],
  ],
  // Additional test cases
  [[], [], [], []],
  [[1], [], [], [1]],
  [[1], [1], [1], [1]],
  [
  [1, 2, 3],
  [2, 3, 4],
  [3, 4, 5],
  [1, 2, 3, 4, 5],
  ],
  [[1, 1, 1], [1, 1], [1], [1]],
  [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9],
  [1, 2, 3, 4, 5, 6, 7, 8, 9],
  ],
  ];
  for (const [arr1, arr2, arr3, want] of tests) {
    const got = threeWayMerge(arr1, arr2, arr3);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
      `\nthreeWayMerge(${JSON.stringify(arr1)}, ${JSON.stringify(arr2)}, ${JSON.stringify(arr3)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
