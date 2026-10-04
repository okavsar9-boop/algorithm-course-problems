# 11.9 - Most Prolific Level
# Run: python3 11_09_most_prolific_level.py

from collections import deque
from collections import defaultdict

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def most_prolific_level(root):
  
  def level_counts(root):
    Q = deque()
    Q.append((root, 0))
    level_count = defaultdict(int)
    while Q:
      node, depth = Q.popleft()
      if not node:
        continue
      level_count[depth] += 1
      Q.append((node.left, depth + 1))
      Q.append((node.right, depth + 1))
    return level_count

  level_count = level_counts(root)
  res = -1
  max_prolificness = -1  # Less than any valid prolificness.
  for level in level_count:
    next_level_count = level_count.get(level + 1, 0)
    prolificness = next_level_count / level_count[level]
    if prolificness > max_prolificness:
      max_prolificness = prolificness
      res = level
  return res


def run_tests():
  # Test 1
  root1 = Node(5,
               Node(2,
                    None,
                    Node(6)),
               Node(9,
                    Node(9,
                         None,
                         Node(1)),
                    Node(8)))

  # Test 2: Empty tree
  root2 = None

  # Test 3: Single node
  root3 = Node(1)

  # Test 4: Perfect binary tree
  root4 = Node(1,
               Node(2,
                    Node(4),
                    Node(5)),
               Node(3,
                    Node(6),
                    Node(7)))

  # Test 5: Unbalanced tree
  root5 = Node(1,
               Node(2,
                    Node(4,
                         Node(8),
                         Node(9)),
                    Node(5)),
               Node(3))

  # Test 6: Example from the book
  root6 = Node(1,
               Node(2,
                    Node(4,
                         Node(8),
                         Node(9)),
                    Node(5,
                         None,
                         Node(11))),
               None)
  # Test 7
  root7 = Node(1,
               Node(2,
                    Node(4,
                         Node(8),
                         Node(9))))
  tests = [
      (root1, [0]),
      (root2, [-1]),  # Empty tree
      (root3, [0]),  # Single node: level 0 has prolificness 0
      # Level 0->1 and 1->2 both have prolificness 2
      (root4, [0, 1]), # Both level 0 and 1 are valid answers
      (root5, [0]),
      (root6, [1]),
      (root7, [2]),
  ]

  for i, (root, valid_wants) in enumerate(tests):
    got = most_prolific_level(root)
    assert got in valid_wants, f"\nmost_prolific_level(root{        i + 1}): got: {got}, valid_wants: {valid_wants}\n"

run_tests()
