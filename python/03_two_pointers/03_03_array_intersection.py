# 3.3 - Array Intersection
# Run: python3 03_03_array_intersection.py

def common_elements(arr1, arr2):
  p1, p2 = 0, 0
  res = []
  while p1 < len(arr1) and p2 < len(arr2):
    if arr1[p1] == arr2[p2]:
      res.append(arr1[p1])
      p1 += 1
      p2 += 1
    elif arr1[p1] < arr2[p2]:
      p1 += 1
    else:
      p2 += 1
  return res


def run_tests():
  tests = [ # Example 1 from the book
    ([1, 2, 3], [1, 3, 5], [1, 3]), # Example 2 from the book
    ([1, 1, 1], [1, 1], [1, 1]), # Additional test cases
    ([], [], []),
    ([1], [], []),
    ([], [1], []),
    ([1], [1], [1]),
    ([1, 2, 3], [4, 5, 6], []),
    ([1, 2, 2, 3], [2, 2, 3], [2, 2, 3]),
    ]
  for arr1, arr2, want in tests:
    got = common_elements(arr1, arr2)
    assert got == want, f"\ncommon_elements({arr1}, {arr2}): got: {got}, want: {want}\n"

run_tests()
