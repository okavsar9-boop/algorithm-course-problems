# 21.5 - Next Greater Element
# Run: python3 21_05_next_greater_element.py

def next_greater_element(arr):
  n = len(arr)
  nge = [-1] * n
  stack = []

  # Iterate right to left
  for i in range(n - 1, -1, -1):
    # Pop all NGE candidates from stack that are <= arr[i]
    while stack and arr[stack[-1]] <= arr[i]:
      stack.pop()

    # If stack not empty, top is NGE of i
    if stack:
      nge[i] = stack[-1]

    # Add i to stack as candidate for future elements
    stack.append(i)

  return nge


def run_tests():
  tests = [
      # Example 1 from the book
      ([5, 3, 10, 8, 8, 10], [2, 2, -1, 5, 5, -1]),
      # Example 2 from the book
      ([4, 2, 6, 4, 5, 2, 4, 7, 3, 7], [2, 2, 7, 4, 7, 6, 7, -1, 9, -1]),
      # Example 3 from the book
      ([5, 5, 5, 5, 5], [-1, -1, -1, -1, -1]),
      # Example 4 from the book
      ([5, 6, 7, 8, 9], [1, 2, 3, 4, -1]),
      # Edge case - empty array
      ([], []),
      # Edge case - single element
      ([1], [-1])
  ]

  for arr, want in tests:
    got = next_greater_element(arr)
    assert got == want, f"\nnext_greater_element({arr}): got: {got}, want: {want}\n"

run_tests()
