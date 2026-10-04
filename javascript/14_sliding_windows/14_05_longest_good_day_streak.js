// 14.5 - Longest Good Day Streak
// Run: node 14_05_longest_good_day_streak.js

function maxNoBadDays(sales) {
  let l = 0,
    r = 0;
  let curMax = 0;
  while (r < sales.length) {
    const canGrow = sales[r] >= 10;
    if (canGrow) {
      r++;
      curMax = Math.max(curMax, r - l);
    } else {
      l = r + 1;
      r = r + 1;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example from the book
    [[0, 14, 7, 12, 10, 20], 3],
    // Edge case - empty array
    [[], 0],
    // Edge case - all good days
    [[10, 11, 12], 3],
    // Edge case - all bad days
    [[1, 2, 3], 0],
    // alternating
    [[10, 5, 10, 5], 1],
  ];
  for (const [sales, want] of tests) {
    const got = maxNoBadDays(sales);
    if (got !== want) {
      throw new Error(
        `\nmaxNoBadDays(${JSON.stringify(sales)}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
