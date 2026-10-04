# 7.3 - Delete Operations
# Run: python3 07_03_delete_operations.py

def process_operations(nums, operations):
  n = len(nums)
  deleted = set()
  sorted_indices = []
  for i in range(n):
    sorted_indices.append(i)

  # Since the indices start in order and the sort is stable, we break ties by
  # smallest index, as required by the problem.
  sorted_indices.sort(key=lambda i: nums[i])

  smallest_idx = 0
  for op in operations:
    if 0 <= op < n:
      deleted.add(op)
    else:
      # Skip until the next non-deleted smallest index.
      while smallest_idx < n and sorted_indices[smallest_idx] in deleted:
        smallest_idx += 1
      if smallest_idx < n:
        deleted.add(sorted_indices[smallest_idx])
        smallest_idx += 1
  res = []
  for i in range(n):
    if not i in deleted:
      res.append(nums[i])
  return res


def run_tests():
  tests = [
      # Example 1 from the book
      ([50, 30, 70, 20, 80], [2, -1, 4, -1], [50]),
      # Example 2 from the book
      ([1, 2, 3], [], [1, 2, 3]),
      # Example 3 from the book
      ([1, 2, 3], [-1, -1, -1], []),
      # Edge case - delete all indices
      ([1, 2, 3], [0, 1, 2], []),
      # Edge case - single element
      ([1], [-1], []),
      # Edge case - duplicates
      ([5, 5, 5], [-1, -1], [5]),
      # Edge case - negative numbers
      ([-3, -2, -1], [-1, -1], [-1]),
      # Mixed operations with duplicates
      ([10, 10, 20, 20], [1, -1, -1], [20]),
      # Operations targeting same index
      ([1, 2, 3], [0, 0, 0], [2, 3]),
      # Alternating index and min operations
      ([5, 4, 3, 2, 1], [2, -1, 0, -1], [4]),
      # Large numbers within constraints
      ([10**9, -(10**9), 0], [-1, -1], [10**9])
  ]
  for nums, operations, want in tests:
    got = process_operations(nums, operations)
    assert got == want, f"\nprocess_operations({nums}, {operations}): got: {got}, want: {want}\n"

run_tests()
