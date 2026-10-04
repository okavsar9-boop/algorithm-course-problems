# 17.5 - Fewest Script Runs
# Run: python3 17_05_fewest_script_runs.py

import math

def minimum_script_runs(meetings):
  meetings.sort(key=lambda x: x[1])
  count = 0
  prev_end = -math.inf
  for l, r in meetings:
    if l > prev_end:
      count += 1
      prev_end = r
  return count


def run_tests():
  # Example test cases
  tests = [
      # Example 1
      ([[2, 3], [1, 4], [2, 3], [3, 6], [8, 10]], 2),
      # Example 2 - Counterexample from solution
      ([[1, 3], [2, 5], [3, 6], [4, 7], [5, 8], [7, 9]], 2),

      # Additional test cases
      # Edge case: No meetings
      ([], 0),
      # Edge case: All meetings overlap
      ([[1, 5], [2, 6], [3, 7]], 1),
      # Edge case: Non-overlapping meetings
      ([[1, 2], [3, 4], [5, 6]], 3),
      # Edge case: Single meeting
      ([[1, 2]], 1),
      # Edge case: Large number of meetings
      ([[i, i + 1] for i in range(100)], 50),
  ]

  for meetings, want in tests:
    got = minimum_script_runs(meetings)
    assert got == want, f"\nminimum_script_runs({meetings}): got: {        got}, want: {want}\n"

run_tests()
