# 18.8 - Number of Palindromic Splits
# Run: python3 18_08_number_of_palindromic_splits.py

def count_palindromic_splits_dp(s):
  n = len(s)
  if n == 0:
    return 0

  # Preprocessing: compute all palindromic substrings
  palindrome_set = set(find_palindromes(s))

  memo = {}

  def count_splits(i):
    # Base case: reached the end of string
    if i == n:
      return 1

    if i in memo:
      return memo[i]

    # Try all possible palindromic substrings starting at i
    total = 0
    for j in range(i, n):
      if (i, j) in palindrome_set:
        total += count_splits(j + 1)

    memo[i] = total
    return total

  return count_splits(0)

def find_palindromes(s):
  n = len(s)
  palindromes = []
  for i in range(n):
    l, r = i, i
    while l >= 0 and r < n and s[l] == s[r]:
      palindromes.append((l, r))
      l -= 1
      r += 1
    l, r = i, i + 1
    while l >= 0 and r < n and s[l] == s[r]:
      palindromes.append((l, r))
      l -= 1
      r += 1
  return palindromes

def topological_sort(graph):
  # Initialization
  V = len(graph)
  in_degrees = [0 for _ in range(V)]
  for node in range(V):
    for nbr in graph[node]:
      in_degrees[nbr] += 1
  degree_zero = []
  for node in range(V):
    if in_degrees[node] == 0:
      degree_zero.append(node)

  # Main 'peel-off' loop
  topo_order = []
  while degree_zero:
    node = degree_zero.pop()
    topo_order.append(node)
    for nbr in graph[node]:
      in_degrees[nbr] -= 1
      if in_degrees[nbr] == 0:
        degree_zero.append(nbr)
  return topo_order

def count_palindromic_splits(s):
  palindromes = find_palindromes(s)
  n = len(s)
  if n == 0:
    return 0

  # Use nodes 0 to n, where an edge from i to j indicates that s[i:j] is a
  # palindrome.
  # Node n is the special goal node.
  graph = [[] for _ in range(n + 1)]
  for l, r in palindromes:
    graph[l].append(r + 1)

  topo_order = topological_sort(graph)  # Recipe 1.

  counts = [0] * (n + 1)
  counts[0] = 1
  for node in topo_order:
    for nbr in graph[node]:
      counts[nbr] += counts[node]
  return counts[n]


def run_tests():
  tests = [
      ("abbaab", 6),
      ("aabaa", 6),
      ("a", 1),
      ("aa", 2),
      ("aaa", 4),
      ("aaaa", 8),
      ("aaaaa", 16),
      ("abc", 1),
      ("aaba", 3),
      ("abcdedcba", 5),
      ("", 0),
  ]
  for s, want in tests:
    got = count_palindromic_splits(s)
    assert got == want, f"\ncount_palindromic_splits(\"{s}\"): got: {got}, want: {want}\n"
    got_dp = count_palindromic_splits_dp(s)
    assert got_dp == want, f"\ncount_palindromic_splits_dp(\"{s}\"): got: {got_dp}, want: {want}\n"

run_tests()
