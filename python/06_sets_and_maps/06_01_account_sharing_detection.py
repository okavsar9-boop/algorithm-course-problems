# 6.1 - Account Sharing Detection
# Run: python3 06_01_account_sharing_detection.py

def account_sharing(connections):
  seen = set()
  for ip, username in connections:
    if username in seen:
      return ip
    seen.add(username)
  return ""


def run_tests():
  tests = [
      # Example 1 
      ([("203.0.113.10", "mike"), ("298.51.100.25", "bob"),
        ("292.0.2.5", "mike"), ("203.0.113.15", "bob2")], "203.0.113.10"),
      # Example 2 
      ([("111.0.0.0", "mike"), ("111.0.0.1", "mike"),
        ("111.0.0.2", "bob"), ("111.0.0.3", "bob")], "111.0.0.0"),
      # Example 3 
      ([("111.0.0.0", "mike"), ("111.0.0.1", "mike2"),
        ("111.0.0.2", "mike3"), ("111.0.0.3", "mike4")], ""),
      # Edge case - empty list
      ([], ""),
      # Edge case - single connection
      ([("1.1.1.1", "alice")], ""),
  ]
  for connections, want in tests:
    got = account_sharing(connections)

    # Check if got matches want directly
    if got == want:
      continue

    # If want is empty, got must also be empty
    if want == "":
      assert got == "", f"\naccount_sharing({connections}): got: {          got}, want: {want}\n"
      continue

run_tests()
