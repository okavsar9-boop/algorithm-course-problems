# 16.2 - Minivan Road Trip
# Run: python3 16_02_minivan_road_trip.py

import math

def minivan_road_trip(times, k):
  n = len(times)
  memo = {}

  def delay(i):
    if i >= n:
      return 0
    if i >= n - k - 1:
      return times[i]
    if i in memo:
      return memo[i]
    memo[i] = times[i] + min(delay(i + p) for p in range(1, k + 2))
    return memo[i]

  min_delay = math.inf
  for p in range(k + 1):
    min_delay = min(min_delay, delay(p))
  return min_delay


def run_tests():
  tests = [
      ([8, 1, 2, 3, 9, 6, 2, 4], 2, 6),
      ([8, 1, 2, 3, 9, 6, 2, 4], 3, 4),
      ([10, 10], 1, 10),
      ([10, 10], 2, 0),
      ([], 2, 0),
      ([5, 5, 5, 5, 5], 2, 5),
  ]
  for times, k, want in tests:
    got = minivan_road_trip(times, k)
    assert got == want, f"\nminivan_road_trip({times}, {k}): got: {        got}, want: {want}\n"

run_tests()
