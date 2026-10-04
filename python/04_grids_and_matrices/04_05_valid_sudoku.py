# 4.5 - Valid Sudoku
# Run: python3 04_05_valid_sudoku.py

class ValidSudoku:
  
  def solve(self, board):
    return self.valid_rows(board) and self.valid_cols(board) and self.valid_subgrids(board)

  def valid_rows(self, board):
    R, C = len(board), len(board[0])
    for r in range(R):
      seen = set()
      for c in range(C):
        if board[r][c] in seen:
          return False
        if board[r][c] != 0:
          seen.add(board[r][c])
    return True

  def valid_cols(self, board):
    R, C = len(board), len(board[0])
    for c in range(C):
      seen = set()
      for r in range(R):
        if board[r][c] in seen:
          return False
        if board[r][c] != 0:
          seen.add(board[r][c])
    return True

  def valid_subgrid(self, board, r, c):
    seen = set()
    for new_r in range(r, r + 3):
      for new_c in range(c, c + 3):
        if board[new_r][new_c] in seen:
          return False
        if board[new_r][new_c] != 0:
          seen.add(board[new_r][new_c])
    return True

  def valid_subgrids(self, board):
    for r in range(3):
      for c in range(3):
        if not self.valid_subgrid(board, r * 3, c * 3):
          return False
    return True


def run_tests():
  tests = [
      # Example 1 from book - valid sudoku
      ([[5, 0, 0, 0, 0, 0, 0, 0, 6],
        [0, 0, 9, 0, 5, 0, 3, 0, 0],
        [0, 3, 0, 0, 0, 2, 0, 0, 0],
        [8, 0, 0, 7, 0, 0, 0, 0, 9],
        [0, 0, 2, 0, 0, 0, 8, 0, 0],
        [4, 0, 0, 0, 0, 6, 0, 0, 3],
        [0, 0, 0, 3, 0, 0, 0, 4, 0],
        [0, 0, 3, 0, 8, 0, 2, 0, 0],
        [9, 0, 0, 0, 0, 0, 0, 0, 7]], True),
      # Example 2 from book - invalid sudoku (duplicate 7 in bottom right subgrid)
      ([[5, 0, 0, 0, 0, 0, 0, 0, 6],
        [0, 0, 9, 0, 5, 0, 3, 0, 0],
        [0, 3, 0, 0, 0, 2, 0, 0, 0],
        [8, 0, 0, 7, 0, 0, 0, 0, 9],
        [0, 0, 2, 0, 0, 0, 8, 0, 0],
        [4, 0, 0, 0, 0, 6, 0, 0, 3],
        [0, 0, 0, 3, 0, 0, 0, 4, 0],
        [0, 0, 3, 0, 8, 0, 7, 0, 0],
        [9, 0, 0, 0, 0, 0, 0, 0, 7]], False),
      # Edge case - empty board
      ([[0] * 9 for _ in range(9)], True),
      # Edge case - full valid board
      ([[1, 2, 3, 4, 5, 6, 7, 8, 9],
        [4, 5, 6, 7, 8, 9, 1, 2, 3],
        [7, 8, 9, 1, 2, 3, 4, 5, 6],
        [2, 3, 1, 5, 6, 4, 8, 9, 7],
        [5, 6, 4, 8, 9, 7, 2, 3, 1],
        [8, 9, 7, 2, 3, 1, 5, 6, 4],
        [3, 1, 2, 6, 4, 5, 9, 7, 8],
        [6, 4, 5, 9, 7, 8, 3, 1, 2],
        [9, 7, 8, 3, 1, 2, 6, 4, 5]], True),
  ]

  solution = ValidSudoku()
  for board, want in tests:
    got = solution.solve(board)
    assert got == want, f"\nsolve({board}): got: {got}, want: {want}\n"

run_tests()
