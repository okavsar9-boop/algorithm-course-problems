# 14.2 - Most Sales in K Days
# Run: python3 14_02_most_sales_in_k_days.py

def most_sales_in_k_days(sales, k):
  l, r = 0, 0
  window_sum = 0
  cur_max = 0
  best_start = 0
  while r < len(sales):
    window_sum += sales[r]
    r += 1
    if r - l == k:
      if window_sum > cur_max:
        cur_max = window_sum
        best_start = l
      window_sum -= sales[l]
      l += 1
  return best_start


def run_tests():
  tests = [
      # Example from the book
      ([8, 1, 3, 7], 2, 2),
      # Edge case - k=1
      ([5, 10, 15, 5], 1, 2),
      # Edge case - k=len(sales)
      ([1, 2, 3], 3, 0),
      # Edge case - multiple valid answers, return first
      ([10, 5, 10], 2, 0),
  ]
  for sales, k, want in tests:
    got = most_sales_in_k_days(sales, k)
    assert got == want, f"\nmost_sales_in_k_days({sales}, {k}): got: {        got}, want {want}\n"

run_tests()
