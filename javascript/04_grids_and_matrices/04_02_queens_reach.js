// 4.2 - Queen's Reach
// Run: node 04_02_queens_reach.js

function safeCells(board) {
  const n = board.length;
  if (n == 0) return [];

  function isValid(r, c) {
    return 0 <= r && r < n && 0 <= c && c < n && board[r][c] !== 1;
  }

  const directions = [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1], // Vertical and horizontal
    [-1, -1],
    [-1, 1],
    [1, -1],
    [1, 1], // Diagonals
  ];

  const res = Array(n)
    .fill()
    .map(() => Array(n).fill(0));

  function markReachableCells(r, c) {
    for (const [dirR, dirC] of directions) {
      let newR = r + dirR,
        newC = c + dirC;
      while (isValid(newR, newC)) {
        res[newR][newC] = 1;
        newR += dirR;
        newC += dirC;
      }
    }
  }

  for (let r = 0; r < n; r++) {
    for (let c = 0; c < n; c++) {
      if (board[r][c] === 1) {
        res[r][c] = 1;
        markReachableCells(r, c);
      }
    }
  }
  return res;
}


function runTests() {
  const tests = [
    [
      [
        [0, 0, 0, 1],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [1, 0, 0, 0],
      ],
      [
        [1, 1, 1, 1],
        [1, 0, 1, 1],
        [1, 1, 0, 1],
        [1, 1, 1, 1],
      ],
    ],
    // Edge case - 1x1 board with queen
    [[[1]], [[1]]],
    // Edge case - 1x1 board without queen
    [[[0]], [[0]]],
    // Edge case - no queens
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

  for (const [board, want] of tests) {
    const got = safeCells(board);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nsafeCells(${JSON.stringify(board)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
