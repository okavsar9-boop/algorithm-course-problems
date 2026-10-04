# 5.10 - Water Refilling
# Run: python3 05_10_water_refilling.py

def num_refills(a, b):

  # "Can we pour 'num_pours' times?"
  def is_before(num_pours):
    return num_pours * b <= a

  # Exponential search (repeated doubling until we find an upper bound).
  k = 1
  while is_before(k * 2):
    k *= 2

  # Binary search between k and k*2
  l, r = k, k * 2
  while r-l > 1:
    gap = r - l
    half_gap = gap >> 1  # Bit shift instead of division
    mid = l + half_gap
    if is_before(mid):
      l = mid
    else:
      r = mid
  return l


def run_tests():
  tests = [
    # Basic cases
    (10, 2, 5),
    (10, 3, 3),
    (10, 4, 2),
    (10, 5, 2),
    # Large numbers
    (1_000_000, 1, 1_000_000),
    # Large numbers with multiple refills
    (1_000_000, 500_000, 2),
    # Random cases
    (18, 5, 3),
    (182_983, 90, 2033),
  ]

  for a, b, expected in tests:
    result = num_refills(a, b)
    assert result == expected, f"num_refills({a}, {b}): got {result}, want {expected}"

run_tests()
