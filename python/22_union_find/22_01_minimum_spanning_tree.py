# 22.1 - Minimum Spanning Tree
# Run: python3 22_01_minimum_spanning_tree.py

import math
from heapq import heappush, heappop

def build_adjacency_list(edges, V):
  adj_list = [[] for _ in range(V)]
  for u, v, w in edges:
    adj_list[u].append((v, w))
    adj_list[v].append((u, w))
  return adj_list

def prim(V, edges):  # Assumes the graph is connected.
  adj_list = build_adjacency_list(edges, V)
  min_edge = [math.inf for _ in range(V)]
  min_edge[0] = 0
  vis = [False for _ in range(V)]
  PQ = [(0, 0)]
  mst_cost = 0
  while len(PQ) > 0:
    _, u = heappop(PQ)  # Only need the node, not the edge weight
    if vis[u]:
      continue  # Not first extraction -- obsolete copy
    vis[u] = True
    mst_cost += min_edge[u]
    for v, w in adj_list[u]:
      if not vis[v] and w < min_edge[v]:
        min_edge[v] = w
        heappush(PQ, (w, v))
  return mst_cost

def kruskal(V, edges):  # Assumes the graph is connected.
  uf = UnionFind()
  for u in range(V):
    uf.add(u)
  mst_cost = 0
  edges.sort(key=lambda edge: edge[2])
  for u, v, weight in edges:
    repr_u, repr_v = uf.find(u), uf.find(v)
    if repr_u != repr_v:
      uf.union(u, v)
      mst_cost += weight
  return mst_cost

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
      # Example from book
      (9, [[0, 1, 3], [1, 8, 9], [8, 7, 5], [7, 4, 13], [4, 3, 4], [3, 0, 5],
           [1, 5, 8], [5, 4, 2], [4, 2, 3], [2, 1, -1], [2, 5, 10], [5, 6, 11],
           [6, 8, 0], [6, 7, -2]], 18),
      # Single edge
      (2, [[0, 1, 5]], 5),
      # Triangle graph
      (3, [[0, 1, 1], [1, 2, 2], [0, 2, 3]], 3),
      # Square graph
      (4, [[0, 1, 1], [1, 2, 2], [2, 3, 3], [3, 0, 4]], 6),
      # Negative weights
      (3, [[0, 1, -2], [1, 2, -3], [0, 2, 1]], -5),
  ]

  for n, edges, want in tests:
    got_prim = prim(n, edges)
    assert got_prim == want, f"\nprim({n}, {edges}): got: {got_prim}, want: {want}\n"
    got_kruskal = kruskal(n, edges)
    assert got_kruskal == want, f"\nkruskal({n}, {edges}): got: {got_kruskal}, want: {want}\n"

run_tests()
