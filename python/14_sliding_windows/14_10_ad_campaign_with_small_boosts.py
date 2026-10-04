# 14.10 - Ad Campaign With Small Boosts
# Run: python3 14_10_ad_campaign_with_small_boosts.py

def max_consecutive_good_days_with_small_boost(projected_sales, k):
  l, r = 0, 0
  window_between_5_and_9 = 0
  cur_max = 0
  while r < len(projected_sales):
    can_grow = (projected_sales[r] >= 10 or
                (5 <= projected_sales[r] < 10 and window_between_5_and_9 < k))
    if can_grow:
      if projected_sales[r] < 10:
        window_between_5_and_9 += 1
      r += 1
      cur_max = max(cur_max, r - l)
    elif l == r:
      l += 1
      r += 1
    else:
      if 5 <= projected_sales[l] < 10:
        window_between_5_and_9 -= 1
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([8, 4, 8], 3, 1),
      # Example 2 from the book
      ([10, 5, 8], 1, 2),
      ([8, 8, 8], 3, 3),
      # Example with mix of values
      ([4, 8, 12, 3, 9], 2, 2),
      # Edge case - empty array
      ([], 1, 0),
      # Edge case - k=0
      ([5, 10, 5], 0, 1),
      # Edge case - all values between 5-9
      ([7, 8, 9], 3, 3),
      # Edge case - values below 5 break sequence
      ([8, 4, 8], 2, 1),
  ]
  for projected_sales, k, want in tests:
    got = max_consecutive_good_days_with_small_boost(projected_sales, k)
    assert got == want, f"\nmax_consecutive_good_days_with_small_boost({projected_sales}, {        k}): got: {got}, want {want}\n"

run_tests()
