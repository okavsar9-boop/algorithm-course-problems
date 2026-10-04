# 21.3 - Largest Temperature Change
# Run: python3 21_03_largest_temperature_change.py

from collections import deque
import math

class MaxMinQueue:
  def __init__(self):
    self.queue = deque()
    self.mono_decr_deque = deque()  # For max
    self.mono_incr_deque = deque()  # For min

  def max(self):
    return self.mono_decr_deque[0]

  def min(self):
    return self.mono_incr_deque[0]

  def pop(self):
    # Check if we are popping the max
    if self.queue[0] == self.mono_decr_deque[0]:
      self.mono_decr_deque.popleft()
    # Check if we are popping the min
    if self.queue[0] == self.mono_incr_deque[0]:
      self.mono_incr_deque.popleft()

    self.queue.popleft()

  def push(self, val):
    self.queue.append(val)
    # Remove elements from the decreasing deque that can never be the max
    while self.mono_decr_deque and self.mono_decr_deque[-1] < val:
      self.mono_decr_deque.pop()
    self.mono_decr_deque.append(val)
    # Remove elements from the increasing deque that can never be the min
    while self.mono_incr_deque and self.mono_incr_deque[-1] > val:
      self.mono_incr_deque.pop()
    self.mono_incr_deque.append(val)

def largest_temperature_change(arr, k):
  l, r = 0, 0
  max_min_queue = MaxMinQueue()
  res = -math.inf
  while r < len(arr):
    max_min_queue.push(arr[r])
    r += 1
    if r - l == k:
      res = max(res, max_min_queue.max() - max_min_queue.min())
      max_min_queue.pop()
      l += 1
  return res


def run_tests():
  tests = [
      # Example 1 from the book
      ([12, 13, 12, 13, 13, 12, 11, 12], 3, 2),
      # Example 2 from the book
      ([10, 30], 2, 20),
      # all same temperature
      ([10, 10, 10, 10], 2, 0),
      # strictly increasing
      ([10, 20, 30, 40], 3, 20),
      # strictly decreasing
      ([40, 30, 20, 10], 3, 20),
      # k equals length
      ([15, 10, 25], 3, 15),
      # Mixed sequence
      ([22, 18, 25, 20, 15, 21, 16], 4, 10)
  ]
  for arr, k, want in tests:
    got = largest_temperature_change(arr, k)
    assert got == want, f"\nlargest_temperature_change({arr}, {k}): got: {        got}, want: {want}\n"

run_tests()
