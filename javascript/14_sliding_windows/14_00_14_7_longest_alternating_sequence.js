//  - 14.7 Longest Alternating Sequence
// Run: node 14_00_14_7_longest_alternating_sequence.js

function longestAlternatingSequence(sales) {
  let l = 0,
    r = 0;
  let curMax = 0;
  while (r < sales.length) {
    const canGrow = l === r || sales[r - 1] >= 10 !== sales[r] >= 10;
    if (canGrow) {
      r++;
      curMax = Math.max(curMax, r - l);
    } else {
      l = r;
      r++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[8, 9, 20, 0, 9], 3],
    // Example 2 from the book
    [[0, 0, 0], 1],
    // Edge case - empty array
    [[], 0],
    // Edge case - single element
    [[10], 1],
    // perfect alternation
    [[5, 10, 5, 10], 4],
    // all good days
    [[10, 11, 12], 1],
  ];
  for (const [sales, want] of tests) {
    const got = longestAlternatingSequence(sales);
    if (got !== want) {
      throw new Error(
        `\nlongestAlternatingSequence(${JSON.stringify(sales)}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
