// 14.18 - Count Subarrays With at Most k Bad Days
// Run: node 14_18_count_subarrays_with_at_most_k_bad_days.js

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
    // Example from the book
    [[0, 20, 5], 1, 5],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k = 0
    [[0, 20, 5], 0, 1],
    // Edge case - all good days
    [[10, 20, 30], 1, 6],
    // Edge case - all bad days
    [[0, 5, 8], 2, 5],
  ];
  for (const [sales, k, want] of tests) {
    const got = countAtMostKBadDays(sales, k);
    if (got !== want) {
      throw new Error(
        `\ncountAtMostKBadDays(${JSON.stringify(sales)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
