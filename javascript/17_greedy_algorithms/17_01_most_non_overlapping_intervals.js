// 17.1 - Most Non-Overlapping Intervals
// Run: node 17_01_most_non_overlapping_intervals.js

function mostNonOverlappingIntervals(intervals) {
  intervals.sort((a, b) => a[1] - b[1]);
  let count = 0;
  let prevEnd = -Infinity;
  for (const [l, r] of intervals) {
    if (l > prevEnd) {
      count++;
      prevEnd = r;
    }
  }
  return count;
}


function runTests() {
  const tests = [
    // Example 1
    [
      [
        [2, 3],
        [1, 4],
        [2, 3],
        [3, 6],
        [8, 9],
      ],
      2,
    ],

    // Additional test cases
    // Edge case: No intervals
    [[], 0],
    // Edge case: All intervals overlap
    [
      [
        [1, 5],
        [2, 6],
        [3, 7],
      ],
      1,
    ],
    [
      [
        [1, 2],
        [2, 3],
        [3, 4],
      ],
      2,
    ],
    // Edge case: Non-overlapping intervals (considering inclusive endpoints)
    [
      [
        [1, 2],
        [3, 4],
        [5, 6],
      ],
      3,
    ],
    // Edge case: Single interval
    [[[1, 2]], 1],
  ];

  for (const [intervals, want] of tests) {
    const testIntervals = JSON.parse(JSON.stringify(intervals)); // Deep copy
    const got = mostNonOverlappingIntervals(testIntervals);
    if (got !== want) {
      throw new Error(
        `\nmostNonOverlappingIntervals(${JSON.stringify(intervals)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
