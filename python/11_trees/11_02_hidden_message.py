# 11.2 - Hidden Message
# Run: python3 11_02_hidden_message.py

class Node:
  def __init__(self, text, left=None, right=None):
    self.text = text
    self.left = left
    self.right = right

def hidden_message(root):
  message = []

  def visit(node):
    if not node:
      return
    if node.text[0] == 'b':
      message.append(node.text[1])
      visit(node.left)
      visit(node.right)
    elif node.text[0] == 'i':
      visit(node.left)
      message.append(node.text[1])
      visit(node.right)
    else:
      visit(node.left)
      visit(node.right)
      message.append(node.text[1])

  visit(root)
  return ''.join(message)


def run_tests():

  # Test 1: Example from the book - "nice_try!"
  root1 = Node("bn")
  root1.left = Node("i_")
  root1.left.left = Node("ae")
  root1.left.right = Node("it")
  root1.left.left.left = Node("bi")
  root1.left.left.right = Node("bc")
  root1.right = Node("a!")
  root1.right.left = Node("br")
  root1.right.right = Node("ay")
  # Test 2: Empty tree
  root2 = None

  # Test 3: Single TreeNode with before order
  root3 = Node("bx")

  # Test 4: Single TreeNode with in order
  root4 = Node("ix")

  # Test 5: Single TreeNode with after order
  root5 = Node("ax")

  # Test 6: All before order TreeNodes
  root6 = Node("b1",
               Node("b2",
                    Node(
                        "b4", None, None),
                    Node("b5", None, None)),
               Node("b3",
                    Node(
                        "b6", None, None),
                    Node("b7", None, None)))

  # Test 7: All in order TreeNodes
  root7 = Node("i1",
               Node("i2",
                    Node(
                        "i4", None, None),
                    Node("i5", None, None)),
               Node("i3",
                    Node(
                        "i6", None, None),
                    Node("i7", None, None)))

  # Test 8: All after order TreeNodes
  root8 = Node("a1",
               Node("a2",
                    Node(
                        "a4", None, None),
                    Node("a5", None, None)),
               Node("a3",
                    Node(
                        "a6", None, None),
                    Node("a7", None, None)))

  # Test 9: Mixed orders forming "hello"
  root9 = Node("bh",
               Node("be",
                    Node(
                        "bl", None, None),
                    Node("il", None, None)),
               Node("ao", None, None))

  # Test 10: Invalid first character
  root10 = Node("cx")

  # Test 11: Text too short
  root11 = Node("i")

  # Test 12: Text too long
  root12 = Node("bxy")

  tests = [
      (root1, "nice_try!"),  # Example from book
      (root2, ""),          # Empty tree
      (root3, "x"),         # Single TreeNode before
      (root4, "x"),         # Single TreeNode in
      (root5, "x"),         # Single TreeNode after
      (root6, "1245367"),   # All before order
      (root7, "4251637"),   # All in order
      (root8, "4526731"),   # All after order
      (root9, "hello"),     # Mixed orders spelling "hello"
  ]

  # Run normal test cases
  for i, (root, want) in enumerate(tests, 1):
    got = hidden_message(root)
    assert got == want, f"\nhidden_message(): got: {got}, want: {want}\n"

run_tests()
