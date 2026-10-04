// 14.17 - Strong Start and Ending
// Run: node 14_17_strong_start_and_ending.js

function maxGoodDaysStartAndEndTwoPointers(projectedSales, k) {
  const n = projectedSales.length;

  // Count total bad days
  const B = projectedSales.filter((x) => x < 10).length;
  if (B <= k) {
    return n;
  }

  // General case: there are more than k bad days

  let suffixPtr = n; // suffix pointer
  let suffixBad = 0; // bad days in suffix

  for (let i = n - 1; i >= 0; i--) {
    if (projectedSales[i] < 10) {
      if (suffixBad === k) {
        suffixPtr = i + 1;
        break;
      } else {
        suffixBad++;
      }
    }
  }

  // Initial result: empty prefix + best suffix found
  let res = n - suffixPtr;

  let prefixBad = 0; // bad days in prefix
  for (let prefixPtr = 0; prefixPtr < n; prefixPtr++) {
    if (projectedSales[prefixPtr] < 10) {
      prefixBad++;
    }

    // Shrink suffix to maintain constraint:
    // total bad days in prefix + suffix <= k
    while (suffixPtr < n && prefixBad + suffixBad > k) {
      if (projectedSales[suffixPtr] < 10) {
        suffixBad--;
      }
      suffixPtr++;
    }

    if (prefixBad > k) {
      break;
    }

    // Calculate combined length
    const prefixLength = prefixPtr + 1;
    const suffixLength = n - suffixPtr;
    res = Math.max(res, prefixLength + suffixLength);
  }

  return res;
}

function maxGoodDaysStartAndEndSlidingWindow(projectedSales, k) {
  const n = projectedSales.length;

  // Count total bad days
  const B = projectedSales.filter((x) => x < 10).length;
  if (B <= k) {
    return n;
  }

  const targetBad = B - k;

  // Find minimum window containing target_bad bad days
  let l = 0,
    r = 0;
  let windowBad = 0;
  let minWindow = Infinity;

  while (true) {
    const mustGrow = windowBad < targetBad;
    if (mustGrow) {
      if (r === projectedSales.length) {
        break;
      }
      if (projectedSales[r] < 10) {
        windowBad++;
      }
      r++;
    } else {
      if (r - l < minWindow) {
        minWindow = r - l;
      }
      if (projectedSales[l] < 10) {
        windowBad--;
      }
      l++;
    }
  }

  // If we can't find a window with target_bad bad days
  if (minWindow === Infinity) {
    return projectedSales.length;
  }

  // Return length of prefix + suffix of good days
  return projectedSales.length - minWindow;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[10, 0, 0, 0, 10, 0, 0, 10], 2, 5],
    // Example 2 from the book
    [[0, 10, 0, 10], 1, 3],
    // Example 3
    [[5, 5, 5], 2, 2],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k=0
    [[5, 10, 5], 0, 0],
    // Edge case - all good days
    [[10, 10, 10], 1, 3],
    // Edge case - all bad days
    [[5, 5, 5], 2, 2],
    // Edge case - k >= number of bad days
    [[5, 10, 5, 10], 3, 4],
  ];

  for (const [projectedSales, k, want] of tests) {
    const gotTwoPointers = maxGoodDaysStartAndEndTwoPointers(projectedSales, k);
    if (gotTwoPointers !== want) {
      throw new Error(
        `\nmaxGoodDaysStartAndEndTwoPointers(${JSON.stringify(projectedSales)}, ${k}): got: ${gotTwoPointers}, want: ${want}\n`,
      );
    }

    const gotSlidingWindow = maxGoodDaysStartAndEndSlidingWindow(
      projectedSales,
      k,
    );
    if (gotSlidingWindow !== want) {
      throw new Error(
        `\nmaxGoodDaysStartAndEndSlidingWindow(${JSON.stringify(projectedSales)}, ${k}): got: ${gotSlidingWindow}, want: ${want}\n`,
      );
    }
  }
}

runTests();
