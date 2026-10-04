# 11.16 - BST Kth Element
# Run: python3 11_16_bst_kth_element.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def kth_element(root, k):
  steps = 0
  res = None

  def visit(node):
    nonlocal steps, res
    if not node or res is not None:
      return
    visit(node.left)
    if steps == k:
      res = node.val
    steps += 1
    visit(node.right)

  visit(root)
  return res


def run_tests():

  root = Node(5,
              Node(2,
                   Node(1),
                   Node(4)),
              Node(8,
                   Node(6),
                   Node(9)))

  tests = [
      (Node(5,
            Node(2,
                 None,
                 Node(4)),
            Node(9,
                 Node(9),
                 Node(11))), 4, 9),
      (Node(1), 0, 1),  # Single node
      (root, 0, 1),
      (root, 1, 2),
      (root, 2, 4),
      (root, 3, 5),
      (root, 4, 6),
      (root, 5, 8),
      (root, 6, 9),
  ]

  for root, k, want in tests:
    got = kth_element(root, k)
    assert got == want, f"\nkth_element(root, {k}): got: {got}, want: {want}\n"

run_tests()
