# 6.7 - Word Expansion Class
# Run: python3 06_07_word_expansion_class.py

class Checker:
  def __init__(self, s):
    self.s = s

  def expands_into(self, s2):
    if len(s2) != len(self.s) + 1:
      return False

    # Create frequency maps for both strings
    freq = dict()
    for c in s2:
      if c not in freq:
        freq[c] = 0
      freq[c] += 1

    for c in self.s:
      if c not in freq:
        return False
      freq[c] -= 1
      if freq[c] == 0:
        del freq[c]

    # Should have exactly one character with frequency 1
    return len(freq) == 1 and list(freq.values())[0] == 1


def run_tests():
  tests = [
      # Example 1 
      (("tea", [
          ("tea", False),
          ("team", True),
          ("seam", False),
      ])),
      # Example 2 
      (("on", [
          ("nooo", False),
          ("not", True),
          ("now", True),
      ])),
      # Additional test cases
      (("", [
          ("a", True),
          ("", False),
          ("ab", False),
      ])),
      (("xyz", [
          ("wxyz", True),
          ("xyzw", True),
          ("xyza", True),
          ("xyz", False),
      ])),
  ]

  for s, checks in tests:
    checker = Checker(s)
    for s2, want in checks:
      got = checker.expands_into(s2)
      assert got == want, \
          f"\nChecker({repr(s)}).expands_into({repr(s2)}): got: {              got}, want: {want}\n"

run_tests()
