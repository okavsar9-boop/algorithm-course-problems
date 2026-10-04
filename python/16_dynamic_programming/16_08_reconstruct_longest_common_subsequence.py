# 16.8 - Reconstruct Longest Common Subsequence
# Run: python3 16_08_reconstruct_longest_common_subsequence.py

def lcs_reconstruction(s1, s2):
  memo = {}

  def lcs_rec(i1, i2):
    if i1 == len(s1) or i2 == len(s2):
      return ""
    if (i1, i2) in memo:
      return memo[(i1, i2)]
    if s1[i1] == s2[i2]:
      memo[(i1, i2)] = s1[i1] + lcs_rec(i1 + 1, i2 + 1)
    else:
      opt1, opt2 = lcs_rec(i1 + 1, i2), lcs_rec(i1, i2 + 1)
      if len(opt1) >= len(opt2):
        memo[(i1, i2)] = opt1
      else:
        memo[(i1, i2)] = opt2
    return memo[(i1, i2)]

  return lcs_rec(0, 0)

def lcs_reconstruction_optimal(s1, s2):
  memo = {}

  def lcs_rec(i1, i2):
    if i1 == len(s1) or i2 == len(s2):
      return 0
    if (i1, i2) in memo:
      return memo[(i1, i2)]
    if s1[i1] == s2[i2]:
      memo[(i1, i2)] = 1 + lcs_rec(i1 + 1, i2 + 1)
    else:
      memo[(i1, i2)] = max(lcs_rec(i1 + 1, i2), lcs_rec(i1, i2 + 1))
    return memo[(i1, i2)]

  i1, i2 = 0, 0
  res = []
  while i1 < len(s1) and i2 < len(s2):
    if s1[i1] == s2[i2]:
      res.append(s1[i1])
      i1 += 1
      i2 += 1
    elif lcs_rec(i1 + 1, i2) >= lcs_rec(i1, i2 + 1):
      i1 += 1
    else:
      i2 += 1
  return ''.join(res)


def run_tests():
  tests = [  # s1, s2, length of the LCS
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

  def is_subsequence(subseq, s):
    i = 0
    for char in s:
      if i < len(subseq) and char == subseq[i]:
        i += 1
    return i == len(subseq)

  for s1, s2, want_length in tests:
    got = lcs_reconstruction_optimal(s1, s2)
    assert len(
        got) == want_length, f"\nlcs_reconstruction_optimal({s1}, {s2}): got length: {len(got)}, want length: {want_length}\n"
    assert is_subsequence(
        got, s1), f"\nlcs_reconstruction_optimal({s1}, {s2}): result '{got}' is not a subsequence of '{s1}'\n"
    assert is_subsequence(
        got, s2), f"\nlcs_reconstruction_optimal({s1}, {s2}): result '{got}' is not a subsequence of '{s2}'\n"

    got2 = lcs_reconstruction(s1, s2)
    assert len(
        got2) == want_length, f"\nlcs_reconstruction({s1}, {s2}): got length: {len(got2)}, want length: {want_length}\n"
    assert is_subsequence(
        got2, s1), f"\nlcs_reconstruction({s1}, {s2}): result '{got2}' is not a subsequence of '{s1}'\n"
    assert is_subsequence(
        got2, s2), f"\nlcs_reconstruction({s1}, {s2}): result '{got2}' is not a subsequence of '{s2}'\n"

run_tests()
