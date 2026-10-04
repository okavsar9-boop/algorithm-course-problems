# 6.9 - Product of Alphabetical Sums
# Run: python3 06_09_product_of_alphabetical_sums.py

def alphabetic_sum_product(words, target):
  
  def alphabetical_sum(word):
    return sum(ord(c) - ord('a') + 1 for c in word)

  sums = set()
  for word in words:
    sums.add(alphabetical_sum(word))

  for i in sums:
    if target % i != 0:
      continue
    for j in sums:
      k = target / (i * j)
      if k in sums:
        return True
  return False


def run_tests():
  tests = [
      # Example 1 
      (["abc", "fg", "hij", "klm", "nop", "qrs", "vwx"], 1620, True),
      # Example 2 
      (["a", "b"], 2, True),
      # Additional test cases
      ([], 1, False),
      (["a"], 1, True),
      (["a", "b", "c"], 6, True),
      (["a", "b", "c"], 7, False),
  ]
  for words, target, want in tests:
    got = alphabetic_sum_product(words, target)
    assert got == want, \
        f"\nalphabetic_sum_product({words}, {target}): got: {            got}, want: {want}\n"

run_tests()
