// 19.4 - Balance Point
// Run: node 19_04_balance_point.js

function balancedIndex(arr) {
  let prefixSum = 0;
  let postfixSum = arr.reduce((a, b) => a + b, 0) - arr[0];

  for (let i = 0; i < arr.length; i++) {
    if (prefixSum === postfixSum) {
      return i;
    }
    prefixSum += arr[i];
    if (i + 1 < arr.length) {
      postfixSum -= arr[i + 1];
    }
  }
  return -1;
}


function runTests() {
  const tests = [
    // Example from the book
    [[3, 5, -2, 7, 2, 2, 2], 3],
    // Edge case: No balance point
    [[1, 2, 3], -1],
    // Edge case: Balance at start
    [[0, 1, -1], 0],
    // Edge case: Balance at end
    [[1, -1, 0], 2],
  ];

  for (const [arr, want] of tests) {
    const got = balancedIndex(arr);
    if (got !== want) {
      throw new Error(
        `\nbalancedIndex(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
