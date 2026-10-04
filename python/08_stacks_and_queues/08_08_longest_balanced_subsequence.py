# 8.8 - Longest Balanced Subsequence
# Run: python3 08_08_longest_balanced_subsequence.py

stack = []

def longest_balanced_subsequence(s):
  invalid_indices = set()
  stack = []
  for i, c in enumerate(s):
    if c == "(":
      stack.append(i)
    elif not stack:
      invalid_indices.add(i)
    else:
      stack.pop()

  while stack:
    invalid_indices.add(stack.pop())

  res = []
  for i, c in enumerate(s):
    if i not in invalid_indices:
      res.append(c)
  return ''.join(res)


def run_tests():
  tests = [
      ("))(())(()", ["(())()"]),
      ("(()()", ["()()", "(())"]),
      ("(()(()(", ["()()", "(())"]),
      ("())(()", ["()()"]),
      ("(", [""]),
      ("", [""]),
  ]
  for s, want in tests:
    got = longest_balanced_subsequence(s)
    assert got in want, f"\nlongest_balanced_subsequence({s}): got: {        got}, want: {want}\n"

run_tests()
