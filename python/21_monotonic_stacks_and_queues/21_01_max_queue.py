# 21.1 - Max Queue
# Run: python3 21_01_max_queue.py

from collections import deque

class MaxQueue:
  def __init__(self):
    self.queue = deque()
    self.mono_decr_deque = deque()

  def peek(self):
    return self.queue[0]

  def size(self):
    return len(self.queue)

  def max(self):
    return self.mono_decr_deque[0]

  def pop(self):
    val = self.queue[0]
    # Check if we are popping the max
    if val == self.mono_decr_deque[0]:
      self.mono_decr_deque.popleft()
    self.queue.popleft()
    return val

  def push(self, val):
    self.queue.append(val)
    # Remove elements from the monotonic deque that can never be the max
    while self.mono_decr_deque and self.mono_decr_deque[-1] < val:
      self.mono_decr_deque.pop()
    self.mono_decr_deque.append(val)


def run_tests():
  tests = [
      # Example from the book
      [
          ["push", 10],
          ["push", 30],
          ["push", 20],
          ["max", 30],
          ["pop", 10],
          ["max", 30],
          ["pop", 30],
          ["max", 20],
          ["push", 50],
          ["push", 30],
          ["push", 20],
          ["push", 10],
          ["max", 50],
          ["push", 50],
          ["max", 50],
          ["pop", 20],
          ["pop", 50],
          ["max", 50]
      ],
      # Edge cases
      [
          ["push", 1],
          ["max", 1],
          ["pop", 1],
          ["push", 2],
          ["max", 2]
      ],
      # Multiple equal values
      [
          ["push", 5],
          ["push", 5],
          ["max", 5],
          ["pop", 5],
          ["max", 5]
      ]
  ]

  for ops in tests:
    q = MaxQueue()
    for op in ops:
      if op[0] == "push":
        q.push(op[1])
      elif op[0] == "pop":
        got = q.pop()
        assert got == op[1], f"\npop(): got: {got}, want: {op[1]}\n"
      elif op[0] == "max":
        got = q.max()
        assert got == op[1], f"\nmax(): got: {got}, want: {op[1]}\n"

run_tests()
