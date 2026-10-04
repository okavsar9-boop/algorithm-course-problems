# 16.1 - Road Trip
# Run: python3 16_01_road_trip.py

def delay_inefficient(times):  # Inefficient -- do not use in interviews.
  n = len(times)
  if n < 3:
    return 0

  def delay_rec(i):
    if i >= n - 3:
      return times[i]
    return times[i] + min(delay_rec(i + 1), delay_rec(i + 2), delay_rec(i + 3))

  return min(delay_rec(0), delay_rec(1), delay_rec(2))

def delay_memoized(times):
  n = len(times)
  if n < 3:
    return 0

  memo = {}

  def delay_rec(i):
    if i >= n - 3:
      return times[i]
    if i in memo:
      return memo[i]
    memo[i] = times[i] + \
        min(delay_rec(i + 1), delay_rec(i + 2), delay_rec(i + 3))
    return memo[i]

  return min(delay_rec(0), delay_rec(1), delay_rec(2))


def delay_tabulated(times):
  n = len(times)
  if n < 3:
    return 0

  dp = [0] * n
  dp[n - 1], dp[n - 2], dp[n - 3] = times[n - 1], times[n - 2], times[n - 3]
  for i in range(n - 4, -1, -1):
    dp[i] = times[i] + min(dp[i + 1], dp[i + 2], dp[i + 3])
  return min(dp[0], dp[1], dp[2])

def delay_tabulated_w_space_optimization(times):
  n = len(times)
  if n < 3:
    return 0
  dp1, dp2, dp3 = times[n - 3], times[n - 2], times[n - 1]
  for i in range(n - 4, -1, -1):
    cur = times[i] + min(dp1, dp2, dp3)
    dp1, dp2, dp3 = cur, dp1, dp2
  return min(dp1, dp2, dp3)

def run_tests():
  tests = [
      ([8, 1, 2, 3, 9, 6, 2, 4], 6),
      ([8, 1, 2, 3, 9, 3, 2, 4], 5),
      ([10, 10], 0),
      ([1, 2, 3, 4, 5, 6, 7, 8, 9], 12),
      ([5, 5, 5, 5, 5, 5, 5, 5, 5], 15),
      ([1, 1, 1, 1, 1, 1, 1, 1, 1, 1], 3),
      ([1, 2, 3], 1),
      ([1, 2], 0),
      ([1], 0),
      ([], 0),
  ]
  for times, want in tests:
    got = delay_inefficient(times)
    got_memoized = delay_memoized(times)
    got_tabulated = delay_tabulated(times)
    got_tabulated_w_space_optimization = delay_tabulated_w_space_optimization(
        times)
    assert got == got_memoized == got_tabulated == got_tabulated_w_space_optimization, f"\ndelay_inefficient({        times}) != delay_memoized({times}) != delay_tabulated({times}) != delay_tabulated_w_space_optimization({times})\n"
    assert got == want, f"\ndelay({times}): got: {got}, want: {want}\n"

run_tests()
