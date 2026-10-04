# 19.2 - YouTube Video Reception
# Run: python3 19_02_youtube_video_reception.py

def good_reception_scores(likes, dislikes, periods):
  positive_days = [0] * len(likes)
  for i in range(len(likes)):
    if likes[i] > dislikes[i]:
      positive_days[i] = 1
  # Range sum queries recipe
  prefix_sum = [0] * len(positive_days)
  prefix_sum[0] = positive_days[0]
  for i in range(1, len(positive_days)):
    prefix_sum[i] = prefix_sum[i-1] + positive_days[i]

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
    ([6, 3, 4, 8, 7, 2, 6, 5, 0, 1], [6, 0, 8, 0, 0, 0, 1, 8, 0, 2], [[0, 1], [0, 5], [5, 8], [3, 3]], [1, 4, 2, 1]),
    # Edge case: All days positive
    ([10, 20, 30], [0, 0, 0], [[0, 2]], [3]),
    # Edge case: All days negative
    ([0, 0, 0], [10, 20, 30], [[0, 2]], [0]),
    # Edge case: Mixed days
    ([1, 2, 3], [3, 2, 1], [[0, 2]], [1]),
  ]

  for likes, dislikes, periods, want in tests:
    got = good_reception_scores(likes, dislikes, periods)
    assert got == want, f"\ngood_reception_scores({likes}, {dislikes}, {periods}): got: {got}, want: {want}\n"

run_tests()
