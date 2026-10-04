# 9.2 - Nested Array Sum
# Run: python3 09_02_nested_array_sum.py

# Lazy checking: check if the argument is an integer at the start of
# each call. This handles integer elements encountered during recursion.
def nested_array_sum(arr):
  if isinstance(arr, int):
    return arr
  res = 0
  for elem in arr:
    res += nested_array_sum(elem)
  return res

# Eager checking: check if each element is an integer before recursing.
# This avoids recursing on integers entirely.
def nested_array_sum_eager(arr):
  res = 0
  for elem in arr:
    if isinstance(elem, int):
      res += elem
    else:
      res += nested_array_sum_eager(elem)
  return res


def run_tests():
  tests = [
      # Example 1 from book
      ([1, [2, 3], [4, [5]], 6], 21),
      # Example 2 from book
      ([[[[1]], 2]], 3),
      # Example 3 from book
      ([], 0),
      # Edge case - all nested single numbers
      ([[[[[1]]]]], 1),
      # Edge case - multiple empty arrays
      ([[], [], []], 0),
      # Edge case - mixed empty and non-empty arrays
      ([[], [1, 2], [], [3]], 6),
      # Edge case - deeply nested mixed arrays
      ([1, [2, [], [3, []], []], [4, [5, []]]], 15),
      # Edge case - all zeros
      ([0, [0, 0], [0, [0]], 0], 0),
      # Edge case - negative numbers
      ([-1, [-2, 3], [4, [-5]], 6], 5),
      # Stress test - large deeply nested array
      ([list(range(10)), [list(range(10, 20)), list(range(20, 30))],
        [list(range(30, 40)), [list(range(40, 50))]], list(range(50, 60))],
          sum(range(60)))
  ]
  # Test both implementations to verify they produce the same results
  for arr, want in tests:
    got = nested_array_sum(arr)
    assert got == want, f"\nnested_array_sum({arr}): got: {got}, want: {want}\n"
    got_eager = nested_array_sum_eager(arr)
    assert got_eager == want, \
        f"\nnested_array_sum_eager({arr}): got: {got_eager}, want: {want}\n"

run_tests()
