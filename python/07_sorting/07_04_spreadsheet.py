# 7.4 - Spreadsheet
# Run: python3 07_04_spreadsheet.py

class Spreadsheet:
  def __init__(self, rows, cols):
    self.rows = rows
    self.cols = cols

    self.sheet = []
    for _ in range(rows):
      self.sheet.append([0] * cols)

  def new(self, rows, cols):
    self.rows = rows
    self.cols = cols
    self.sheet = []
    for _ in range(rows):
      self.sheet.append([0] * cols)

  def set(self, row, col, value):
    self.sheet[row][col] = value

  def get(self, row, col):
    return self.sheet[row][col]

  def sort_rows_by_column(self, col):
    self.sheet.sort(key=lambda row: row[col])

  def sort_columns_by_row(self, row):
    columns_with_values = []
    for col in range(self.cols):
      columns_with_values.append((col, self.sheet[row][col]))

    sorted_columns = sorted(columns_with_values, key=lambda x: x[1])
    sorted_sheet = []
    for r in range(self.rows):
      new_row = []
      for col, _ in sorted_columns:
        new_row.append(self.sheet[r][col])
      sorted_sheet.append(new_row)
    self.sheet = sorted_sheet


def run_tests():
  tests = [
      # Example from the book
      (lambda s: [
          s.new(3, 3),
          s.set(0, 0, 5),
          s.set(0, 1, 3),
          s.set(0, 2, 8),
          s.set(1, 0, 6),
          s.set(2, 1, 1),
          s.sort_columns_by_row(0),
          s.sort_rows_by_column(1)
      ], [
          [1, 0, 0],
          [3, 5, 8],
          [0, 6, 0],
      ]),
      # Edge case - 1x1 spreadsheet
      (lambda s: [
          s.new(1, 1),
          s.set(0, 0, 42)
      ], [
          [42],
      ]),
      # Edge case - sort empty rows
      (lambda s: [
          s.new(3, 2),
          s.sort_rows_by_column(0)
      ], [
          [0, 0],
          [0, 0],
          [0, 0],
      ]),
  ]

  for operations, want in tests:
    s = Spreadsheet(0, 0)
    operations(s)
    for r in range(len(want)):
      for c in range(len(want[0])):
        got = s.get(r, c)
        expect = want[r][c]
        assert got == expect, f"\nget({r}, {c}): got: {got}, want: {expect}\n"

run_tests()
