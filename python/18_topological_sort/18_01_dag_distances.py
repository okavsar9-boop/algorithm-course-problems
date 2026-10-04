# 18.1 - DAG Distances
# Run: python3 18_01_dag_distances.py

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

def distance(graph, start):
  topo_order = topological_sort(graph)  # Recipe 1.

  distances = {start: 0}
  for node in topo_order:
    if node not in distances: continue
    for nbr, weight in graph[node]:
      if nbr not in distances or distances[node] + weight < distances[nbr]:
        distances[nbr] = distances[node] + weight

  res = []
  for i in range(len(graph)):
    if i in distances:
      res.append(distances[i])
    else:
      res.append(math.inf)
  return res


def run_tests():
  tests = [
    # Example from the book
    ([[[1, 10]], 
      [], 
      [[1, 10]], 
      [[4, 12]], 
      [[1, 11], 
       [2, 21], 
       [5, 14]], 
       [[2, -30]]], 
       4, 
       [math.inf, -6, -16, math.inf, 0, 14]),
    # Edge case: Single node graph
    ([[]], 0, [0]),
    # Edge case: Disconnected graph
    ([[[1, 5]], [], [[3, 2]], []], 0, [0, 5, math.inf, math.inf]),
    # Edge case: Graph with negative weights
    ([[[1, -1]], [[2, -2]], []], 0, [0, -1, -3]),
    # Edge case: Start node with no outgoing edges
    ([[[1, 2]], [[2, 3]], []], 2, [math.inf, math.inf, 0]),
  ]

  for graph, start, want in tests:
    got = distance(graph, start)
    assert got == want, f"\ndistance({graph}, {start}): got: {got}, want: {want}\n"

run_tests()
