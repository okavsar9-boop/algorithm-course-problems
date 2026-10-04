# 3.15 - Quicksort Partition
# Run: python3 03_15_quicksort_partition.py

def partition(arr, pivot):
  # First pass: partition into <= pivot and > pivot
  l, r = 0, len(arr) - 1
  while l < r:
    if arr[l] <= pivot:
      l += 1
    elif arr[r] > pivot:
      r -= 1
    else:
      arr[l], arr[r] = arr[r], arr[l]
      l += 1
      r -= 1

  # Find the boundary between <= pivot and > pivot
  boundary = 0
  while boundary < len(arr) and arr[boundary] <= pivot:
    boundary += 1

  # Second pass: partition the <= pivot section into < pivot and = pivot
  l, r = 0, boundary - 1
  while l < r:
    if arr[l] < pivot:
      l += 1
    elif arr[r] == pivot:
      r -= 1
    else:
      arr[l], arr[r] = arr[r], arr[l]
      l += 1
      r -= 1


def run_tests():
  def is_valid_partition(arr, pivot):
    # Find boundaries between sections
    first = 0
    while first < len(arr) and arr[first] < pivot:
      first += 1
    second = first
    while second < len(arr) and arr[second] == pivot:
      second += 1

    # Check that all elements are in their correct sections
    for i in range(first):
      if arr[i] >= pivot:
        return False
    for i in range(first, second):
      if arr[i] != pivot:
        return False
    for i in range(second, len(arr)):
      if arr[i] <= pivot:
        return False
    return True

  tests = [
      # Example 1 from the book
      ([1, 7, 2, 3, 3, 5, 3], 4),
      # Example 2 from the book
      ([1, 7, 2, 3, 3, 5, 3], 3),
      # Additional test cases
      ([], 1),
      ([1], 1),
      ([1, 2], 1),
      ([2, 1], 1),
      ([3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5], 4),
  ]
  for arr, pivot in tests:
    arr_copy = arr.copy()  # Make a copy since partition modifies in place
    partition(arr_copy, pivot)
    assert is_valid_partition(arr_copy, pivot), \
        f"\npartition({arr}, {pivot}): got: {arr_copy}\n"
run_tests()
