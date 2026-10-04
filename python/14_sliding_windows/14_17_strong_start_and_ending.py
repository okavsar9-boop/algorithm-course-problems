# 14.17 - Strong Start and Ending
# Run: python3 14_17_strong_start_and_ending.py

import math

def max_good_days_start_and_end_two_pointers(projected_sales, k):
  n = len(projected_sales)

  # Count total bad days
  B = sum(1 for x in projected_sales if x < 10)
  if B <= k:
    return n

  # General case: there are more than k bad days

  suffix_ptr = n  # suffix pointer
  suffix_bad = 0  # bad days in suffix

  for i in range(n - 1, -1, -1):
    if projected_sales[i] < 10:
      if suffix_bad == k:
        suffix_ptr = i + 1
        break
      else:
        suffix_bad += 1

  # Initial result: empty prefix + best suffix found
  res = n - suffix_ptr

  prefix_bad = 0  # bad days in prefix
  for prefix_ptr in range(n):
    if projected_sales[prefix_ptr] < 10:
      prefix_bad += 1

    # Shrink suffix to maintain constraint:
    # total bad days in prefix + suffix <= k
    while suffix_ptr < n and prefix_bad + suffix_bad > k:
      if projected_sales[suffix_ptr] < 10:
        suffix_bad -= 1
      suffix_ptr += 1

    if prefix_bad > k:
      break

    # Calculate combined length
    prefix_length = prefix_ptr + 1
    suffix_length = n - suffix_ptr
    res = max(res, prefix_length + suffix_length)

  return res

"If we look for a subarray instead of for a prefix and a suffix, what property should the subarray have?"

def max_good_days_start_and_end_sliding_window(projected_sales, k):
  n = len(projected_sales)

  # Count total bad days
  B = sum(1 for x in projected_sales if x < 10)
  if B <= k:
    return n

  target_bad = B - k

  # Find minimum window containing target_bad bad days
  l, r = 0, 0
  window_bad = 0
  min_window = math.inf

  while True:
    must_grow = window_bad < target_bad
    if must_grow:
      if r == len(projected_sales):
        break
      if projected_sales[r] < 10:
        window_bad += 1
      r += 1
    else:
      if r - l < min_window:
        min_window = r - l
      if projected_sales[l] < 10:
        window_bad -= 1
      l += 1

  # If we can't find a window with target_bad bad days
  if min_window == math.inf:
    return len(projected_sales)

  # Return length of prefix + suffix of good days
  return len(projected_sales) - min_window


def run_tests():
  tests = [
      # Example 1 from the book
      ([10, 0, 0, 0, 10, 0, 0, 10], 2, 5),
      # Example 2 from the book
      ([0, 10, 0, 10], 1, 3),
      # Example 3
      ([5, 5, 5], 2, 2),
      # Edge case - empty array
      ([], 1, 0),
      # Edge case - k=0
      ([5, 10, 5], 0, 0),
      # Edge case - all good days
      ([10, 10, 10], 1, 3),
      # Edge case - all bad days
      ([5, 5, 5], 2, 2),
      # Edge case - k >= number of bad days
      ([5, 10, 5, 10], 3, 4),
  ]

  for projected_sales, k, want in tests:
    got_two_pointers = max_good_days_start_and_end_two_pointers(
        projected_sales, k)
    assert got_two_pointers == want, f"\nmax_good_days_start_and_end_two_pointers({projected_sales}, {k}): got: {got_two_pointers}, want: {want}\n"

    got_sliding_window = max_good_days_start_and_end_sliding_window(
        projected_sales, k)
    assert got_sliding_window == want, f"\nmax_good_days_start_and_end_sliding_window({projected_sales}, {k}): got: {got_sliding_window}, want: {want}\n"

run_tests()
