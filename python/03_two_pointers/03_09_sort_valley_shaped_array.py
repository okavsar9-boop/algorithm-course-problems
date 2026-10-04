# 3.9 - Sort Valley-Shaped Array
# Run: python3 03_09_sort_valley_shaped_array.py

def sort_valley_array(arr):
  if len(arr) == 0:
    return []
  l, r = 0, len(arr) - 1
  res = [0] * len(arr)
  i = len(arr) - 1
  while l < r:
    if arr[l] >= arr[r]:
      res[i] = arr[l]
      l += 1
      i -= 1
    else:
      res[i] = arr[r]
      r -= 1
      i -= 1
  res[0] = arr[l]
  return res


def run_tests():
  tests = [ # Example 1 from the book
    ([8, 4, 2, 6], [2, 4, 6, 8]), # Example 2 from the book
    ([1, 2], [1, 2]), # Example 3 from the book
    ([2, 2, 1, 1], [1, 1, 2, 2]), # Additional test cases
    ([], []),
    ([1], [1]),
    ([3, 2, 1, 4], [1, 2, 3, 4]),
    ([5, 4, 3, 2, 1, 2, 3], [1, 2, 2, 3, 3, 4, 5]),
    ([1, 1, 1, 1], [1, 1, 1, 1]),
    ]
  for arr, want in tests:
    got = sort_valley_array(arr)
    assert got == want, f"\nsort_valley_array({arr}): got: {got}, want: {want}\n"

run_tests()
