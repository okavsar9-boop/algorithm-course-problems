# 14.9 - Ad Campaign Boost
# Run: python3 14_09_ad_campaign_boost.py

def max_consecutive_good_days(projected_sales, k):
  l, r = 0, 0
  window_bad_days = 0
  cur_max = 0
  while r < len(projected_sales):
    can_grow = projected_sales[r] >= 10 or window_bad_days < k
    if can_grow:
      if projected_sales[r] < 10:
        window_bad_days += 1
      r += 1
      cur_max = max(cur_max, r - l)
    else:
      if projected_sales[l] < 10:
        window_bad_days -= 1
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([5, 0, 20, 0, 5], 2, 3),
      # Example 2 from the book
      ([0, 10, 0, 10], 1, 3),
      # Edge case - k=len(projected_sales)
      ([5, 5, 5], 3, 3),
  ]
  for projected_sales, k, want in tests:
    got = max_consecutive_good_days(projected_sales, k)
    assert got == want, f"\nmax_consecutive_good_days({projected_sales}, {k}): got: {        got}, want {want}\n"

run_tests()
