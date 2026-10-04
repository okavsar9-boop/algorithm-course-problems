# 18.4 - Counting Paths
# Run: python3 18_04_counting_paths.py

def topological_sort(graph):
  # Initialization
  V = len(graph)
  in_degrees = [0 for _ in range(V)]
  for node in range(V):
    for nbr in graph[node]:
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
    for nbr in graph[node]:
      in_degrees[nbr] -= 1
      if in_degrees[nbr] == 0:
        degree_zero.append(nbr)
  return topo_order

def path_count(graph, start):
  topo_order = topological_sort(graph)  # Recipe 1.

  counts = [0] * len(graph)
  counts[start] = 1
  for node in topo_order:
    for nbr in graph[node]:
      counts[nbr] += counts[node]
  return counts


def run_tests():
  tests = [
      # Example from the book
      ([[1], [], [1], [4], [1, 2, 5], [2]], 4, [0, 3, 2, 0, 1, 1]),
      # Edge case: Single node graph
      ([[]], 0, [1]),
      ([[1], []], 0, [1, 1]),
      ([[1], [2], []], 1, [0, 1, 1]),
      ([[1, 2], [3], [3], []], 0, [1, 1, 1, 2]),
  ]

  for graph, start, want in tests:
    got = path_count(graph, start)
    assert got == want, f"\npath_count({graph}, {start}): got: {        got}, want: {want}\n"

run_tests()
