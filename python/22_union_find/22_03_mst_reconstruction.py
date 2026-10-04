# 22.3 - MST Reconstruction
# Run: python3 22_03_mst_reconstruction.py

from collections import deque

def kruskal(V, edges):
  uf = UnionFind()
  for u in range(V):
    uf.add(u)
  mst_edges = []
  edges.sort(key=lambda edge: edge[2])
  for u, v, weight in edges:
    repr_u, repr_v = uf.find(u), uf.find(v)
    if repr_u != repr_v:
      uf.union(u, v)
      mst_edges.append([u, v, weight])

  if len(mst_edges) == V - 1:
    return mst_edges
  return []  # The graph was not connected.

class UnionFind:
  def __init__(self):
    self.parent = {}
    self.size = {}

  # Assumes x is not already in the UnionFind.
  def add(self, x):
    self.parent[x] = x
    self.size[x] = 1

  # Assumes x is already in the UnionFind.
  def find(self, x):
    root = self.parent[x]
    while self.parent[root] != root:
      root = self.parent[root]
    while x != root:
      self.parent[x], x = root, self.parent[x]
    return root

  # Assumes x and y are already in the UnionFind.
  def union(self, x, y):
    repr_x, repr_y = self.find(x), self.find(y)
    if repr_x == repr_y:
      return  # They are already in the same set.
    if self.size[repr_x] < self.size[repr_y]:
      self.size[repr_y] += self.size[repr_x]
      self.parent[repr_x] = repr_y
    else:
      self.size[repr_x] += self.size[repr_y]
      self.parent[repr_y] = repr_x


def run_tests():
  tests = [
      # Example 1 from book
      (9, [[0, 1, 3], [1, 8, 9], [8, 7, 5], [7, 4, 13], [4, 3, 4], [3, 0, 5],
           [1, 5, 8], [5, 4, 2], [4, 2, 3], [2, 1, -1], [2, 5, 10], [5, 6, 11],
           [6, 8, 0], [6, 7, -2]],
       [[0, 1, 3], [1, 8, 9], [4, 3, 4], [5, 4, 2], [4, 2, 3], [2, 1, -1],
          [6, 8, 0], [6, 7, -2]]),
      # Example 2 - not connected
      (3, [[0, 1, 1]], []),
      # Example 3 - not unique solution
      (3, [[0, 1, 1], [1, 2, 1], [2, 0, 1]], [[0, 1, 1], [1, 2, 1]]),
      # Single edge
      (2, [[0, 1, 5]], [[0, 1, 5]]),
      # Triangle graph
      (3, [[0, 1, 1], [1, 2, 2], [0, 2, 3]], [[0, 1, 1], [1, 2, 2]]),
      # Empty graph
      (0, [], []),
  ]

  for V, edges, want in tests:
    got = kruskal(V, edges)
    # We check that 'got' has (1) the right number of edges, (2) the right cost,
    # and (3) no cycles.
    assert len(got) == len(want), f"\nkruskal({V}, {edges}): got:\n{        got}, want: {len(want)} edges"
    assert sum([w for _, _, w in got]) == sum([w for _, _, w in want]), f"\nkruskal({V}, {edges}): got:\n{        got}, want cost {sum([w for _, _, w in want])}"

    if not got:
      continue

    # Check that 'got' has no cycles with BFS
    graph = [[] for _ in range(V)]
    for u, v, _ in got:
      graph[u].append(v)
      graph[v].append(u)

    visited = {0}
    parent = {0: -1}
    Q = deque([0])

    while Q:
      u = Q.popleft()
      for v in graph[u]:
        if v not in visited:
          visited.add(v)
          parent[v] = u
          Q.append(v)
        elif parent.get(u) != v:  # Found a cycle
          assert False, f"\nkruskal({V}, {edges}): got contains a cycle"

run_tests()
