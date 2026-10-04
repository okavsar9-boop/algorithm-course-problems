// 5.7 - Search In Sorted Grid
// Run: node 05_07_search_in_sorted_grid.js

function searchInSortedGrid(grid, target) {
  const R = grid.length;
  const C = grid[0].length;

  // Before is < target, after is >= target
  function isBefore(i) {
    const row = Math.floor(i / C);
    const col = i % C;
    return grid[row][col] < target;
  }

  // Ensure the first element is 'before' and the last is 'after'
  if (grid[0][0] > target || grid[R - 1][C - 1] < target) {
    return [-1, -1];
  }
  if (grid[0][0] === target) {
    return [0, 0];
  }
  if (grid[R - 1][C - 1] === target) {
    return [R - 1, C - 1];
  }

  // Binary search for the transition point
  let l = 0,
    r = R * C - 1;
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  const row = Math.floor(r / C);
  const col = r % C;
  if (grid[row][col] === target) {
    return [row, col];
  }
  return [-1, -1];
}


function runTests() {
  const tests = [
    [
      [
        [1, 3, 5],
        [7, 9, 11],
        [13, 15, 17],
      ],
      9,
      [1, 1],
    ], // Example 1
    [
      [
        [1, 3, 5],
        [7, 9, 11],
      ],
      4,
      [-1, -1],
    ], // Example 2
    [
      [
        [2, 3],
        [4, 5],
      ],
      1,
      [-1, -1],
    ], // 2x2 grid, all grid after
    [
      [
        [1, 2],
        [3, 4],
      ],
      5,
      [-1, -1],
    ], // 2x2 grid, all grid before
    [
      [
        [1, 2],
        [3, 4],
        [5, 6],
      ],
      1,
      [0, 0],
    ], // 3x2 grid, first element
    [
      [
        [1, 2, 3],
        [4, 5, 6],
      ],
      6,
      [1, 2],
    ], // 2x3 grid, last element
    [[[7]], 7, [0, 0]], // Single element edge case
    [[[7]], 6, [-1, -1]], // Single element edge case (not found)
  ];

  for (const [grid, target, want] of tests) {
    const got = searchInSortedGrid(grid, target);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nsearchInSortedGrid(${JSON.stringify(grid)}, ${target}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
