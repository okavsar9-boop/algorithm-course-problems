# 17.6 - Time Traveler Max Year
# Run: python3 17_06_time_traveler_max_year.py

import heapq

def latest_reachable_year_binary_search(jumping_points, k, max_aging):
  n = len(jumping_points)

  def gap_size(year_idx):
    # Gap from jumping_points[year_idx] to jumping_points[year_idx + 1]
    return jumping_points[year_idx + 1] - jumping_points[year_idx]

  # Sort gap indices (0 to n-2) by size (descending)
  sorted_gaps = sorted(range(n - 1), key=gap_size, reverse=True)

  def year_reached(gap_indices_to_skip):
    # Year we reach if we skip the gaps in gap_indices_to_skip
    # (assuming we can reach them).
    return jumping_points[0] + max_aging + sum(gap_size(i) for i in gap_indices_to_skip)

  # Returns whether we can reach the end of gap idx.
  # Takes O(n) time by leveraging the sorted_gaps array.
  def can_reach_year_linear(year_idx):
    total_aging = 0
    jumps_used = 0
    for idx in sorted_gaps:
      if idx >= year_idx:
        continue
      if jumps_used < k:
        jumps_used += 1
      else:
        total_aging += gap_size(idx)
        if total_aging > max_aging:
          return False
    return True

  # Binary search over year indices (0 to n-1).
  # Goal: find the transition point from the last year we can reach to the
  # first year we can't (if any).
  # Before region: we can reach the end of year year_idx.
  # After region: we can't reach the end of year year_idx.
  def is_before(year_idx):
    return can_reach_year_linear(year_idx)

  l, r = 0, n - 1
  if is_before(r):
    # Edge case: we can reach the last year.
    # We skip the k largest gaps and age naturally through the rest.
    return year_reached(sorted_gaps[:k])

  # Now we can binary search until l and r are next to each other.
  # O(n log n) time.
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  # Now we know we can reach year l, but not year l+1.
  # Thus, we should skip the largest k gaps up to l.
  sorted_gaps_up_to_l = sorted(range(l), key=gap_size, reverse=True)
  return year_reached(sorted_gaps_up_to_l[:k])

"can I get here with the available jumps?"

def can_reach_year(jumping_points, k, max_aging, year_idx):
  # Easier Version of the problem:
  # Given a year = jumping_points[year_idx], is it possible to reach it?
  gaps = []
  for i in range(year_idx):
    gaps.append(jumping_points[i + 1] - jumping_points[i])

  # Sort gaps by size (descending)
  gaps.sort(reverse=True)

  total_aging = sum(gaps[k:]) if len(gaps) > k else 0
  return total_aging <= max_aging

def latest_reachable_year_greedy(jumping_points, k, max_aging):
  gaps = []
  for i in range(1, len(jumping_points)):
    gaps.append(jumping_points[i] - jumping_points[i - 1])

  min_heap = []
  total_gap_sum = 0
  sum_heap = 0
  for i, gap in enumerate(gaps):
    aged = total_gap_sum - sum_heap
    heapq.heappush(min_heap, gap)
    sum_heap += gap
    total_gap_sum += gap
    if len(min_heap) > k:
      smallest_jump = heapq.heappop(min_heap)
      sum_heap -= smallest_jump
    new_aged = total_gap_sum - sum_heap
    if new_aged > max_aging:
      # We can't reach the end of gap i.
      # We get to jumping_points[i] and age naturally from there.
      remaining_aging = max_aging - aged
      return jumping_points[i] + remaining_aging

  # Reached the last jumping point
  aged = total_gap_sum - sum_heap
  remaining_aging = max_aging - aged
  return jumping_points[len(jumping_points) - 1] + remaining_aging


def run_tests():
  # Example test cases
  # (jumping_points, k, max_aging, want)
  tests = [
      # Example 1
      ([2020, 2024], 0, 2, 2022),
      # Example 2
      ([2020, 2024], 1, 1, 2025),
      # Example 3
      ([1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001, 2021], 4, 45, 2021),
      # Example 4
      ([1, 10, 30], 1, 5, 15),
      # Example 5
      ([1, 3, 6, 7, 11, 16, 17, 19], 2, 4, 12),

      ([1, 5, 10], 1, 2, 7),
      ([1, 3, 10, 20], 1, 3, 11),
      ([1, 4, 15], 1, 4, 16),

      # Additional test cases
      # Edge case: No jumps allowed, but within aging limit
      ([2000, 2001, 2002], 0, 2, 2002),
      # Edge case: No jumps allowed, exceeding aging limit
      ([2000, 2005, 2010], 0, 4, 2004),
  ]

  for jumping_points, k, max_aging, want in tests:
    got = latest_reachable_year_binary_search(jumping_points, k, max_aging)
    assert got == want, f"\nlatest_reachable_year_binary_search({jumping_points}, {k}, {max_aging}): got: {got}, want: {want}\n"

    got = latest_reachable_year_greedy(jumping_points, k, max_aging)
    assert got == want, f"\nlatest_reachable_year({jumping_points}, {k}, {max_aging}): got: {got}, want: {want}\n"

run_tests()
