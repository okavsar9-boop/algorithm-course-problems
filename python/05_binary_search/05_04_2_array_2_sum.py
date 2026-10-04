# 5.4 - 2-Array 2-Sum
# Run: python3 05_04_2_array_2_sum.py

def two_array_two_sum(sorted_arr, unsorted_arr):

  def binary_search(arr, target):

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

  for i, val in enumerate(unsorted_arr):
    idx = binary_search(sorted_arr, -val)
    if idx != -1:
      return [idx, i]
  return [-1, -1]


def run_tests():
  tests = [
      # Example from book
      ([-5, -4, -1, 4, 6, 6, 7], [-3, 7, 18, 4, 6], [1, 3]),
      # no solution
      ([1, 2, 3], [1, 2, 3], [-1, -1]),
      ([1], [-1], [0, 0]),
      ([1, 2], [-2, -1], [1, 0]),
      ([0, 1, 2, 3], [3, 2, 1, 0], [0, 3]),
  ]

  for sorted_arr, unsorted_arr, want in tests:
    got = two_array_two_sum(sorted_arr, unsorted_arr)
    assert got == want, f"\ntwo_array_two_sum({sorted_arr}, {unsorted_arr}): got: {        got}, want: {want}\n"

run_tests()
