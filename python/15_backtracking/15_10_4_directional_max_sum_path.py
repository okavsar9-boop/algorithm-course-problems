# 15.10 - 4-Directional Max-Sum Path
# Run: python3 15_10_4_directional_max_sum_path.py

import math

def four_directional_max_sum_path(grid):
  R, C = len(grid), len(grid[0])
  best_sum = -math.inf
  seen = {(0, 0)}

  def visit(r, c, path_sum):
    nonlocal best_sum

    # Process leaf/full solution
    if r == R - 1 and c == C - 1:
      if path_sum > best_sum:
        best_sum = path_sum
      return

    # Try each direction
    for dr, dc in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
      nr, nc = r + dr, c + dc
      if (nr, nc) not in seen and 0 <= nr < R and 0 <= nc < C:
        seen.add((nr, nc))
        visit(nr, nc, path_sum + grid[nr][nc])
        seen.remove((nr, nc))

  # Start from top-left
  visit(0, 0, grid[0][0])
  return best_sum


def run_tests():
  tests = [
      # Example from the book
      ([[1, -4, 3],
        [-2, 7, -6],
        [5, -4, 9]], 12),
      # Edge case - 1x1 grid
      ([[5]], 5),
      # Edge case - 1xN grid
      ([[1, 2, 3]], 6),
      # Edge case - Nx1 grid
      ([[1], [2], [3]], 6),
      # 2x2 grid
      ([[1, 2], [3, 4]], 8),
  ]

  for grid, want in tests:
    got = four_directional_max_sum_path(grid)
    assert got == want, f"\nfour_directional_max_sum_path({grid}): got: {        got}, want: {want}\n"

run_tests()
