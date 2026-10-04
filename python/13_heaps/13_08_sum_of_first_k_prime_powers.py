# 13.8 - Sum of First K Prime Powers
# Run: python3 13_08_sum_of_first_k_prime_powers.py

import heapq

def sum_of_powers(primes, k):
  m = 10**9 + 7
  # Initialize the heap with the first power of each prime.
  # Each element is a tuple (power, base)
  min_heap = [(p, p) for p in primes]
  heapq.heapify(min_heap)
  res = 0
  for _ in range(k):
    power, base = heapq.heappop(min_heap)
    res = (res + power) % m
    heapq.heappush(min_heap, ((power * base) % m, base))
  return res


def run_tests():
  """Test sum_of_powers function"""
  tests = [
      # Example 1 from the book
      ([2], 1, 2),
      # Example 2 from the book
      ([5], 3, 155),
      # Example 3 from the book
      ([2, 3], 7, 69),
      # k is 0
      ([2, 3], 0, 0),
      # k < primes.length
      ([5, 7, 11, 13, 17, 19], 4, 36),
      # prime order doesn't matter
      ([19, 17, 13, 11, 7, 5], 4, 36),
  ]
  for primes, n, want in tests:
    got = sum_of_powers(primes, n)
    assert got == want, f"\nsum_of_powers({primes}, {n}): got: {got}, want: {want}\n"

run_tests()
