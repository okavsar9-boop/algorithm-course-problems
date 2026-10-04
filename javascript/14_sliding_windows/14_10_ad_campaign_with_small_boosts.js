// 14.10 - Ad Campaign With Small Boosts
// Run: node 14_10_ad_campaign_with_small_boosts.js

function maxConsecutiveGoodDaysWithSmallBoost(projectedSales, k) {
  let l = 0,
    r = 0;
  let windowBetween5And9 = 0;
  let curMax = 0;
  while (r < projectedSales.length) {
    const canGrow =
      projectedSales[r] >= 10 ||
      (5 <= projectedSales[r] &&
        projectedSales[r] < 10 &&
        windowBetween5And9 < k);
    if (canGrow) {
      if (projectedSales[r] < 10) {
        windowBetween5And9++;
      }
      r++;
      curMax = Math.max(curMax, r - l);
    } else if (l === r) {
      l++;
      r++;
    } else {
      if (5 <= projectedSales[l] && projectedSales[l] < 10) {
        windowBetween5And9--;
      }
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[8, 4, 8], 3, 1],
    // Example 2 from the book
    [[10, 5, 8], 1, 2],
    [[8, 8, 8], 3, 3],
    // Example with mix of values
    [[4, 8, 12, 3, 9], 2, 2],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k=0
    [[5, 10, 5], 0, 1],
    // Edge case - all values between 5-9
    [[7, 8, 9], 3, 3],
    // Edge case - values below 5 break sequence
    [[8, 4, 8], 2, 1],
  ];
  for (const [projectedSales, k, want] of tests) {
    const got = maxConsecutiveGoodDaysWithSmallBoost(projectedSales, k);
    if (got !== want) {
      throw new Error(
        `\nmaxConsecutiveGoodDaysWithSmallBoost(${JSON.stringify(projectedSales)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
