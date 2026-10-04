# 5.9 - Min-Subarray-Sum Split
# Run: python3 05_09_min_subarray_sum_split.py

import math

def is_before(arr, k, max_sum):
  splits_required = get_splits_required(arr, max_sum)
  return splits_required > k

def get_splits_required(arr, max_sum):
  splits_required = 1
  current_sum = 0
  for num in arr:
    if current_sum + num > max_sum:
      splits_required += 1
      current_sum = num  # Start a new subarray with the current number.
    else:
      current_sum += num
  return splits_required

def min_subarray_sum_split(arr, k):
  l, r = max(arr), sum(arr)  # Range for the maximum subarray sum.
  if not is_before(arr, k, l):
    return l
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(arr, k, mid):
      l = mid
    else:
      r = mid
  return r

def min_subarray_sum_split_memoization(arr, k):
  n = len(arr)
  memo = {}

  def min_split_rec(i, x):
    if (i, x) in memo:
      return memo[(i, x)]

    # Base cases
    if n - i == x:  # Put each element in its own subarray.
      memo[(i, x)] = max(arr[i:])
    elif x == 1:    # Put all elements in one subarray.
      memo[(i, x)] = sum(arr[i:])
    else:  # General case
      current_sum = 0
      res = math.inf
      for p in range(i, n - x + 1):
        current_sum += arr[p]
        res = min(res, max(current_sum, min_split_rec(p + 1, x - 1)))
      memo[(i, x)] = res

    return memo[(i, x)]

  return min_split_rec(0, k)


def run_tests():
  tests = [
      # Example 1 from the book
      ([10, 5, 8, 9, 11], 3, 17),
      # Example 2 from the book
      ([10, 10, 10, 10, 10], 2, 30),
      # Extra example
      ([9, 12, 13], 3, 13),
      # Edge case - k=1
      ([1, 2, 3], 1, 6),
      # Edge case - k=length
      ([1, 2, 3], 3, 3),
      # Edge case - single element
      ([5], 1, 5)
  ]

  for arr, k, want in tests:
    got = min_subarray_sum_split(arr, k)
    assert got == want, f"\nmin_subarray_sum_split({arr}, {k}): got: {        got}, want: {want}\n"

    got = min_subarray_sum_split_memoization(arr, k)
    assert got == want, f"\nmin_subarray_sum_split_memoization({arr}, {k}): got: {        got}, want: {want}\n"

run_tests()
