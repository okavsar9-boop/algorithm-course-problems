# 7.2 - Nested Circles
# Run: python3 07_02_nested_circles.py

from math import sqrt

def contains(c1, c2):
  (x1, y1), r1 = c1
  (x2, y2), r2 = c2
  center_distance = sqrt((x1 - x2)**2 + (y1 - y2)**2)
  return center_distance + r2 < r1

def are_circles_nested(circles):
  circles.sort(key=lambda c: c[1], reverse=True)

  for i in range(len(circles) - 1):
    if not contains(circles[i], circles[i + 1]):
      return False
  return True


def run_tests():
  tests = [
    # Example 1 from the book
    ([((4, 4), 5), ((8, 4), 2)], False),
    # Example 2 from the book
    ([((5, 3), 3), ((5, 3), 2), ((4, 4), 5)], True),
    # Example 3 from the book
    ([((5, 3), 3)], True),
    # Edge case - two identical circles
    ([((1, 1), 2), ((1, 1), 2)], False),
    # Edge case - touching circles
    ([((0, 0), 4), ((0, 0), 2)], True),
    # Edge case - empty list
    ([], True),
    # Edge case - negative coordinates
    ([((-5, -3), 4), ((-5, -3), 2)], True),
    # Edge case - negative radius
    ([((0, 0), -2)], True),
    # Edge case - max coordinate values
    ([((10000, 10000), 10000), ((0, 0), 100)], False),
    # Edge case - min coordinate values
    ([((-10000, -10000), 10000), ((0, 0), 100)], False),
    # Edge case - multiple circles with same center
    ([((1, 1), 5), ((1, 1), 4), ((1, 1), 3), ((1, 1), 2)], True),
    # Edge case - circles not sorted by radius
    ([((0, 0), 2), ((0, 0), 4), ((0, 0), 3)], True),
  ]
  for circles, want in tests:
    got = are_circles_nested(circles)
    assert got == want, f"\nare_circles_nested({circles}): got: {got}, want: {want}\n"

run_tests()
