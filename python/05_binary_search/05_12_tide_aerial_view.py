# 5.12 - Tide Aerial View
# Run: python3 05_12_tide_aerial_view.py

class TideAerialView:

  # Time: O(log n)
  # Space: O(1)
  def get_ones_in_row(self, row):
    if row[0] == 0:
      return 0
    if row[-1] == 1:
      return len(row)

    def is_before(idx):
      return row[idx] == 1

    l, r = 0, len(row)
    while r - l > 1:
      mid = (l + r) // 2
      if is_before(mid):
        l = mid
      else:
        r = mid
    return r

  # Time: O(n log n)
  # Space: O(1)
  def get_ones_in_picture(self, picture):
    ones = 0
    for row in picture:
      ones += self.get_ones_in_row(row)
    return ones

  # Time: O((log k) * n log n)
  # Space: O(1)
  def solve(self, pictures):
    
    def is_before(picture):
      water = self.get_ones_in_picture(picture)
      total = len(picture[0])**2
      return water / total < 0.5

    if not is_before(pictures[0]):
      return 0
    if is_before(pictures[-1]):
      return len(pictures) - 1

    l, r = 0, len(pictures) - 1
    while r - l > 1:
      mid = (l + r) // 2
      if is_before(pictures[mid]):
        l = mid
      else:
        r = mid

    # Return the closest one to the midpoint, or l in case of a tie
    l_water = self.get_ones_in_picture(pictures[l])
    r_water = self.get_ones_in_picture(pictures[r])
    mid_point = len(pictures[0])**2 / 2
    return l if abs(l_water - mid_point) <= abs(r_water - mid_point) else r


def run_tests():
  tests = [
      # Example from the book
      ([[[0, 0, 0],
         [0, 0, 0],
         [0, 0, 0]],
        [[1, 0, 0],
         [0, 0, 0],
         [1, 0, 0]],
        [[1, 1, 0],
         [0, 0, 0],
         [1, 0, 0]],
        [[1, 1, 0],
         [1, 1, 1],
         [1, 0, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [1, 1, 0]]], 2),
      # 3 pictures with increasing water
      ([[[1, 0, 0],
         [1, 0, 0],
         [1, 0, 0]],
        [[1, 1, 0],
         [1, 1, 0],
         [1, 0, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [1, 0, 0]]], 1),
      # 2 pictures
      ([[[1, 0],
         [0, 0]],
        [[1, 1],
         [1, 0]]], 0),
      # Incremental progression
      ([[[0, 0, 0],
         [0, 0, 0],
         [0, 0, 0]],
        [[1, 0, 0],
         [0, 0, 0],
         [0, 0, 0]],
        [[1, 0, 0],
         [1, 0, 0],
         [0, 0, 0]],
        [[1, 1, 0],
         [1, 0, 0],
         [0, 0, 0]],
        [[1, 1, 1],
         [1, 0, 0],
         [0, 0, 0]],
        [[1, 1, 1],
         [1, 1, 0],
         [0, 0, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [0, 0, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [1, 0, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [1, 1, 0]],
        [[1, 1, 1],
         [1, 1, 1],
         [1, 1, 1]],
        ], 4),
      # Edge case - single picture
      ([[[1, 1], [0, 0]]], 0),
      # Edge case - all water
      ([[[1, 1], [1, 1]]], 0),
      # Edge case - all land
      ([[[0, 0], [0, 0]]], 0)
  ]

  for pictures, want in tests:
    got = TideAerialView().solve(pictures)
    assert got == want, f"\ntide_aerial_view({pictures}): got: {        got}, want: {want}\n"

run_tests()
