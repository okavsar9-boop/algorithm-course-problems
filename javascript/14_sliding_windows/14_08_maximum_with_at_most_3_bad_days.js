// 14.8 - Maximum With at Most 3 Bad Days
// Run: node 14_08_maximum_with_at_most_3_bad_days.js

function maxAtMost3BadDays(sales) {
  let l = 0,
    r = 0;
  let windowBadDays = 0;
  let curMax = 0;
  while (r < sales.length) {
    const canGrow = sales[r] >= 10 || windowBadDays < 3;
    if (canGrow) {
      if (sales[r] < 10) {
        windowBadDays++;
      }
      r++;
      curMax = Math.max(curMax, r - l);
    } else {
      if (sales[l] < 10) {
        windowBadDays--;
      }
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example from the book
    [[0, 14, 7, 9, 0, 20, 10, 0, 10], 6],
    // Edge case - empty array
    [[], 0],
    // Edge case - single element
    [[5], 1],
    // all good days
    [[10, 11, 12], 3],
    // all bad days
    [[1, 2, 3], 3],
    // exactly 3 bad days
    [[5, 10, 5, 10, 5], 5],
    // More than 3 bad days
    [[5, 10, 5, 5, 10, 5], 5],
  ];
  for (const [sales, want] of tests) {
    const got = maxAtMost3BadDays(sales);
    if (got !== want) {
      throw new Error(
        `\nmaxAtMost3BadDays(${JSON.stringify(
          sales,
        )}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
