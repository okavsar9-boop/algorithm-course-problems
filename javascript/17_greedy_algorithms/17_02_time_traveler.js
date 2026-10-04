// 17.2 - Time Traveler
// Run: node 17_02_time_traveler.js

function canReachGoal(jumpingPoints, k, maxAging) {
  const n = jumpingPoints.length;
  const gaps = [];
  for (let i = 1; i < n; i++) {
    gaps.push(jumpingPoints[i] - jumpingPoints[i - 1]);
  }
  gaps.sort((a, b) => a - b);
  let totalAging = 0;
  for (let i = 0; i < n - 1 - k; i++) {
    totalAging += gaps[i];
  }
  return totalAging <= maxAging;
}


function runTests() {
  const tests = [
    // Example 1
    [[2020, 2024], 0, 3, false],
    // Example 2
    [[2020, 2024], 1, 1, true],
    // Example 3
    [[1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001, 2021], 4, 45, true],

    // Additional test cases
    // Edge case: No jumps allowed, but within aging limit
    [[2000, 2001, 2002], 0, 2, true],
    // Edge case: No jumps allowed, exceeding aging limit
    [[2000, 2005, 2010], 0, 4, false],
  ];

  for (const [points, jumps, maxAging, want] of tests) {
    const got = canReachGoal(points, jumps, maxAging);
    if (got !== want) {
      throw new Error(
        `\ncanReachGoal(${JSON.stringify(points)}, ${jumps}, ${maxAging}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
