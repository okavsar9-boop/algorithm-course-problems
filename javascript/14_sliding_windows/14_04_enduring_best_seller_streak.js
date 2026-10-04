// 14.4 - Enduring Best Seller Streak
// Run: node 14_04_enduring_best_seller_streak.js

function hasEnduringBestSellerStreak(bestSeller, k) {
  // Fixed-length window solution. O(k) space.
  let l = 0,
    r = 0;
  const windowCounts = new Map();
  while (r < bestSeller.length) {
    windowCounts.set(bestSeller[r], (windowCounts.get(bestSeller[r]) || 0) + 1);
    r++;
    if (r - l === k) {
      if (windowCounts.size === 1) {
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

function hasEnduringBestSellerStreak2(bestSeller, k) {
  // Resetting window solution. O(1) space.
  let l = 0,
    r = 0;
  while (r < bestSeller.length) {
    const canGrow = l === r || bestSeller[l] === bestSeller[r];
    if (canGrow) {
      r++;
      if (r - l === k) {
        return true;
      }
    } else {
      l = r;
    }
  }
  return false;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [["book3", "book1", "book3", "book3", "book2"], 3, false],
    // Example 2 from the book
    [["book3", "book1", "book3", "book3", "book2"], 2, true],
    [["book1", "book1", "book2", "book1"], 2, true],
    // Edge case - k=1
    [["book1", "book2"], 1, true],
    // Edge case - k=len(bestSeller)
    [["book1", "book1", "book1"], 3, true],
    // no same sequence possible
    [["book1", "book2", "book1"], 2, false],
  ];
  for (const [bestSeller, k, want] of tests) {
    let got = hasEnduringBestSellerStreak(bestSeller, k);
    if (got !== want) {
      throw new Error(
        `\nhasEnduringBestSellerStreak(${JSON.stringify(bestSeller)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
    got = hasEnduringBestSellerStreak2(bestSeller, k);
    if (got !== want) {
      throw new Error(
        `\nhasEnduringBestSellerStreak2(${JSON.stringify(bestSeller)}, ${k}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
