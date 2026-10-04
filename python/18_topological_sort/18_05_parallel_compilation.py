# 18.5 - Parallel Compilation
# Run: python3 18_05_parallel_compilation.py

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

def compile_time(seconds, imports):
  V = len(seconds)
  graph = [[] for _ in range(V)]
  for package in range(V):
    for imported_package in imports[package]:
      graph[imported_package].append(package)

  topo_order = topological_sort(graph)  # Recipe 1.
  durations = {}
  for node in topo_order:
    if node not in durations:
      durations[node] = seconds[node]
    for nbr in graph[node]:
      if nbr not in durations:
        durations[nbr] = 0
      durations[nbr] = max(durations[nbr], seconds[nbr] + durations[node])

  return max(durations.values())


def run_tests():
  tests = [
      # Example from the book
      ([10, 20, 30], [[], [], [0, 1]], 50),
      # Example from the book
      ([10, 20, 30], [[], [], []], 30),
      # Single package
      ([10], [[]], 10),
      # Linear dependency
      ([10, 20, 30], [[1], [2], []], 60),
      # Complex dependencies
      ([5, 10, 15, 20], [[], [0], [1], [0, 2]], 50),
  ]

  for seconds, imports, want in tests:
    got = compile_time(seconds, imports)
    assert got == want, f"\ncompile_time({seconds}, {imports}): got: {        got}, want: {want}\n"

run_tests()
