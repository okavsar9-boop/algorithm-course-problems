# 11.12 - BST Search
# Run: python3 11_12_bst_search.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def contains_target(root, target):
  cur_node = root
  while cur_node:
    if cur_node.val == target:
      return True
    elif cur_node.val > target:
      cur_node = cur_node.left
    else:
      cur_node = cur_node.right
  return False


def run_tests():
  # Test 1
  root1 = Node(5,
               Node(2,
                    None,
                    Node(4)),
               Node(9,
                    Node(9,
                         None,
                         Node(9)),
                    Node(11)))

  # Test 2: Empty tree
  root2 = None

  # Test 3: Single node
  root3 = Node(1)

  # Test 4: Perfect BST
  root4 = Node(4,
               Node(2,
                    Node(1),
                    Node(3)),
               Node(6,
                    Node(5),
                    Node(7)))

  # Test 5: Unbalanced BST
  root5 = Node(5,
               Node(3,
                    Node(2,
                         Node(1),
                         None),
                    Node(4)),
               None)

  tests = [
      (root1, 6, False),
      (root1, 9, True),
      (root1, 3, False),
      (root1, 4, True),
      (root2, 1, False),  # Empty tree
  ]

  for i, (root, target, want) in enumerate(tests):
    got = contains_target(root, target)
    assert got == want, f"\nfind(root{        i + 1}, {target}): got: {got}, want: {want}\n"

run_tests()
