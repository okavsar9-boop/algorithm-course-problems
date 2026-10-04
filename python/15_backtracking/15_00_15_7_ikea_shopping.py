#  - 15.7 IKEA Shopping
# Run: python3 15_00_15_7_ikea_shopping.py

def maximize_style(budget, prices, ratings):
  best_rating_sum = 0
  best_items = []
  n = len(prices)
  items = []

  def visit(i, cur_cost, cur_rating_sum):
    nonlocal best_items, best_rating_sum
    if i == n:
      if cur_rating_sum > best_rating_sum:
        best_rating_sum = cur_rating_sum
        best_items = items.copy()
      return

    # Choice 1: skip item i.
    visit(i + 1, cur_cost, cur_rating_sum)
    # Choice 2: pick item i (if within budget).
    if cur_cost + prices[i] <= budget:
      items.append(i)
      visit(i + 1, cur_cost + prices[i], cur_rating_sum + ratings[i])
      items.pop()

  visit(0, 0, 0)
  return best_items


def run_tests():
  tests = [
      # Example 1 from the book
      (20, [10, 5, 15, 8, 3], [7.0, 3.5, 9.0, 6.0, 2.0], [0, 3]),
      # Example 2 from the book
      (10, [2, 3, 4, 5], [1.0, 2.0, 3.5, 4.0], [2, 3]),
      # Edge case - budget is 0
      (0, [1, 2, 3], [1.0, 2.0, 3.0], []),
      # Edge case - no items
      (10, [], [], []),
      # Larger budget
      (50, [10, 20, 30], [10.0, 20.0, 30.0], [1, 2]),
  ]
  for budget, prices, ratings, want in tests:
    got = maximize_style(budget, prices, ratings)
    assert got == want, f"\nmaximize_style({budget}, {prices}, {ratings}): got: {        got}, want: {want}\n"

run_tests()
