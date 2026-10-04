# 15.11 - Escape with All Clues
# Run: python3 15_11_escape_with_all_clues.py

def escape_with_all_clues(room):

  def get_clues():
    clues = []
    for i in range(len(room)):
      for j in range(len(room[0])):
        if room[i][j] == 2:
          clues.append((i, j))
    return clues

  def valid_moves(pos):
    i, j = pos
    moves = []
    for dir_i, dir_j in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
      nbr_i, nbr_j = i + dir_i, j + dir_j
      if (0 <= nbr_i < len(room) and
          0 <= nbr_j < len(room[0]) and
              room[nbr_i][nbr_j] != 1):
        moves.append((nbr_i, nbr_j))
    return moves

  all_clues = set(get_clues())
  shortest_path = []

  # State of the current partial solution
  current_path = [(0, 0)]
  current_visited = {(0, 0)}
  current_clues_left = all_clues.copy()
  if (0, 0) in current_clues_left:
    current_clues_left.remove((0, 0))

  def visit():
    nonlocal shortest_path, current_path, current_visited, current_clues_left

    # Prune if path is already longer than shortest found
    if shortest_path and len(current_path) >= len(shortest_path):
      return

    # Found valid solution
    if not current_clues_left:
      if not shortest_path or len(current_path) < len(shortest_path):
        shortest_path = current_path.copy()
      return

    # Try each valid move
    cur = current_path[-1]
    for next_pos in valid_moves(cur):
      if next_pos in current_visited:
        continue  # Don't revisit cells

      # Modify state
      current_path.append(next_pos)
      current_visited.add(next_pos)
      if next_pos in all_clues:
        current_clues_left.remove(next_pos)

      visit()

      # Undo state changes
      current_path.pop()
      current_visited.remove(next_pos)
      if next_pos in all_clues:
        current_clues_left.add(next_pos)

  visit()
  return [list(p) for p in shortest_path]


def run_tests():
  tests = [
      # Example 1 from the book
      ([[0, 1, 0],
        [0, 2, 0],
        [0, 0, 2]], [[0, 0], [1, 0], [1, 1], [1, 2], [2, 2]]),

      # Example 2 from the book
      ([[0, 0, 0],
        [2, 1, 2]], []),

      # Example 3 from the book
      ([[0, 0, 1, 2],
        [0, 1, 0, 0]], []),

      # single clue
      ([[0, 2]], [[0, 0], [0, 1]]),

      # no valid path
      ([[0, 1],
        [1, 2]], []),

      # multiple clues in a line
      ([[0, 2, 2]], [[0, 0], [0, 1], [0, 2]]),

      # 2x2: cannot reach both clues without revisiting the first square
      ([[0, 2],
        [2, 1]], []),

      # Shortest path doesn't start by going to the closest clue
      ([[0, 0, 0, 0, 0],
        [1, 1, 0, 1, 1],
        [2, 0, 0, 0, 0],
        [0, 0, 2, 0, 2],
        [0, 0, 0, 0, 0]], [[0, 0], [0, 1], [0, 2], [1, 2], [2, 2], [2, 1], [2, 0], [3, 0], [3, 1], [3, 2], [3, 3], [3, 4]]),
  ]

  for room, want in tests:
    got = escape_with_all_clues(room)
    if got != want and not (got and want and len(got) == len(want)):
      raise AssertionError(f"\nescape_with_all_clues({room}): got: {                           got}, want: {want}\n")

run_tests()
