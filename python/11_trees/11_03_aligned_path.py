# 11.3 - Aligned Path
# Run: python3 11_03_aligned_path.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def aligned_path(root):
  res = 0

  def visit(node, depth):
    nonlocal res
    if not node:
      return 0
    left_chain = visit(node.left, depth + 1)
    right_chain = visit(node.right, depth + 1)
    current_chain = 0
    if node.val == depth:
      current_chain = 1 + max(left_chain, right_chain)
      # For each aligned node, try using it as the highest node in the path
      res = max(res, left_chain + right_chain + 1)
    return current_chain

  visit(root, 0)
  return res


def run_tests():
  tests = [
      # Test 1: Example from the book
      (Node(7, Node(1, Node(2, Node(4), Node(3)),
                    Node(8)), Node(3, Node(2, Node(3), Node(3)))), 3),
      # Variation 1
      (Node(7, Node(1, Node(20, Node(4), Node(3)),
                    Node(8)), Node(3, Node(2, Node(3), Node(3)))), 3),
      # Variation 2
      (Node(7, Node(1, Node(2, Node(4), Node(3)),
                    Node(8)), Node(3, Node(20, Node(3), Node(3)))), 3),
      # Variation 3
      (Node(7, Node(1, Node(20, Node(4), Node(3)),
                    Node(8)), Node(3, Node(20, Node(3), Node(3)))), 1),
      # Test 2: Empty tree
      (None, 0),
      # Test 3: Single aligned node
      (Node(0), 1),
      # Test 4: Single unaligned node
      (Node(1), 0),
      # Test 5: Path through root
      (Node(0, Node(1), Node(1)), 3),
      # Test 6: No aligned nodes
      (Node(5, Node(4), Node(2)), 0),
      # Test 7
      (Node(0, Node(1, Node(2), Node(2)), Node(1)), 4),
  ]

  for i, (root, want) in enumerate(tests, 1):
    got = aligned_path(root)
    assert got == want, f"\naligned_path(): got: {got}, want: {want}\n"

run_tests()
