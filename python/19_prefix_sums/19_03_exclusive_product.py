# 19.3 - Exclusive Product
# Run: python3 19_03_exclusive_product.py

def exclusive_product_array(arr):
  m = 10**9 + 7
  n = len(arr)
  prefix_product = [1] * n
  prefix_product[0] = arr[0]
  for i in range(1, n):
    prefix_product[i] = (prefix_product[i - 1] * arr[i]) % m

  postfix_product = [1] * n
  postfix_product[n - 1] = arr[n - 1]
  for i in range(n - 2, -1, -1):
    postfix_product[i] = (postfix_product[i + 1] * arr[i]) % m

  res = [1] * n
  res[0] = postfix_product[1]
  res[n - 1] = prefix_product[n - 2]
  for i in range(1, n - 1):
    res[i] = (prefix_product[i - 1] * postfix_product[i + 1]) % m
  return res


def run_tests():
  tests = [
      # Example from the book
      ([1, 3, 2, 1], [6, 2, 3, 6]),
      # Edge case: Contains zero
      ([0, 1, 2, 3], [6, 0, 0, 0]),
      # Edge case: All ones
      ([1, 1, 1, 1], [1, 1, 1, 1]),
      # Edge case: Large numbers
      ([10000, 10000], [10000, 10000]),
  ]

  for arr, want in tests:
    got = exclusive_product_array(arr)
    assert got == want, f"\nexclusive_product_array({arr}): got: {        got}, want: {want}\n"

run_tests()
