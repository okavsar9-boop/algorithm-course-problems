// 15.10 - 4-Directional Max-Sum Path
// Run: node 15_10_4_directional_max_sum_path.js

function fourDirectionalMaxSumPath(grid) {
  const R = grid.length;
  const C = grid[0].length;
  let bestSum = -Infinity;
  const seen = new Set(["0,0"]);

  function visit(r, c, pathSum) {
    // Process leaf/full solution
    if (r === R - 1 && c === C - 1) {
      if (pathSum > bestSum) {
        bestSum = pathSum;
      }
      return;
    }

    // Try each direction
    const directions = [
      [0, 1],
      [1, 0],
      [0, -1],
      [-1, 0],
    ];
    for (const [dr, dc] of directions) {
      const nr = r + dr;
      const nc = c + dc;
      const key = `${nr},${nc}`;
      if (!seen.has(key) && nr >= 0 && nr < R && nc >= 0 && nc < C) {
        seen.add(key);
        visit(nr, nc, pathSum + grid[nr][nc]);
        seen.delete(key);
      }
    }
  }

  // Start from top-left
  visit(0, 0, grid[0][0]);
  return bestSum;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [
        [1, -4, 3],
        [-2, 7, -6],
        [5, -4, 9],
      ],
      12,
    ],
    // Edge case - 1x1 grid
    [[[5]], 5],
    // Edge case - 1xN grid
    [[[1, 2, 3]], 6],
    // Edge case - Nx1 grid
    [[[1], [2], [3]], 6],
    // 2x2 grid
    [
      [
        [1, 2],
        [3, 4],
      ],
      8,
    ],
  ];

  for (const [grid, want] of tests) {
    const got = fourDirectionalMaxSumPath(grid);
    if (got !== want) {
      throw new Error(
        `\nfourDirectionalMaxSumPath(${JSON.stringify(grid)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
