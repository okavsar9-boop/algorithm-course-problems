# 14.1 - Most Weekly Sales
# Run: python3 14_01_most_weekly_sales.py

def most_weekly_sales(sales):
  l, r = 0, 0
  window_sum = 0
  cur_max = 0
  while r < len(sales):
    window_sum += sales[r]
    r += 1
    if r - l == 7:
      cur_max = max(cur_max, window_sum)
      window_sum -= sales[l]
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([0, 3, 7, 12, 10, 5, 0, 1, 0, 15, 12, 11, 1], 44),
      # Example 2 from the book
      ([0, 3, 7, 12], 0),
      # Edge case - empty array
      ([], 0),
      # Edge case - exactly 7 days
      ([1, 2, 3, 4, 5, 6, 7], 28),
      # Edge case - all zeros
      ([0, 0, 0, 0, 0, 0, 0, 0], 0),
  ]
  for sales, want in tests:
    got = most_weekly_sales(sales)
    assert got == want, f"\nmost_weekly_sales({sales}): got: {        got}, want: {want}\n"

run_tests()
