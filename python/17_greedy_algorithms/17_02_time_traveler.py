# 17.2 - Time Traveler
# Run: python3 17_02_time_traveler.py

def can_reach_goal(jumping_points, k, max_aging):
  n = len(jumping_points)
  gaps = []
  for i in range(1, n):
    gaps.append(jumping_points[i] - jumping_points[i - 1])
  gaps.sort()
  total_aging = sum(gaps[:n - 1 - k])
  return total_aging <= max_aging


def run_tests():
  # Example test cases
  tests = [
      # Example 1
      ([2020, 2024], 0, 3, False),
      # Example 2
      ([2020, 2024], 1, 1, True),
      # Example 3
      ([1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001, 2021], 4, 45, True),

      # Additional test cases
      # Edge case: No jumps allowed, but within aging limit
      ([2000, 2001, 2002], 0, 2, True),
      # Edge case: No jumps allowed, exceeding aging limit
      ([2000, 2005, 2010], 0, 4, False),
  ]

  for jumping_points, jumps, max_aging, want in tests:
    got = can_reach_goal(jumping_points, jumps, max_aging)
    assert got == want, f"\ncan_reach_goal({jumping_points}, {jumps}, {max_aging}): got: {got}, want: {want}\n"

run_tests()
