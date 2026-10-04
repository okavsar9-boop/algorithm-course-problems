// 9.2 - Nested Array Sum
// Run: node 09_02_nested_array_sum.js

// Lazy checking: check if the argument is a number at the start of
// each call. This handles number elements encountered during recursion.
function nestedArraySum(arr) {
  if (typeof arr === "number") {
    return arr;
  }
  let res = 0;
  for (const elem of arr) {
    res += nestedArraySum(elem);
  }
  return res;
}

// Eager checking: check if each element is a number before recursing.
// This avoids recursing on numbers entirely.
function nestedArraySumEager(arr) {
  let res = 0;
  for (const elem of arr) {
    if (typeof elem === "number") {
      res += elem;
    } else {
      res += nestedArraySumEager(elem);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example 1 from book
    [[1, [2, 3], [4, [5]], 6], 21],
    // Example 2 from book
    [[[[1]], 2], 3],
    // Example 3 from book
    [[], 0],
    // Edge case - all nested single numbers
    [[[[[[1]]]]], 1],
    // Edge case - multiple empty arrays
    [[[], [], []], 0],
    // Edge case - mixed empty and non-empty arrays
    [[[], [1, 2], [], [3]], 6],
    // Edge case - deeply nested mixed arrays
    [[1, [2, [], [3, []], []], [4, [5, []]]], 15],
    // Edge case - all zeros
    [[0, [0, 0], [0, [0]], 0], 0],
    // Edge case - negative numbers
    [[-1, [-2, 3], [4, [-5]], 6], 5],
  ];

  // Test both implementations to verify they produce the same results
  for (const [arr, want] of tests) {
    const got = nestedArraySum(arr);
    if (got !== want) {
      throw new Error(
        `\nnestedArraySum(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`
      );
    }
    const gotEager = nestedArraySumEager(arr);
    if (gotEager !== want) {
      throw new Error(
        `\nnestedArraySumEager(${JSON.stringify(arr)}): got: ${gotEager}, ` +
        `want: ${want}\n`
      );
    }
  }
}

runTests();
