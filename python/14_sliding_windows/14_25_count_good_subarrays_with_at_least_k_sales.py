# 14.25 - Count Good Subarrays With at Least k Sales
# Run: python3 14_25_count_good_subarrays_with_at_least_k_sales.py

def count_good_subarrays_with_at_least_k_sales(sales, k):
  # First find maximal subarrays without bad days
  good_subarrays = []
  start = 0
  for i in range(len(sales)):
    if sales[i] < 10:
      if i > start:
        good_subarrays.append(sales[start:i])
      start = i + 1
  if start < len(sales):
    good_subarrays.append(sales[start:])

  # Then count subarrays with at least k total sales in each good subarray
  total = 0
  for sub in good_subarrays:
    total += count_at_least_k_total_sales(sub, k)
  return total

def count_at_least_k_total_sales(arr, k):
  n = len(arr)
  total_subarrays = n * (n + 1) // 2
  if k == 0:
    return total_subarrays
  return total_subarrays - count_at_most_k_total_sales(arr, k - 1)

def count_at_most_k_total_sales(arr, k):
  l, r = 0, 0
  window_sum = 0
  count = 0
  while r < len(arr):
    window_sum += arr[r]
    r += 1
    while l < r and window_sum > k:
      window_sum -= arr[l]
      l += 1
    count += r - l
  return count


def run_tests():
  tests = [
      # Example with mix of good and bad days
      ([15, 20, 5, 30, 25], 50, 1),
      # Edge case - empty array
      ([], 10, 0),
      # Edge case - all good days
      ([10, 20, 30], 40, 2),
      # Edge case - all bad days
      ([0, 5, 8], 10, 0),
      # Edge case - k = 0
      ([10, 20, 5, 30], 0, 4),
  ]
  for sales, k, want in tests:
    got = count_good_subarrays_with_at_least_k_sales(sales, k)
    assert got == want, f"\ncount_good_subarrays_with_at_least_k_sales({sales}, {k}): got: {        got}, want: {want}\n"

run_tests()
