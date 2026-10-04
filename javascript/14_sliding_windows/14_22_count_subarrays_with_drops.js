// 14.22 - Count Subarrays With Drops
// Run: node 14_22_count_subarrays_with_drops.js

function countAtMostKDrops(arr, k) {
  let l = 0,
    r = 0;
  let windowDrops = 0;
  let count = 0;
  while (r < arr.length) {
    const canGrow = r === 0 || arr[r] >= arr[r - 1] || windowDrops < k;
    if (canGrow) {
      if (r > 0 && arr[r] < arr[r - 1]) {
        windowDrops++;
      }
      r++;
      count += r - l;
    } else {
      if (arr[l] > arr[l + 1]) {
        windowDrops--;
      }
      l++;
    }
  }
  return count;
}

function countExactlyKDrops(k, atMostKDrops, atMostKMinus1Drops) {
  if (k === 0) {
    return atMostKDrops;
  }
  return atMostKDrops - atMostKMinus1Drops;
}

function countAtLeastKDrops(n, k, atMostKMinus1Drops) {
  const totalCount = (n * (n + 1)) / 2;
  if (k === 0) {
    return totalCount;
  }
  return totalCount - atMostKMinus1Drops;
}

function countSubarraysWithDrops(arr, k) {
  const atMostKDrops = countAtMostKDrops(arr, k);
  const atMostKMinus1Drops = k === 0 ? 0 : countAtMostKDrops(arr, k - 1);
  return [
    atMostKDrops,
    countExactlyKDrops(k, atMostKDrops, atMostKMinus1Drops),
    countAtLeastKDrops(arr.length, k, atMostKMinus1Drops),
  ];
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[1, 2, 3], 1, [6, 0, 0]],
    // Example 2 from the book
    [[3, 2, 1], 1, [5, 2, 3]],
    // Example 3
    [[5, 4, 3, 2, 1], 2, [12, 3, 6]],
    // Edge case - empty array
    [[], 1, [0, 0, 0]],
    // Edge case - single element
    [[1], 1, [1, 0, 0]],
    // Edge case - k = 0
    [[5, 3, 2, 1], 0, [4, 4, 10]],
    // Alternating
    [[6, 2, 7, 3, 8, 4, 9, 5, 10, 6], 3, [50, 8, 13]],
  ];
  for (const [arr, k, want] of tests) {
    const got = countSubarraysWithDrops(arr, k);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ncountSubarraysWithDrops(${JSON.stringify(
          arr,
        )}, ${k}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
