# 18.3 - DAG Longest Path
# Run: python3 18_03_dag_longest_path.py

import math

def topological_sort(graph):
  # Initialization
  V = len(graph)
  in_degrees = [0 for _ in range(V)]
  for node in range(V):
    for nbr, _ in graph[node]:
      in_degrees[nbr] += 1
  degree_zero = []
  for node in range(V):
    if in_degrees[node] == 0:
      degree_zero.append(node)

  # Main 'peel-off' loop
  topo_order = []
  while degree_zero:
    node = degree_zero.pop()
    topo_order.append(node)
    for nbr, _ in graph[node]:
      in_degrees[nbr] -= 1
      if in_degrees[nbr] == 0:
        degree_zero.append(nbr)
  
  if len(topo_order) < V:
    return []  # There is a cycle; some nodes couldn't be peeled off
  return topo_order

def longest_path(graph, start):
  topo_order = topological_sort(graph)  # Recipe 1.

  lengths = {i: -math.inf for i in range(len(graph))}
  lengths[start] = 0
  for node in topo_order:
    if lengths[node] == -math.inf: continue
    for nbr, weight in graph[node]:
      if lengths[node] + weight > lengths[nbr]:
        lengths[nbr] = lengths[node] + weight

  return [lengths[i] for i in range(len(graph))]


def run_tests():
  tests = [
    # Example from the book
    ([[[1, 10]], [], [[1, 10]], [[4, 12]], [[1, 11], [2, 21], [5, 14]], [[2, -30]]], 4, [-math.inf, 31, 21, -math.inf, 0, 14]),
    # Edge case: Single node graph
    ([[]], 0, [0]),
    # Edge case: Disconnected graph
    ([[[1, 5]], [], [[3, 2]], []], 0, [0, 5, -math.inf, -math.inf]),
    # Edge case: Graph with negative weights
    ([[[1, -1]], [[2, -2]], []], 0, [0, -1, -3]),
    # Edge case: Start node with no outgoing edges
    ([[[1, 2]], [[2, 3]], []], 2, [-math.inf, -math.inf, 0]),
  ]

  for graph, start, want in tests:
    got = longest_path(graph, start)
    assert got == want, f"\nlongest_path({graph}, {start}): got: {got}, want: {want}\n"

run_tests()
