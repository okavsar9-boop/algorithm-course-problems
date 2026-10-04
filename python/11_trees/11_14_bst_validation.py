# 11.14 - BST Validation
# Run: python3 11_14_bst_validation.py

import math

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def is_bst(root):
  prev_value = -math.inf
  res = True

  def visit(node):
    nonlocal prev_value, res
    if not node or not res:
      return
    visit(node.left)
    if node.val < prev_value:
      res = False
    else:
      prev_value = node.val
    visit(node.right)

  visit(root)
  return res


def run_tests():
  # Example 1 - valid BST
  root1 = Node(5,
               Node(2,
                    None,
                    Node(4)),
               Node(9,
                    Node(9,
                         None,
                         Node(9)),
                    Node(11)))

  # Example 2 - empty tree
  root2 = None

  # Example 3 - single node
  root3 = Node(1)

  # Example 4 - invalid BST (right child smaller than parent)
  root4 = Node(5,
               Node(2),
               Node(4))

  # Example 5 - invalid BST (left child larger than parent)
  root5 = Node(5,
               Node(6),
               Node(7))

  tests = [
      (root1, True),  # Valid BST
      (root2, True),  # Empty tree is valid
      (root3, True),  # Single node is valid
  ]

  for i, (root, want) in enumerate(tests):
    got = is_bst(root)
    assert got == want, f"\nis_bst(root{i + 1}): got: {got}, want: {want}\n"

run_tests()
