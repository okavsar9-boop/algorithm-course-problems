// 5.2 - CCTV Footage
// Run: node 05_02_cctv_footage.js

function findBike(t1, t2, isStolen) {

  function isBefore(t) {
    return !isStolen(t);
  }

  let l = t1,
    r = t2;
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
    // Example 1 - stolen at t=5
    [1, 10, (t) => t >= 5, 5],
    // Example 2 - stolen at start
    [1, 5, (t) => t >= 2, 2],
    // Example 3 - stolen at end
    [1, 5, (t) => t >= 5, 5],
    // Edge case - two timestamps
    [5, 6, (t) => t >= 6, 6],
  ];

  for (const [t1, t2, isStolen, want] of tests) {
    const got = findBike(t1, t2, isStolen);
    if (got !== want) {
      throw new Error(`\nfindBike(${t1}, ${t2}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
