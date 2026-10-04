// 15.9 - Count Unique Submultisets with Sum Zero
// Run: node 15_09_count_unique_submultisets_with_sum_zero.js

function countUniqueSubmultisetsWithSumZero(S) {

  function visit(index, currentSum) {
    if (index === uniqueElements.length) {
      return currentSum === 0 ? 1 : 0;
    }

    const element = uniqueElements[index];
    const count = frequency.get(element);
    let totalCount = 0;

    // Try all possible counts of the current element
    for (let i = 0; i <= count; i++) {
      totalCount += visit(index + 1, currentSum + i * element);
    }

    return totalCount;
  }

  const frequency = new Map();
  // Build frequency map
  for (const num of S) {
    frequency.set(num, (frequency.get(num) || 0) + 1);
  }

  // Extract unique elements
  const uniqueElements = Array.from(frequency.keys());

  return visit(0, 0);
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[1, 1, -1, -1], 3],
    // Example 2 from the book
    [[], 1],
    // Example 3 from the book
    [[-1, 2, 1, 0, 3], 4],
    // Edge case - no zero-sum submultisets
    [[1, 2, 3], 1],
    // Edge case - all zeros
    [[0, 0, 0], 4],
  ];

  for (const [S, want] of tests) {
    const got = countUniqueSubmultisetsWithSumZero(S);
    if (got !== want) {
      throw new Error(
        `\ncountUniqueSubmultisetsWithSumZero(${JSON.stringify(S)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
