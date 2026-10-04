// 3.11 - Interval Intersection
// Run: node 03_11_interval_intersection.js

function intersection(int1, int2) {
  const overlapStart = Math.max(int1[0], int2[0]);
  const overlapEnd = Math.min(int1[1], int2[1]);
  return [overlapStart, overlapEnd];
}

function intervalIntersection(arr1, arr2) {
  let p1 = 0,
  p2 = 0;
  const n1 = arr1.length,
  n2 = arr2.length;
  const res = [];

  while (p1 < n1 && p2 < n2) {
    const int1 = arr1[p1],
    int2 = arr2[p2];
    if (int1[1] < int2[0]) {
      p1++;
    } else if (int2[1] < int1[0]) {
      p2++;
    } else {
      res.push(intersection(int1, int2));
      if (int1[1] < int2[1]) {
        p1++;
      } else {
        p2++;
      }
    }
  }
  return res;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [
  [
  [0, 1],
  [4, 6],
  [7, 8],
  ],
  [
  [2, 3],
  [5, 9],
  [10, 11],
  ],
  [
  [5, 6],
  [7, 8],
  ],
  ],
  // Example 2 from the book
  [
  [
  [2, 4],
  [5, 8],
  ],
  [
  [3, 3],
  [4, 7],
  ],
  [
  [3, 3],
  [4, 4],
  [5, 7],
  ],
  ],
  // Additional test cases
  [[], [], []],
  [[[1, 2]], [], []],
  [[[1, 3]], [[2, 4]], [[2, 3]]],
  [[[1, 5]], [[2, 3]], [[2, 3]]],
  [
  [
  [1, 2],
  [3, 4],
  ],
  [[2, 3]],
  [
  [2, 2],
  [3, 3],
  ],
  ],
  ];

  for (const [arr1, arr2, want] of tests) {
    const got = intervalIntersection(arr1, arr2);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
      `\nintervalIntersection(${JSON.stringify(arr1)}, ${JSON.stringify(arr2)}): ` +
      `got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
