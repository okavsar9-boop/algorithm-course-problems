// 4.3 - Spiral Order
// Run: node 04_03_spiral_order.js

function spiral(n) {

  function isValid(grid, r, c) {
    return (
      0 <= r && r < grid.length && 0 <= c < grid[0].length && grid[r][c] === 0
    );
  }

  let val = n * n - 1;
  const res = Array(n)
    .fill()
    .map(() => Array(n).fill(0));
  let r = n - 1,
    c = n - 1;
  const directions = [
    [-1, 0],
    [0, -1],
    [1, 0],
    [0, 1],
  ]; // Counterclockwise
  let dir = 0; // Start going up

  while (val > 0) {
    res[r][c] = val;
    val--;
    if (!isValid(res, r + directions[dir][0], c + directions[dir][1])) {
      dir = (dir + 1) % 4; // Change directions counterclockwise
    }
    r += directions[dir][0];
    c += directions[dir][1];
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from book
    [
      5,
      [
        [16, 17, 18, 19, 20],
        [15, 4, 5, 6, 21],
        [14, 3, 0, 7, 22],
        [13, 2, 1, 8, 23],
        [12, 11, 10, 9, 24],
      ],
    ],
    // Edge case - 1x1
    [1, [[0]]],
    // Edge case - 3x3
    [
      3,
      [
        [4, 5, 6],
        [3, 0, 7],
        [2, 1, 8],
      ],
    ],
  ];

  for (const [n, want] of tests) {
    const got = spiral(n);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nspiral(${n}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
