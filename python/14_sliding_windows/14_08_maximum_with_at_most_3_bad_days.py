# 14.8 - Maximum With at Most 3 Bad Days
# Run: python3 14_08_maximum_with_at_most_3_bad_days.py

def max_at_most_3_bad_days(sales):
  l, r = 0, 0
  window_bad_days = 0
  cur_max = 0
  while r < len(sales):
    can_grow = sales[r] >= 10 or window_bad_days < 3
    if can_grow:
      if sales[r] < 10:
        window_bad_days += 1
      r += 1
      cur_max = max(cur_max, r - l)
    else:
      if sales[l] < 10:
        window_bad_days -= 1
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example from the book
      ([0, 14, 7, 9, 0, 20, 10, 0, 10], 6),
      # Edge case - empty array
      ([], 0),
      # Edge case - single element
      ([5], 1),
      # all good days
      ([10, 11, 12], 3),
      # all bad days
      ([1, 2, 3], 3),
      # exactly 3 bad days
      ([5, 10, 5, 10, 5], 5),
      # More than 3 bad days
      ([5, 10, 5, 5, 10, 5], 5),
  ]
  for sales, want in tests:
    got = max_at_most_3_bad_days(sales)
    assert got == want, f"\nmax_at_most_3_bad_days({sales}): got: {        got}, want {want}\n"

run_tests()
