# 16.7 - Longest Common Subsequence
# Run: python3 16_07_longest_common_subsequence.py

def longest_common_subsequence(s1, s2):
  memo = {}

  def lcs(i1, i2):
    if i1 == len(s1) or i2 == len(s2):
      return 0
    if (i1, i2) in memo:
      return memo[(i1, i2)]
    if s1[i1] == s2[i2]:
      memo[(i1, i2)] = 1 + lcs(i1 + 1, i2 + 1)
    else:
      memo[(i1, i2)] = max(lcs(i1 + 1, i2), lcs(i1, i2 + 1))
    return memo[(i1, i2)]

  return lcs(0, 0)


def run_tests():
  tests = [
      ("HAHAH", "AAAAHH", 3),
      ("", "AA", 0),
      ("ABC", "BCA", 2),
      ("ABCD", "ACBAD", 3),
      ("", "", 0),
      ("ABCDEFGHIJ", "ACBDEFGHIK", 8),
      ("AAAAAAAAAAAAAAA", "AAAAAAAAAAAAA", 13),
      ("THEQUICKBROWNFOX", "THESLOWREDFOX", 8),
      ("AAAAABBBBBCCCCCDDDDD", "BBBBBCCCCCDDDDDEEEEE", 15),
  ]
  for s1, s2, want in tests:
    got = longest_common_subsequence(s1, s2)
    assert got == want, f"\nlongest_common_subsequence({s1}, {s2}): got: {        got}, want: {want}\n"

run_tests()
