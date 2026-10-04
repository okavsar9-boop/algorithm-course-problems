// 4.1 - Chess Moves
// Run: node 04_01_chess_moves.js

function chessMoves(board, piece, r, c) {

  function isValid(board, r, c) {
    return (
      0 <= r &&
      r < board.length &&
      0 <= c &&
      c < board[0].length &&
      board[r][c] !== 1
    );
  }

  const moves = [];
  const kingDirections = [
    [-1, 0],
    [1, 0],
    [0, -1],
    [0, 1], // Vertical and horizontal
    [-1, -1],
    [-1, 1],
    [1, -1],
    [1, 1], // Diagonals
  ];
  const knightDirections = [
    [-2, 1],
    [-1, 2],
    [1, 2],
    [2, 1],
    [2, -1],
    [1, -2],
    [-1, -2],
    [-2, -1],
  ];

  const directions = piece === "knight" ? knightDirections : kingDirections;

  for (const [dirR, dirC] of directions) {
    let newR = r + dirR;
    let newC = c + dirC;
    if (piece === "queen") {
      while (isValid(board, newR, newC)) {
        if (board[newR][newC] === 1) {
          // Stop at obstacles
          break;
        }
        moves.push([newR, newC]);
        newR += dirR;
        newC += dirC;
      }
    } else if (isValid(board, newR, newC)) {
      moves.push([newR, newC]);
    }
  }
  return moves;
}


function runTests() {
  const tests = [
    // Example 1 from the book - king moves
    [
      [
        [0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
      ],
      "king",
      3,
      5,
      [
        [2, 5],
        [3, 4],
        [4, 4],
        [4, 5],
      ],
    ],
    // Example 2 from the book - knight moves
    [
      [
        [0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
      ],
      "knight",
      4,
      3,
      [
        [2, 2],
        [3, 5],
        [5, 5],
      ],
    ],
    // Example 3 from the book - queen moves
    [
      [
        [0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
      ],
      "queen",
      4,
      4,
      [
        [3, 4],
        [3, 5],
        [4, 0],
        [4, 1],
        [4, 2],
        [4, 3],
        [4, 5],
        [5, 3],
        [5, 4],
        [5, 5],
      ],
    ],
    // Edge case - 1x1 board
    [[[0]], "queen", 0, 0, []],
    // Edge case - all occupied except current position
    [
      [
        [1, 1],
        [1, 0],
      ],
      "knight",
      1,
      1,
      [],
    ],
  ];

  for (const [board, piece, r, c, want] of tests) {
    const got = chessMoves(board, piece, r, c);
    // Sort both lists for consistent comparison
    got.sort((a, b) => a[0] - b[0] || a[1] - b[1]);
    want.sort((a, b) => a[0] - b[0] || a[1] - b[1]);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nchessMoves(${JSON.stringify(board)}, ${piece}, ${r}, ${c}): ` +
        `got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
