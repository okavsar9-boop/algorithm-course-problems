# 6.4 - Multi-Account Cheating
# Run: python3 06_04_multi_account_cheating.py

def multi_account_cheating(users):
  unique_lists = set()
  for _, ips in users:
    immutable_list = tuple(sorted(ips))
    if immutable_list in unique_lists:
      return True
    unique_lists.add(immutable_list)
  return False


def run_tests():
  tests = [
      # Example 
      ([("mike", ["203.0.3.10", "208.51.0.5", "52.0.2.5"]),
        ("bob", ["111.0.0.10", "222.0.0.5", "222.0.0.8"]),
          ("bob2", ["222.0.0.5", "222.0.0.8", "111.0.0.10"])], True),
      # Additional test cases
      ([], False),
      ([("alice", ["1.1.1.1"])], False),
      ([("alice", ["1.1.1.1", "2.2.2.2"]),
        ("bob", ["2.2.2.2", "1.1.1.1"])], True),
      ([("alice", ["1.1.1.1"]), ("bob", ["2.2.2.2"])], False),
  ]
  for users, want in tests:
    got = multi_account_cheating(users)
    assert got == want, f"\nmulti_account_cheating({users}): got: {        got}, want: {want}\n"

run_tests()
