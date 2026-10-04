# 15.1 - Max-Sum Path
# Run: python3 15_01_max_sum_path.py

import math

def max_sum_path_backtracking(grid):
  # Inefficient backtracking solution. DP is better!
  max_sum = -math.inf
  R, C = len(grid), len(grid[0])

  def visit(r, c, cur_sum):
    nonlocal max_sum
    if r == R - 1 and c == C - 1:
      max_sum = max(max_sum, cur_sum)
      return

    if r + 1 < R:
      visit(r + 1, c, cur_sum + grid[r + 1][c])  # Go down.
    if c + 1 < C:
      visit(r, c + 1, cur_sum + grid[r][c + 1])  # Go right.

  visit(0, 0, grid[0][0])
  return max_sum

def max_sum_path_memoization(grid):
  R, C = len(grid), len(grid[0])
  memo = {}

  def dp(r, c):
    if (r, c) in memo:
      return memo[(r, c)]

    if r == R - 1 and c == C - 1:
      return grid[r][c]

    max_sum = -math.inf
    # Try going down
    if r + 1 < R:
      max_sum = max(max_sum, grid[r][c] + dp(r + 1, c))
    # Try going right
    if c + 1 < C:
      max_sum = max(max_sum, grid[r][c] + dp(r, c + 1))

    memo[(r, c)] = max_sum
    return max_sum

  return dp(0, 0)


def run_tests():
  tests = [
      # Example 1 from the book
      ([[1, 4, 3], [2, 7, 6], [5, 8, 9]], 29),
      # Example 2 from the book
      ([[5]], 5),
      # Additional test cases
      # Edge case - single row
      ([[1, 2, 3, 4]], 10),
      # Edge case - single column
      ([[1], [2], [3], [4]], 10),
      # Larger grid
      ([[1, 2, 3], [4, 5, 6], [7, 8, 9]], 29),
      # Edge case - all elements are the same
      ([[1, 1, 1], [1, 1, 1], [1, 1, 1]], 5),
  ]
  for grid, want in tests:
    got_backtracking = max_sum_path_backtracking(grid)
    got_memoization = max_sum_path_memoization(grid)
    assert got_backtracking == got_memoization, (
        f"\nmax_sum_path_backtracking({grid}) != max_sum_path_memoization({grid})\n"
    )
    assert got_backtracking == want, (
        f"\nmax_sum_path_backtracking({grid}): got: {got_backtracking}, want: {want}\n"
    )
    assert got_memoization == want, (
        f"\nmax_sum_path_memoization({grid}): got: {got_memoization}, want: {want}\n"
    )

run_tests()
