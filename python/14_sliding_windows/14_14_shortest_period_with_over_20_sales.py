# 14.14 - Shortest Period With Over 20 Sales
# Run: python3 14_14_shortest_period_with_over_20_sales.py

import math

def shortest_over_20_sales(sales):
  l, r = 0, 0
  window_sum = 0
  cur_min = math.inf
  while True:
    must_grow = window_sum <= 20
    if must_grow:
      if r == len(sales):
        break
      window_sum += sales[r]
      r += 1
    else:
      cur_min = min(cur_min, r - l)
      window_sum -= sales[l]
      l += 1
  return cur_min if cur_min != math.inf else -1


def run_tests():
  tests = [
      # Example 1 from the book
      ([5, 10, 15, 5, 10], 2),
      # Example 2 from the book
      ([5, 10, 4, 5, 10], 4),
      # Example 3 from the book
      ([5, 5, 5, 5], -1),
      # Edge case - empty array
      ([], -1),
      # Edge case - single element over 20
      ([21], 1),
      # Edge case - exactly 20 sales not enough
      ([10, 10], -1),
  ]
  for sales, want in tests:
    got = shortest_over_20_sales(sales)
    assert got == want, f"\nshortest_over_20_sales({sales}): got: {        got}, want {want}\n"

run_tests()
