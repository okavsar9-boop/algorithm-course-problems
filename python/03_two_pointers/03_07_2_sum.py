# 3.7 - 2-Sum
# Run: python3 03_07_2_sum.py

def two_sum(arr):
  l, r = 0, len(arr) - 1
  while l < r:
    if arr[l] + arr[r] > 0:
      r -= 1
    elif arr[l] + arr[r] < 0:
      l += 1
    else:
      return True
  return False


def run_tests():
  tests = [ # Example 1 from the book
    ([-5, -2, -1, 1, 1, 10], True), # Example 2 from the book
    ([-3, 0, 0, 1, 2], True), # Example 3 from the book
    ([-5, -3, -1, 0, 2, 4, 6], False), # Additional test cases
    ([], False),
    ([0], False),
    ([-1, 1], True),
    ([-2, -1, 0, 1], True),
    ([1, 2, 3, 4], False),
    ]
  for arr, want in tests:
    got = two_sum(arr)
    assert got == want, f"\ntwo_sum({arr}): got: {got}, want: {want}\n"

run_tests()
