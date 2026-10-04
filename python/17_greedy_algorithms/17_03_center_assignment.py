# 17.3 - Center Assignment
# Run: python3 17_03_center_assignment.py

import math

def minimize_distance(points, center1, center2):
  # Function to calculate Euclidean distance between two points
  def dist(point1, point2):
    return math.sqrt((point1[0] - point2[0]) ** 2 + (point1[1] - point2[1]) ** 2)

  n = len(points)
  assignment = [0] * n
  baseline = 0
  for i, p in enumerate(points):
    if dist(p, center1) <= dist(p, center2):
      assignment[i] = 1
      baseline += dist(p, center1)
    else:
      assignment[i] = 2
      baseline += dist(p, center2)

  c1_count = assignment.count(1)
  if c1_count == n // 2:
    return baseline

  switch_costs = []
  for i, p in enumerate(points):
    if assignment[i] == 1 and c1_count > n // 2:
      switch_costs.append(dist(p, center2) - dist(p, center1))
    if assignment[i] == 2 and c1_count < n // 2:
      switch_costs.append(dist(p, center1) - dist(p, center2))

  res = baseline
  switch_costs.sort()
  for cost in switch_costs[:abs(c1_count - n // 2)]:
    res += cost
  return res


def run_tests():
  # Example test cases
  tests = [
      # Example 1
      ([[0, 1], [1, 0], [-1, 0], [0, -1]], [0, 0], [1, 1], 4),
      # Example 2
      ([[0, 0], [0, 0]], [0, 0], [1, 1], 1.414),
      # Example 3
      ([[0, 0.5], [1, 0.5]], [0, 0], [1, 1], 1),
      # Example 4
      ([], [0.3, -3.3], [-1.6, 4.6], 0),
      # Example 5
      ([[0, 0], [3, 0]], [2, 0], [5, 0], 4),

      # Additional test cases
      # Edge case: All points are the same
      ([[0, 0], [0, 0], [0, 0], [0, 0]], [0, 0], [0, 0], 0),
  ]

  for points, center1, center2, want in tests:
    got = minimize_distance(points, center1, center2)
    assert abs(
        got - want) < 1e-3, f"\nminimize_distance({points}, {center1}, {center2}): got: {got}, want: {want}\n"

run_tests()
