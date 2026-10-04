# 5.2 - CCTV Footage
# Run: python3 05_02_cctv_footage.py

def find_bike(t1, t2, is_stolen):

  def is_before(t):
    return not is_stolen(t)

  l, r = t1, t2
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid
  return r


def run_tests():
  tests = [
      # Example 1 - stolen at t=5
      (1, 10, lambda t: t >= 5, 5),
      # Example 2 - stolen at start
      (1, 5, lambda t: t >= 2, 2),
      # Example 3 - stolen at end
      (1, 5, lambda t: t >= 5, 5),
      # Edge case - two timestamps
      (5, 6, lambda t: t >= 6, 6)
  ]

  for t1, t2, is_stolen, want in tests:
    got = find_bike(t1, t2, is_stolen)
    assert got == want, f"\nfind_bike({t1}, {t2}): got: {got}, want: {want}\n"

run_tests()
