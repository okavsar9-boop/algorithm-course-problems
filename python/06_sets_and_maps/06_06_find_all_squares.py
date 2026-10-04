# 6.6 - Find All Squares
# Run: python3 06_06_find_all_squares.py

def find_squared(arr):
  num_to_index = {}
  for i, num in enumerate(arr):
    num_to_index[num] = i

  res = []
  for i, num in enumerate(arr):
    square = num ** 2
    if square in num_to_index:
      res.append([i, num_to_index[square]])
  return res


def run_tests():
  tests = [
      # Example 
      ([4, 10, 3, 100, 5, 2, 10000], [[5, 0], [1, 3], [3, 6]]),
      # Additional test cases
      ([], []),
      ([1], [[0, 0]]),
      ([2, 4], [[0, 1]]),
  ]
  for arr, want in tests:
    got = find_squared(arr)
    # Sort both lists to compare them regardless of order
    got.sort()
    want.sort()
    assert got == want, f"\nfind_squared({arr}): got: {got}, want: {want}\n"

run_tests()
