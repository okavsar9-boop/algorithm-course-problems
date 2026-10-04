// 5.11 - Min Pages Per Day
// Run: node 05_11_min_pages_per_day.js

function minPagesPerDay(pageCounts, days) {

  // How many days it takes to finish the book with a given daily page limit.
  function daysToFinish(dailyLimit) {
    let d = 0;
    for (const pages of pageCounts) {
      // Ceiling division to handle leftover pages.
      d += Math.ceil(pages / dailyLimit);
    }
    return d;
  }

  // Defines a transition point over the range of # of pages per day (dailyLimit).
  // In the 'before' region, the daily limit is not enough to finish the book in time.
  // In the 'after' region, the daily limit is enough to finish the book in time.
  function isBefore(dailyLimit) {
    return daysToFinish(dailyLimit) > days;
  }

  let l = 0;
  // In case we have more days than max pages in any chapter,
  // we might need to read as little as 1 page per day.
  let r = Math.max(...pageCounts);

  // Binary search for the transition point from 'before' to 'after' region.
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  // Return the first value in the 'after' region, i.e., the smallest daily limit
  // that allows us to finish the book in time.
  return r;
}


function runTests() {
  const tests = [
    // Example from book
    [[20, 15, 17, 10], 5, 17],
    [[20, 15, 17, 10], 14, 5],
    [[20, 15, 17, 10], 17, 4],
    // Edge case - single chapter
    [[10], 5, 2],
    // Edge case - days = chapters
    [[1, 2, 3], 3, 3],
    // Edge case - more days than max chapter pages
    [[20], 21, 1],
  ];

  for (const [pageCounts, days, want] of tests) {
    const got = minPagesPerDay(pageCounts, days);
    if (got !== want) {
      throw new Error(
        `\nminPagesPerDay(${JSON.stringify(pageCounts)}, ${days}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
