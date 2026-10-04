# 4.8 - Matrix Operations
# Run: python3 04_08_matrix_operations.py

class Matrix:
  def __init__(self, grid):
    self.matrix = [row.copy() for row in grid]

  def transpose(self):
    matrix = self.matrix
    for r in range(len(matrix)):
      for c in range(r):
        matrix[r][c], matrix[c][r] = matrix[c][r], matrix[r][c]

  def reflect_horizontally(self):
    self.matrix.reverse()

  def reflect_vertically(self):
    for row in self.matrix:
      row.reverse()

  def rotate_clockwise(self):
    self.transpose()
    self.reflect_vertically()

  def rotate_counterclockwise(self):
    self.transpose()
    self.reflect_horizontally()


def run_tests():
  tests = [
      # Test transpose
      ([[1, 2], [3, 4]], "transpose", [[1, 3], [2, 4]]),
      # Test horizontal reflection
      ([[1, 2], [3, 4]], "reflect_horizontally", [[3, 4], [1, 2]]),
      # Test vertical reflection
      ([[1, 2], [3, 4]], "reflect_vertically", [[2, 1], [4, 3]]),
      # Test clockwise rotation
      ([[1, 2], [3, 4]], "rotate_clockwise", [[3, 1], [4, 2]]),
      # Test counterclockwise rotation
      ([[1, 2], [3, 4]], "rotate_counterclockwise", [[2, 4], [1, 3]]),
      # Edge case - 1x1 matrix
      ([[5]], "transpose", [[5]]),
      # Edge case - 3x3 matrix
      ([[1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]], "rotate_clockwise",
       [[7, 4, 1],
        [8, 5, 2],
        [9, 6, 3]]),
  ]

  for grid, operation, want in tests:
    matrix = Matrix(grid)
    getattr(matrix, operation)()
    got = matrix.matrix
    assert got == want, (f"\nMatrix({grid}).{operation}(): "
                         f"got: {got}, want: {want}\n")

run_tests()
