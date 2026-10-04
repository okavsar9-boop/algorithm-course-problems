# 21.6 - Largest Rectangle
# Run: python3 21_06_largest_rectangle.py

def next_smaller_element(arr):
  n = len(arr)
  nse = [n] * n
  stack = []
  for i in range(n - 1, -1, -1):
    while stack and arr[stack[-1]] >= arr[i]:
      stack.pop()
    if stack:
      nse[i] = stack[-1]
    stack.append(i)
  return nse

def prev_smaller_element(arr):
  n = len(arr)
  pse = [-1] * n
  stack = []
  for i in range(n):
    while stack and arr[stack[-1]] >= arr[i]:
      stack.pop()
    if stack:
      pse[i] = stack[-1]
    stack.append(i)
  return pse

def largest_rectangle(tiles):
  n = len(tiles)
  nse = next_smaller_element(tiles)
  pse = prev_smaller_element(tiles)

  max_area = 0
  for i in range(n):
    width = nse[i] - pse[i] - 1
    area = width * tiles[i]
    max_area = max(max_area, area)
  return max_area


def run_tests():
  tests = [
      # Example 1 from the book
      ([1, 2, 3], 4),
      # Example 2 from the book
      ([2, 1, 2], 3),
      # Example 3 from the book
      ([1, 2, 5, 2, 1], 6),
      # Edge cases
      ([1], 1),
      ([0], 0),
      ([5, 5, 5, 5, 5], 25),
      ([0, 0, 0, 0, 0], 0),
      ([1, 0, 1], 1),
  ]

  for tiles, want in tests:
    got = largest_rectangle(tiles)
    assert got == want, f"\nlargest_rectangle({tiles}): got: {got}, want: {want}\n"

run_tests()
