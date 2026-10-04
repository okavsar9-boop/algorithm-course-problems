# 6.3 - Most Frequent Octet
# Run: python3 06_03_most_frequent_octet.py

def most_frequent_octet(ips):
  octet_to_count = dict()
  for ip in ips:
    first_octet = ip.split('.')[0]
    if not first_octet in octet_to_count:
      octet_to_count[first_octet] = 0
    octet_to_count[first_octet] += 1

  most_frequent = None
  for octet, count in octet_to_count.items():
    if not most_frequent or count > octet_to_count[most_frequent]:
      most_frequent = octet
  return most_frequent


def run_tests():
  tests = [
      # Example 
      (["203.0.113.10", "208.51.100.5", "202.0.2.5", "203.0.113.5"], "203"),
      # Additional test cases
      ([], None),
      (["192.168.1.1"], "192"),
      (["10.0.0.1", "10.0.0.2", "192.168.1.1"], "10"),
      (["172.16.0.1", "172.16.0.2", "172.17.0.1", "172.16.0.3"], "172"),
  ]
  for ips, want in tests:
    got = most_frequent_octet(ips)
    assert got == want, f"\nmost_frequent_octet({ips}): got: {        got}, want: {want}\n"

run_tests()
