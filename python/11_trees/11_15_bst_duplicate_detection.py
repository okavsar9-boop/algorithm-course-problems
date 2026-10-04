# 11.15 - BST Duplicate Detection
# Run: python3 11_15_bst_duplicate_detection.py

import math

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def has_duplicate(root):
  prev_value = -math.inf
  res = False

  def visit(node):
    nonlocal prev_value, res
    if not node or res:
      return
    visit(node.left)
    if node.val == prev_value:
      res = True
    prev_value = node.val
    visit(node.right)

  visit(root)
  return res


def run_tests():
  # Example 1 - BST with duplicates
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

  # Example 4 - BST without duplicates
  root4 = Node(5,
               Node(2,
                    Node(1),
                    Node(4)),
               Node(8,
                    Node(6),
                    Node(9)))

  tests = [
      (root1, True),  # Has duplicates (9s)
      (root4, False),  # No duplicates
  ]

  for i, (root, want) in enumerate(tests):
    got = has_duplicate(root)
    assert got == want, f"\nhas_duplicate(root{        i + 1}): got: {got}, want: {want}\n"

run_tests()
