// 14.12 - Longest Period at Most k Distinct
// Run: node 14_12_longest_period_at_most_k_distinct.js

function maxAtMostKDistinct(bestSeller, k) {
  let l = 0,
    r = 0;
  const windowCounts = new Map();
  let curMax = 0;
  while (r < bestSeller.length) {
    const canGrow =
      windowCounts.has(bestSeller[r]) || windowCounts.size + 1 <= k;
    if (canGrow) {
      windowCounts.set(
        bestSeller[r],
        (windowCounts.get(bestSeller[r]) || 0) + 1,
      );
      r++;
      curMax = Math.max(curMax, r - l);
    } else {
      windowCounts.set(bestSeller[l], windowCounts.get(bestSeller[l]) - 1);
      if (windowCounts.get(bestSeller[l]) === 0) {
        windowCounts.delete(bestSeller[l]);
      }
      l++;
    }
  }
  return curMax;
}


function runTests() {
  const tests = [
    // Example from the book
    [["book1", "book1", "book2", "book1", "book3", "book1"], 2, 4],
    // Edge case - empty array
    [[], 1, 0],
    // Edge case - k=1
    [["book1", "book2", "book1"], 1, 1],
    // Edge case - k=len(bestSeller)
    [["book1", "book2", "book3"], 3, 3],
    // Edge case - all same book
    [["book1", "book1", "book1"], 1, 3],
  ];
  for (const [bestSeller, k, want] of tests) {
    const got = maxAtMostKDistinct(bestSeller, k);
    if (got !== want) {
      throw new Error(
        `\nmaxAtMostKDistinct(${JSON.stringify(
          bestSeller,
        )}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
