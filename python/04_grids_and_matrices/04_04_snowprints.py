# 4.4 - Snowprints
# Run: python3 04_04_snowprints.py

def distance_to_river(field):
  R, C = len(field), len(field[0])

  def has_footprints(r, c):
    return 0 <= r < R and 0 <= c < C and field[r][c] == 1

  # Find starting position in first column
  r, c = 0, 0
  while field[r][c] != 1:
    r += 1

  closest = r

  # Track fox through remaining columns
  while c < C - 1:  # Stop before last column
    for dir_r in [-1, 0, 1]:  # Check up, same level, down
      new_r = r + dir_r
      new_c = c + 1
      if has_footprints(new_r, new_c):
        r, c = new_r, new_c
        closest = min(closest, r)
        break

  return closest


def run_tests():
  tests = [
      # Example from book
      ([[0, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [1, 1, 0, 1, 0, 0],
        [0, 0, 0, 0, 1, 1]], 1),
      # Edge case - top of grid
      ([[1, 1, 1, 1],
        [0, 0, 0, 0],
        [0, 0, 0, 0]], 0),
      # Edge case - bottom of grid
      ([[0, 0, 0, 0],
        [0, 0, 0, 0],
        [1, 1, 1, 1]], 2),
      # Edge case - single column
      ([[0], [1], [0]], 1),
      # Edge case - single row
      ([[1]], 0),
      # Edge case - zigzag path
      ([[0, 0, 0, 0],
        [1, 0, 0, 0],
        [0, 1, 0, 0],
        [0, 0, 1, 1]], 1),
      # Test max up/down movement
      ([[0, 0, 0, 0],
        [1, 0, 0, 0],
        [0, 1, 0, 0],
        [0, 0, 1, 0],
        [0, 0, 0, 1]], 1),
      # Test staying at same level
      ([[0, 0, 0, 0],
        [1, 1, 1, 1],
        [0, 0, 0, 0]], 1),
      # Test going up then down
      ([[0, 0, 0, 0],
        [1, 0, 0, 0],
        [0, 1, 1, 0],
        [0, 0, 0, 1]], 1)
  ]

  for field, want in tests:
    got = distance_to_river(field)
    assert got == want, f"\ndistance_to_river({field}): got: {        got}, want: {want}\n"

run_tests()
