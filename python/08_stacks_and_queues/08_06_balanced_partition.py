# 8.6 - Balanced Partition
# Run: python3 08_06_balanced_partition.py

def max_balanced_partition(s):
  height = 0
  res = 0
  for c in s:
    if c == '(':
      height += 1
    else:
      height -= 1
      if height == 0:
        res += 1
  return res


def run_tests():
  tests = [
      ("((()))(()())()(()(()))", 4),
      ("()()()", 3),
      ("(((())))", 1),
      ("", 0),
      ("()", 1),
  ]
  for s, want in tests:
    got = max_balanced_partition(s)
    assert got == want, f"\nmax_balanced_partition({s}): got: {        got}, want: {want}\n"

run_tests()
