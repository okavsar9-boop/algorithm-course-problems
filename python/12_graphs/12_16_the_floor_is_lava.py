# 12.16 - The Floor Is Lava
# Run: python3 12_16_the_floor_is_lava.py

import math

def segment_distance(min1, max1, min2, max2):
  return max(0, max(min1, min2) - min(max1, max2))

def distance(furniture1, furniture2):
  x_min1, y_min1, x_max1, y_max1 = furniture1
  x_min2, y_min2, x_max2, y_max2 = furniture2
  # Calculate the x and y gaps between rectangles
  x_gap = segment_distance(x_min1, x_max1, x_min2, x_max2)
  y_gap = segment_distance(y_min1, y_max1, y_min2, y_max2)
  if x_gap == 0:
    return y_gap
  elif y_gap == 0:
    return x_gap
  else:
    return math.sqrt(x_gap**2 + y_gap**2)

def can_reach(furniture, d):
  V = len(furniture)
  graph = [[] for _ in range(V)]
  for i in range(V):
    for j in range(i + 1, V):
      if distance(furniture[i], furniture[j]) <= d:
        graph[i].append(j)
        graph[j].append(i)

  visited = {0}

  def visit(node):
    for nbr in graph[node]:
      if not nbr in visited:
        visited.add(nbr)
        visit(nbr)

  visit(0)
  return V - 1 in visited


def run_tests():
  tests = [
      # Example 1 from the book:
      [[[1, 1, 9, 5],
        [12, 9, 20, 13],
        [16, 2, 22, 7],
        [24, 9, 26, 11],
        [29, 1, 31, 5]], 5, True],
      # Example 2 from the book:
      [[[1, 1, 9, 5],
        [12, 9, 20, 13],
        [16, 2, 22, 7],
        [24, 9, 26, 11],
        [29, 1, 31, 5]], 4, False],

      [[[0, 0, 1, 1], [1, 1, 2, 2], [2, 2, 3, 3], [3, 3, 4, 4], [4, 4, 5, 5]], 0, True],
      [[[0, 0, 1, 1], [1, 1, 2, 2], [2, 2, 3, 3], [3, 3, 4, 4], [4, 4, 5, 5]], 1, True],
      [[[0, 0, 1, 1], [1, 1, 2, 2], [3, 3, 4, 4], [4, 4, 5, 5]], 1, False],
      [[[0, 0, 1, 1], [1, 1, 2, 2], [3, 3, 4, 4], [4, 4, 5, 5]], 2, True],
      # Single piece of furniture
      [[[0, 0, 1, 1]], 5, True],
      # Two pieces far apart
      [[[0, 0, 1, 1], [10, 10, 11, 11]], 5, False],
      # Two pieces just within reach
      [[[0, 0, 1, 1], [5, 5, 6, 6]], 5.7, True],
      # Two pieces just out of reach
      [[[0, 0, 1, 1], [5, 5, 6, 6]], 5.6, False],
      # Pieces in a line
      [[[0, 0, 1, 1], [1, 0, 2, 1], [2, 0, 3, 1]], 1.5, True],
  ]
  for furniture, d, want in tests:
    got = can_reach(furniture, d)
    assert got == want, f"\ncan_reach({furniture}, {d}): got: {        got}, want: {want}\n"

run_tests()
