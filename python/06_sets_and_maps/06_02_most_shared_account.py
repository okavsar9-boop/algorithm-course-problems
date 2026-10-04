# 6.2 - Most Shared Account
# Run: python3 06_02_most_shared_account.py

def most_shared_account(connections):
  user_to_count = dict()
  for _, user in connections:
    if not user in user_to_count:
      user_to_count[user] = 0
    user_to_count[user] += 1
  most_shared_user = None
  for user, count in user_to_count.items():
    if not most_shared_user or count > user_to_count[most_shared_user]:
      most_shared_user = user
  return most_shared_user


def run_tests():
  tests = [
      # Example 
      ([("203.0.113.10", "mike"), ("208.51.100.25", "bob"),
        ("202.0.2.5", "mike"), ("203.0.113.15", "bob2")], "mike"),
      # Additional test cases
      ([], None),
      ([("1.1.1.1", "alice")], "alice"),
      ([("1.1.1.1", "alice"), ("1.1.1.2", "bob"),
        ("1.1.1.3", "alice"), ("1.1.1.4", "bob")], "alice"),
  ]
  for connections, want in tests:
    got = most_shared_account(connections)
    assert got == want or (want and got and
                           len([(ip, u) for ip, u in connections if u == got]) ==
                           len([(ip, u) for ip, u in connections if u == want])), \
        f"\nmost_shared_account({connections}): got: {got}, want: {want}\n"

run_tests()
