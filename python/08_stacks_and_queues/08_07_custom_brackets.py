# 8.7 - Custom Brackets
# Run: python3 08_07_custom_brackets.py

def custom_brackets(s, brackets):
  open_to_close = dict()
  close_set = set()
  for pair in brackets:
    open_to_close[pair[0]] = pair[1]
    close_set.add(pair[1])

  stack = []
  for c in s:
    if c in open_to_close:
      stack.append(open_to_close[c])
    elif c in close_set:
      if not stack or stack[-1] != c:
        return False
      stack.pop()
  return len(stack) == 0


def run_tests():
  tests = [
      # Example 1 from book
      ("((a+b)*[c-d]-{e/f})", ["()", "[]", "{}"], True),
      # Example 2 from book
      ("()[}", ["()", "[]", "{}"], False),
      # Example 3 from book
      ("([)]", ["()", "[]", "{}"], False),
      # Example 4 from book
      ("<div> hello :) </div>", ["<>", "()"], False),
      # Example 5 from book
      (")))(()((", [")("], True),
      # Empty string
      ("", ["()"], True),
      # Single character
      ("(", ["()"], False),
      # Multiple bracket types
      ("<<>>()[]{}", ["<>", "()", "[]", "{}"], True),
      # Nested brackets
      ("[{()}]", ["()", "[]", "{}"], True),
      # Unmatched opening bracket
      ("(()", ["()"], False),
      # Unmatched closing bracket
      ("())", ["()"], False),
      # Wrong order of closing
      ("({)}", ["()", "{}"], False),
      # Non-bracket characters mixed in
      ("a(b)c[d]e", ["()", "[]"], True),
      # Multiple identical bracket pairs
      ("<<>>", ["<>"], True),
  ]
  for s, brackets, want in tests:
    got = custom_brackets(s, brackets)
    assert got == want, f"\ncustom_brackets({s}, {brackets}): got: {        got}, want: {want}\n"

run_tests()
