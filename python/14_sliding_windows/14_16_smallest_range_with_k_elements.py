# 14.16 - Smallest Range With k Elements
# Run: python3 14_16_smallest_range_with_k_elements.py

def smallest_range_with_k_elements(arr, k):
  arr.sort()
  l, r = 0, 0
  best_low, best_high = 0, float('inf')
  while True:
    must_grow = (r - l) < k
    if must_grow:
      if r == len(arr):
        break
      r += 1
    else:
      if arr[r - 1] - arr[l] < best_high - best_low:
        best_low, best_high = arr[l], arr[r - 1]
      l += 1
  return [best_low, best_high]


def run_tests():
  tests = [
      # Example 1 from the book
      ([1, 2, 5, 7, 8], 3, [5, 8]),
      # Example 2 from the book - both [2,5] and [5,8] are valid
      ([5, 5, 2, 2, 8, 8], 3, [2, 5]),
      # Example 3 from the book
      ([0], 1, [0, 0]),
      # Edge case - k=len(arr)
      ([1, 5, 10], 3, [1, 10]),
      # Edge case - all same number
      ([5, 5, 5], 2, [5, 5]),
  ]
  for arr, k, want in tests:
    got = smallest_range_with_k_elements(arr, k)
    # For this problem, there might be multiple valid answers
    # We check if the range contains at least k elements and is minimal
    count = sum(1 for x in arr if want[0] <= x <= want[1])
    assert count >= k, f"\nsmallest_range_with_k_elements({arr}, {k}): range {        got} contains fewer than {k} elements\n"
    assert got[1] - got[0] <= want[1] - \
        want[0], f"\nsmallest_range_with_k_elements({arr}, {k}): range {        got} is larger than {want}\n"

run_tests()
