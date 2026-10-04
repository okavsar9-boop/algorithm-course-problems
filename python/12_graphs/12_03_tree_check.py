# 12.3 - Tree Check
# Run: python3 12_03_tree_check.py

def is_tree(graph):
  # Start from node 0 (the starting node doesn't matter).
  predecessors = {0: None}
  found_cycle = False

  def visit(node):
    nonlocal found_cycle
    if found_cycle:
      return
    for nbr in graph[node]:
      if nbr not in predecessors:
        predecessors[nbr] = node
        visit(nbr)
      elif nbr != predecessors[node]:
        found_cycle = True

  visit(0)
  connected = len(predecessors) == len(graph)
  return not found_cycle and connected


def run_tests():
  tests = [
    # Example 1 from the book
    ([[2], [2, 5], [0, 1, 3, 4], [2], [2], [1]], True),
    # Example 2 from the book
    ([[2], [5], [0, 3], [2], [], [1]], False),
    # Example 3 from the book
    ([[1], [0, 2, 5], [1, 3, 4], [2], [2, 5], [1, 4]], False),
    # Single node
    ([[]], True),
    # Two nodes connected
    ([[1], [0]], True),
    # Two nodes disconnected
    ([[], []], False),
    # Line graph (valid tree)
    ([[1], [0, 2], [1, 3], [2]], True),
    # Cycle
    ([[1, 3], [2, 0], [3, 1], [0, 2]], False),
    # Complete graph K4 (not a tree)
    ([[1, 2, 3], [0, 2, 3], [0, 1, 3], [0, 1, 2]], False),
    # Star graph
    ([[1, 2, 3, 4], [0], [0], [0], [0]], True),
  ]
  for graph, want in tests:
    got = is_tree(graph)
    assert got == want, f"\nis_tree({graph}): got: {got}, want: {want}\n"

run_tests()
