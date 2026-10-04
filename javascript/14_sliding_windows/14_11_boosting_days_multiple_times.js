// 14.11 - Boosting Days Multiple Times
// Run: node 14_11_boosting_days_multiple_times.js

function maxConsecutiveWithKBoosts(projectedSales, k) {
  let l = 0,
    r = 0;
  let usedBoosts = 0;
  let curMax = 0;
  while (r < projectedSales.length) {
    const canGrow =
      usedBoosts + Math.max(10 - projectedSales[r], 0) <= k;
    if (canGrow) {
      usedBoosts += Math.max(10 - projectedSales[r], 0);
      r++;
      curMax = Math.max(curMax, r - l);
    } else if (l === r) {
      r++;
      l++;
    } else {
      usedBoosts -= Math.max(10 - projectedSales[l], 0);
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[5, 5, 15, 0, 10], 12, 3],
    // Example 2 from the book
    [[5, 5, 15, 0, 10], 15, 4],
    // Edge case - empty array
    [[], 5, 0],
    // Edge case - k=0
    [[5, 10, 5], 0, 1],
    // all values need max boost
    [[0, 0, 0], 30, 3],
    // not enough boosts for all three days
    [[0, 0, 0], 29, 2],
  ];
  for (const [projectedSales, k, want] of tests) {
    const got = maxConsecutiveWithKBoosts(projectedSales, k);
    if (got !== want) {
      throw new Error(
        `\nmaxConsecutiveWithKBoosts(${JSON.stringify(projectedSales)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
