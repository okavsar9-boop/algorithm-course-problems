# 12.1 - Adjacency List Validation
# Run: python3 12_01_adjacency_list_validation.py

def validate(graph):
  V = len(graph)
  for node in range(V):
    seen = set()
    for nbr in graph[node]:
      if nbr < 0 or nbr >= V:
        return False  # Invalid node index.
      if nbr == node:
        return False  # Self-loop.
      if nbr in seen:
        return False  # Parallel edge.
      seen.add(nbr)

  edges = set()
  for node1 in range(V):
    for node2 in graph[node1]:
      edge = (min(node1, node2), max(node1, node2))
      if edge in edges:
        edges.remove(edge)
      else:
        edges.add(edge)
  return len(edges) == 0


def run_tests():
  tests = [
      # Valid cases
      [[[1], [0]], True],  # Simple valid graph
      [[[1, 2], [0, 2], [0, 1]], True],  # Triangle graph
      [[], True],  # Empty graph
      [[[]], True],  # Single isolated node

      # Invalid node index cases
      [[[2], [0]], False],  # Node index too large
      [[[-1], []], False],  # Negative node index

      # Self-loop cases
      [[[0], []], False],  # Self loop
      [[[1], [1]], False],  # Self loop in second node

      # Parallel edge cases
      [[[1, 1], [0, 0]], False],  # Same edge twice from first node
      [[[1], [0, 2, 0], [1]], False],  # Same edge twice from second node

      # Unmatched edge cases
      [[[1], []], False],  # Edge only in one direction
      [[[1, 2], [0], []], False],  # Some edges missing their pairs
      [[[1], [2], [0]], False],  # Cycle with unmatched edges
  ]
  for graph, want in tests:
    got = validate(graph)
    assert got == want, f"\nvalidate({graph}): got: {got}, want: {want}\n"

run_tests()
