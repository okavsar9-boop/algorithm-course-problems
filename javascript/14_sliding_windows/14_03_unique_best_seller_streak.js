// 14.3 - Unique Best Seller Streak
// Run: node 14_03_unique_best_seller_streak.js

function hasUniqueKDays(bestSeller, k) {
  let l = 0,
    r = 0;
  const windowCounts = new Map();
  while (r < bestSeller.length) {
    windowCounts.set(bestSeller[r], (windowCounts.get(bestSeller[r]) || 0) + 1);
    r++;
    if (r - l === k) {
      if (windowCounts.size === k) {
        return true;
      }
      windowCounts.set(bestSeller[l], windowCounts.get(bestSeller[l]) - 1);
      if (windowCounts.get(bestSeller[l]) === 0) {
        windowCounts.delete(bestSeller[l]);
      }
      l++;
    }
  }
  return false;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      ["book3", "book1", "book3", "book3", "book2", "book3", "book4", "book3"],
      3,
      true,
    ],
    // Example 2 from the book
    [
      ["book3", "book1", "book3", "book3", "book2", "book3", "book4", "book3"],
      4,
      false,
    ],
    // Edge case - k=1
    [["book1", "book2"], 1, true],
    // Edge case - k=len(bestSeller)
    [["book1", "book2", "book3"], 3, true],
    // no unique sequence possible
    [["book1", "book1", "book1"], 2, false],
  ];
  for (const [bestSeller, k, want] of tests) {
    const got = hasUniqueKDays(bestSeller, k);
    if (got !== want) {
      throw new Error(
        `\nhasUniqueKDays(${JSON.stringify(
          bestSeller,
        )}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
