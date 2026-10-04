// 19.5 - YouTube Video Unusual Days
// Run: node 19_05_youtube_video_unusual_days.js

function maxTotalDeviation(likes, dislikes) {
  const scores = likes
    .map((like, i) => like - dislikes[i])
    .sort((a, b) => a - b);
  const n = scores.length;
  const prefixSum = new Array(n).fill(0);
  prefixSum[0] = scores[0];
  for (let i = 1; i < n; i++) {
    prefixSum[i] = prefixSum[i - 1] + scores[i];
  }
  let maxDeviation = 0;
  for (let i = 0; i < n; i++) {
    let left = 0,
      right = 0;
    if (i > 0) {
      left = i * scores[i] - prefixSum[i - 1];
    }
    if (i < n - 1) {
      right = prefixSum[n - 1] - prefixSum[i] - (n - i - 1) * scores[i];
    }
    maxDeviation = Math.max(maxDeviation, left + right);
  }
  return maxDeviation;
}


function runTests() {
  const tests = [
    // Example from the book
    [[3, 6, 1], [0, 1, 9], 24],
    // Edge case: All same scores
    [[1, 1, 1], [1, 1, 1], 0],
    // Edge case: Increasing scores
    [[1, 2, 3], [0, 0, 0], 3],
    // Edge case: Decreasing scores
    [[3, 2, 1], [0, 0, 0], 3],
  ];

  for (const [likes, dislikes, want] of tests) {
    const got = maxTotalDeviation(likes, dislikes);
    if (got !== want) {
      throw new Error(
        `\nmaxTotalDeviation(${JSON.stringify(likes)}, ${JSON.stringify(dislikes)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
