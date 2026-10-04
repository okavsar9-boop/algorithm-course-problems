# 19.5 - YouTube Video Unusual Days
# Run: python3 19_05_youtube_video_unusual_days.py

def max_total_deviation(likes, dislikes):
  scores = [likes[i] - dislikes[i] for i in range(len(likes))]
  scores = sorted(scores)
  n = len(scores)
  prefix_sum = [0] * n
  prefix_sum[0] = scores[0]
  for i in range(1, n):
    prefix_sum[i] = prefix_sum[i - 1] + scores[i]
  max_deviation = 0
  for i in range(n):
    left, right = 0, 0
    if i > 0:
      left = i * scores[i] - prefix_sum[i - 1]
    if i < n - 1:
      right = prefix_sum[n - 1] - prefix_sum[i] - (n - i - 1) * scores[i]
    max_deviation = max(max_deviation, left + right)
  return max_deviation


def run_tests():
  tests = [
      # Example from the book
      ([3, 6, 1], [0, 1, 9], 24),
      # Edge case: All same scores
      ([1, 1, 1], [1, 1, 1], 0),
      # Edge case: Increasing scores
      ([1, 2, 3], [0, 0, 0], 3),
      # Edge case: Decreasing scores
      ([3, 2, 1], [0, 0, 0], 3),
  ]

  for likes, dislikes, want in tests:
    got = max_total_deviation(likes, dislikes)
    assert got == want, f"\nmax_total_deviation({likes}, {dislikes}): got: {        got}, want: {want}\n"

run_tests()
