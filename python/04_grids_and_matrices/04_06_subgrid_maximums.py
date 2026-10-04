# 4.6 - Subgrid Maximums
# Run: python3 04_06_subgrid_maximums.py

def subgrid_maximums(grid):
  R, C = len(grid), len(grid[0])
  res = [row.copy() for row in grid]
  for r in range(R - 1, -1, -1):
    for c in range(C - 1, -1, -1):
      if r + 1 < R:
        res[r][c] = max(res[r][c], res[r + 1][c])
      if c + 1 < C:
        res[r][c] = max(res[r][c], res[r][c + 1])
  return res


def run_tests():
  tests = [
      # Example from book
      ([[1, 5, 3],
        [4, -1, 0],
        [2, 0, 2]],
       [[5, 5, 3],
        [4, 2, 2],
        [2, 2, 2]]),
      # Edge case - 1x1 grid
      ([[5]], [[5]]),
      # Edge case - single row
      ([[1, 2, 3]], [[3, 3, 3]]),
      # Edge case - single column
      ([[1], [2], [3]], [[3], [3], [3]]),
      # Edge case - negative numbers
      ([[-1, -2],
        [-3, -4]],
       [[-1, -2],
        [-3, -4]]),
  ]

  for grid, want in tests:
    got = subgrid_maximums(grid)
    assert got == want, f"\nsubgrid_maximums({grid}): got: {        got}, want: {want}\n"

run_tests()
