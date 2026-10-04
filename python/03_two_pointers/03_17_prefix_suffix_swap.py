# 3.17 - Prefix-Suffix Swap
# Run: python3 03_17_prefix_suffix_swap.py

def swap_prefix_suffix(arr):
  if len(arr) == 0:
    return

  n = len(arr)

  # Reverse the whole array
  l, r = 0, n - 1
  while l < r:
    arr[l], arr[r] = arr[r], arr[l]
    l += 1
    r -= 1

  # Reverse the last n/3 elements
  l, r = 2 * n // 3, n - 1
  while l < r:
    arr[l], arr[r] = arr[r], arr[l]
    l += 1
    r -= 1

  # Reverse the first 2n/3 elements
  l, r = 0, (2 * n // 3) - 1
  while l < r:
    arr[l], arr[r] = arr[r], arr[l]
    l += 1
    r -= 1


def run_tests():
  tests = [ # Example from the book
    (list("badreview"), list("reviewbad")), # Additional test cases
    ([], []),
    (list("abc"), list("bca")),
    (list("abcdef"), list("cdefab")),
    (list("123456789"), list("456789123")),
    (list("aaabbbccc"), list("bbbcccaaa")),
    ]
  for arr, want in tests:
    arr_copy = arr.copy() # Make a copy since swap_prefix_suffix modifies in place
    swap_prefix_suffix(arr_copy)
    assert arr_copy == want, f"\nswap_prefix_suffix({arr}): got: {arr_copy}, want: {want}\n"

run_tests()
