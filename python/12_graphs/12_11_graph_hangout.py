# 12.11 - Graph Hangout
# Run: python3 12_11_graph_hangout.py

from collections import deque
import math

def bfs(graph, start):
  Q = deque()
  Q.append(start)
  distances = {start: 0}
  while Q:
    node = Q.popleft()
    for nbr in graph[node]:
      if nbr not in distances:
        distances[nbr] = distances[node] + 1
        Q.append(nbr)
  return distances

def walking_distance_to_coffee(graph, node1, node2, node3):
  distances1 = bfs(graph, node1)
  distances2 = bfs(graph, node2)
  distances3 = bfs(graph, node3)
  res = math.inf
  for i in range(len(graph)):
    res = min(res, distances1[i] + distances2[i] + distances3[i])
  return res


def run_tests():
  tests = [
    # Example from the book
      ([
      [1, 14],        # 0: Outer ring connections
      [0, 2],         # 1
      [1, 3],         # 2
      [2, 4],         # 3
      [3, 5, 19],     # 4: Connector from outer to inner ring
      [4, 6],         # 5
      [5, 7],         # 6
      [6, 8],         # 7
      [7, 9, 21],     # 8: Connector from outer to inner ring
      [8, 10],        # 9
      [9, 11],        # 10
      [10, 12],       # 11
      [11, 13],       # 12
      [12, 14],       # 13
      [0, 13, 15],    # 14: Connector from outer to inner ring
      [14, 16],       # 15
      [15, 17],       # 16
      [16, 18, 20],   # 17: Center node connections
      [17, 19],       # 18
      [18, 4],        # 19
      [17, 21],       # 20
      [8, 20]         # 21
      ], 14, 4, 8, 9),
      # Cycle with 5 nodes
      ([[1, 4], [0, 2], [1, 3], [2, 4], [0, 3]], 0, 2, 4, 3),
      # Simple line graph
      ([[1], [0, 2], [1]], 0, 1, 2, 2),
      # Star graph - optimal meeting point is center
      ([[1], [0, 2, 3, 4], [1], [1], [1]], 0, 2, 3, 3),
      # Complete graph - can meet at any node
      ([[1, 2, 3], [0, 2, 3], [0, 1, 3], [0, 1, 2]], 0, 1, 2, 2),
      # Edge case - all start at same node
      ([[1], [0]], 0, 0, 0, 0),
      # Edge case - two start at same node
      ([[1], [0, 2], [1]], 0, 0, 2, 2),
  ]
  for graph, node1, node2, node3, want in tests:
    got = walking_distance_to_coffee(graph, node1, node2, node3)
    assert got == want, f"\nwalking_distance_to_coffee({graph}, {node1}, {node2}, {        node3}): got: {got}, want: {want}\n"

run_tests()
