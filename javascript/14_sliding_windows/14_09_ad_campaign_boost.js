// 14.9 - Ad Campaign Boost
// Run: node 14_09_ad_campaign_boost.js

function maxConsecutiveGoodDays(projectedSales, k) {
  let l = 0,
    r = 0;
  let windowBadDays = 0;
  let curMax = 0;
  while (r < projectedSales.length) {
    const canGrow = projectedSales[r] >= 10 || windowBadDays < k;
    if (canGrow) {
      if (projectedSales[r] < 10) {
        windowBadDays++;
      }
      r++;
      curMax = Math.max(curMax, r - l);
    } else {
      if (projectedSales[l] < 10) {
        windowBadDays--;
      }
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[5, 0, 20, 0, 5], 2, 3],
    // Example 2 from the book
    [[0, 10, 0, 10], 1, 3],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k=0
    [[5, 10, 5], 0, 1],
    // Edge case - k=len(projectedSales)
    [[5, 5, 5], 3, 3],
  ];
  for (const [projectedSales, k, want] of tests) {
    const got = maxConsecutiveGoodDays(projectedSales, k);
    if (got !== want) {
      throw new Error(
        `\nmaxConsecutiveGoodDays(${JSON.stringify(projectedSales)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
