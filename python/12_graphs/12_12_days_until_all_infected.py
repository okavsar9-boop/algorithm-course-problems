# 12.12 - Days Until All Infected
# Run: python3 12_12_days_until_all_infected.py

from collections import deque

def multisource_BFS(graph, sources):
  Q = Queue()
  distances = {}
  for start in sources:
    Q.push(start)
    distances[start] = 0
  while not Q.empty(): # Normal BFS loop.
    node = Q.pop()
    for nbr in graph[node]:
      if nbr not in distances:
        distances[nbr] = distances[node] + 1
        Q.push(nbr)

  # Do something with distances.

def all_infected(graph, infected):
  Q = deque()
  distances = {}

  # Initialize queue with infected nodes
  for start in infected:
    Q.append(start)
    distances[start] = 0

  # Multisource BFS
  while Q:
    node = Q.popleft()
    for nbr in graph[node]:
      if nbr not in distances:
        distances[nbr] = distances[node] + 1
        Q.append(nbr)

  # If any node wasn't reached, graph is disconnected
  if len(distances) < len(graph):
    return -1

  # Return max distance - that's how many days it takes
  return max(distances.values())


def run_tests():
  tests = [
      # Example from the book
      ([
          [1, 14],        # 0: Outer ring connections
          [0, 2],         # 1
          [1, 3],         # 2
          [2, 4],         # 3
          [3, 5, 19],     # 4: Connector from outer to inner ring
          [4, 6],         # 5
          [5, 7],         # 6
          [6, 8],         # 7
          [7, 9, 21],     # 8: Connector from outer to inner ring
          [8, 10],        # 9
          [9, 11],        # 10
          [10, 12],       # 11
          [11, 13],       # 12
          [12, 14],       # 13
          [0, 13, 15],    # 14: Connector from outer to inner ring
          [14, 16],       # 15
          [15, 17],       # 16
          [16, 18, 20],   # 17: Center node connections
          [17, 19],       # 18
          [18, 4],        # 19
          [17, 21],       # 20
          [8, 20]         # 21
      ],
          [0, 8, 17],     # infected
          3),
      (
          [[1, 2], [0, 2], [0, 1, 3], [2]],  # graph
          [0],                               # infected
          2                                  # want
      ),
      # Single node graph
      (
          [[]],
          [0],
          0
      ),
      # Line graph
      (
          [[1], [0, 2], [1, 3], [2]],
          [0],
          3
      ),
      # Multiple initial infected nodes
      (
          [[1, 2], [0, 3], [0, 3], [1, 2]],
          [0, 3],
          1
      )
  ]

  for graph, infected, want in tests:
    got = all_infected(graph, infected)
    assert got == want, f"\nall_infected({graph}, {infected}): got: {got}, want: {want}\n"

run_tests()
