# 14.21 - Count Subarrays With Good Start and Ending
# Run: python3 14_21_count_subarrays_with_good_start_and_ending.py

def count_subarrays_with_good_start_and_ending(sales):
  n = len(sales)
  good_days = sum(1 for x in sales if x >= 10)
  return good_days * (good_days + 1) // 2


def run_tests():
  tests = [
      # Example with mix of good and bad days
      ([0, 20, 5, 15, 10], 6),
      # Edge case - empty array
      ([], 0),
      # Edge case - all good days
      ([10, 20, 30], 6),
      # Edge case - all bad days
      ([0, 5, 8], 0),
      # Edge case - single good day
      ([10], 1),
  ]
  for sales, want in tests:
    got = count_subarrays_with_good_start_and_ending(sales)
    assert got == want, f"\ncount_subarrays_with_good_start_and_ending({sales}): got: {        got}, want: {want}\n"

run_tests()
