# 11.4 - Tree Layout
# Run: python3 11_04_tree_layout.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def most_stacked(root):
  pos_to_count = dict()

  def visit(node, r, c):
    if not node:
      return
    if (r, c) not in pos_to_count:
      pos_to_count[(r, c)] = 0
    pos_to_count[(r, c)] += 1
    visit(node.left, r + 1, c)
    visit(node.right, r, c + 1)

  visit(root, 0, 0)
  return max(pos_to_count.values())


def run_tests():
  # Test 1: Example from the book - two nodes stacked
  root1 = Node(1)
  root1.left = Node(2)
  root1.right = Node(3)
  root1.left.left = Node(4)
  root1.left.right = Node(5)
  root1.left.left.right = Node(7)
  root1.right.left = Node(6)
  root1.right.left.left = Node(8)
  root1.right.left.right = Node(9)

  root2 = Node(1)

  root3 = Node(1,
               Node(2),
               Node(3))

  # Test 4: Perfect binary tree of depth 4
  root4 = Node(1,
               Node(2,
                    Node(4,
                         Node(8),
                         Node(9, None, Node(16))),
                    Node(5,
                         Node(10, None, Node(17)),
                         Node(11, Node(18), None))),
               Node(3,
                    Node(6,
                         Node(12),
                         Node(13)),
                    Node(7,
                         Node(14, Node(19), None),
                         Node(15, Node(20), None))))

  tests = [
      (root1, 2),  # Example from book
      (root2, 1),  # Single node
      (root3, 1),
      (root4, 4),
  ]

  for i, (root, want) in enumerate(tests, 1):
    got = most_stacked(root)
    assert got == want, f"\nmost_stacked(): got: {got}, want: {want}\n"

run_tests()
