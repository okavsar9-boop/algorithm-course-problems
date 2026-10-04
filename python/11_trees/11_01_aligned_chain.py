# 11.1 - Aligned Chain
# Run: python3 11_01_aligned_chain.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def longest_aligned_chain(root):
  res = 0

  def visit(node, depth):  # Inner recursive function.
    nonlocal res  # To make res visible inside visit().
    if not node:
      return 0
    left_chain = visit(node.left, depth + 1)
    right_chain = visit(node.right, depth + 1)
    current_chain = 0
    if node.val == depth:
      current_chain = 1 + max(left_chain, right_chain)
      res = max(res, current_chain)
    return current_chain

  visit(root, 0)  # Trigger DFS, which updates the 'global' res.
  return res


def run_tests():
  tests = [
      # Test 1: from the book
      (Node(7, Node(1, Node(2, Node(4), Node(3)),
                    Node(8)), Node(3, Node(2, Node(3)))), 3),
      # Test 2
      (Node(0,
            Node(1,
                 Node(2,
                      Node(3),
                      None),
                 Node(4)),
            Node(5)), 4),

      # Test 3: Empty tree
      (None, 0),

      # Test 4: Single node aligned at root
      (Node(0), 1),

      # Test 5: Single node not aligned
      (Node(1), 0),

      # Test 6: Multiple valid chains, should return longest
      (Node(0,
            Node(1,
                 Node(2,
                      Node(4),
                      None),
                 Node(2,
                      Node(3),
                      None))), 4),

      # Test 7: No aligned nodes
      (Node(5,
            Node(4,
                 Node(3),
                 Node(3)),
            Node(2)), 0),

      # Test 8
      (Node(0,
            Node(1),
            Node(1)), 2),
  ]

  for i, (root, want) in enumerate(tests, 1):
    got = longest_aligned_chain(root)
    assert got == want, f"\nTest {i} failed! Got: {got}, Want: {want}"

run_tests()
