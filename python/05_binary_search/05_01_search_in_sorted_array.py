# 5.1 - Search In Sorted Array
# Run: python3 05_01_search_in_sorted_array.py

def search_in_sorted_array(arr, target):
  l, r = 0, len(arr) - 1
  while l <= r:
    mid = (l + r) // 2
    if arr[mid] == target:
      return mid
    elif arr[mid] < target:
      l = mid + 1
    else:
      r = mid - 1
  return -1

def search_in_sorted_array_with_transition_point(arr, target):
  if not arr:
    return -1

  def is_before(i):
    return arr[i] < target

  # Handle edge cases to ensure l is in the before region
  # and r is in the after region
  l, r = 0, len(arr) - 1
  if not is_before(l):
    if arr[l] == target:
      return l
    return -1
  if is_before(r):
    return -1

  # Main binary search loop
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  if arr[r] == target:
    return r
  return -1


def run_tests():
  tests = [
      # Example 1 from book
      ([-2, 0, 3, 4, 7, 9, 11], 3, 2),
      # Example 2 from book
      ([-2, 0, 3, 4, 7, 9, 11], 2, -1),
      # Edge case - empty array
      ([], 5, -1),
      # Edge case - target at start
      ([1, 2, 3], 1, 0),
      # Edge case - target at end
      ([1, 2, 3], 3, 2),
      # Edge case - single element
      ([5], 5, 0),
      # Edge case - not found
      ([1, 3, 5], 2, -1)
  ]

  for arr, target, want in tests:
    got = search_in_sorted_array(arr, target)
    assert got == want, f"\nsearch_in_sorted_array({arr}, {target}): got: {        got}, want: {want}\n"
    got = search_in_sorted_array_with_transition_point(arr, target)
    assert got == want, f"\nsearch_in_sorted_array_with_transition_point({arr}, {target}): got: {        got}, want: {want}\n"

run_tests()
