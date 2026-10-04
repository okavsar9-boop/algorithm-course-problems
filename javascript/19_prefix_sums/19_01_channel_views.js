// 19.1 - Channel Views
// Run: node 19_01_channel_views.js

function channelViews(views, periods) {
  if (!views.length || !periods.length) {
    return [];
  }
  const prefixSum = new Array(views.length).fill(0);
  prefixSum[0] = views[0];
  for (let i = 1; i < views.length; i++) {
    prefixSum[i] = prefixSum[i - 1] + views[i];
  }
  const res = [];
  for (const [l, r] of periods) {
    if (l === 0) {
      res.push(prefixSum[r]);
    } else {
      res.push(prefixSum[r] - prefixSum[l - 1]);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [3, 5, 4, 8, 7, 2, 5, 3, 2, 3],
      [
        [0, 1],
        [0, 5],
        [5, 8],
        [3, 3],
      ],
      [8, 29, 12, 8],
    ],
    // Edge case: Single day period
    [[10, 20, 30], [[1, 1]], [20]],
    // Edge case: Full range
    [[1, 2, 3, 4, 5], [[0, 4]], [15]],
    // Edge case: Empty views
    [[], [], []],
    // Edge case: Periods with zero-length
    [
      [1, 2, 3, 4, 5],
      [
        [2, 2],
        [0, 0],
      ],
      [3, 1],
    ],
  ];

  for (const [views, periods, want] of tests) {
    const got = channelViews(views, periods);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nchannelViews(${JSON.stringify(views)}, ${JSON.stringify(periods)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
