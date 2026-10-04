# 12.15 - RGB Distances
# Run: python3 12_15_rgb_distances.py

from collections import deque

def multisource_BFS(graph, sources):
  Q = Queue()
  distances = {}
  for start in sources:
    Q.push(start)
    distances[start] = 0
  while not Q.empty(): # Normal BFS loop
    node = Q.pop()
    for nbr in graph[node]:
      if nbr not in distances:
        distances[nbr] = distances[node] + 1
        Q.push(nbr)

  # Do something with distances
def grid_bfs(grid, start_r, start_c):
  # Returns if (r, c) is in bounds, not in distances, and "walkable".
  def is_valid(r, c):
    ...

  directions = [(-1, 0), (1, 0), (0, 1), (0, -1)]
  Q = Queue()
  Q.push((start_r, start_c))
  distances = {(start_r, start_c): 0}
 
  while not Q.empty():
    r, c = Q.pop()
    for dir_r, dir_c in directions:
      nbr_r, nbr_c = r + dir_r, c + dir_c
      if is_valid(nbr_r, nbr_c):
        distances[(nbr_r, nbr_c)] = distances[(r, c)] + 1
        Q.push((nbr_r, nbr_c))

  # Do something with distances.

def get_sources(screen, target):
  sources = []
  for i in range(len(screen)):
    for j in range(len(screen[0])):
      if screen[i][j] == target:
        sources.append((i, j))
  return sources

def multisource_bfs(screen, sources):
  rows, cols = len(screen), len(screen[0])
  distances = {}
  Q = deque()
  
  # Initialize with sources
  for r, c in sources:
    Q.append((r, c))
    distances[(r, c)] = 0
    
  # BFS
  while Q:
    r, c = Q.popleft()
    for nr, nc in [(r+1, c), (r-1, c), (r, c+1), (r, c-1)]:
      if (0 <= nr < rows and 0 <= nc < cols and 
          (nr, nc) not in distances):
        distances[(nr, nc)] = distances[(r, c)] + 1
        Q.append((nr, nc))
        
  return distances

def rgb_distances(screen):
  rows, cols = len(screen), len(screen[0])
  output = [[0] * cols for _ in range(rows)]
  
  # Map each color to its target
  targets = {'R': 'G', 'G': 'B', 'B': 'R'}
  
  # For each color, do multisource BFS from its target color
  for color, target in targets.items():
    sources = get_sources(screen, target)
    distances = multisource_bfs(screen, sources)
    
    # Fill in distances for cells of current color
    for i in range(rows):
      for j in range(cols):
        if screen[i][j] == color:
          output[i][j] = distances[(i, j)]
          
  return output


def run_tests():
  tests = [
    # Example from the book
    (
      [
        "RRRGRB",
        "BGRGRR",
        "RRRGRR",
        "RGRRRR",
        "GBGRGG"
      ],
      [
        [2, 1, 1, 2, 1, 1],
        [1, 1, 1, 3, 1, 2],
        [2, 1, 1, 4, 1, 2],
        [1, 1, 1, 1, 1, 1],
        [1, 2, 1, 1, 3, 4]
      ]
    ),
    # Single row
    (
      ["RGB"],
      [[1, 1, 2]]
    ),
    # Single column
    (
      ["R", "G", "B"],
      [[1], [1], [2]]
    ),
    # All colors adjacent
    (
      ["RGB",
       "BGR"],
      [[1, 1, 1],
       [1, 1, 1]]
    )
  ]

  for screen, want in tests:
    got = rgb_distances(screen)
    assert got == want, \
      f"\nrgb_distances({screen}): got: {got}, want: {want}\n"

run_tests()
