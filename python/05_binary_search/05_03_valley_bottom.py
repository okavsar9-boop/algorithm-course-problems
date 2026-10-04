# 5.3 - Valley Bottom
# Run: python3 05_03_valley_bottom.py

def valley_bottom(arr):

  def is_before(i):
    return i == 0 or arr[i] < arr[i - 1]

  l, r = 0, len(arr) - 1
  if is_before(r):
    return arr[r]

  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid
  return arr[l]


def run_tests():
  tests = [
      # Example 1 from book
      ([6, 5, 4, 7, 9], 4),
      # Example 2 from book
      ([5, 6, 7], 5),
      # Example 3 from book
      ([7, 6, 5], 5),
      ([2, 1], 1),
      ([3, 2, 4], 2)
  ]

  for arr, want in tests:
    got = valley_bottom(arr)
    assert got == want, f"\nvalley_bottom({arr}): got: {got}, want: {want}\n"

run_tests()
