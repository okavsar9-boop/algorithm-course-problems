// 17.5 - Fewest Script Runs
// Run: node 17_05_fewest_script_runs.js

function minimumScriptRuns(meetings) {
  meetings.sort((a, b) => a[1] - b[1]);
  let count = 0;
  let prevEnd = -Infinity;
  for (const [l, r] of meetings) {
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
        [8, 10],
      ],
      2,
    ],
    // Example 2 - Counterexample from solution
    [
      [
        [1, 3],
        [2, 5],
        [3, 6],
        [4, 7],
        [5, 8],
        [7, 9],
      ],
      2,
    ],
    // Edge case: No meetings
    [[], 0],
    // Edge case: All meetings overlap
    [
      [
        [1, 5],
        [2, 6],
        [3, 7],
      ],
      1,
    ],
    // Edge case: Non-overlapping meetings
    [
      [
        [1, 2],
        [3, 4],
        [5, 6],
      ],
      3,
    ],
    // Edge case: Single meeting
    [[[1, 2]], 1],
  ];

  for (const [meetings, want] of tests) {
    const got = minimumScriptRuns(meetings);
    if (got !== want) {
      throw new Error(
        `\nminimumScriptRuns(${JSON.stringify(meetings)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
