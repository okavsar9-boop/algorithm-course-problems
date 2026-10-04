# 11.7 - Evaluate Expression Tree
# Run: python3 11_07_evaluate_expression_tree.py

class Node:
  def __init__(self, kind, num, children):
    self.kind = kind          # One of "sum", "product", "max", "min", or "num".
    self.num = num            # Only valid when kind is "num".
    self.children = children  # Only valid when kind is not "num".

def product(vals):
  res = 1
  for val in vals:
    res *= val
  return res

def evaluate(root):
  if root.kind == "num":
    return root.num
  children_evals = []
  for child in root.children:
    children_evals.append(evaluate(child))
  if root.kind == "sum":
    return sum(children_evals)
  if root.kind == "product":
    return product(children_evals)
  if root.kind == "max":
    return max(children_evals)
  if root.kind == "min":
    return min(children_evals)
  raise ValueError("Invalid node kind")


def run_tests():
  # Test 0: Example from the book
  root0 = Node("min", None, [
      Node("max", None, [
          Node("num", 4, None),
          Node("num", 6, None),
          Node("sum", None, [
              Node("num", 5, None),
              Node("num", 7, None)
          ])
      ]),
      Node("sum", None, [
          Node("product", None, [
              Node("num", 6, None),
              Node("num", 8, None)
          ])
      ])
  ])

  # Test 1: Example - (2 + 3) * 4
  root1 = Node("product", None, [
      Node("sum", None, [
          Node("num", 2, None),
          Node("num", 3, None)
      ]),
      Node("num", 4, None)
  ])

  # Test 2: Single number node
  root2 = Node("num", 5, None)

  # Test 3: Empty sum node
  root3 = Node("sum", None, [])

  # Test 4: Empty product node
  root4 = Node("product", None, [])

  # Test 5: Complex expression with all operations
  # min(2, max(3,4)) + product(1,2,3)
  root5 = Node("sum", None, [
      Node("min", None, [
          Node("num", 2, None),
          Node("max", None, [
              Node("num", 3, None),
              Node("num", 4, None)
          ])
      ]),
      Node("product", None, [
          Node("num", 1, None),
          Node("num", 2, None),
          Node("num", 3, None)
      ])
  ])

  tests = [
      (root0, 12),
      (root1, 20),  # (2 + 3) * 4 = 20
      (root2, 5),   # Single number
      (root3, 0),   # Empty sum = 0
      (root4, 1),   # Empty product = 1
      (root5, 8),   # min(2,max(3,4)) + product(1,2,3) = 2 + 6 = 8
      (root0.children[0], 12),
      (root0.children[1], 48),
  ]

  for i, (root, want) in enumerate(tests, 1):
    got = evaluate(root)
    assert got == want, f"\nevaluate(root{i}): got: {got}, want: {want}\n"

run_tests()
