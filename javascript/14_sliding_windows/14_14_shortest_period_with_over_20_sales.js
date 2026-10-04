// 14.14 - Shortest Period With Over 20 Sales
// Run: node 14_14_shortest_period_with_over_20_sales.js

function shortestOver20Sales(sales) {
  let l = 0,
    r = 0;
  let windowSum = 0;
  let curMin = Infinity;
  while (true) {
    const mustGrow = windowSum <= 20;
    if (mustGrow) {
      if (r === sales.length) {
        break;
      }
      windowSum += sales[r];
      r++;
    } else {
      curMin = Math.min(curMin, r - l);
      windowSum -= sales[l];
      l++;
    }
  }
  return curMin !== Infinity ? curMin : -1;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[5, 10, 15, 5, 10], 2],
    // Example 2 from the book
    [[5, 10, 4, 5, 10], 4],
    // Example 3 from the book
    [[5, 5, 5, 5], -1],
    // Edge case - empty array
    [[], -1],
    // Edge case - single element over 20
    [[21], 1],
    // Edge case - exactly 20 sales not enough
    [[10, 10], -1],
  ];
  for (const [sales, want] of tests) {
    const got = shortestOver20Sales(sales);
    if (got !== want) {
      throw new Error(
        `\nshortestOver20Sales(${JSON.stringify(sales)}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
