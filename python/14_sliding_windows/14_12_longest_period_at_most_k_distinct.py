# 14.12 - Longest Period at Most k Distinct
# Run: python3 14_12_longest_period_at_most_k_distinct.py

from collections import defaultdict

def max_at_most_k_distinct(best_seller, k):
  l, r = 0, 0
  window_counts = defaultdict(int)
  cur_max = 0
  while r < len(best_seller):
    can_grow = best_seller[r] in window_counts or len(window_counts) + 1 <= k
    if can_grow:
      window_counts[best_seller[r]] += 1
      r += 1
      cur_max = max(cur_max, r - l)
    else:
      window_counts[best_seller[l]] -= 1
      if window_counts[best_seller[l]] == 0:
        del window_counts[best_seller[l]]
      l += 1
  return cur_max


def run_tests():
  tests = [
      # Example from the book
      (["book1", "book1", "book2", "book1", "book3", "book1"], 2, 4),
      # Edge case - empty array
      ([], 1, 0),
      # Edge case - k=1
      (["book1", "book2", "book1"], 1, 1),
      # Edge case - k=len(best_seller)
      (["book1", "book2", "book3"], 3, 3),
      # Edge case - all same book
      (["book1", "book1", "book1"], 1, 3),
  ]
  for best_seller, k, want in tests:
    got = max_at_most_k_distinct(best_seller, k)
    assert got == want, f"\nmax_at_most_k_distinct({best_seller}, {k}): got: {        got}, want {want}\n"

run_tests()
