// 16.3 - Restaurant Ratings
// Run: node 16_03_restaurant_ratings.js

function restaurantRatings(ratings) {
  const n = ratings.length;
  const memo = new Map();

  function ratingSum(i) {
    if (i >= n) {
      return 0;
    }
    if (memo.has(i)) {
      return memo.get(i);
    }
    memo.set(i, Math.max(ratings[i] + ratingSum(i + 2), ratingSum(i + 1)));
    return memo.get(i);
  }

  return ratingSum(0);
}


function runTests() {
  const tests = [
    [[8, 1, 3, 9, 5, 2, 1], 19],
    [[8, 1, 3, 7, 5, 2, 4], 20],
    [[10, 10, 10, 10, 10], 30],
    [[], 0],
    [[5, 5, 5, 5, 5], 15],
  ];
  for (const [ratings, want] of tests) {
    const got = restaurantRatings(ratings);
    if (got !== want) {
      throw new Error(
        `\nrestaurantRatings(${JSON.stringify(ratings)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
