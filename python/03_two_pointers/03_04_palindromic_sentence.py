# 3.4 - Palindromic Sentence
# Run: python3 03_04_palindromic_sentence.py

def palindromic_sentence(s):
  l, r = 0, len(s) - 1
  while l < r:
    if not s[l].isalpha():
      l += 1
    elif not s[r].isalpha():
      r -= 1
    else:
      if s[l].lower() != s[r].lower():
        return False
      l += 1
      r -= 1
  return True


def run_tests():
  tests = [
    # Example from the book
    ("Bob wondered, 'Now, Bob?'", True),
    # Additional test cases
    ("", True),
    ("a", True),
    ("A man, a plan, a canal: Panama", True),
    ("race a car", False),
    ("Was it a car or a cat I saw?", True),
    ("hello", False),
    (".,?!'", True),
    ]
  for s, want in tests:
    got = palindromic_sentence(s)
    assert got == want, f"\npalindromic_sentence({s}): got: {got}, want: {want}\n"

run_tests()
