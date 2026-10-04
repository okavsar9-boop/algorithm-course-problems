# 4.3 - Spiral Order
# Run: python3 04_03_spiral_order.py

def spiral(n):
  
  def is_valid(grid, r, c):
    return 0 <= r < len(grid) and 0 <= c < len(grid[0]) and grid[r][c] == 0

  val = n * n - 1
  res = [[0] * n for _ in range(n)]
  r, c = n - 1, n - 1
  directions = [[-1, 0], [0, -1], [1, 0], [0, 1]]  # Counterclockwise
  dir = 0  # Start going up

  while val > 0:
    res[r][c] = val
    val -= 1
    if not is_valid(res, r + directions[dir][0], c + directions[dir][1]):
      dir = (dir + 1) % 4  # Change directions counterclockwise
    r, c = r + directions[dir][0], c + directions[dir][1]
  return res


def run_tests():
  tests = [
      # Example from book
      (5, [
          [16, 17, 18, 19, 20],
          [15, 4, 5, 6, 21],
          [14, 3, 0, 7, 22],
          [13, 2, 1, 8, 23],
          [12, 11, 10, 9, 24]
      ]),
      # Edge case - 1x1
      (1, [[0]]),
      # Edge case - 3x3
      (3, [
          [4, 5, 6],
          [3, 0, 7],
          [2, 1, 8]
      ]),
  ]

  for n, want in tests:
    got = spiral(n)
    assert got == want, f"\nspiral({n}): got: {got}, want: {want}\n"

run_tests()
