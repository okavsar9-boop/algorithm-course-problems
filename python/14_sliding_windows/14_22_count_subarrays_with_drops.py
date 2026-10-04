# 14.22 - Count Subarrays With Drops
# Run: python3 14_22_count_subarrays_with_drops.py

def count_at_most_k_drops(arr, k):
  l, r = 0, 0
  window_drops = 0
  count = 0
  while r < len(arr):
    can_grow = r == 0 or arr[r] >= arr[r - 1] or window_drops < k
    if can_grow:
      if r > 0 and arr[r] < arr[r - 1]:
        window_drops += 1
      r += 1
      count += r - l
    else:
      if arr[l] > arr[l + 1]:
        window_drops -= 1
      l += 1
  return count

def count_exactly_k_drops(k, at_most_k_drops, at_most_k_minus_1_drops):
  if k == 0:
    return at_most_k_drops
  return at_most_k_drops - at_most_k_minus_1_drops

def count_at_least_k_drops(n, k, at_most_k_minus_1_drops):
  total_count = n * (n + 1) // 2
  if k == 0:
    return total_count
  return total_count - at_most_k_minus_1_drops

def count_subarrays_with_drops(arr, k):
  at_most_k_drops = count_at_most_k_drops(arr, k)
  at_most_k_minus_1_drops = 0 if k == 0 else count_at_most_k_drops(arr, k - 1)
  return [
      at_most_k_drops,
      count_exactly_k_drops(k, at_most_k_drops, at_most_k_minus_1_drops),
      count_at_least_k_drops(len(arr), k, at_most_k_minus_1_drops),
  ]


def run_tests():
  tests = [
      # Example 1 from the book
      ([1, 2, 3], 1, [6, 0, 0]),
      # Example 2 from the book
      ([3, 2, 1], 1, [5, 2, 3]),
      # Example 3
      ([5, 4, 3, 2, 1], 2, [12, 3, 6]),
      # Edge case - empty array
      ([], 1, [0, 0, 0]),
      # Edge case - single element
      ([1], 1, [1, 0, 0]),
      # Edge case - k = 0
      ([5, 3, 2, 1], 0, [4, 4, 10]),
      # Alternating
      ([6, 2, 7, 3, 8, 4, 9, 5, 10, 6], 3, [50, 8, 13]),
  ]
  for arr, k, want in tests:
    got = count_subarrays_with_drops(arr, k)
    assert got == want, f"\ncount_subarrays_with_drops({arr}, {k}): got: {got}, want: {want}\n"

run_tests()
