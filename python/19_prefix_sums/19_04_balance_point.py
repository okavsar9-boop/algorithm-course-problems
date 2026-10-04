# 19.4 - Balance Point
# Run: python3 19_04_balance_point.py

def balanced_index(arr):
  prefix_sum = 0
  postfix_sum = sum(arr) - arr[0]

  for i in range(len(arr)):
    if prefix_sum == postfix_sum:
      return i
    prefix_sum += arr[i]
    if i + 1 < len(arr):
      postfix_sum -= arr[i+1]
  return -1


def run_tests():
  tests = [
    # Example from the book
    ([3, 5, -2, 7, 2, 2, 2], 3),
    # Edge case: No balance point
    ([1, 2, 3], -1),
    # Edge case: Balance at start
    ([0, 1, -1], 0),
    # Edge case: Balance at end
    ([1, -1, 0], 2),
  ]

  for arr, want in tests:
    got = balanced_index(arr)
    assert got == want, f"\nbalanced_index({arr}): got: {got}, want: {want}\n"

run_tests()
