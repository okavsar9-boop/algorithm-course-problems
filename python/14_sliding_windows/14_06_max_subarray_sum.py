# 14.6 - Max Subarray Sum
# Run: python3 14_06_max_subarray_sum.py

def max_subarray_sum(arr):
  max_val = max(arr)
  if max_val <= 0:  # Edge case without positive values
    return max_val

  r = 0  # We don't need the l pointer
  window_sum = 0
  cur_max = 0
  while r < len(arr):
    can_grow = window_sum + arr[r] >= 0
    if can_grow:
      window_sum += arr[r]
      r += 1
      cur_max = max(cur_max, window_sum)
    else:
      window_sum = 0
      r = r + 1
  return cur_max


def run_tests():
  tests = [
      # Example 1 from the book
      ([1, 2, 3, -2, 1], 6),
      # Example 2 from the book
      ([1, 2, 3, -2, 7], 11),
      # Example 3 from the book
      ([1, 2, 3, -8, 7], 7),
      # Example 4 from the book
      ([-2, -3, -4], -2),
      # Edge case - single element
      ([5], 5),
      # Edge case - all positive
      ([1, 2, 3], 6),
  ]
  for arr, want in tests:
    got = max_subarray_sum(arr)
    assert got == want, f"\nmax_subarray_sum({arr}): got: {got}, want {want}\n"

run_tests()
