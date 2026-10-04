# 3.2 - Smaller Prefixes
# Run: python3 03_02_smaller_prefixes.py

def smaller_prefixes(arr):
  sp, fp = 0, 0
  slow_sum, fast_sum = 0, 0
  while fp < len(arr):
    slow_sum += arr[sp]
    fast_sum += arr[fp] + arr[fp + 1]
    if slow_sum >= fast_sum:
      return False
    sp += 1
    fp += 2
  return True


def run_tests():
  tests = [ # Example 1 from the book
    ([1, 2, 2, -1], True), # Example 2 from the book
    ([1, 2, -2, 1, 3, 5], False), # Additional test cases
    ([0, 3, 7, 12, 10, 5, 0, 1], True),
    ([], True),
    ([1, 2], True),
    ([2, 1], True),
    ([-2, 1, -4, 5, -3, 7], True),
    ([-2, 1, -14, 8, -3, 2], False),
    ]
  for arr, want in tests:
    got = smaller_prefixes(arr)
    assert got == want, f"\nsmaller_prefixes({arr}): got: {got}, want: {want}\n"

run_tests()
