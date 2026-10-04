// 3.3 - Array Intersection
// Run: node 03_03_array_intersection.js

function commonElements(arr1, arr2) {
  let p1 = 0,
  p2 = 0;
  const res = [];
  while (p1 < arr1.length && p2 < arr2.length) {
    if (arr1[p1] === arr2[p2]) {
      res.push(arr1[p1]);
      p1++;
      p2++;
    } else if (arr1[p1] < arr2[p2]) {
      p1++;
    } else {
      p2++;
    }
  }
  return res;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [
  [1, 2, 3],
  [1, 3, 5],
  [1, 3],
  ],
  // Example 2 from the book
  [
  [1, 1, 1],
  [1, 1],
  [1, 1],
  ],
  // Additional test cases
  [[], [], []],
  [[1], [], []],
  [[], [1], []],
  [[1], [1], [1]],
  [[1, 2, 3], [4, 5, 6], []],
  [
  [1, 2, 2, 3],
  [2, 2, 3],
  [2, 2, 3],
  ],
  ];
  for (const [arr1, arr2, want] of tests) {
    const got = commonElements(arr1, arr2);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
      `\ncommonElements(${JSON.stringify(arr1)}, ${JSON.stringify(arr2)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
  return true;
}

runTests();
