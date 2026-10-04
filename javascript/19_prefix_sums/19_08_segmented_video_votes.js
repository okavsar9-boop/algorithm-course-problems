// 19.8 - Segmented Video Votes
// Run: node 19_08_segmented_video_votes.js

function rangeUpdates(n, votes) {
  const diff = new Array(n).fill(0);
  for (const [l, r, v] of votes) {
    diff[l] += v;
    if (r + 1 < n) {
      diff[r + 1] -= v;
    }
  }

  // Recipe 1.
  const prefixSum = new Array(n).fill(0);
  prefixSum[0] = diff[0];
  for (let i = 1; i < n; i++) {
    prefixSum[i] = prefixSum[i - 1] + diff[i];
  }
  return prefixSum;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      6,
      [
        [3, 4, 1],
        [0, 0, 1],
        [1, 3, 1],
        [0, 5, -1],
      ],
      [0, 0, 0, 1, 0, -1],
    ],
    // Edge case: No votes
    [5, [], [0, 0, 0, 0, 0]],
    // Edge case: All likes
    [3, [[0, 2, 1]], [1, 1, 1]],
    // Edge case: All dislikes
    [3, [[0, 2, -1]], [-1, -1, -1]],
  ];

  for (const [n, votes, want] of tests) {
    const got = rangeUpdates(n, votes);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nrangeUpdates(${n}, ${JSON.stringify(votes)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
