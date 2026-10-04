// 17.4 - Minimum Triplet Medians
// Run: node 17_04_minimum_triplet_medians.js

function minimizeMiddleSum(arr) {
  arr.sort((a, b) => a - b);
  let middleSum = 0;
  for (let i = 0; i < Math.floor(arr.length / 3); i++) {
    middleSum += arr[i * 2 + 1];
  }
  return middleSum;
}


function runTests() {
  const tests = [
    // Example 1
    [[6, 5, 8, 2, 1, 9], 8],
    // Example 2
    [[6, 5, 8, 2, 1, 9, 12, 15, 14], 17],

    // Additional test cases
    // Edge case: Single triplet
    [[1, 2, 3], 2],
    // Test with 6 elements
    [[10, 20, 60, 30, 40, 50], 60],
    // Test with 9 elements
    [[1, 3, 5, 7, 9, 11, 13, 15, 17], 21],
    // Test with 12 elements
    [[2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24], 40],
    // Test with 15 elements
    [[10, 11, 12, 13, 14, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9], 30],
    // Test with 18 elements
    [
      [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90],
      210,
    ],
  ];

  for (const [arr, want] of tests) {
    const input = [...arr];
    const got = minimizeMiddleSum(input);
    if (got !== want) {
      throw new Error(
        `\nminimizeMiddleSum(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
