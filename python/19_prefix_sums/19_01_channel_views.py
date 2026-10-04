# 19.1 - Channel Views
# Run: python3 19_01_channel_views.py

def channel_views(views, periods):
  if not views or not periods:
    return []
  prefix_sum = [0] * len(views)
  prefix_sum[0] = views[0]
  for i in range(1, len(views)):
    prefix_sum[i] = prefix_sum[i-1] + views[i]
  res = []
  for l, r in periods:
    if l == 0:
      res.append(prefix_sum[r])
    else:
      res.append(prefix_sum[r] - prefix_sum[l-1])
  return res


def run_tests():
  tests = [
    # Example from the book
    ([3, 5, 4, 8, 7, 2, 5, 3, 2, 3], [[0, 1], [0, 5], [5, 8], [3, 3]], [8, 29, 12, 8]),
    # Edge case: Single day period
    ([10, 20, 30], [[1, 1]], [20]),
    # Edge case: Full range
    ([1, 2, 3, 4, 5], [[0, 4]], [15]),
    # Edge case: Empty views
    ([], [], []),
    # Edge case: Periods with zero-length
    ([1, 2, 3, 4, 5], [[2, 2], [0, 0]], [3, 1]),
  ]

  for views, periods, want in tests:
    got = channel_views(views, periods)
    assert got == want, f"\nchannel_views({views}, {periods}): got: {got}, want: {want}\n"

run_tests()
