// 16.4 - Count 0-Sum Paths
// Run: node 16_04_count_0_sum_paths.js

function count0SumPaths(grid) {
  const R = grid.length;
  const C = grid[0].length;
  const memo = new Map();

  function numPaths(r, c) {
    if (r >= R || c >= C || grid[r][c] === 1) {
      return 0;
    }
    const key = `${r},${c}`;
    if (memo.has(key)) {
      return memo.get(key);
    }
    if (r === R - 1 && c === C - 1) {
      return 1;
    }
    const result =
      numPaths(r + 1, c) + numPaths(r, c + 1) + numPaths(r + 1, c + 1);
    memo.set(key, result);
    return result;
  }

  return numPaths(0, 0);
}


function runTests() {
  const tests = [
    [
      [
        [0, 1, 1],
        [0, 0, 0],
        [1, 0, 0],
      ],
      7,
    ],
    [[[1]], 0],
    [
      [
        [0, 0],
        [0, 0],
      ],
      3,
    ],
    [[[0]], 1],
    [
      [
        [0, 0, 0],
        [0, 1, 0],
        [0, 0, 0],
      ],
      4,
    ],
  ];
  for (const [grid, want] of tests) {
    const got = count0SumPaths(grid);
    if (got !== want) {
      throw new Error(
        `\ncount0SumPaths(${JSON.stringify(grid)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
