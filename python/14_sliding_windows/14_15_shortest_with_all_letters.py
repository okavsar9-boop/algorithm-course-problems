# 14.15 - Shortest With All Letters
# Run: python3 14_15_shortest_with_all_letters.py

from collections import defaultdict
import math

def shortest_with_all_letters(s1, s2):
  l, r = 0, 0
  missing = defaultdict(int)
  for c in s2:
    missing[c] += 1
  distinct_missing = len(missing)
  cur_min = math.inf

  while True:
    must_grow = distinct_missing > 0
    if must_grow:
      if r == len(s1):
        break
      if s1[r] in missing:
        missing[s1[r]] -= 1
        if missing[s1[r]] == 0:
          distinct_missing -= 1
      r += 1
    else:
      cur_min = min(cur_min, r - l)
      if s1[l] in missing:
        missing[s1[l]] += 1
        if missing[s1[l]] == 1:
          distinct_missing += 1
      l += 1
  return cur_min if cur_min != math.inf else -1


def run_tests():
  tests = [
      # Example 1 from the book
      ("helloworld", "well", 5),
      # Example 2 from the book
      ("helloworld", "weelll", -1),
      # Edge case - s2 is single character
      ("hello", "l", 1),
      # s2 not in s1
      ("hello", "z", -1),
  ]
  for s1, s2, want in tests:
    got = shortest_with_all_letters(s1, s2)
    assert got == want, f"\nshortest_with_all_letters({s1}, {s2}): got: {        got}, want {want}\n"

run_tests()
