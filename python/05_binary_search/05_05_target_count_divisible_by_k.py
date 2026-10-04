# 5.5 - Target Count Divisible By K
# Run: python3 05_05_target_count_divisible_by_k.py

def target_count_divisible_by_k(arr, target, k):

  # Returns -1 if target is not in arr, or the first index of target in arr
  # otherwise.
  def binary_search_first():

    def is_before(i):
      return arr[i] < target

    l, r = 0, len(arr) - 1
    if arr[l] > target or arr[r] < target:
      return -1
    if arr[l] == target:
      return l

    while r - l > 1:
      mid = (l + r) // 2
      if is_before(mid):
        l = mid
      else:
        r = mid

    if arr[r] == target:
      return r
    return -1

  # Assumes target is in arr. Returns the last index of target in arr.
  def binary_search_last():
    
    def is_before(i):
      return arr[i] <= target

    l, r = 0, len(arr) - 1
    if arr[r] == target:
      return r

    while r - l > 1:
      mid = (l + r) // 2
      if is_before(mid):
        l = mid
      else:
        r = mid
    return l

  first = binary_search_first()
  if first == -1:
    return True  # 0 is a multiple of any number.
  last = binary_search_last()
  count = last - first + 1
  return count % k == 0


def run_tests():
  tests = [
      # Example 1
      ([1, 2, 2, 2, 2, 2, 2, 3], 2, 3, True),
      # Example 2
      ([1, 2, 2, 2, 2, 2, 2, 3], 2, 4, False),
      # Example 3: 0 occurrences, 0 is multiple of any number
      ([1, 2, 2, 2, 2, 2, 2, 3], 4, 3, True),
      # Example 4
      ([1, 1, 2, 2, 2], 1, 3, False),
      # single occurrence, at the start
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 1, 1, True),
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 1, 2, False),
      # single occurrence, at the end
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 19, 1, True),
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 19, 2, False),
      # single occurrence, in the middle
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 9, 1, True),
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 9, 2, False),
      # smaller than any elements
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 0, 1, True),
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 0, 2, True),
      # larger than any elements
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 20, 1, True),
      ([1, 3, 5, 7, 9, 11, 13, 15, 17, 19], 20, 2, True),
      # Edge case - every occurrence is target
      ([5, 5, 5, 5, 5], 5, 5, True),
      ([5, 5, 5, 5, 5], 5, 3, False),
  ]
  for arr, target, k, want in tests:
    got = target_count_divisible_by_k(arr, target, k)
    assert got == want, f"\ntarget_count_divisible_by_k({arr}, {target}, {k}): got: {        got}, want: {want}\n"

run_tests()
