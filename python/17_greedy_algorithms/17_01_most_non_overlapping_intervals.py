# 17.1 - Most Non-Overlapping Intervals
# Run: python3 17_01_most_non_overlapping_intervals.py

import math

def most_non_overlapping_intervals(intervals):
  intervals.sort(key=lambda x: x[1])
  count = 0
  prev_end = -math.inf
  for l, r in intervals:
    if l > prev_end:
      count += 1
      prev_end = r
  return count


def run_tests():
  # Example test cases
  tests = [
      # Example 1
      ([[2, 3], [1, 4], [2, 3], [3, 6], [8, 9]], 2),

      # Additional test cases
      # Edge case: No intervals
      ([], 0),
      # Edge case: All intervals overlap
      ([[1, 5], [2, 6], [3, 7]], 1),
      ([[1, 2], [2, 3], [3, 4]], 2),
      # Edge case: Non-overlapping intervals (considering inclusive endpoints)
      ([[1, 2], [3, 4], [5, 6]], 3),
      # Edge case: Single interval
      ([[1, 2]], 1),
      # Edge case: Large number of intervals
      ([[i, i + 1] for i in range(100)], 50),
  ]

  for intervals, want in tests:
    got = most_non_overlapping_intervals(intervals)
    assert got == want, f"\nmost_non_overlapping_intervals({intervals}): got: {        got}, want: {want}\n"

run_tests()
