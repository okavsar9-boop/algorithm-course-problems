# 16.4 - Count 0-Sum Paths
# Run: python3 16_04_count_0_sum_paths.py

def count_0_sum_paths(grid):
  R, C = len(grid), len(grid[0])
  memo = {}

  def num_paths(r, c):
    if r >= R or c >= C or grid[r][c] == 1:
      return 0
    if (r, c) in memo:
      return memo[(r, c)]
    if r == R - 1 and c == C - 1:
      return 1
    memo[(r, c)] = num_paths(r + 1, c) + \
        num_paths(r, c + 1) + num_paths(r + 1, c + 1)
    return memo[(r, c)]

  return num_paths(0, 0)


def run_tests():
  tests = [
      ([[0, 1, 1],
        [0, 0, 0],
          [1, 0, 0]], 7),
      ([[1]], 0),
      ([[0, 0],
        [0, 0]], 3),
      ([[0]], 1),
      ([[0, 0, 0],
        [0, 1, 0],
          [0, 0, 0]], 4),
  ]
  for grid, want in tests:
    got = count_0_sum_paths(grid)
    assert got == want, f"\ncount_0_sum_paths({grid}): got: {        got}, want: {want}\n"

run_tests()
