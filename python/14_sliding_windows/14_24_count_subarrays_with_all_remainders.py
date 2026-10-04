# 14.24 - Count Subarrays With All Remainders
# Run: python3 14_24_count_subarrays_with_all_remainders.py

from collections import defaultdict

def count_all_3_groups(arr):
  n = len(arr)
  total_count = n * (n + 1) // 2
  return total_count - count_at_most_2_groups(arr)

def count_at_most_2_groups(arr):
  l, r = 0, 0
  window_counts = defaultdict(int)
  count = 0
  while r < len(arr):
    can_grow = arr[r] % 3 in window_counts or len(window_counts) < 2
    if can_grow:
      window_counts[arr[r] % 3] += 1
      r += 1
      count += r - l
    else:
      window_counts[arr[l] % 3] -= 1
      if window_counts[arr[l] % 3] == 0:
        del window_counts[arr[l] % 3]
      l += 1
  return count


def run_tests():
  tests = [
      # Example 1 from the book
      ([9, 8, 7], 1),
      # Example 2 from the book
      ([1, 2, 3, 4, 5], 6),
      # Example 3 from the book
      ([1, 3, 4, 6, 7, 9], 0),
      # Edge case - empty array
      ([], 0),
      # Edge case - single element
      ([3], 0),
  ]
  for arr, want in tests:
    got = count_all_3_groups(arr)
    assert got == want, f"\ncount_all_3_groups({arr}): got: {        got}, want: {want}\n"

run_tests()
