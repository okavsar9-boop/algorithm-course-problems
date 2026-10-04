# 14.20 - Count Subarrays With at Least k Bad Days
# Run: python3 14_20_count_subarrays_with_at_least_k_bad_days.py

def count_at_least_k_bad_days(sales, k):
  n = len(sales)
  total_subarrays = n * (n + 1) // 2
  if k == 0:
    return total_subarrays
  return total_subarrays - count_at_most_k_bad_days(sales, k - 1)

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
      # Example from the book
      ([0, 20, 5], 1, 5),
      # Edge case - empty array
      ([], 1, 0),
      # Edge case - k = 0
      ([0, 20, 5], 0, 6),
      # all good days
      ([10, 20, 30], 1, 0),
      # all bad days
      ([0, 5, 8], 2, 3),
  ]
  for sales, k, want in tests:
    got = count_at_least_k_bad_days(sales, k)
    assert got == want, f"\ncount_at_least_k_bad_days({sales}, {k}): got: {        got}, want: {want}\n"

run_tests()
