# 18.6 - Longest Path of Increasing Degrees
# Run: python3 18_06_longest_path_of_increasing_degrees.py

def dag_neighbors(graph, node):
  return [nbr for nbr in graph[node] if len(graph[nbr]) > len(graph[node])]

def topological_sort(graph):
  # Initialization
  V = len(graph)
  in_degrees = [0 for _ in range(V)]
  for node in range(V):
    for nbr in dag_neighbors(graph, node):
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
    for nbr in dag_neighbors(graph, node):
      in_degrees[nbr] -= 1
      if in_degrees[nbr] == 0:
        degree_zero.append(nbr)

  return topo_order

def longest_path_of_increasing_degrees(V, edges):
  graph = [[] for _ in range(V)]
  for u, v in edges:
    graph[u].append(v)
    graph[v].append(u)

  topo_order = topological_sort(graph)  # Recipe 1 (modified).

  lengths = {i: 0 for i in range(V)}
  for node in topo_order:
    for nbr in dag_neighbors(graph, node):
      if lengths[node] + 1 > lengths[nbr]:
        lengths[nbr] = lengths[node] + 1
  return max(lengths.values())


def run_tests():
  tests = [
      # Example from the book.
      (8, [[0, 1], [1, 2], [2, 3], [0, 2], [0, 4], [
          2, 6], [3, 7], [2, 7], [4, 5], [5, 6], [6, 7]], 2),
      # Edge case: Single node.
      (1, [], 0),
      # Can do 0 -> 1 or 2 -> 1.
      (3, [[0, 1], [1, 2]], 1),
      # Cycle graph.
      (4, [[0, 1], [1, 2], [2, 3], [3, 0]], 0),
      # Star graph.
      (4, [[0, 1], [0, 2], [0, 3]], 1),
      # Can do 3 -> 2 -> 1 -> 0.
      (10, [[0, 1], [1, 2], [2, 3], [2, 4], [3, 5], [3, 6], [3, 7]], 3),
  ]

  for V, edges, want in tests:
    got = longest_path_of_increasing_degrees(V, edges)
    assert got == want, f"\nlongest_path_of_increasing_degrees({V}, {edges}): got: {        got}, want: {want}\n"

run_tests()
