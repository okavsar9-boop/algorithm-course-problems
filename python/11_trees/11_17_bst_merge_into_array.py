# 11.17 - BST Merge Into Array
# Run: python3 11_17_bst_merge_into_array.py

class Node:
  def __init__(self, val, left=None, right=None):
    self.val = val
    self.left = left
    self.right = right

def merge_into_array(root1, root2):
  
  def inorder(root, arr):
    if not root:
      return
    inorder(root.left, arr)
    arr.append(root.val)
    inorder(root.right, arr)

  arr1, arr2 = [], []
  inorder(root1, arr1)
  inorder(root2, arr2)

  # Merge sorted arrays
  res = []
  i = j = 0
  while i < len(arr1) and j < len(arr2):
    if arr1[i] <= arr2[j]:
      res.append(arr1[i])
      i += 1
    else:
      res.append(arr2[j])
      j += 1

  res.extend(arr1[i:])
  res.extend(arr2[j:])
  return res


def run_tests():
  root1 = Node(5,
               Node(2,
                    None,
                    Node(4)),
               Node(9,
                    Node(9),
                    Node(11)))

  root2 = Node(3,
               Node(2,
                    Node(1)),
               Node(7,
                    Node(6),
                    Node(8)))

  root3 = Node(2,
               Node(2),
               Node(2))

  root4 = Node(2,
               Node(2),
               Node(2))

  tests = [
      # Example 1 from the book
      (root1, root2, [1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9, 11]),
      # Example 2 from the book
      (root3, root4, [2, 2, 2, 2, 2, 2]),
      # Edge cases
      (None, None, []),
      (Node(1), None, [1]),
      (None, Node(1), [1]),
      (Node(1), Node(2), [1, 2]),
  ]

  for root1, root2, want in tests:
    got = merge_into_array(root1, root2)
    assert got == want, f"\nmerge_into_array({root1}, {root2}): got: {        got}, want: {want}\n"

run_tests()
