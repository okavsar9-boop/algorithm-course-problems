// 7.3 - Delete Operations
// Run: node 07_03_delete_operations.js

function processOperations(nums, operations) {
  const n = nums.length;
  const deleted = new Set();
  const sortedIndices = Array.from({ length: n }, (_, i) => i);

  // Since the indices start in order and the sort is stable, we break ties by
  // smallest index, as required by the problem.
  sortedIndices.sort((i1, i2) => nums[i1] - nums[i2]);

  let smallestIdx = 0;
  for (const op of operations) {
    if (0 <= op && op < n) {
      deleted.add(op);
    } else {
      // Skip until the next non-deleted smallest index.
      while (smallestIdx < n && deleted.has(sortedIndices[smallestIdx])) {
        smallestIdx++;
      }
      if (smallestIdx < n) {
        deleted.add(sortedIndices[smallestIdx]);
        smallestIdx++;
      }
    }
  }

  const res = [];
  for (let i = 0; i < n; i++) {
    if (!deleted.has(i)) {
      res.push(nums[i]);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [[50, 30, 70, 20, 80], [2, -1, 4, -1], [50]],
    // Edge case - empty operations
    [[1, 2, 3], [], [1, 2, 3]],
    // Edge case - delete all
    [[1, 2, 3], [-1, -1, -1], []],
    // Edge case - delete all indices
    [[1, 2, 3], [0, 1, 2], []],
    // Edge case - single element
    [[1], [-1], []],
    // Edge case - duplicates
    [[5, 5, 5], [-1, -1], [5]],
    // Edge case - negative numbers
    [[-3, -2, -1], [-1, -1], [-1]],
    // Mixed operations with duplicates
    [[10, 10, 20, 20], [1, -1, -1], [20]],
    // Operations targeting same index
    [
      [1, 2, 3],
      [0, 0, 0],
      [2, 3],
    ],
    // Alternating index and min operations
    [[5, 4, 3, 2, 1], [2, -1, 0, -1], [4]],
    // Large numbers within constraints
    [[10 ** 9, -(10 ** 9), 0], [-1, -1], [10 ** 9]],
  ];

  for (const [nums, operations, want] of tests) {
    const got = processOperations(nums, operations);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nprocessOperations(${nums}, ${operations}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
