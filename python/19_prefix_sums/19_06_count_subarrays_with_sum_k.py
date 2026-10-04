# 19.6 - Count Subarrays With Sum K
# Run: python3 19_06_count_subarrays_with_sum_k.py

def count_subarrays(arr, k):
  prefix_sum = [0] * len(arr)
  prefix_sum[0] = arr[0]
  for i in range(1, len(arr)):
    prefix_sum[i] = prefix_sum[i-1] + arr[i]

  prefix_sum_to_count = {0: 1}  # For the empty prefix.
  count = 0
  for val in prefix_sum:
    if val - k in prefix_sum_to_count:
      count += prefix_sum_to_count[val - k]
    if val not in prefix_sum_to_count:
      prefix_sum_to_count[val] = 0
    prefix_sum_to_count[val] += 1
  return count


def run_tests():
  tests = [
    # Example from the book
    ([1, 2, 3, 2, 1], 3, 3),
    ([-1, -2, -3, 2, 1], -3, 4),
    # Edge case: All zeros
    ([0, 0, 0], 0, 6),
    # Edge case: No subarray with sum k
    ([1, 2, 3], 10, 0),
  ]

  for arr, k, want in tests:
    got = count_subarrays(arr, k)
    assert got == want, f"\ncount_subarrays({arr}, {k}): got: {got}, want: {want}\n"

run_tests()
