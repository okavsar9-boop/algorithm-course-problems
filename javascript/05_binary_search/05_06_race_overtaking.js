// 5.6 - Race Overtaking
// Run: node 05_06_race_overtaking.js

function raceOvertaking(p1, p2) {

  function isBefore(i) {
    return p1[i] > p2[i];
  }

  let l = 0,
    r = p1.length - 1;
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }
  return r;
}


function runTests() {
  const tests = [
    // Example 1 from book
    [[2, 4, 6, 8, 10], [1, 3, 5, 9, 11], 3],
    // Example
    [[2, 3, 4, 5, 6], [1, 2, 3, 6, 7], 3],
    // Example
    [[3, 4, 5], [2, 5, 6], 1],
    // Edge case - overtake at start
    [[2, 3], [1, 4], 1],
  ];

  for (const [p1, p2, want] of tests) {
    const got = raceOvertaking(p1, p2);
    if (got !== want) {
      throw new Error(
        `\nraceOvertaking(${JSON.stringify(p1)}, ${JSON.stringify(p2)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
