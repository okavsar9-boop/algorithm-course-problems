# 9.3 - Powers Mod M
# Run: python3 09_03_powers_mod_m.py

def power(a, p, m):
  if p == 0:
    return 1
  if p % 2 == 0:
    half = power(a, p // 2, m)
    return (half * half) % m
  return (a * power(a, p - 1, m)) % m


def run_tests():
  tests = [
    # Example 1 from book
    ((2, 5, 100), 32),
    # Example 2 from book
    ((2, 5, 30), 2),
    # Edge cases
    ((2, 0, 10), 1),
    ((3, 1, 5), 3),
    ((5, 3, 7), 6),
    # Large test case
    ((123456789, 987654321, 1000000007), 652541198),
  ]

  for (a, p, m), want in tests:
    got = power(a, p, m)
    assert got == want, f"\npower({a}, {p}, {m}): got: {got}, want: {want}\n"

run_tests()
