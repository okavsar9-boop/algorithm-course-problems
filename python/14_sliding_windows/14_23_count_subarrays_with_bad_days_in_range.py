# 14.23 - Count Subarrays With Bad Days in Range
# Run: python3 14_23_count_subarrays_with_bad_days_in_range.py

def count_bad_days_range(sales, k1, k2):
  if k1 == 0:
    return count_at_most_k_bad_days(sales, k2)
  return count_at_most_k_bad_days(sales, k2) - count_at_most_k_bad_days(sales, k1 - 1)

def count_at_most_k_bad_days(sales, k):
  l, r = 0, 0
  window_bad_days = 0
  count = 0
  while r < len(sales):
    can_grow = sales[r] >= 10 or window_bad_days < k
    if can_grow:
      if sales[r] < 10:
        window_bad_days += 1
      r += 1
      count += r - l
    else:
      if sales[l] < 10:
        window_bad_days -= 1
      l += 1
  return count


def run_tests():
  tests = [
      # Example 1 from the book
      ([0, 20, 5], 2, 2, 1),
      # Example 2 from the book
      ([0, 20, 5], 1, 2, 5),
      # Edge case - empty array
      ([], 1, 2, 0),
      # Edge case - k1 = k2 = 0
      ([0, 20, 5], 0, 0, 1),
      # Edge case - all good days
      ([10, 20, 30], 1, 2, 0),
  ]
  for sales, k1, k2, want in tests:
    got = count_bad_days_range(sales, k1, k2)
    assert got == want, f"\ncount_bad_days_range({sales}, {k1}, {k2}): got: {        got}, want: {want}\n"

run_tests()
