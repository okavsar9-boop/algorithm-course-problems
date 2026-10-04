# 9.5 - Laminal Arrays
# Run: python3 09_05_laminal_arrays.py

def max_laminal_sum_inefficient(arr):  # O(n log n)

  # Returns the max sum for a laminal array in arr[l:r].

  def max_laminal_sum_rec(l, r):
    if r - l == 1:
      return arr[l]    
    mid = (l + r) // 2
    option1 = max_laminal_sum_rec(l, mid)
    option2 = max_laminal_sum_rec(mid, r)
    option3 = sum(arr[l:r])
    return max(option1, option2, option3)

  return max_laminal_sum_rec(0, len(arr))

def max_laminal_sum(arr):  # O(n)

  # Returns the max sum for a laminal array in arr[l:r] and the sum of arr[l:r].
  def max_laminal_sum_rec(l, r):
    if r - l == 1:
      return arr[l], arr[l]
    mid = (l + r) // 2
    option1, left_sum = max_laminal_sum_rec(l, mid)
    option2, right_sum = max_laminal_sum_rec(mid, r)
    option3 = left_sum + right_sum
    return max(option1, option2, option3), option3

  res, _ = max_laminal_sum_rec(0, len(arr))
  return res


def run_tests():
  tests = [
      # Example 1 from book
      ([3, -9, 2, 4, -1, 5, 5, -4], 6),
      # Example 2 from book
      ([1], 1),
      # Example 3 from book
      ([-1, -2], -1),
      # Additional test case
      ([1, 2, 3, 4], 10),
      # Additional test case with all negatives
      ([-2, -1, -4, -3], -1),
      # Large test case
      ([1, -2, 3, -4, 5, -6, 7, -8, 9, -10, 11, -
        12, 13, -14, 15, -16], 15),
  ]
  for arr, want in tests:
    got = max_laminal_sum(arr)
    assert got == want, f"\nmax_laminal_sum({arr}): got: {got}, want: {want}\n"
    got = max_laminal_sum_inefficient(arr)
    assert got == want, f"\nmax_laminal_sum_inefficient({arr}): got: {got}, want: {want}\n"

run_tests()
