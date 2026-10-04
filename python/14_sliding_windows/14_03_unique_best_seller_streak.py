# 14.3 - Unique Best Seller Streak
# Run: python3 14_03_unique_best_seller_streak.py

from collections import defaultdict

def has_unique_k_days(best_seller, k):
  l, r = 0, 0
  window_counts = defaultdict(int)
  while r < len(best_seller):
    window_counts[best_seller[r]] += 1
    r += 1
    if r - l == k:
      if len(window_counts) == k:
        return True
      window_counts[best_seller[l]] -= 1
      if window_counts[best_seller[l]] == 0:
        del window_counts[best_seller[l]]
      l += 1
  return False


def run_tests():
  tests = [
      # Example 1 from the book
      (["book3", "book1", "book3", "book3", "book2", "book3", "book4", "book3"], 3, True),
      # Example 2 from the book
      (["book3", "book1", "book3", "book3", "book2",
       "book3", "book4", "book3"], 4, False),
      # Edge case - k=1
      (["book1", "book2"], 1, True),
      # Edge case - k=len(best_seller)
      (["book1", "book2", "book3"], 3, True),
      # no unique sequence possible
      (["book1", "book1", "book1"], 2, False),
  ]
  for best_seller, k, want in tests:
    got = has_unique_k_days(best_seller, k)
    assert got == want, f"\nhas_unique_k_days({best_seller}, {k}): got: {        got}, want {want}\n"

run_tests()
