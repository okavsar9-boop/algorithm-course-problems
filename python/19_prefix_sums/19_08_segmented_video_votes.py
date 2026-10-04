# 19.8 - Segmented Video Votes
# Run: python3 19_08_segmented_video_votes.py

def range_updates(n, votes): 
  diff = [0] * n
  for l, r, v in votes:
    diff[l] += v
    if r + 1 < n:
      diff[r + 1] -= v

  # Recipe 1.
  prefix_sum = [0] * n
  prefix_sum[0] = diff[0]
  for i in range(1, n):
    prefix_sum[i] = prefix_sum[i - 1] + diff[i]
  return prefix_sum


def run_tests():
  tests = [
    # Example from the book
    (6, [[3, 4, 1], [0, 0, 1], [1, 3, 1], [0, 5, -1]], [0, 0, 0, 1, 0, -1]),
    # Edge case: No votes
    (5, [], [0, 0, 0, 0, 0]),
    # Edge case: All likes
    (3, [[0, 2, 1]], [1, 1, 1]),
    # Edge case: All dislikes
    (3, [[0, 2, -1]], [-1, -1, -1]),
  ]

  for n, votes, want in tests:
    got = range_updates(n, votes)
    assert got == want, f"\nrange_updates({n}, {votes}): got: {got}, want: {want}\n"

run_tests()
