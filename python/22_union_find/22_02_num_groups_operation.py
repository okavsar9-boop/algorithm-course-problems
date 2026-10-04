# 22.2 - Num Groups Operation
# Run: python3 22_02_num_groups_operation.py

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

class CustomUnionFind:
  def __init__(self):
    self.parent = {}
    self.size = {}

  def add(self, x):
    self.parent[x] = x
    self.size[x] = 1

  def find(self, x):
    root = self.parent[x]
    while self.parent[root] != root:
      root = self.parent[root]
    while x != root:
      self.parent[x], x = root, self.parent[x]
    return root

  def union(self, x, y):
    repr_x, repr_y = self.find(x), self.find(y)
    if repr_x == repr_y:
      return
    if self.size[repr_x] < self.size[repr_y]:
      self.size[repr_y] += self.size[repr_x]
      self.parent[repr_x] = repr_y
      del self.size[repr_x]
    else:
      self.size[repr_x] += self.size[repr_y]
      self.parent[repr_y] = repr_x
      del self.size[repr_y]

  def size(self):
    return len(self.parent)

  def num_groups(self):
    return len(self.size)


def run_tests():
  # Test basic operations
  uf = CustomUnionFind()
  uf.add(1)
  uf.add(2)
  uf.add(3)
  assert uf.num_groups() == 3, f"\ngot: {uf.num_groups()}, want: 3\n"
  uf.union(1, 2)
  assert uf.num_groups() == 2, f"\ngot: {uf.num_groups()}, want: 2\n"
  uf.union(2, 3)
  assert uf.num_groups() == 1, f"\ngot: {uf.num_groups()}, want: 1\n"
  uf.add(4)
  uf.add(5)
  assert uf.num_groups() == 3, f"\ngot: {uf.num_groups()}, want: 3\n"
  uf.union(4, 5)
  assert uf.num_groups() == 2, f"\ngot: {uf.num_groups()}, want: 2\n"

  # Test find after unions
  uf = CustomUnionFind()
  uf.add(1)
  uf.add(2)
  uf.add(3)
  uf.union(1, 2)
  uf.union(2, 3)
  assert uf.find(1) == uf.find(3), f"\nuf.find(1) != uf.find(3)\n"

  # Test multiple unions of same elements
  uf = CustomUnionFind()
  uf.add(1)
  uf.add(2)
  uf.union(1, 2)
  uf.union(1, 2)
  assert uf.num_groups() == 1, f"\ngot: {uf.num_groups()}, want: 1\n"

run_tests()
