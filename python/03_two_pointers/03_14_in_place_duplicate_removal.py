# 3.14 - In-Place Duplicate Removal
# Run: python3 03_14_in_place_duplicate_removal.py

def remove_duplicates(arr):
  s, w = 0, 0
  while s < len(arr):
    must_keep = s == 0 or arr[s] != arr[s - 1]
    if must_keep:
      arr[w] = arr[s]
      w += 1
    s += 1
  return w


def run_tests():
  tests = [
      # Example from the book
      ([1, 2, 2, 3, 3, 3, 5], 4, [1, 2, 3, 5]),
      # Additional test cases
      ([], 0, []),
      ([1], 1, [1]),
      ([1, 1], 1, [1]),
      ([1, 2], 2, [1, 2]),
      ([1, 1, 1], 1, [1]),
      ([1, 2, 2, 2, 3], 3, [1, 2, 3]),
  ]
  for arr, want_len, want_prefix in tests:
    arr_copy = arr.copy()  # Make a copy since remove_duplicates modifies in place
    got_len = remove_duplicates(arr_copy)
    assert got_len == want_len, \
        f"\nremove_duplicates({arr}): got length: {            got_len}, want length: {want_len}\n"
    assert arr_copy[:want_len] == want_prefix, \
        f"\nremove_duplicates({arr}): got prefix: {        arr_copy[:want_len]}, want prefix: {want_prefix}\n"

run_tests()
