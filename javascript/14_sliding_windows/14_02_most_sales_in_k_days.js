// 14.2 - Most Sales in K Days
// Run: node 14_02_most_sales_in_k_days.js

function mostSalesInKDays(sales, k) {
  let l = 0,
    r = 0;
  let windowSum = 0;
  let curMax = 0;
  let bestStart = 0;
  while (r < sales.length) {
    windowSum += sales[r];
    r++;
    if (r - l === k) {
      if (windowSum > curMax) {
        curMax = windowSum;
        bestStart = l;
      }
      windowSum -= sales[l];
      l++;
    }
  }
  return bestStart;
}


function runTests() {
  const tests = [
    // Example from the book
    [[8, 1, 3, 7], 2, 2],
    // Edge case - k=1
    [[5, 10, 15, 5], 1, 2],
    // Edge case - k=len(sales)
    [[1, 2, 3], 3, 0],
    // Edge case - multiple valid answers, return first
    [[10, 5, 10], 2, 0],
  ];
  for (const [sales, k, want] of tests) {
    const got = mostSalesInKDays(sales, k);
    if (got !== want) {
      throw new Error(
        `\nmostSalesInKDays(${JSON.stringify(sales)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
