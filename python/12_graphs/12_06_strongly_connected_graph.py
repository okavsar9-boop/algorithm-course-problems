# 12.6 - Strongly Connected Graph
# Run: python3 12_06_strongly_connected_graph.py

def visit(graph, visited, node):
  if node in visited:
    return
  visited.add(node)
  for nbr in graph[node]:
    visit(graph, visited, nbr)

def strongly_connected(graph):
  V = len(graph)
  visited = set()
  visit(graph, visited, 0)
  if len(visited) < V:
    return False

  reverse_graph = [[] for _ in range(V)]
  for node in range(V):
    for nbr in graph[node]:
      reverse_graph[nbr].append(node)

  reverse_visited = set()
  visit(reverse_graph, reverse_visited, 0)
  return len(reverse_visited) == V


def run_tests():
  tests = [
      # Example strongly connected
      [[[1], [2], [0]], True],
      # Example not strongly connected
      [[[1], [2], []], False],
      # Single node
      [[[]], True],
      # Two nodes, strongly connected
      [[[1], [0]], True],
      # Two nodes, not strongly connected
      [[[1], []], False],
      # Cycle of 4 nodes
      [[[1], [2], [3], [0]], True],
      # Almost cycle of 4 nodes, missing one edge
      [[[1], [2], [3], []], False],
      # Complete graph
      [[[1, 2], [0, 2], [0, 1]], True],
  ]
  for graph, want in tests:
    got = strongly_connected(graph)
    assert got == want, f"\nstrongly_connected({graph}): got: {        got}, want: {want}\n"

run_tests()
