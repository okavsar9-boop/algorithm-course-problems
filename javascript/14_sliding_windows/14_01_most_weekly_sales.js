// 14.1 - Most Weekly Sales
// Run: node 14_01_most_weekly_sales.js

function mostWeeklySales(sales) {
  let l = 0,
    r = 0;
  let windowSum = 0;
  let curMax = 0;
  while (r < sales.length) {
    windowSum += sales[r];
    r++;
    if (r - l === 7) {
      curMax = Math.max(curMax, windowSum);
      windowSum -= sales[l];
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[0, 3, 7, 12, 10, 5, 0, 1, 0, 15, 12, 11, 1], 44],
    // Example 2 from the book
    [[0, 3, 7, 12], 0],
    // Edge case - empty array
    [[], 0],
    // Edge case - exactly 7 days
    [[1, 2, 3, 4, 5, 6, 7], 28],
    // Edge case - all zeros
    [[0, 0, 0, 0, 0, 0, 0, 0], 0],
  ];
  for (const [sales, want] of tests) {
    const got = mostWeeklySales(sales);
    if (got !== want) {
      throw new Error(
        `\nmostWeeklySales(${JSON.stringify(
          sales,
        )}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
