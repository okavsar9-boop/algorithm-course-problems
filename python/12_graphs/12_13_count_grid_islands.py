# 12.13 - Count Grid Islands
# Run: python3 12_13_count_grid_islands.py

def grid_dfs(grid, visited, start_r, start_c):
  # Returns if (r, c) is in bounds, not visited, and "walkable."
  def is_valid(r, c):
    return 0 <= r < len(grid) and 0 <= c < len(grid[0]) and not (r, c) in visited and grid[r][c] == 1

  directions = [(-1, 0), (1, 0), (0, 1), (0, -1)]

  def visit(r, c):
    for dir_r, dir_c in directions:
      nbr_r, nbr_c = r + dir_r, c + dir_c
      if is_valid(nbr_r, nbr_c):
        visited.add((nbr_r, nbr_c))
        visit(nbr_r, nbr_c)

  visit(start_r, start_c)

def count_islands(grid):
  R, C = len(grid), len(grid[0])
  count = 0
  visited = set()
  for r in range(R):
    for c in range(C):
      if grid[r][c] == 1 and (r, c) not in visited:
        visited.add((r, c))
        grid_dfs(grid, visited, r, c)
        count += 1
  return count


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

def count_islands_in_place(grid):
  directions = [(-1, 0), (1, 0), (0, 1), (0, -1)]
  R, C = len(grid), len(grid[0])

  def in_bounds(r, c):
    return 0 <= r < R and 0 <= c < C

  # Finds the parent in the stack (neighbor with less negative stack value by 1)
  def find_parent(r, c):
    current_value = grid[r][c]
    for dr, dc in directions:
      nbr_r, nbr_c = r + dr, c + dc
      if in_bounds(nbr_r, nbr_c) and grid[nbr_r][nbr_c] == current_value + 1:
        return nbr_r, nbr_c
    return None, None

  def find_unvisited_neighbor(r, c):
    for dr, dc in directions:
      nbr_r, nbr_c = r + dr, c + dc
      if in_bounds(nbr_r, nbr_c) and grid[nbr_r][nbr_c] == 1:
        return nbr_r, nbr_c
    return None, None

  def iterative_dfs(start_r, start_c):
    stack_level = -1
    grid[start_r][start_c] = stack_level
    head_r, head_c = start_r, start_c
    while stack_level < 0:
      nbr_r, nbr_c = find_unvisited_neighbor(head_r, head_c)
      if nbr_r is not None:
        stack_level -= 1
        grid[nbr_r][nbr_c] = stack_level
        head_r, head_c = nbr_r, nbr_c
      else:
        # No unvisited neighbors, backtrack
        stack_level += 1
        head_r, head_c = find_parent(head_r, head_c)

  island_count = 0
  for start_r in range(R):
    for start_c in range(C):
      if grid[start_r][start_c] == 1:
        iterative_dfs(start_r, start_c)
        island_count += 1
  return island_count

def run_tests():
  tests = [
      # Example 1 from the book
      ([[0, 0, 1, 0],
        [1, 1, 0, 1],
        [0, 0, 1, 1]], 3),
      # Example 2 from the book
      ([[]], 0),
      # Edge case - single cell
      ([[1]], 1),
      # Edge case - all water
      ([[0, 0], [0, 0]], 0),
      # Edge case - all land
      ([[1, 1], [1, 1]], 1),
      # Multiple islands
      ([[1, 0, 1],
        [0, 0, 0],
        [1, 0, 1]], 4)
  ]

  # Test count_islands function
  for grid_template, want in tests:
    # Create a deep copy for the count_islands function
    grid = grid_template.copy()
    got = count_islands(grid)
    assert got == want, f"\ncount_islands({grid_template}): got: {got}, want: {want}\n"

  # Test count_islands_in_place function
  for grid_template, want in tests:
    # Create a deep copy for the in-place function since it modifies the grid
    grid = grid_template.copy()
    got = count_islands_in_place(grid)
    assert got == want, f"\ncount_islands_in_place({grid_template}): got: {got}, want: {want}\n"

run_tests()
