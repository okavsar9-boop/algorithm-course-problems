# 12.8 - Highest Average Elevation Gain
# Run: python3 12_08_highest_average_elevation_gain.py

def build_adj_list(V, edges):
  graph = [[] for _ in range(V)]
  for node1, node2, gain in edges:
    graph[node1].append((node2, gain))
    graph[node2].append((node1, gain))
  return graph

def label_components(graph):
  
  def dfs(node, cc_id):
    cc_ids[node] = cc_id
    for nbr, _ in graph[node]:
      if nbr not in cc_ids:
        dfs(nbr, cc_id)

  cc_ids = {}
  cc_id = 0
  for node in range(len(graph)):
    if node not in cc_ids:
      dfs(node, cc_id)
      cc_id += 1
  return cc_ids, cc_id

def highest_average_elevation_gain(V, edges):
  if not edges:
    return 0

  # Build adjacency list with weights
  graph = build_adj_list(V, edges)

  # Label nodes with component IDs
  cc_ids, num_components = label_components(graph)

  # Calculate average gain for each component
  cc_gains = [0] * num_components
  cc_edges = [0] * num_components
  for node in range(V):
    for nbr, gain in graph[node]:
      if node < nbr:  # Only count each edge once.
        cc_id = cc_ids[node]
        cc_gains[cc_id] += gain
        cc_edges[cc_id] += 1

  # Find max average gain
  max_avg = 0
  for cc_id in range(num_components):
    if cc_edges[cc_id] > 0:
      avg = cc_gains[cc_id] / cc_edges[cc_id]
      max_avg = max(max_avg, avg)

  return max_avg


def run_tests():
  tests = [
      # Example from the book
      (
          4,  # V
          [[0, 1, 3], [1, 2, 2], [2, 3, 1], [3, 0, 2]],  # edges
          2  # want
      ),
      # Single edge
      (
          2,
          [[0, 1, 5]],
          5
      ),
      # No edges
      (
          3,
          [],
          0
      ),
      # Multiple components
      (
          6,
          [[0, 1, 1], [1, 2, 2],  # Component 1: avg 1.5
           [3, 4, 3], [4, 5, 5]],  # Component 2: avg 4.0
          4
      ),
      # Single node component
      (
          3,
          [[0, 1, 2]],  # Node 2 is isolated
          2
      )
  ]

  for V, edges, want in tests:
    got = highest_average_elevation_gain(V, edges)
    assert abs(got - want) < 1e-6, \
        f"\nhighest_average_elevation_gain({V}, {edges}): got: {got}, want: {want}\n"

run_tests()
