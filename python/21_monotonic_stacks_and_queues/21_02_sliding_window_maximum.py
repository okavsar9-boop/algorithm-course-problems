# 21.2 - Sliding Window Maximum
# Run: python3 21_02_sliding_window_maximum.py

from collections import deque

class MaxQueue:
  def __init__(self):
    self.queue = deque()
    self.mono_decr_deque = deque()

  def max(self):
    return self.mono_decr_deque[0]

  def pop(self):
    # Check if we are popping the max
    if self.queue[0] == self.mono_decr_deque[0]:
      self.mono_decr_deque.popleft()
    self.queue.popleft()

  def push(self, val):
    self.queue.append(val)
    # Remove elements from the monotonic deque that can never be the max
    while self.mono_decr_deque and self.mono_decr_deque[-1] < val:
      self.mono_decr_deque.pop()
    self.mono_decr_deque.append(val)

def sliding_window_max(arr, k):
  l, r = 0, 0
  max_queue = MaxQueue()
  res = []
  while r < len(arr):
    max_queue.push(arr[r])
    r += 1
    if r - l == k:
      res.append(max_queue.max())
      max_queue.pop()
      l += 1
  return res


def run_tests():
  tests = [
      # Example 1 from the book
      ([10, 20, 30, 40, 30, 20, 10], 2, [20, 30, 40, 40, 30, 20]),
      # Example 2 from the book
      ([10, 20, 30, 40, 30, 20, 10], 3, [30, 40, 40, 40, 30]),
      # Window size 1 just returns the array
      ([1, 2, 3], 1, [1, 2, 3]),
      # Window size equals array length
      ([5, 2, 1], 3, [5]),
      # Array with duplicates
      ([1, 1, 1, 2, 2, 2], 2, [1, 1, 2, 2, 2]),
      # Decreasing sequence
      ([5, 4, 3, 2, 1], 3, [5, 4, 3]),
      # Increasing sequence
      ([1, 2, 3, 4, 5], 3, [3, 4, 5]),
      # Mixed sequence
      ([1, 5, 2, 6, 3], 3, [5, 6, 6])
  ]
  for arr, k, want in tests:
    got = sliding_window_max(arr, k)
    assert got == want, f"\nsliding_window_max({arr}, {k}): got: {        got}, want: {want}\n"

run_tests()
