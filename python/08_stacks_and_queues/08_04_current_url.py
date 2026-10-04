# 8.4 - Current URL
# Run: python3 08_04_current_url.py

def current_url(actions):
  stack = []
  for action, value in actions:
    if action == "go":
      stack.append(value)
    else:
      while len(stack) > 1 and value > 0:
        stack.pop()
        value -= 1
  return stack[-1]


def run_tests():
  tests = [
      ([["go", "google.com"], ["go", "wikipedia.com"], ["go", "amazon.com"], ["back", 4], [
       "go", "youtube.com"], ["go", "netflix.com"], ["back", 1]], "youtube.com"),
      ([["go", "example.com"], ["back", 1]], "example.com"),
      ([["go", "site1.com"], ["go", "site2.com"],
       ["back", 1], ["back", 1]], "site1.com"),
  ]
  for actions, want in tests:
    got = current_url(actions)
    assert got == want, f"\ncurrent_url({actions}): got: {got}, want: {want}\n"

run_tests()
