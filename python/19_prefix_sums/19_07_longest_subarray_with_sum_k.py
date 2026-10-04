# 19.7 - Longest Subarray With Sum K
# Run: python3 19_07_longest_subarray_with_sum_k.py

def longest_subarray_with_sum_k(arr, k):
  prefix_sum = [0] * len(arr)
  prefix_sum[0] = arr[0]
  for i in range(1, len(arr)):
    prefix_sum[i] = prefix_sum[i - 1] + arr[i]

  prefix_sum_to_index = {0: -1}  # For the empty prefix.
  res = -1
  for r, val in enumerate(prefix_sum):
    if val - k in prefix_sum_to_index:
      l = prefix_sum_to_index[val - k]
      res = max(res, r - l)
    if val not in prefix_sum_to_index:
      prefix_sum_to_index[val] = r
  return res


def run_tests():
  tests = [
      # Example from the book
      ([1, 2, 3, 2, 1], 3, 2),
      ([-1, -2, -3, 2, 1], -3, 5),
      # Edge case: All zeros
      ([0, 0, 0], 0, 3),
      # Edge case: No subarray with sum k
      ([1, 2, 3], 10, -1),
  ]

  for arr, k, want in tests:
    got = longest_subarray_with_sum_k(arr, k)
    assert got == want, f"\ncount_subarrays({arr}, {k}): got: {        got}, want: {want}\n"

run_tests()
