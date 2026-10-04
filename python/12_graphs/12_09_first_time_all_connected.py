# 12.9 - First Time All Connected
# Run: python3 12_09_first_time_all_connected.py

def first_time_all_connected(V, cables):
  
  def visit(graph, visited, node):
    for nbr in graph[node]:
      if nbr not in visited:
        visited.add(nbr)
        visit(graph, visited, nbr)

  def is_before(cable_index):
    graph = [[] for _ in range(V)]
    for i in range(cable_index + 1):
      node1, node2 = cables[i]
      graph[node1].append(node2)
      graph[node2].append(node1)
    visited = {0}
    visit(graph, visited, 0)
    return len(visited) < V

  l, r = 0, len(cables) - 1
  if is_before(r):
    return -1
  while r - l > 1:
    mid = l + (r - l) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid
  return r

def first_time_all_connected_union_find(V, cables):
  uf = UnionFind()
  for x in range(V):
    uf.add(x)
  groups = V
  for i in range(len(cables)):
    x, y = cables[i]

    # If x and y are not in the same group yet, union their groups.
    if uf.find(x) != uf.find(y):
      uf.union(x, y)
      groups -= 1
      if groups == 1:
        return i
  return -1
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
      # Case from picture - becomes connected after cables[2].
      (4, [(0, 2), (1, 3), (0, 1), (1, 2)], 2),
      # Edge case - never gets fully connected
      (3, [(0, 1)], -1),
      # Edge case - gets connected with final cable
      (3, [(0, 1), (1, 2)], 1),
      # Larger test case
      (5, [(0, 1), (2, 3), (1, 2), (3, 4), (0, 4)], 3),
      # Edge case - redundant cables don't affect result
      (4, [(0, 1), (1, 2), (2, 0), (2, 3), (3, 0)], 3),
      # No edges added.
      (4, [], -1),
      # One edge added.
      (4, [(0, 1)], -1),
  ]
  for V, cables, want in tests:
    got = first_time_all_connected(V, cables)
    assert (
        got == want
    ), f"first_time_all_connected({V}, {cables}): got: {got}, want {want}\n"
    got = first_time_all_connected_union_find(V, cables)
    assert (
        got == want
    ), f"first_time_all_connected_union_find({V}, {cables}): got: {got}, want {want}\n"

run_tests()
