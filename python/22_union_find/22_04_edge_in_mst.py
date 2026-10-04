# 22.4 - Edge In MST
# Run: python3 22_04_edge_in_mst.py

import math

def edge_in_mst(V, edges, i):
  mst_cost = kruskal(V, edges)
  mst_cost_without_i = kruskal(V, edges[:i] + edges[i + 1:])
  return mst_cost != mst_cost_without_i

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

def kruskal(V, edges):
  uf = UnionFind()
  num_ccs = V
  for u in range(V):
    uf.add(u)
  mst_cost = 0
  edges.sort(key=lambda edge: edge[2])
  for u, v, weight in edges:
    repr_u, repr_v = uf.find(u), uf.find(v)
    if repr_u != repr_v:
      uf.union(u, v)
      mst_cost += weight
      num_ccs -= 1

  if num_ccs != 1:
    return math.inf
  return mst_cost


def run_tests():
  tests = [
      # Graph from the book
      (4, [[0, 1, 5], [1, 2, 5], [2, 3, 20], [3, 0, 20]], 0, True),
      (4, [[0, 1, 5], [1, 2, 5], [2, 3, 20], [3, 0, 20]], 1, True),
      (4, [[0, 1, 5], [1, 2, 5], [2, 3, 20], [3, 0, 20]], 2, False),
      (4, [[0, 1, 5], [1, 2, 5], [2, 3, 20], [3, 0, 20]], 3, False),
      # Edge case - single edge
      (2, [[0, 1, 5]], 0, True),
      # Triangle graph - all edges same weight
      (3, [[0, 1, 1], [1, 2, 1], [2, 0, 1]], 0, False),
      # Square graph - one edge much heavier
      (4, [[0, 1, 1], [1, 2, 1], [2, 3, 1], [3, 0, 10]], 3, False),
      # Negative weights
      (3, [[0, 1, -2], [1, 2, 1], [2, 0, 1]], 0, True),
  ]

  for V, edges, i, want in tests:
    got = edge_in_mst(V, edges, i)
    assert got == want, f"\nedge_in_mst({V}, {edges}, {i}): got: {        got}, want: {want}\n"

run_tests()
