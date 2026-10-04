# 12.7 - Hilliest Connected Component
# Run: python3 12_07_hilliest_connected_component.py

def label_nodes_with_cc_ids(graph):
  node_to_cc = {}

  def visit(node, cc_id):
    if node in node_to_cc:
      return
    node_to_cc[node] = cc_id
    for nbr in graph[node]:
      visit(nbr, cc_id)

  cc_id = 0
  for node in range(len(graph)):
    if node not in node_to_cc:
      visit(node, cc_id)
      cc_id += 1

  return node_to_cc

def max_hilliness(graph, heights):
  node_to_cc = label_nodes_with_cc_ids(graph)
  V = len(graph)
  cc_to_elevation_gain_sum = {}
  cc_to_num_edges = {}
  for node in range(V):
    cc = node_to_cc[node]
    if cc not in cc_to_num_edges:
      cc_to_elevation_gain_sum[cc] = 0
      cc_to_num_edges[cc] = 0
    for nbr in graph[node]:
      if nbr > node:  # Only count each edge once.
        cc_to_num_edges[cc] += 1
        cc_to_elevation_gain_sum[cc] += abs(heights[node] - heights[nbr])
  res = 0
  for cc in cc_to_num_edges:
    if cc_to_num_edges[cc] > 0:
      res = max(res, cc_to_elevation_gain_sum[cc] / cc_to_num_edges[cc])
  return res


def run_tests():
  tests = [
      # Example
      [[[1, 3], [0, 2], [1, 3], [0, 2]], [4, 1, 3, 2], 2],
      # Single node component
      [[[]], [5], 0],
      # Two disconnected components
      [[[1], [0], [3], [2]], [1.5, 5.5, 0.0, 5.0], 5],
      # All nodes same height
      [[[1, 2], [0, 2], [0, 1]], [3, 3, 3], 0],
      # Line graph
      [[[1], [0, 2], [1]], [1, 5, 2], 3.5],
      # Complete graph
      [[[1, 2, 3], [0, 2, 3], [0, 1, 3], [0, 1, 2]],
       [1, 4, 7, 10], (3 + 6 + 9 + 3 + 6 + 3) / 6]
  ]
  for graph, heights, want in tests:
    got = max_hilliness(graph, heights)
    assert abs(
        got - want) < 0.0001, f"\nmax_hilliness({graph}, {heights}): got: {got}, want: {want}\n"

run_tests()
