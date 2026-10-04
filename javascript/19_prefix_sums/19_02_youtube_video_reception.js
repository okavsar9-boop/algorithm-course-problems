// 19.2 - YouTube Video Reception
// Run: node 19_02_youtube_video_reception.js

function goodReceptionScores(likes, dislikes, periods) {
  const positiveDays = new Array(likes.length).fill(0);
  for (let i = 0; i < likes.length; i++) {
    if (likes[i] > dislikes[i]) {
      positiveDays[i] = 1;
    }
  }
  // Range sum queries recipe
  const prefixSum = new Array(positiveDays.length).fill(0);
  prefixSum[0] = positiveDays[0];
  for (let i = 1; i < positiveDays.length; i++) {
    prefixSum[i] = prefixSum[i - 1] + positiveDays[i];
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
      [6, 3, 4, 8, 7, 2, 6, 5, 0, 1],
      [6, 0, 8, 0, 0, 0, 1, 8, 0, 2],
      [
        [0, 1],
        [0, 5],
        [5, 8],
        [3, 3],
      ],
      [1, 4, 2, 1],
    ],
    // Edge case: All days positive
    [[10, 20, 30], [0, 0, 0], [[0, 2]], [3]],
    // Edge case: All days negative
    [[0, 0, 0], [10, 20, 30], [[0, 2]], [0]],
    // Edge case: Mixed days
    [[1, 2, 3], [3, 2, 1], [[0, 2]], [1]],
  ];

  for (const [likes, dislikes, periods, want] of tests) {
    const got = goodReceptionScores(likes, dislikes, periods);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ngoodReceptionScores(${JSON.stringify(likes)}, ${JSON.stringify(dislikes)}, ${JSON.stringify(periods)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
