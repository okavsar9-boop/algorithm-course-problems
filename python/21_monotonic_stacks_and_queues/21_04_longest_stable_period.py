# 21.4 - Longest Stable Period
# Run: python3 21_04_longest_stable_period.py

from collections import deque

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

def longest_stable_period(temperatures, t):
  l, r = 0, 0
  max_min_queue = MaxMinQueue()
  cur_best = 0
  while r < len(temperatures):
    can_grow = l == r or (max(max_min_queue.max(), temperatures[r]) - min(
        max_min_queue.min(), temperatures[r]) <= t)
    if can_grow:
      max_min_queue.push(temperatures[r])
      r += 1
      cur_best = max(cur_best, r - l)
    else:
      max_min_queue.pop()
      l += 1
  return cur_best


def run_tests():
  tests = [
      # Example 1 from the book
      ([12, 16, 14, 15, 13, 17], 3, 4),
      # Example 2 from the book
      ([30, 10], 100, 2),
      # Example 3 from the book
      ([30, 10], 1, 1),
      # All same temperature
      ([10, 10, 10, 10], 0, 4),
      # Strictly increasing
      ([10, 20, 30, 40], 5, 1),
      # Strictly decreasing
      ([40, 30, 20, 10], 5, 1),
      # Mixed sequence
      ([22, 18, 25, 20, 15, 21, 16], 4, 2)
  ]
  for temperatures, t, want in tests:
    got = longest_stable_period(temperatures, t)
    assert got == want, f"\nlongest_stable_period({temperatures}, {t}): got: {got}, want: {want}\n"

run_tests()
