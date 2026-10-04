# 3.16 - Dutch Flag Problem
# Run: python3 03_16_dutch_flag_problem.py

def sort_colors(arr):
  # Count occurrences of each color
  r_count = sum(1 for c in arr if c == 'R')
  w_count = sum(1 for c in arr if c == 'W')

  # Rewrite array with the right number of each color
  i = 0
  for _ in range(r_count):
    arr[i] = 'R'
    i += 1
  for _ in range(w_count):
    arr[i] = 'W'
    i += 1
  while i < len(arr):
    arr[i] = 'B'
    i += 1


def run_tests():
  tests = [ # Example from the book
    (list("RWBBWRW"), list("RRWWWBB")), # Additional test cases
    ([], []),
    (list("R"), list("R")),
    (list("W"), list("W")),
    (list("B"), list("B")),
    (list("RW"), list("RW")),
    (list("WR"), list("RW")),
    (list("RWB"), list("RWB")),
    (list("RRRWWBBB"), list("RRRWWBBB")),
    (list("BBBWWRRR"), list("RRRWWBBB")),
    ]
  for arr, want in tests:
    arr_copy = arr.copy() # Make a copy since function modifies in place
    sort_colors(arr_copy)
    assert arr_copy == want, f"\nsort_colors({arr}): got: {arr_copy}, want: {want}\n"

run_tests()
