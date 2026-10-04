// 4.6 - Subgrid Maximums
// Run: node 04_06_subgrid_maximums.js

function subgridMaximums(grid) {
  const R = grid.length;
  const C = grid[0].length;
  const res = grid.map((row) => [...row]);
  for (let r = R - 1; r >= 0; r--) {
    for (let c = C - 1; c >= 0; c--) {
      if (r + 1 < R) {
        res[r][c] = Math.max(res[r][c], res[r + 1][c]);
      }
      if (c + 1 < C) {
        res[r][c] = Math.max(res[r][c], res[r][c + 1]);
      }
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from book
    [
      [
        [1, 5, 3],
        [4, -1, 0],
        [2, 0, 2],
      ],
      [
        [5, 5, 3],
        [4, 2, 2],
        [2, 2, 2],
      ],
    ],
    // Edge case - 1x1 grid
    [[[5]], [[5]]],
    // Edge case - single row
    [[[1, 2, 3]], [[3, 3, 3]]],
    // Edge case - single column
    [
      [[1], [2], [3]],
      [[3], [3], [3]],
    ],
    // Edge case - negative numbers
    [
      [
        [-1, -2],
        [-3, -4],
      ],
      [
        [-1, -2],
        [-3, -4],
      ],
    ],
  ];

  for (const [grid, want] of tests) {
    const got = subgridMaximums(grid);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nsubgridMaximums(${JSON.stringify(grid)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
