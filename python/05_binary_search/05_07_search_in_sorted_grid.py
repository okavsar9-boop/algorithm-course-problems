# 5.7 - Search In Sorted Grid
# Run: python3 05_07_search_in_sorted_grid.py

def search_in_sorted_grid(grid, target):
  R, C = len(grid), len(grid[0])

  # Before is < target, after is >= target
  def is_before(i):
    row, col = i // C, i % C
    return grid[row][col] < target

  # Ensure the first element is 'before' and the last element is 'after'
  if grid[0][0] > target or grid[R-1][C-1] < target:
    return [-1, -1]
  if grid[0][0] == target:
    return [0, 0]
  if grid[R-1][C-1] == target:
    return [R-1, C-1]

  # Binary search for the transition point
  l, r = 0, R * C - 1
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  row, col = r // C, r % C
  if grid[row][col] == target:
    return [row, col]
  return [-1, -1]


def run_tests():
  tests = [
      ([[1, 3, 5], [7, 9, 11], [13, 15, 17]], 9, [1, 1]),  # Example 1
      ([[1, 3, 5], [7, 9, 11]], 4, [-1, -1]),  # Example 2
      ([[2, 3], [4, 5]], 1, [-1, -1]),  # 2x2 grid, all grid after
      ([[1, 2], [3, 4]], 5, [-1, -1]),  # 2x2 grid, all grid before
      ([[1, 2], [3, 4], [5, 6]], 1, [0, 0]),  # 3x2 grid, first element
      ([[1, 2, 3], [4, 5, 6]], 6, [1, 2]),  # 2x3 grid, last element
      ([[7]], 7, [0, 0]),  # Single element edge case
      ([[7]], 6, [-1, -1])  # Single element edge case (not found)
  ]

  for grid, target, want in tests:
    got = search_in_sorted_grid(grid, target)
    assert got == want, (
        f"\nsearch_in_sorted_grid({grid}, {target}): got: {got}, want: {want}\n")

run_tests()
