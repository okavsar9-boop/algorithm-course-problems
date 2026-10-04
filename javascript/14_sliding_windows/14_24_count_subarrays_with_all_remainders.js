// 14.24 - Count Subarrays With All Remainders
// Run: node 14_24_count_subarrays_with_all_remainders.js

function countAll3Groups(arr) {
  const n = arr.length;
  const totalCount = (n * (n + 1)) / 2;
  return totalCount - countAtMost2Groups(arr);
}

function countAtMost2Groups(arr) {
  let l = 0,
    r = 0;
  const windowCounts = new Map();
  let count = 0;
  while (r < arr.length) {
    const canGrow = windowCounts.has(arr[r] % 3) || windowCounts.size < 2;
    if (canGrow) {
      windowCounts.set(arr[r] % 3, (windowCounts.get(arr[r] % 3) || 0) + 1);
      r++;
      count += r - l;
    } else {
      windowCounts.set(arr[l] % 3, windowCounts.get(arr[l] % 3) - 1);
      if (windowCounts.get(arr[l] % 3) === 0) {
        windowCounts.delete(arr[l] % 3);
      }
      l++;
    }
  }
  return count;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[9, 8, 7], 1],
    // Example 2 from the book
    [[1, 2, 3, 4, 5], 6],
    // Example 3 from the book
    [[1, 3, 4, 6, 7, 9], 0],
    // Edge case - empty array
    [[], 0],
    // Edge case - single element
    [[3], 0],
  ];
  for (const [arr, want] of tests) {
    const got = countAll3Groups(arr);
    if (got !== want) {
      throw new Error(
        `\ncountAll3Groups(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
