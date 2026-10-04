# 11.10 - Zig-Zag Order
# Run: python3 11_10_zig_zag_order.py

from collections import deque

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def zig_zag_order(root):
  res = []
  Q = deque()
  Q.append((root, 0))
  cur_level = []
  cur_depth = 0
  while Q:
    node, depth = Q.popleft()
    if not node:
      continue
    if depth > cur_depth:
      if cur_depth % 2 == 0:
        res += cur_level
      else:
        res += cur_level[::-1]  # Reverse order.
      cur_level = []
      cur_depth = depth
    cur_level.append(node)
    Q.append((node.left, depth + 1))
    Q.append((node.right, depth + 1))
  if cur_depth % 2 == 0:   # Add the last level.
    res += cur_level
  else:
    res += cur_level[::-1]
  return res


def run_tests():
  # Example 1 from the book
  root1 = Node(1)
  root1.left = Node(2)
  root1.right = Node(3)
  root1.left.left = Node(4)
  root1.left.right = Node(5)
  root1.right.left = Node(6)
  root1.right.right = Node(7)

  # Example 2 - empty tree
  root2 = None

  # Example 3 - single node
  root3 = Node(1)

  # Example 4 - unbalanced tree
  root4 = Node(1)
  root4.left = Node(2)
  root4.left.left = Node(3)
  root4.left.left.left = Node(4)

  # Example 5 - complete binary tree
  root5 = Node(1)
  root5.left = Node(2)
  root5.right = Node(3)
  root5.left.left = Node(4)
  root5.left.right = Node(5)
  root5.right.left = Node(6)
  root5.right.right = Node(7)
  root5.left.left.left = Node(8)
  root5.left.left.right = Node(9)
  root5.left.right.left = Node(10)
  root5.left.right.right = Node(11)
  root5.right.left.left = Node(12)
  root5.right.right.left = Node(14)
  root5.right.right.right = Node(15)

  tests = [
      # Example 1 - basic tree
      (root1, [1, 3, 2, 4, 5, 6, 7]),
      # Example 2 - empty tree
      (root2, []),
      # Example 3 - single node
      (root3, [1]),
      # Example 4 - unbalanced tree
      (root4, [1, 2, 3, 4]),
      # Example 5 - complete binary tree
      (root5, [1, 3, 2, 4, 5, 6, 7, 15, 14, 12, 11, 10, 9, 8])
  ]

  for i, (root, want) in enumerate(tests):
    got = [node.val for node in zig_zag_order(root)]
    assert got == want, f"\nExample {        i + 1}: zig_zag_order({root}): got: {got}, want: {want}\n"

run_tests()
