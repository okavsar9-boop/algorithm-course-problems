# 18.7 - Supersequence
# Run: python3 18_07_supersequence.py

def has_cycle(graph):
  # Use topological sort to return whether there is a cycle.

  # Initialization
  in_degree = {}
  for node in graph:
    in_degree[node] = 0
  for node in graph:
    for nbr in graph[node]:
      in_degree[nbr] += 1
  degree_zero = []
  for node in in_degree:
    if in_degree[node] == 0:
      degree_zero.append(node)

  # Main 'peel-off' loop
  topo_order = []
  while degree_zero:
    node = degree_zero.pop()
    topo_order.append(node)
    for nbr in graph[node]:
      in_degree[nbr] -= 1
      if in_degree[nbr] == 0:
        degree_zero.append(nbr)

  return len(topo_order) != len(graph)

def can_form_supersequence(arr):
  # Build the graph
  graph = {}
  for word in arr:
    for c in word:
      if c not in graph:
        graph[c] = set()
  for word in arr:
    for i in range(len(word) - 1):
      graph[word[i]].add(word[i + 1])

  # Check for cycles using topological sort
  return not has_cycle(graph)


def run_tests():
  tests = [
      (["abc", "bde", "df", "cfe"], True),
      # Cycle present
      (["ab", "ba"], False),
      # Edge case: Single letter
      (["a"], True),
      # Edge case: Empty array
      ([], True),
      # Multiple words with no dependencies
      (["a", "b", "c"], True),
      # Long chain
      (["ab", "bc", "cd", "de", "ef", "fg"], True),
      # Cycle
      (["abc", "bcd", "cda"], False),
      # Same letter multiple times
      (["aba", "bab"], False),
  ]

  for arr, want in tests:
    got = can_form_supersequence(arr)
    assert got == want, f"\ncan_form_supersequence({arr}): got: {        got}, want: {want}\n"

run_tests()
