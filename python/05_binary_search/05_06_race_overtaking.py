# 5.6 - Race Overtaking
# Run: python3 05_06_race_overtaking.py

def race_overtaking(p1, p2):

  def is_before(i):
    return p1[i] > p2[i]

  l, r = 0, len(p1) - 1
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid
  return r


def run_tests():
  tests = [
      # Example 1 from book
      ([2, 4, 6, 8, 10], [1, 3, 5, 9, 11], 3),
      # Example
      ([2, 3, 4, 5, 6], [1, 2, 3, 6, 7], 3),
      # Example
      ([3, 4, 5], [2, 5, 6], 1),
      # Edge case - overtake at start
      ([2, 3], [1, 4], 1),
  ]

  for p1, p2, want in tests:
    got = race_overtaking(p1, p2)
    assert got == want, f"\nrace_overtaking({p1}, {p2}): got: {        got}, want: {want}\n"

run_tests()
