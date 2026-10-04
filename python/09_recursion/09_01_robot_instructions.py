# 9.1 - Robot Instructions
# Run: python3 09_01_robot_instructions.py

def moves(seq):
  res = []
  
  def moves_rec(pos):
    if pos == len(seq):
      return
    if seq[pos] == '2':
      moves_rec(pos+1)
      moves_rec(pos+2)
    else:
      res.append(seq[pos])
      moves_rec(pos+1)
  
  moves_rec(0)
  return ''.join(res)


def run_tests():
  tests = [
    # Example 1 from book
    ("LL", "LL"),
    # Example 2 from book
    ("2LR", "LRR"),
    # Example 3 from book
    ("2L", "L"),
    # Example 4 from book
    ("22LR", "LRRLR"),
    # Example 5 from book
    ("LL2R2L", "LLRLL"),
    # Edge case - empty string
    ("", ""),
    # Edge case - single character
    ("L", "L"),
    # Multiple 2s in a row
    ("2222LR", "LRRLRLRRLRRLR"),
  ]

  for seq, want in tests:
    got = moves(seq)
    assert got == want, f"\nmoves({seq}): got: {got}, want: {want}\n"

run_tests()
