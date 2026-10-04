# 4.2 - Queen's Reach
# Run: python3 04_02_queens_reach.py

def safe_cells(board):

  directions = [
      [-1, 0], [1, 0], [0, -1], [0, 1],  # Vertical and horizontal
      [-1, -1], [-1, 1], [1, -1], [1, 1]  # Diagonals
  ]

  n = len(board)

  def is_valid(r, c):
    return 0 <= r < n and 0 <= c < n and board[r][c] != 1

  res = [[0] * n for _ in range(n)]

  def mark_reachable_cells(r, c):
    for dir_r, dir_c in directions:
      new_r, new_c = r + dir_r, c + dir_c
      while is_valid(new_r, new_c):
        res[new_r][new_c] = 1
        new_r += dir_r
        new_c += dir_c

  for r in range(n):
    for c in range(n):
      if board[r][c] == 1:
        res[r][c] = 1
        mark_reachable_cells(r, c)
  return res


def run_tests():
  tests = [
      ([[0, 0, 0, 1],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [1, 0, 0, 0]],
       [[1, 1, 1, 1],
        [1, 0, 1, 1],
        [1, 1, 0, 1],
        [1, 1, 1, 1]]),
      # Edge case - 1x1 board with queen
      ([[1]], [[1]]),
      # Edge case - 1x1 board without queen
      ([[0]], [[0]]),
      # Edge case - no queens
      ([[0, 0], [0, 0]], [[0, 0], [0, 0]]),
  ]

  for board, want in tests:
    got = safe_cells(board)
    assert got == want, f"\nsafe_cells({board}): got: {got}, want: {want}\n"

run_tests()
