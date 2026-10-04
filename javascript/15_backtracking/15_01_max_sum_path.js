// 15.1 - Max-Sum Path
// Run: node 15_01_max_sum_path.js

function maxSumPathBacktracking(grid) {
  // Inefficient backtracking solution. DP is better!
  const R = grid.length;
  const C = grid[0].length;
  let maxSum = -Infinity;

  function visit(r, c, curSum) {
    if (r === R - 1 && c === C - 1) {
      maxSum = Math.max(maxSum, curSum);
      return;
    }

    if (r + 1 < R) {
      visit(r + 1, c, curSum + grid[r + 1][c]); // Go down.
    }
    if (c + 1 < C) {
      visit(r, c + 1, curSum + grid[r][c + 1]); // Go right.
    }
  }

  visit(0, 0, grid[0][0]);
  return maxSum;
}
function maxSumPathMemoization(grid) {
  const R = grid.length;
  const C = grid[0].length;
  const memo = new Map();

  function dp(r, c) {
    const key = `${r},${c}`;
    if (memo.has(key)) {
      return memo.get(key);
    }

    if (r === R - 1 && c === C - 1) {
      return grid[r][c];
    }

    let maxSum = -Infinity;
    // Try going down
    if (r + 1 < R) {
      maxSum = Math.max(maxSum, grid[r][c] + dp(r + 1, c));
    }
    // Try going right
    if (c + 1 < C) {
      maxSum = Math.max(maxSum, grid[r][c] + dp(r, c + 1));
    }

    memo.set(key, maxSum);
    return maxSum;
  }

  return dp(0, 0);
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [
        [1, 4, 3],
        [2, 7, 6],
        [5, 8, 9],
      ],
      29,
    ],
    // Example 2 from the book
    [[[5]], 5],
    // Additional test cases
    // Edge case - single row
    [[[1, 2, 3, 4]], 10],
    // Edge case - single column
    [[[1], [2], [3], [4]], 10],
    // Larger grid
    [
      [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9],
      ],
      29,
    ],
    // Edge case - all elements are the same
    [
      [
        [1, 1, 1],
        [1, 1, 1],
        [1, 1, 1],
      ],
      5,
    ],
  ];

  for (const [grid, want] of tests) {
    const gotBacktracking = maxSumPathBacktracking(grid);
    const gotMemoization = maxSumPathMemoization(grid);

    if (gotBacktracking !== gotMemoization) {
      throw new Error(
        `\nmaxSumPathBacktracking(${JSON.stringify(grid)}) != ` +
          `maxSumPathMemoization(${JSON.stringify(grid)})\n`,
      );
    }
    if (gotBacktracking !== want) {
      throw new Error(
        `\nmaxSumPathBacktracking(${JSON.stringify(grid)}): ` +
          `got: ${gotBacktracking}, want: ${want}\n`,
      );
    }
    if (gotMemoization !== want) {
      throw new Error(
        `\nmaxSumPathMemoization(${JSON.stringify(grid)}): ` +
          `got: ${gotMemoization}, want: ${want}\n`,
      );
    }
  }
}

runTests();
