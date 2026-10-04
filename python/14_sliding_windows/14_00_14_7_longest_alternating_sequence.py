#  - 14.7 Longest Alternating Sequence
# Run: python3 14_00_14_7_longest_alternating_sequence.py

def longest_alternating_sequence(sales):
  l, r = 0, 0
  cur_max = 0
  while r < len(sales):
    can_grow = l == r or (sales[r - 1] >= 10) != (sales[r] >= 10)
    if can_grow:
      r += 1
      cur_max = max(cur_max, r - l)
    else:
      l = r
      r += 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([8, 9, 20, 0, 9], 3),
      # Example 2 from the book
      ([0, 0, 0], 1),
      # Edge case - empty array
      ([], 0),
      # Edge case - single element
      ([10], 1),
      # perfect alternation
      ([5, 10, 5, 10], 4),
      # all good days
      ([10, 11, 12], 1),
  ]
  for sales, want in tests:
    got = longest_alternating_sequence(sales)
    assert got == want, f"\nlongest_alternating_sequence({sales}): got: {        got}, want {want}\n"

run_tests()
