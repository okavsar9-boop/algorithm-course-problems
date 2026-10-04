# 15.8 - White Hat Hacker
# Run: python3 15_08_white_hat_hacker.py

def find_password(check_password, max_length):

  def visit(password):
    if len(password) > max_length:
      return None
    if check_password(password):
      return password
    for char in "abcdefghijklmnopqrstuvwxyz":
      if char not in password:  # Only add characters not already in password
        result = visit(password + char)
        if result:
          return result
    return None

  return visit("")


def run_tests():

  def check_password_factory(correct_password):
    return lambda s: s == correct_password

  tests = [
      "a",
      "bc",
      "def",
      "ghij",
      # Try higher numbers if you'd like to see how fast this stops working
      # but you'll need to edit the max_length variable in the find_password
      # function to make it longer. 8 characters may take a long time to run.
      # "klmno",
      # "pqrstu",
      # "vwxyzab"
  ]
  for correct_password in tests:
    check_password = check_password_factory(correct_password)
    got = find_password(check_password, 4)
    assert got == correct_password, f"\nfind_password({correct_password}): got: {        got}, want: {correct_password}\n"

run_tests()
