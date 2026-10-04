// 14.25 - Count Good Subarrays With at Least k Sales
// Run: node 14_25_count_good_subarrays_with_at_least_k_sales.js

function countGoodSubarraysWithAtLeastKSales(sales, k) {
  // First find maximal subarrays without bad days
  const goodSubarrays = [];
  let start = 0;
  for (let i = 0; i < sales.length; i++) {
    if (sales[i] < 10) {
      if (i > start) {
        goodSubarrays.push(sales.slice(start, i));
      }
      start = i + 1;
    }
  }
  if (start < sales.length) {
    goodSubarrays.push(sales.slice(start));
  }

  // Then count subarrays with at least k total sales in each good subarray
  let total = 0;
  for (const sub of goodSubarrays) {
    total += countAtLeastKTotalSales(sub, k);
  }
  return total;
}

function countAtLeastKTotalSales(arr, k) {
  const n = arr.length;
  const totalSubarrays = (n * (n + 1)) / 2;
  if (k === 0) {
    return totalSubarrays;
  }
  return totalSubarrays - countAtMostKTotalSales(arr, k - 1);
}

function countAtMostKTotalSales(arr, k) {
  let l = 0,
    r = 0;
  let windowSum = 0;
  let count = 0;
  while (r < arr.length) {
    windowSum += arr[r];
    r++;
    while (l < r && windowSum > k) {
      windowSum -= arr[l];
      l++;
    }
    count += r - l;
  }
  return count;
}


function runTests() {
  const tests = [
    // Example with mix of good and bad days
    [[15, 20, 5, 30, 25], 50, 1],
    // Edge case - empty array
    [[], 10, 0],
    // Edge case - all good days
    [[10, 20, 30], 40, 2],
    // Edge case - all bad days
    [[0, 5, 8], 10, 0],
    // Edge case - k = 0
    [[10, 20, 5, 30], 0, 4],
  ];
  for (const [sales, k, want] of tests) {
    const got = countGoodSubarraysWithAtLeastKSales(sales, k);
    if (got !== want) {
      throw new Error(
        `\ncountGoodSubarraysWithAtLeastKSales(${JSON.stringify(
          sales,
        )}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
