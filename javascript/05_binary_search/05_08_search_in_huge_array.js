// 5.8 - Search In Huge Array
// Run: node 05_08_search_in_huge_array.js

function findThroughApi(target, fetch) {

  function isBefore(idx) {
    return fetch(idx) !== -1 && fetch(idx) < target;
  }

  let l = 0;
  if (!isBefore(l)) {
    if (fetch(l) === target) {
      return l;
    }
    return -1;
  }

  // Step 1: Get the rightmost boundary
  let r = 1;
  while (isBefore(r)) {
    r *= 2;
  }

  // Step 2: Binary search
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  if (fetch(r) === target) {
    return r;
  }
  return -1;
}


function runTests() {

  function makeFetchFunction(secretArray) {
    return function fetch(idx) {
      if (idx >= secretArray.length || idx < 0) {
        return -1;
      }
      return secretArray[idx];
    };
  }

  const tests = [
    // Example 1 - target exists
    [5, 2, [1, 3, 5, 7, 9]],
    // Example 2 - target doesn't exist
    [6, -1, [1, 3, 5, 7, 9]],
    // Edge case - target at start
    [1, 0, [1, 3, 5, 7, 9]],
    // Edge case - target at end
    [9, 4, [1, 3, 5, 7, 9]],
    // All duplicates
    [1, 0, [1, 1, 1, 1, 1, 1, 1, 1]],
    // Ensure we don't go out of bounds
    [10, 9, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]],
  ];

  for (const [target, want, secretArray] of tests) {
    const fetch = makeFetchFunction(secretArray);
    const got = findThroughApi(target, fetch);
    if (got !== want) {
      throw new Error(`findThroughApi(${target}): got ${got}, want ${want}`);
    }
  }
}

runTests();
