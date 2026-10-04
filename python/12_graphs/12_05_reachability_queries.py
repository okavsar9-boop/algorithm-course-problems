# 12.5 - Reachability Queries
# Run: python3 12_05_reachability_queries.py

def connected_component_queries(graph, queries):
  node_to_cc = {}

  def visit(node, cc_id):
    if node in node_to_cc:
      return
    node_to_cc[node] = cc_id
    for nbr in graph[node]:
      visit(nbr, cc_id)

  cc_id = 0
  for node in range(len(graph)):
    if not node in node_to_cc:
      visit(node, cc_id)
      cc_id += 1

  res = []
  for node1, node2 in queries:
    res.append(node_to_cc[node1] == node_to_cc[node2])
  return res


def run_tests():
  tests = [
      # Cycle graph with 6 nodes
      [[[1, 5], [0, 2, 4], [1, 3, 5], [2, 4], [1, 3, 5], [0, 2, 4]],
       [(0, 4), (0, 3)],
          [True, True]],
      # Example
      [[[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]],
       [(0, 4), (0, 3)],
          [True, False]],
      # Simple line graph
      [[[1], [0, 2], [1]],
          [(0, 2), (0, 1)],
          [True, True]],
      # Disconnected components
      [[[1], [0], [3], [2]],
          [(0, 1), (0, 2), (2, 3)],
          [True, False, True]],
      # Complete graph
      [[[1, 2], [0, 2], [0, 1]],
          [(0, 1), (1, 2), (0, 2)],
          [True, True, True]],
      # Single node
      [[[]],
          [(0, 0)],
          [True]],
      # Empty queries
      [[[1], [0]],
          [],
          []]
  ]
  for graph, queries, want in tests:
    got = connected_component_queries(graph, queries)
    assert got == want, f"\nconnected_component_queries({graph}, {queries}): got: {        got}, want: {want}\n"

run_tests()
