// 5.5 - Target Count Divisible By K
// Run: node 05_05_target_count_divisible_by_k.js

function targetCountDivisibleByK(arr, target, k) {
  // Returns -1 if target is not in arr, or the first index of target in arr otherwise.
  function binarySearchFirst() {
    const isBefore = (i) => arr[i] < target;

    let l = 0,
      r = arr.length - 1;
    if (arr[l] > target || arr[r] < target) {
      return -1;
    }
    if (arr[l] === target) {
      return l;
    }

    while (r - l > 1) {
      const mid = Math.floor((l + r) / 2);
      if (isBefore(mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    if (arr[r] === target) {
      return r;
    }
    return -1;
  }

  // Assumes target is in arr. Returns the last index of target in arr.
  function binarySearchLast() {
    const isBefore = (i) => arr[i] <= target;

    let l = 0,
      r = arr.length - 1;
    if (arr[r] === target) {
      return r;
    }

    while (r - l > 1) {
      const mid = Math.floor((l + r) / 2);
      if (isBefore(mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return l;
  }

  const first = binarySearchFirst();
  if (first === -1) {
    return true; // 0 is a multiple of any number.
  }
  const last = binarySearchLast();
  const count = last - first + 1;
  return count % k === 0;
}


function runTests() {
  const tests = [
    // Example 1
    [[1, 2, 2, 2, 2, 2, 2, 3], 2, 3, true],
    // Example 2
    [[1, 2, 2, 2, 2, 2, 2, 3], 2, 4, false],
    // Example 3: 0 occurrences, 0 is multiple of any number
    [[1, 2, 2, 2, 2, 2, 2, 3], 4, 3, true],
    // Example 4
    [[1, 1, 2, 2, 2], 1, 3, false],
    // single occurrence, at the start
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 1, 1, true],
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 1, 2, false],
    // single occurrence, at the end
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 19, 1, true],
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 19, 2, false],
    // single occurrence, in the middle
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 9, 1, true],
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 9, 2, false],
    // smaller than any elements
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 0, 1, true],
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 0, 2, true],
    // larger than any elements
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 20, 1, true],
    [[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 20, 2, true],
    // Edge case - every occurrence is target
    [[5, 5, 5, 5, 5], 5, 5, true],
    [[5, 5, 5, 5, 5], 5, 3, false],
  ];

  for (const [arr, target, k, want] of tests) {
    const got = targetCountDivisibleByK(arr, target, k);
    if (got !== want) {
      throw new Error(
        `\ntargetCountDivisibleByK(${JSON.stringify(arr)}, ${target}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
