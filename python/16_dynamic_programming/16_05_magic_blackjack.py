# 16.5 - Magic Blackjack
# Run: python3 16_05_magic_blackjack.py

def num_ways():
  memo = {}
  
  def num_ways_rec(i):
    if i > 21:
      return 1
    if 16 <= i <= 21:
      return 0
    if i in memo:
      return memo[i]
    res = 0
    for card in range(1, 11):
      res += num_ways_rec(i + card)
    memo[i] = res
    return res

  return num_ways_rec(0)


def run_tests():
  TOTAL_POSSIBLE_BUSTS = 100_081
  got = num_ways()
  assert got == TOTAL_POSSIBLE_BUSTS, f"\nnum_ways(<no input>): got: {got}, want: {TOTAL_POSSIBLE_BUSTS}\n"

run_tests()
