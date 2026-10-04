# 11.8 - Left View
# Run: python3 11_08_left_view.py

from collections import deque

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def left_view(root):
  if not root:
    return []
  Q = deque()
  Q.append((root, 0))
  res = [root.val]
  current_depth = 0
  while Q:
    node, depth = Q.popleft()
    if not node:
      continue
    if depth == current_depth + 1:
      res.append(node.val)
      current_depth += 1
    Q.append((node.left, depth + 1))
    Q.append((node.right, depth + 1))
  return res


def run_tests():

  # Test 1
  root1 = Node(1,
               Node(2,
                    Node(4),
                    Node(5)),
               Node(3,
                    None,
                    Node(6)))

  # Test 2: Empty tree
  root2 = None

  # Test 3: Single node
  root3 = Node(1)

  # Test 4: Only right children
  root4 = Node(1,
               None,
               Node(2,
                    None,
                    Node(3)))

  # Test 5: Only left children
  root5 = Node(1,
               Node(2,
                    Node(3),
                    None),
               None)

  # Test 6: Example from the book
  root6 = Node(5,
               Node(2,
                    None,
                    Node(6)),
               Node(9,
                    Node(9,
                         None,
                         Node(1)),
                    Node(8)))

  tests = [
      (root1, [1, 2, 4]),  # Example
      (root2, []),  # Empty tree
      (root3, [1]),  # Single node
      (root4, [1, 2, 3]),  # Only right children
      (root5, [1, 2, 3]),  # Only left children
      (root6, [5, 2, 6, 1])  # Example from the book
  ]

  for i, (root, want) in enumerate(tests):
    got = left_view(root)
    assert got == want, f"\nleft_view(root{i + 1}): got: {got}, want: {want}\n"

run_tests()
