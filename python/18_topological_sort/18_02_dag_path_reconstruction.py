# 18.2 - DAG Path Reconstruction
# Run: python3 18_02_dag_path_reconstruction.py

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

def shortest_path(graph, start, goal):
  topo_order = topological_sort(graph)

  distances = {start: 0}
  predecessors = {}
  for node in topo_order:
    if node not in distances:
      continue
    for nbr, weight in graph[node]:
      if nbr not in distances or distances[node] + weight < distances[nbr]:
        distances[nbr] = distances[node] + weight
        predecessors[nbr] = node

  if goal not in distances:
    return []
  path = [goal]
  while path[-1] != start:
    path.append(predecessors[path[-1]])
  path.reverse()
  return path


def run_tests():
  tests = [
      # Example from the book
      ([[[1, 10]], [], [[1, 10]], [[4, 12]], [
       [1, 11], [2, 21], [5, 14]], [[2, -30]]], 4, 1, [4, 5, 2, 1]),
      # Edge case: Single node graph
      ([[]], 0, 0, [0]),
      # Edge case: Disconnected graph
      ([[[1, 5]], [], [[3, 2]], []], 0, 3, []),
      # Edge case: Graph with negative weights
      ([[[1, -1]], [[2, -2]], []], 0, 2, [0, 1, 2]),
      # Edge case: Start node with no outgoing edges
      ([[[1, 2]], [[2, 3]], []], 2, 0, []),
  ]

  for graph, start, goal, want in tests:
    got = shortest_path(graph, start, goal)
    assert got == want, f"\nshortest_path({graph}, {start}, {goal}): got: {got}, want: {want}\n"

run_tests()
