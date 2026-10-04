# 3.5 - Reverse Case Match
# Run: python3 03_05_reverse_case_match.py

def reverse_case_match(s):
  l, r = 0, len(s) - 1
  while l < len(s) and r >= 0:
    if not s[l].islower():
      l += 1
    elif not s[r].isupper():
      r -= 1
    else:
      if s[l] != s[r].lower():
        return False
      l += 1
      r -= 1
  return True


def run_tests():
  tests = [
      # Example 1 from the book
      ("haDrRAHd", True),
      # Example 2 from the book
      ("haHrARDd", False),
      # Additional test cases
      ("", True),
      ("aA", True),
      ("Aa", True),
      ("BbbB", True),
      ("abAB", False),
      ("abBA", True),
      ("helloworldHELLOWORLD", False),
  ]
  for s, want in tests:
    got = reverse_case_match(s)
    assert got == want, f"\nreverse_case_match({s}): got: {got}, want: {want}\n"

run_tests()
