# 3.8 - Three-Way Merge Without Duplicates
# Run: python3 03_08_three_way_merge_without_duplicates.py

def three_way_merge(arr1, arr2, arr3):
  p1, p2, p3 = 0, 0, 0
  res = []
  while p1 < len(arr1) or p2 < len(arr2) or p3 < len(arr3):
    # Find the smallest value among current positions
    min_val = float('inf')
    if p1 < len(arr1):
      min_val = min(min_val, arr1[p1])
    if p2 < len(arr2):
      min_val = min(min_val, arr2[p2])
    if p3 < len(arr3):
      min_val = min(min_val, arr3[p3])

    # Skip duplicates of min_val in all arrays
    if p1 < len(arr1) and arr1[p1] == min_val:
      p1 += 1
    if p2 < len(arr2) and arr2[p2] == min_val:
      p2 += 1
    if p3 < len(arr3) and arr3[p3] == min_val:
      p3 += 1

    # Only add if we haven't added this value before
    if not res or res[-1] != min_val:
      res.append(min_val)

  return res


def run_tests():
  tests = [ # Example from the book
    ([2, 3, 3, 4, 5, 7], [3, 3, 9], [3, 3, 9], [2, 3, 4, 5, 7, 9]), # Additional test cases
    ([], [], [], []),
    ([1], [], [], [1]),
    ([1], [1], [1], [1]),
    ([1, 2, 3], [2, 3, 4], [3, 4, 5], [1, 2, 3, 4, 5]),
    ([1, 1, 1], [1, 1], [1], [1]),
    ([1, 2, 3], [4, 5, 6], [7, 8, 9], [1, 2, 3, 4, 5, 6, 7, 8, 9]),
    ]
  for arr1, arr2, arr3, want in tests:
    got = three_way_merge(arr1, arr2, arr3)
    assert got == want, f"\nthree_way_merge({arr1}, {arr2}, {arr3}): got: {got}, want: {want}\n"

run_tests()
