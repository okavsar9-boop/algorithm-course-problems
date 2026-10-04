# 14.5 - Longest Good Day Streak
# Run: python3 14_05_longest_good_day_streak.py

def max_no_bad_days(sales):
  l, r = 0, 0
  cur_max = 0
  while r < len(sales):
    can_grow = sales[r] >= 10
    if can_grow:
      r += 1
      cur_max = max(cur_max, r - l)
    else:
      l = r + 1
      r = r + 1
  return cur_max


def run_tests():
  tests = [
      # Example from the book
      ([0, 14, 7, 12, 10, 20], 3),
      # Edge case - empty array
      ([], 0),
      # Edge case - all good days
      ([10, 11, 12], 3),
      # Edge case - all bad days
      ([1, 2, 3], 0),
      # alternating
      ([10, 5, 10, 5], 1),
  ]
  for sales, want in tests:
    got = max_no_bad_days(sales)
    assert got == want, f"\nmax_no_bad_days({sales}): got: {got}, want {want}\n"

run_tests()
