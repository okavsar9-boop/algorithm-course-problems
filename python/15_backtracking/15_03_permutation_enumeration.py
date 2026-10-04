# 15.3 - Permutation Enumeration
# Run: python3 15_03_permutation_enumeration.py

def generate_permutations(arr):
  res = []
  perm = arr.copy()

  def visit(i):
    if i == len(perm) - 1:
      res.append(perm.copy())
      return
    for j in range(i, len(perm)):
      perm[i], perm[j] = perm[j], perm[i]  # Pick perm[j]. 
      visit(i + 1)
      perm[i], perm[j] = perm[j], perm[i]  # Cleanup work: undo change.

  visit(0)
  return res


def run_tests():
  tests = [
    # Example from the book
    (['x', 'y', 'z'], [
      ['x', 'y', 'z'], ['x', 'z', 'y'], ['y', 'x', 'z'],
      ['y', 'z', 'x'], ['z', 'x', 'y'], ['z', 'y', 'x']
    ]),
    # Single element
    (['a'], [['a']]),
    # Two elements
    (['a', 'b'], [['a', 'b'], ['b', 'a']]),
    # Larger set
    (['a', 'b', 'c'], [
      ['a', 'b', 'c'], ['a', 'c', 'b'], ['b', 'a', 'c'],
      ['b', 'c', 'a'], ['c', 'a', 'b'], ['c', 'b', 'a']
    ]),
  ]
  for arr, want in tests:
    got = generate_permutations(arr)
    got.sort()
    want.sort()
    assert got == want, f"\ngenerate_permutations({arr}): got: {got}, want: {want}\n"

run_tests()
