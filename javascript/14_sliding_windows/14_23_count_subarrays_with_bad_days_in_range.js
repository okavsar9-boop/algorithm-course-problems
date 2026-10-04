// 14.23 - Count Subarrays With Bad Days in Range
// Run: node 14_23_count_subarrays_with_bad_days_in_range.js

function countBadDaysRange(sales, k1, k2) {
  if (k1 === 0) {
    return countAtMostKBadDays(sales, k2);
  }
  return countAtMostKBadDays(sales, k2) - countAtMostKBadDays(sales, k1 - 1);
}

function countAtMostKBadDays(sales, k) {
  let l = 0,
    r = 0;
  let windowBadDays = 0;
  let count = 0;
  while (r < sales.length) {
    const canGrow = sales[r] >= 10 || windowBadDays < k;
    if (canGrow) {
      if (sales[r] < 10) {
        windowBadDays++;
      }
      r++;
      count += r - l;
    } else {
      if (sales[l] < 10) {
        windowBadDays--;
      }
      l++;
    }
  }
  return count;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[0, 20, 5], 2, 2, 1],
    // Example 2 from the book
    [[0, 20, 5], 1, 2, 5],
    // Edge case - empty array
    [[], 1, 2, 0],
    // Edge case - k1 = k2 = 0
    [[0, 20, 5], 0, 0, 1],
    // Edge case - all good days
    [[10, 20, 30], 1, 2, 0],
  ];
  for (const [sales, k1, k2, want] of tests) {
    const got = countBadDaysRange(sales, k1, k2);
    if (got !== want) {
      throw new Error(
        `\ncountBadDaysRange(${JSON.stringify(
          sales,
        )}, ${k1}, ${k2}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
