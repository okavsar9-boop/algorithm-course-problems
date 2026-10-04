# 15.6 - Jumping Numbers
# Run: python3 15_06_jumping_numbers.py

def jumping_numbers(n):
  res = []

  def visit(num):
    if num >= n:
      return
    res.append(num)
    last_digit = num % 10
    if last_digit > 0:
      visit(num * 10 + (last_digit - 1))
    if last_digit < 9:
      visit(num * 10 + (last_digit + 1))

  for num in range(1, 10):
    visit(num)
  return sorted(res)


def run_tests():
  tests = [
      # Example from the book
      (34, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32]),
      # Edge case - n is 1
      (1, []),
      (10, [1, 2, 3, 4, 5, 6, 7, 8, 9]),
      # Larger n
      (50, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32, 34, 43, 45]),
      (102, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32,
             34, 43, 45, 54, 56, 65, 67, 76, 78, 87, 89, 98, 101]),
  ]
  for n, want in tests:
    got = jumping_numbers(n)
    assert got == want, f"\njumping_numbers({n}): got: {got}, want: {want}\n"

run_tests()
