// 14.21 - Count Subarrays With Good Start and Ending
// Run: node 14_21_count_subarrays_with_good_start_and_ending.js

function countSubarraysWithGoodStartAndEnding(sales) {
  const goodDays = sales.filter((x) => x >= 10).length;
  return (goodDays * (goodDays + 1)) / 2;
}


function runTests() {
  const tests = [
    // Example with mix of good and bad days
    [[0, 20, 5, 15, 10], 6],
    // Edge case - empty array
    [[], 0],
    // Edge case - all good days
    [[10, 20, 30], 6],
    // Edge case - all bad days
    [[0, 5, 8], 0],
    // Edge case - single good day
    [[10], 1],
  ];
  for (const [sales, want] of tests) {
    const got = countSubarraysWithGoodStartAndEnding(sales);
    if (got !== want) {
      throw new Error(
        `\ncountSubarraysWithGoodStartAndEnding(${JSON.stringify(
          sales,
        )}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
