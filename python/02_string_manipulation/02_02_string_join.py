# 2.2 - String Join
# Run: python3 02_02_string_join.py

def join(arr, s):
  res = []
  for i in range(len(arr)):
    if i != 0:
      for c in s:
        res.append(c)
    for c in arr[i]:
      res.append(c)
  return array_to_string(res)

def array_to_string(arr):
  # Function allowed by the problem statement.
  return ''.join(arr)


def run_tests():
  tests = [ # Example 1 from the book
    (["join", "by", "space"], " ", "join by space"), # Example 2 from the book
    (["b", "", "k", "", "p", "r n", "", "d", "d!!"],
    "ee", "beeeekeeeepeer neeeedeed!!"), # Edge case - empty arrays
    ([], "x", ""),
    ([], "", ""),
    ([], "long separator", ""), # Edge case - single element arrays
    (["a"], "x", "a"),
    ([""], "x", ""),
    (["multiple words"], "x", "multiple words"), # two element arrays
    (["a", "b"], "", "ab"),
    (["a", "b"], " ", "a b"),
    (["", ""], ",", ","), # Edge case - empty strings in array
    (["", "", ""], ",", ",,"),
    (["hello", "", "world"], " ", "hello  world"), # special characters
    (["\n", "\t"], ",", "\n,\t"),
    (["tab", "separated"], "\t", "tab\tseparated"), # long separators
    (["short", "strings"], "very long separator",
    "shortvery long separatorstrings"), # mixed content
    (["123", "abc", "!@#", " "], "|", "123|abc|!@#| "), # whitespace handling
    ([" leading", "trailing ", " both "],
    "|", " leading|trailing | both "), # numbers and special chars
    (["123", "456"], "-", "123-456"),
    (["!@#", "$%^"], "&", "!@#&$%^"),
    ]
  for arr, s, want in tests:
    got = join(arr, s)
    assert got == want, f"\njoin({arr}, {s}): got: {got}, want: {want}\n"

run_tests()
