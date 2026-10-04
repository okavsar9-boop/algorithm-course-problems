# 15.2 - Subset Enumeration
# Run: python3 15_02_subset_enumeration.py

def all_subsets(S):
  res = []    # Global list of subsets.
  subset = [] # State of the current partial solution.

  def visit(i):
    if i == len(S):
      res.append(subset.copy())
      return   
    # Choice 1: pick S[i].
    subset.append(S[i])
    visit(i + 1)
    subset.pop()  # Cleanup work: undo choice 1.

    # Choice 2: skip S[i].
    visit(i + 1)

  visit(0)
  return res


def run_tests():
  tests = [
    # Example from the book
    (['x', 'y', 'z'], [[], ['x'], ['y'], ['z'], ['x', 'y'], ['x', 'z'], ['y', 'z'], ['x', 'y', 'z']]),
    # Edge case - empty set
    ([], [[]]),
    # Single element
    (['a'], [[], ['a']]),
    # Two elements
    (['a', 'b'], [[], ['a'], ['b'], ['a', 'b']]),
    # Larger set
    (['a', 'b', 'c', 'd'], [
      [], ['a'], ['b'], ['c'], ['d'], ['a', 'b'], ['a', 'c'], ['a', 'd'],
      ['b', 'c'], ['b', 'd'], ['c', 'd'], ['a', 'b', 'c'], ['a', 'b', 'd'],
      ['a', 'c', 'd'], ['b', 'c', 'd'], ['a', 'b', 'c', 'd']
    ]),
  ]
  for S, want in tests:
    got = all_subsets(S)
    got.sort()
    want.sort()
    assert got == want, f"\nall_subsets({S}): got: {got}, want: {want}\n"

run_tests()
