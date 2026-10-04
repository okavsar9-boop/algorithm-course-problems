// 4.7 - Subgrid Sums
// Run: node 04_07_subgrid_sums.js

function subgridSums(grid) {
  const R = grid.length;
  const C = grid[0].length;
  const res = grid.map((row) => [...row]);
  for (let r = R - 1; r >= 0; r--) {
    for (let c = C - 1; c >= 0; c--) {
      if (r + 1 < R) {
        res[r][c] += res[r + 1][c];
      }
      if (c + 1 < C) {
        res[r][c] += res[r][c + 1];
      }
      if (r + 1 < R && c + 1 < C) {
        // subtract doublecounted subgrid
        res[r][c] -= res[r + 1][c + 1];
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
        [-1, 2, 3],
        [4, 0, 0],
        [-2, 0, 9],
      ],
      [
        [15, 14, 12],
        [11, 9, 9],
        [7, 9, 9],
      ],
    ],
    // Edge case - 1x1 grid
    [[[5]], [[5]]],
    // Edge case - single row
    [[[1, 2, 3]], [[6, 5, 3]]],
    // Edge case - single column
    [
      [[1], [2], [3]],
      [[6], [5], [3]],
    ],
    // Edge case - all zeros
    [
      [
        [0, 0],
        [0, 0],
      ],
      [
        [0, 0],
        [0, 0],
      ],
    ],
  ];

  for (const [grid, want] of tests) {
    const got = subgridSums(grid);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nsubgridSums(${JSON.stringify(grid)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
