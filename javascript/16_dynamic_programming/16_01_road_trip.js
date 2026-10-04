// 16.1 - Road Trip
// Run: node 16_01_road_trip.js

function delayInefficient(times) {
  const n = times.length;
  if (n < 3) {
    return 0;
  }

  function delayRec(i) {
    if (i >= n - 3) {
      return times[i];
    }
    return (
      times[i] + Math.min(delayRec(i + 1), delayRec(i + 2), delayRec(i + 3))
    );
  }

  return Math.min(delayRec(0), delayRec(1), delayRec(2));
}

function delayMemoized(times) {
  const n = times.length;
  if (n < 3) {
    return 0;
  }

  const memo = new Array(n).fill(-1);

  function delayRec(i) {
    if (i >= n - 3) {
      return times[i];
    }
    if (memo[i] !== -1) {
      return memo[i];
    }
    memo[i] =
      times[i] + Math.min(delayRec(i + 1), delayRec(i + 2), delayRec(i + 3));
    return memo[i];
  }

  return Math.min(delayRec(0), delayRec(1), delayRec(2));
}


function delayTabulated(times) {
  const n = times.length;
  if (n < 3) {
    return 0;
  }

  const dp = new Array(n);
  dp[n - 1] = times[n - 1];
  dp[n - 2] = times[n - 2];
  dp[n - 3] = times[n - 3];
  for (let i = n - 4; i >= 0; i--) {
    dp[i] = times[i] + Math.min(dp[i + 1], dp[i + 2], dp[i + 3]);
  }
  return Math.min(dp[0], dp[1], dp[2]);
}

function delayTabulatedWSpaceOptimization(times) {
  const n = times.length;
  if (n < 3) {
    return 0;
  }
  let dp1 = times[n - 3],
    dp2 = times[n - 2],
    dp3 = times[n - 1];
  for (let i = n - 4; i >= 0; i--) {
    const cur = times[i] + Math.min(dp1, dp2, dp3);
    dp3 = dp2;
    dp2 = dp1;
    dp1 = cur;
  }
  return Math.min(dp1, dp2, dp3);
}

function runTests() {
  const tests = [
    [[8, 1, 2, 3, 9, 6, 2, 4], 6],
    [[8, 1, 2, 3, 9, 3, 2, 4], 5],
    [[10, 10], 0],
    [[1, 2, 3, 4, 5, 6, 7, 8, 9], 12],
    [[5, 5, 5, 5, 5, 5, 5, 5, 5], 15],
    [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1], 3],
    [[1, 2, 3], 1],
    [[1, 2], 0],
    [[1], 0],
    [[], 0],
  ];

  for (const [times, want] of tests) {
    const gotInefficient = delayInefficient(times);
    const gotMemoized = delayMemoized(times);
    const gotTabulated = delayTabulated(times);
    const gotSpaceOpt = delayTabulatedWSpaceOptimization(times);

    if (
      !(
        gotInefficient === gotMemoized &&
        gotMemoized === gotTabulated &&
        gotTabulated === gotSpaceOpt &&
        gotInefficient === want
      )
    ) {
      throw new Error(
        `\ndelay(${JSON.stringify(times)}): got inefficient: ${gotInefficient}, ` +
        `got memoized: ${gotMemoized}, got tabulated: ${gotTabulated}, ` +
        `got space opt: ${gotSpaceOpt}, want: ${want}\n`,
      );
    }
  }
}

runTests();
