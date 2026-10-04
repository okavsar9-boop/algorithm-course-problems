# 9.4 - Lego Castle
# Run: python3 09_04_lego_castle.py

def blocks_rec(n):
  if n == 1:
    return 1

  def roof(i):
    if i == 1:
      return 1
    return roof(i - 1) * 2 + 1

  return blocks_rec(n - 1) * 2 + roof(n)

def blocks_memoized(n):
  memo = dict()

  def roof(i):
    if i == 1:
      return 1
    if i in memo:
      return memo[i]
    memo[i] = roof(i - 1) * 2 + 1
    return memo[i]

  def blocks_rec(n):
    if n == 1:
      return 1
    return blocks_rec(n - 1) * 2 + roof(n)

  return blocks_rec(n)

1, 3, 7, 15, ...

def blocks_iterative(n):
  blocks = 1
  for i in range(2, n + 1):
    roof = 2**i - 1
    blocks = blocks * 2 + roof
  return blocks

def blocks_math(n):
  return n * 2**n - (2**n - 1)


def run_tests():
  tests = [
      (1, 1),
      (2, 5),
      (3, 17),
      (4, 49),
      (5, 129),
      (6, 321),
      (7, 769),
      (8, 1793),
      (9, 4097),
      (10, 9217),
  ]
  for n, want in tests:
    got_rec = blocks_rec(n)
    got_memoized = blocks_memoized(n)
    got_iterative = blocks_iterative(n)
    got_math = blocks_math(n)
    assert got_rec == want, f"\nblocks_rec({n}): got: {got_rec}, want: {want}\n"
    assert got_memoized == want, f"\nblocks_memoized({n}): got: {got_memoized}, want: {want}\n"
    assert got_iterative == want, f"\nblocks_iterative({n}): got: {got_iterative}, want: {want}\n"
    assert got_math == want, f"\nblocks_math({n}): got: {got_math}, want: {want}\n"

run_tests()
