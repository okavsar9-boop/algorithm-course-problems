# 12.10 - Shortest-Path Queries
# Run: python3 12_10_shortest_path_queries.py

from collections import deque

def shortest_path_queries(graph, start, queries):
  Q = deque()
  Q.append(start)
  predecessors = {start: None}
  while Q:
    node = Q.popleft()
    for nbr in graph[node]:
      if nbr not in predecessors:
        predecessors[nbr] = node
        Q.append(nbr)

  res = []
  for node in queries:
    if node not in predecessors:
      res.append([])
    else:
      path = [node]
      while path[len(path) - 1] != start:
        path.append(predecessors[path[len(path) - 1]])
      path.reverse()
      res.append(path)
  return res


def run_tests():
  tests = [
      # Example
      [[[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]], 0, [1, 0, 3, 4],
       [[0, 1], [0], [], [0, 1, 4]]],
      # Simple line graph
      [[[1], [0, 2], [1]], 0, [1, 2],
          [[0, 1], [0, 1, 2]]],
      # Disconnected components
      [[[1], [0], [3], [2]], 0, [1, 2, 3],
          [[0, 1], [], []]],
      # Complete graph
      [[[1, 2], [0, 2], [0, 1]], 0, [1, 2],
          [[0, 1], [0, 2]]],
      # Single node
      [[[]], 0, [0],
          [[0]]],
      # Empty queries
      [[[1], [0]], 0, [],
          []]
  ]
  for graph, start, queries, want in tests:
    got = shortest_path_queries(graph, start, queries)
    assert got == want, f"\nshortest_path_queries({graph}, {start}, {queries}): got: {        got}, want: {want}\n"

run_tests()
