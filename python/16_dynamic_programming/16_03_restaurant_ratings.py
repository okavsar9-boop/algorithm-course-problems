# 16.3 - Restaurant Ratings
# Run: python3 16_03_restaurant_ratings.py

def restaurant_ratings(ratings):
  n = len(ratings)
  memo = {}

  def rating_sum(i):
    if i >= n:
      return 0
    if i in memo:
      return memo[i]
    memo[i] = max(ratings[i] + rating_sum(i + 2), rating_sum(i + 1))
    return memo[i]

  return rating_sum(0)


def run_tests():
  tests = [
    ([8, 1, 3, 9, 5, 2, 1], 19),
    ([8, 1, 3, 7, 5, 2, 4], 20),
    ([10, 10, 10, 10, 10], 30),
    ([], 0),
    ([5, 5, 5, 5, 5], 15),
  ]
  for ratings, want in tests:
    got = restaurant_ratings(ratings)
    assert got == want, f"\nrestaurant_ratings({ratings}): got: {got}, want: {want}\n"

run_tests()
