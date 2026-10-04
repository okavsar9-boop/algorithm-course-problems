# 14.4 - Enduring Best Seller Streak
# Run: python3 14_04_enduring_best_seller_streak.py

from collections import defaultdict

def has_enduring_best_seller_streak(best_seller, k):
  # Fixed-length window solution. O(k) space.
  l, r = 0, 0
  window_counts = defaultdict(int)
  while r < len(best_seller):
    window_counts[best_seller[r]] += 1
    r += 1
    if r - l == k:
      if len(window_counts) == 1:
        return True
      window_counts[best_seller[l]] -= 1
      if window_counts[best_seller[l]] == 0:
        del window_counts[best_seller[l]]
      l += 1
  return False

def has_enduring_best_seller_streak_2(best_seller, k):
  # Resetting window solution. O(1) space.
  l, r = 0, 0
  while r < len(best_seller):
    can_grow = l == r or best_seller[l] == best_seller[r]
    if can_grow:
      r += 1
      if r - l == k:
        return True
    else:
      l = r
  return False


def run_tests():
  tests = [
      # Example 1 from the book
      (["book3", "book1", "book3", "book3", "book2"], 3, False),
      # Example 2 from the book
      (["book3", "book1", "book3", "book3", "book2"], 2, True),
      (["book1", "book1", "book2", "book1"], 2, True),
      # Edge case - k=1
      (["book1", "book2"], 1, True),
      # Edge case - k=len(best_seller)
      (["book1", "book1", "book1"], 3, True),
      # no same sequence possible
      (["book1", "book2", "book1"], 2, False),
  ]
  for best_seller, k, want in tests:
    got = has_enduring_best_seller_streak(best_seller, k)
    assert got == want, f"\nhas_enduring_best_seller_streak({best_seller}, {k}): got: {        got}, want {want}\n"
    got = has_enduring_best_seller_streak_2(best_seller, k)
    assert got == want, f"\nhas_enduring_best_seller_streak_2({best_seller}, {k}): got: {        got}, want {want}\n"

run_tests()
