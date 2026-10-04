# 12.14 - Multi-Exit Maze
# Run: python3 12_14_multi_exit_maze.py

from collections import deque

def exit_distances(maze):
  R, C = len(maze), len(maze[0])
  directions = [(-1, 0), (1, 0), (0, 1), (0, -1)]
  distances = [[-1] * C for _ in range(R)]
  Q = deque()
  for r in range(R):
    for c in range(C):
      if maze[r][c] == 'O':
        distances[r][c] = 0
        Q.append((r, c))

  while Q:
    r, c = Q.popleft()
    for dir_r, dir_c in directions:
      nbr_r, nbr_c = r + dir_r, c + dir_c
      if (0 <= nbr_r < R and 0 <= nbr_c < C and
              maze[nbr_r][nbr_c] != 'X' and distances[nbr_r][nbr_c] == -1):
        distances[nbr_r][nbr_c] = distances[r][c] + 1
        Q.append((nbr_r, nbr_c))
  return distances


def run_tests():
  tests = [
      # Example from book
      [["...X.O",
        "OX.X..",
        "...X..",
        ".X....",
        "XOX.XX"],
       [
          [1, 2, 3, -1, 1, 0],
          [0, -1, 4, -1, 2, 1],
          [1, 2, 3, -1, 3, 2],
          [2, -1, 4, 5, 4, 3],
          [-1, 0, -1, 6, -1, -1]]],
      # Single exit
      [["...",
        ".O.",
        "..."],
          [[2, 1, 2],
           [1, 0, 1],
           [2, 1, 2]]],
      # Multiple exits
      [["O.O",
        "...",
        "O.O"],
          [[0, 1, 0],
           [1, 2, 1],
           [0, 1, 0]]],
      # Walls blocking direct paths
      [["O.X.",
        "XX..",
        "...O"],
          [[0, 1, -1, 2],
           [-1, -1, 2, 1],
           [3, 2, 1, 0]]],
      # Single cell
      [["O"],
          [[0]]]
  ]

  for maze_rows, want in tests:
    maze = [list(row) for row in maze_rows]
    got = exit_distances(maze)
    assert got == want, f"\nexit_distances({maze_rows}): got: {        got}, want: {want}\n"

run_tests()
