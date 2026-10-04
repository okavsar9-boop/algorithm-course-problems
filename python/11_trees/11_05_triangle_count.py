# 11.5 - Triangle Count
# Run: python3 11_05_triangle_count.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def triangle_count(root):
  res = 0

  def visit(node):
    nonlocal res
    if not node:
      return 0, 0  # left_side, right_side counts

    left_side, _ = visit(node.left)  # Only care about left descendants
    _, right_side = visit(node.right)  # Only care about right descendants

    # Number of triangles with this node at the top is min of left and right sides
    res += min(left_side, right_side)

    # Return counts of consecutive left/right descendants
    return (left_side + 1, right_side + 1)

  visit(root)
  return res


def run_tests():
  tests = [
      # Example
      (Node(1,
            Node(2,
                 Node(4),
                 Node(5)),
            Node(3,
                 Node(6),
                 Node(7))), 4),
      (None, 0),  # Empty tree
      (Node(1), 0),  # Single node
      # No triangles - only left children
      (Node(1,
            Node(2,
                     Node(3),
                     None),
            None), 0),
      # No triangles - only right children
      (Node(1,
            None,
            Node(2,
                 None,
                 Node(3))), 0),
      (Node(1,
            Node(2),
            Node(3)), 1),
  ]

  for _, (root, want) in enumerate(tests):
    got = triangle_count(root)
    assert got == want, f"\ntriangle_count(): got: {got}, want: {want}\n"

run_tests()
