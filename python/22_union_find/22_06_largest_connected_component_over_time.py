# 22.6 - Largest Connected Component Over Time
# Run: python3 22_06_largest_connected_component_over_time.py

def max_cc_at_times(n, edges, times):
  edges.sort(key=lambda edge: edge[2])
  uf = UnionFind()
  for u in range(n):
    uf.add(u)
  max_cc = 1
  times_i = 0
  res = [0 for _ in range(len(times))]
  for u, v, time in edges:
    while times_i < len(times) and time > times[times_i]:
      res[times_i] = max_cc
      times_i += 1
    repr_u, repr_v = uf.find(u), uf.find(v)
    if repr_u == repr_v:
      continue
    uf.union(u, v)
    max_cc = max(max_cc, uf.size[uf.find(u)])
  while times_i < len(times):
    res[times_i] = max_cc
    times_i += 1
  return res

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
      # Example from the book
      (4, [[0, 1, 60], [0, 3, 180], [2, 3, 120]], [30, 120, 210], [1, 2, 4]),
      # Edge case - no edges
      (3, [], [10, 20], [1, 1]),
      # Edge case - single node
      (1, [], [5], [1]),
      # Multiple edges at same time
      (4, [[0, 1, 10], [2, 3, 10], [1, 2, 20]], [5, 15, 25], [1, 2, 4]),
      # All edges after last query time
      (3, [[0, 1, 100], [1, 2, 200]], [10, 20], [1, 1]),
  ]

  for V, edges, times, want in tests:
    got = max_cc_at_times(V, edges, times)
    assert got == want, f"\nmax_cc_at_times({V}, {edges}, {times}): got: {        got}, want: {want}\n"

run_tests()
