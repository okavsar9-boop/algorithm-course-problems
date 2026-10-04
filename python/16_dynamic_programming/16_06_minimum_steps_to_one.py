# 16.6 - Minimum Steps to One
# Run: python3 16_06_minimum_steps_to_one.py

def minimum_steps_to_one(n):
  memo = {}

  def num_steps(i):
    if i == 1:
      return 0
    if i in memo:
      return memo[i]
    steps = num_steps(i - 1)
    if i % 2 == 0:
      steps = min(steps, num_steps(i // 2))
    if i % 3 == 0:
      steps = min(steps, num_steps(i // 3))
    memo[i] = 1 + steps
    return memo[i]

  return num_steps(n)


def run_tests():
  tests = [
      (10, 3),
      (1, 0),
      (15, 4),
      (6, 2),
      (7, 3),
      (100, 7),  # larger test case
  ]
  for n, want in tests:
    got = minimum_steps_to_one(n)
    assert got == want, f"\nminimum_steps_to_one({n}): got: {        got}, want: {want}\n"

run_tests()
