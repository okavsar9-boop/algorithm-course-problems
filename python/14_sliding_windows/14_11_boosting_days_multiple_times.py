# 14.11 - Boosting Days Multiple Times
# Run: python3 14_11_boosting_days_multiple_times.py

def max_consecutive_with_k_boosts(projected_sales, k):
  l, r = 0, 0
  used_boosts = 0
  cur_max = 0
  while r < len(projected_sales):
    can_grow = used_boosts + max(10 - projected_sales[r], 0) <= k
    if can_grow:
      used_boosts += max(10 - projected_sales[r], 0)
      r += 1
      cur_max = max(cur_max, r - l)
    elif l == r:
      r += 1
      l += 1
    else:
      used_boosts -= max(10 - projected_sales[l], 0)
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([5, 5, 15, 0, 10], 12, 3),
      # Example 2 from the book
      ([5, 5, 15, 0, 10], 15, 4),
      # Edge case - empty array
      ([], 5, 0),
      # Edge case - k=0
      ([5, 10, 5], 0, 1),
      # all values need max boost
      ([0, 0, 0], 30, 3),
      # all values need max boost
      ([0, 0, 0], 29, 2),
  ]
  for projected_sales, k, want in tests:
    got = max_consecutive_with_k_boosts(projected_sales, k)
    assert got == want, f"\nmax_consecutive_with_k_boosts({projected_sales}, {        k}): got: {got}, want {want}\n"

run_tests()
