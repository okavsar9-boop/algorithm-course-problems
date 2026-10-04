// 4.5 - Valid Sudoku
// Run: node 04_05_valid_sudoku.js

class ValidSudoku {
  solve(board) {
    return (
      this.validRows(board) &&
      this.validCols(board) &&
      this.validSubgrids(board)
    );
  }

  validRows(board) {
    const [R, C] = [board.length, board[0].length];
    for (let r = 0; r < R; r++) {
      const seen = new Set();
      for (let c = 0; c < C; c++) {
        if (board[r][c] !== 0) {
          if (seen.has(board[r][c])) return false;
          seen.add(board[r][c]);
        }
      }
    }
    return true;
  }

  validCols(board) {
    const [R, C] = [board.length, board[0].length];
    for (let c = 0; c < C; c++) {
      const seen = new Set();
      for (let r = 0; r < R; r++) {
        if (board[r][c] !== 0) {
          if (seen.has(board[r][c])) return false;
          seen.add(board[r][c]);
        }
      }
    }
    return true;
  }

  validSubgrid(board, r, c) {
    const seen = new Set();
    for (let newR = r; newR < r + 3; newR++) {
      for (let newC = c; newC < c + 3; newC++) {
        if (board[newR][newC] !== 0) {
          if (seen.has(board[newR][newC])) return false;
          seen.add(board[newR][newC]);
        }
      }
    }
    return true;
  }

  validSubgrids(board) {
    for (let r = 0; r < 9; r += 3) {
      for (let c = 0; c < 9; c += 3) {
        if (!this.validSubgrid(board, r, c)) return false;
      }
    }
    return true;
  }
}


function runTests() {
  const tests = [
    // Example 1 from book - valid sudoku
    [
      [
        [5, 0, 0, 0, 0, 0, 0, 0, 6],
        [0, 0, 9, 0, 5, 0, 3, 0, 0],
        [0, 3, 0, 0, 0, 2, 0, 0, 0],
        [8, 0, 0, 7, 0, 0, 0, 0, 9],
        [0, 0, 2, 0, 0, 0, 8, 0, 0],
        [4, 0, 0, 0, 0, 6, 0, 0, 3],
        [0, 0, 0, 3, 0, 0, 0, 4, 0],
        [0, 0, 3, 0, 8, 0, 2, 0, 0],
        [9, 0, 0, 0, 0, 0, 0, 0, 7],
      ],
      true,
    ],
    // Example 2 from book - invalid sudoku (duplicate 7 in bottom right subgrid)
    [
      [
        [5, 0, 0, 0, 0, 0, 0, 0, 6],
        [0, 0, 9, 0, 5, 0, 3, 0, 0],
        [0, 3, 0, 0, 0, 2, 0, 0, 0],
        [8, 0, 0, 7, 0, 0, 0, 0, 9],
        [0, 0, 2, 0, 0, 0, 8, 0, 0],
        [4, 0, 0, 0, 0, 6, 0, 0, 3],
        [0, 0, 0, 3, 0, 0, 0, 4, 0],
        [0, 0, 3, 0, 8, 0, 7, 0, 0],
        [9, 0, 0, 0, 0, 0, 0, 0, 7],
      ],
      false,
    ],
    // Edge case - empty board
    [
      Array(9)
        .fill()
        .map(() => Array(9).fill(0)),
      true,
    ],
    // Edge case - full valid board
    [
      [
        [1, 2, 3, 4, 5, 6, 7, 8, 9],
        [4, 5, 6, 7, 8, 9, 1, 2, 3],
        [7, 8, 9, 1, 2, 3, 4, 5, 6],
        [2, 3, 1, 5, 6, 4, 8, 9, 7],
        [5, 6, 4, 8, 9, 7, 2, 3, 1],
        [8, 9, 7, 2, 3, 1, 5, 6, 4],
        [3, 1, 2, 6, 4, 5, 9, 7, 8],
        [6, 4, 5, 9, 7, 8, 3, 1, 2],
        [9, 7, 8, 3, 1, 2, 6, 4, 5],
      ],
      true,
    ],
  ];

  const solution = new ValidSudoku();
  for (const [board, want] of tests) {
    const got = solution.solve(board);
    if (got !== want) {
      throw new Error(
        `\nsolve(${JSON.stringify(board)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
