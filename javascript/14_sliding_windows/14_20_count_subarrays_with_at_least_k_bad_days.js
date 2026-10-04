// 14.20 - Count Subarrays With at Least k Bad Days
// Run: node 14_20_count_subarrays_with_at_least_k_bad_days.js

function countAtLeastKBadDays(sales, k) {
  const n = sales.length;
  const totalSubarrays = (n * (n + 1)) / 2;
  if (k === 0) {
    return totalSubarrays;
  }
  return totalSubarrays - countAtMostKBadDays(sales, k - 1);
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
    // Example from the book
    [[0, 20, 5], 1, 5],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k = 0
    [[0, 20, 5], 0, 6],
    // all good days
    [[10, 20, 30], 1, 0],
    // all bad days
    [[0, 5, 8], 2, 3],
  ];
  for (const [sales, k, want] of tests) {
    const got = countAtLeastKBadDays(sales, k);
    if (got !== want) {
      throw new Error(
        `\ncountAtLeastKBadDays(${JSON.stringify(
          sales,
        )}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
