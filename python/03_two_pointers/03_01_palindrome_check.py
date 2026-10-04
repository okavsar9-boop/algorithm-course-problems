# 3.1 - Palindrome Check
# Run: python3 03_01_palindrome_check.py

def palindrome(s):
  l, r = 0, len(s) - 1
  while l < r:
    if s[l] != s[r]:
      return False
    l += 1
    r -= 1
  return True


def run_tests():
  tests = [
    # Example from the book
    ("level", True),
    ("naan", True),
    # Additional test cases
    ("", True),
    ("a", True),
    ("ab", False),
    ("abc", False),
    ("abba", True),
    ("abcba", True),
    ]
  for s, want in tests:
    got = palindrome(s)
    assert got == want, f"\npalindrome({s}): got: {got}, want: {want}\n"

run_tests()
