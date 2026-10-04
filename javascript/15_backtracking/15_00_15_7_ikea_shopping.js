//  - 15.7 IKEA Shopping
// Run: node 15_00_15_7_ikea_shopping.js

function maximizeStyle(budget, prices, ratings) {
  let bestRatingSum = 0;
  let bestItems = [];
  const n = prices.length;
  const items = [];

  function visit(i, curCost, curRatingSum) {
    if (i === n) {
      if (curRatingSum > bestRatingSum) {
        bestRatingSum = curRatingSum;
        bestItems = [...items];
      }
      return;
    }

    // Choice 1: skip item i.
    visit(i + 1, curCost, curRatingSum);
    // Choice 2: pick item i (if within budget).
    if (curCost + prices[i] <= budget) {
      items.push(i);
      visit(i + 1, curCost + prices[i], curRatingSum + ratings[i]);
      items.pop();
    }
  }

  visit(0, 0, 0);
  return bestItems;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [20, [10, 5, 15, 8, 3], [7.0, 3.5, 9.0, 6.0, 2.0], [0, 3]],
    // Example 2 from the book
    [10, [2, 3, 4, 5], [1.0, 2.0, 3.5, 4.0], [2, 3]],
    // Edge case - budget is 0
    [0, [1, 2, 3], [1.0, 2.0, 3.0], []],
    // Edge case - no items
    [10, [], [], []],
    // Larger budget
    [50, [10, 20, 30], [10.0, 20.0, 30.0], [1, 2]],
  ];
  for (const [budget, prices, ratings, want] of tests) {
    const got = maximizeStyle(budget, prices, ratings);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nmaximizeStyle(${budget}, ${JSON.stringify(prices)}, ${JSON.stringify(ratings)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
