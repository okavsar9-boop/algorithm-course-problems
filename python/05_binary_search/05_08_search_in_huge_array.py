# 5.8 - Search In Huge Array
# Run: python3 05_08_search_in_huge_array.py

def find_through_api(target, fetch):

  def is_before(idx):
    return fetch(idx) != -1 and fetch(idx) < target

  l = 0
  if not is_before(l):
    if fetch(l) == target:
      return l
    return -1

  # Step 1: Get the rightmost boundary
  r = 1
  while is_before(r):
    r *= 2

  # Step 2: Binary search
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  if fetch(r) == target:
    return r
  return -1


def run_tests():

  def make_fetch_function(secret_array):

    def fetch(idx):
      if idx >= len(secret_array) or idx < 0:
        return -1
      return secret_array[idx]

    return fetch

  tests = [
      # Example 1 - target exists
      (5, 2, [1, 3, 5, 7, 9]),
      # Example 2 - target doesn't exist
      (6, -1, [1, 3, 5, 7, 9]),
      # Edge case - target at start
      (1, 0, [1, 3, 5, 7, 9]),
      # Edge case - target at end
      (9, 4, [1, 3, 5, 7, 9]),
      # All duplicates
      (1, 0, [1, 1, 1, 1, 1, 1, 1, 1]),
      # Ensure we don't go out of bounds
      (10, 9, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11])
  ]

  for target, want, secret_array in tests:
    fetch = make_fetch_function(secret_array)
    got = find_through_api(target, fetch)
    assert got == want, f"find_through_api({target}): got {got}, want {want}"

run_tests()
