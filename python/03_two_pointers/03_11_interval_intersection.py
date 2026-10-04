# 3.11 - Interval Intersection
# Run: python3 03_11_interval_intersection.py

def intersection(int1, int2):
  overlap_start = max(int1[0], int2[0])
  overlap_end = min(int1[1], int2[1])
  return [overlap_start, overlap_end]

def interval_intersection(arr1, arr2):
  p1, p2 = 0, 0
  n1, n2 = len(arr1), len(arr2)
  res = []
  while p1 < n1 and p2 < n2:
    int1, int2 = arr1[p1], arr2[p2]
    if int1[1] < int2[0]:
      p1 += 1
    elif int2[1] < int1[0]:
      p2 += 1
    else:
      res.append(intersection(int1, int2))
      if int1[1] < int2[1]:
        p1 += 1
      else:
        p2 += 1
  return res


def run_tests():
  tests = [ # Example 1 from the book
    ([[0, 1], [4, 6], [7, 8]], [[2, 3], [5, 9], [10, 11]], [[5, 6], [7, 8]]), # Example 2 from the book
    ([[2, 4], [5, 8]], [[3, 3], [4, 7]], [[3, 3], [4, 4], [5, 7]]), # Additional test cases
    ([], [], []),
    ([[1, 2]], [], []),
    ([[1, 3]], [[2, 4]], [[2, 3]]),
    ([[1, 5]], [[2, 3]], [[2, 3]]),
    ([[1, 2], [3, 4]], [[2, 3]], [[2, 2], [3, 3]]),
    ]
  for arr1, arr2, want in tests:
    got = interval_intersection(arr1, arr2)
    assert got == want, f"\ninterval_intersection({arr1}, {arr2}): got: {got}, want: {want}\n"

run_tests()
