// 16.2 - Minivan Road Trip
// Run: node 16_02_minivan_road_trip.js

function minivanRoadTrip(times, k) {
  const n = times.length;
  const memo = new Map();

  function delay(i) {
    if (i >= n) {
      return 0;
    }
    if (i >= n - k - 1) {
      return times[i];
    }
    if (memo.has(i)) {
      return memo.get(i);
    }
    let minDelay = Infinity;
    for (let p = 1; p <= k + 1; p++) {
      minDelay = Math.min(minDelay, delay(i + p));
    }
    memo.set(i, times[i] + minDelay);
    return memo.get(i);
  }

  let minDelay = Infinity;
  for (let p = 0; p <= k; p++) {
    minDelay = Math.min(minDelay, delay(p));
  }
  return minDelay;
}


function runTests() {
  const tests = [
    [[8, 1, 2, 3, 9, 6, 2, 4], 2, 6],
    [[8, 1, 2, 3, 9, 6, 2, 4], 3, 4],
    [[10, 10], 1, 10],
    [[10, 10], 2, 0],
    [[], 2, 0],
    [[5, 5, 5, 5, 5], 2, 5],
  ];
  for (const [times, k, want] of tests) {
    const got = minivanRoadTrip(times, k);
    if (got !== want) {
      throw new Error(
        `\nminivanRoadTrip(${JSON.stringify(times)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
