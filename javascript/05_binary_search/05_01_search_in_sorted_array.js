// 5.1 - Search In Sorted Array
// Run: node 05_01_search_in_sorted_array.js

function searchInSortedArray(arr, target) {
  let l = 0,
    r = arr.length - 1;
  while (l <= r) {
    let mid = Math.floor((l + r) / 2);
    if (arr[mid] === target) {
      return mid;
    } else if (arr[mid] < target) {
      l = mid + 1;
    } else {
      r = mid - 1;
    }
  }
  return -1;
}

function searchInSortedArrayWithTransitionPoint(arr, target) {
  if (!arr.length) {
    return -1;
  }

  function isBefore(i) {
    return arr[i] < target;
  }

  // Handle edge cases to ensure l is in the before region
  // and r is in the after region
  let l = 0,
    r = arr.length - 1;
  if (!isBefore(l)) {
    if (arr[l] === target) {
      return l;
    }
    return -1;
  }
  if (isBefore(r)) {
    return -1;
  }

  // Main binary search loop
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


function runTests() {
  const tests = [
    // Example 1 from book
    [[-2, 0, 3, 4, 7, 9, 11], 3, 2],
    // Example 2 from book
    [[-2, 0, 3, 4, 7, 9, 11], 2, -1],
    // Edge case - empty array
    [[], 5, -1],
    // Edge case - target at start
    [[1, 2, 3], 1, 0],
    // Edge case - target at end
    [[1, 2, 3], 3, 2],
    // Edge case - single element
    [[5], 5, 0],
    // Edge case - not found
    [[1, 3, 5], 2, -1],
  ];

  for (const [arr, target, want] of tests) {
    let got = searchInSortedArray(arr, target);
    if (got !== want) {
      throw new Error(
        `\nsearchInSortedArray(${JSON.stringify(arr)}, ${target}): got: ${got}, want: ${want}\n`,
      );
    }
    got = searchInSortedArrayWithTransitionPoint(arr, target);
    if (got !== want) {
      throw new Error(
        `\nsearchInSortedArrayWithTransitionPoint(${JSON.stringify(arr)}, ${target}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
