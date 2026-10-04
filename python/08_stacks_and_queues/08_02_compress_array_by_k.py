# 8.2 - Compress Array By K
# Run: python3 08_02_compress_array_by_k.py

arr = [2, 2, 2]

k = 2

def compress_array_k(arr, k):
  stack = []

  def merge(num):
    if not stack or stack[-1][0] != num:
      stack.append([num, 1])
    elif stack[-1][1] < k - 1:
      stack[-1][1] += 1
    else:
      stack.pop()
      merge(num * k)

  for num in arr:
    merge(num)

  res = []
  for num, count in stack:
    for _ in range(count):
      res.append(num)
  return res


def run_tests():
  tests = [
      ([1, 9, 9, 3, 3, 3, 4], 3, [1, 27, 4]),
      ([8, 4, 2, 2], 2, [16]),
      ([4, 4, 4, 4], 5, [4, 4, 4, 4]),
      ([], 2, []),
      ([0, 0, 0, 0], 2, [0]),
  ]
  for arr, k, want in tests:
    got = compress_array_k(arr, k)
    assert got == want, f"\ncompress_array_k({arr}, {k}): got: {        got}, want: {want}\n"

run_tests()
