# 4.1 - Chess Moves
# Run: python3 04_01_chess_moves.py

def chess_moves(board, piece, r, c):

  def is_valid(board, r, c):
    return 0 <= r < len(board) and 0 <= c < len(board[0]) and board[r][c] != 1

  moves = []
  king_directions = [
      [-1, 0], [1, 0], [0, -1], [0, 1],  # Vertical and horizontal
      [-1, -1], [-1, 1], [1, -1], [1, 1]  # Diagonals
  ]
  knight_directions = [[-2, 1], [-1, 2], [1, 2], [2, 1],
                       [2, -1], [1, -2], [-1, -2], [-2, -1]]

  if piece == "knight":
    directions = knight_directions
  else:
    directions = king_directions

  for dir_r, dir_c in directions:
    new_r, new_c = r + dir_r, c + dir_c
    if piece == "queen":
      while is_valid(board, new_r, new_c):
        moves.append([new_r, new_c])
        new_r += dir_r
        new_c += dir_c
    elif is_valid(board, new_r, new_c):
      moves.append([new_r, new_c])
  return moves


def run_tests():
  tests = [
      # Example 1 from the book - king moves
      ([[0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0]], "king", 3, 5,
          [[2, 5], [3, 4], [4, 4], [4, 5]]),
      # Example 2 from the book - knight moves
      ([[0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0]], "knight", 4, 3,
       [[2, 2], [3, 5], [5, 5]]),
      # Example 3 from the book - queen moves
      ([[0, 0, 0, 1, 0, 0],
        [0, 1, 1, 1, 0, 0],
        [0, 1, 0, 1, 1, 0],
        [1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0]], "queen", 4, 4,
       [[3, 4], [3, 5], [4, 0], [4, 1], [4, 2], [4, 3], [4, 5],
        [5, 3], [5, 4], [5, 5]]),
      # Edge case - 1x1 board
      ([[0]], "queen", 0, 0, []),
      # Edge case - all occupied except current position
      ([[1, 1], [1, 0]], "knight", 1, 1, []),
  ]

  for board, piece, r, c, want in tests:
    got = chess_moves(board, piece, r, c)
    # Sort both lists for consistent comparison
    got.sort()
    want.sort()
    assert got == want, (f"\nchess_moves({board}, {piece}, {r}, {c}): "
                         f"got: {got}, want: {want}\n")

run_tests()
