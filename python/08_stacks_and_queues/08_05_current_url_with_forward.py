# 8.5 - Current URL with Forward
# Run: python3 08_05_current_url_with_forward.py

def current_url_with_forward(actions):
  stack = []
  forward_stack = []

  for action, value in actions:
    if action == "go":
      stack.append(value)
      forward_stack = []
    elif action == "back":
      while len(stack) > 1 and value > 0:
        forward_stack.append(stack.pop())
        value -= 1
    else:
      while forward_stack and value > 0:
        stack.append(forward_stack.pop())
        value -= 1

  return stack[-1]

def current_url_with_forward_efficient(actions):
  urls = []
  current = -1

  for action, value in actions:
    if action == "go":
      current += 1
      del urls[current:]  # remove any urls after the current one
      urls.append(value)
    elif action == "back":
      current = max(0, current - value)
    else:
      current = min(len(urls) - 1, current + value)

  return urls[current]


def run_tests():
  tests = [
      ([["go", "google.com"], ["go", "wikipedia.com"], ["back", 1], ["forward", 1], [
       "back", 3], ["go", "netflix.com"], ["forward", 3]], "netflix.com"),
      ([["go", "example.com"], ["forward", 1]], "example.com"),
      ([["go", "site1.com"], ["go", "site2.com"], [
       "back", 1], ["forward", 1], ["back", 1]], "site1.com"),
  ]
  for actions, want in tests:
    got = current_url_with_forward(actions)
    assert got == want, f"\ncurrent_url_with_forward({actions}): got: {        got}, want: {want}\n"
    got = current_url_with_forward_efficient(actions)
    assert got == want, f"\ncurrent_url_with_forward_efficient({actions}): got: {        got}, want: {want}\n"

run_tests()
