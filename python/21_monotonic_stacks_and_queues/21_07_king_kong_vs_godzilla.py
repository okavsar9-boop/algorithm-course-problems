# 21.7 - King Kong Vs Godzilla
# Run: python3 21_07_king_kong_vs_godzilla.py

from collections import deque

class MaxQueue:
  def __init__(self):
    self.queue = deque()
    self.mono_decr_deque = deque()

  def size(self):
    return len(self.queue)

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

def spared_by_king_kong(street):
  n = len(street)
  spared = [False] * n

  # Add all buildings to the queue (except the first one)
  max_queue = MaxQueue()
  for i in range(1, n):
    max_queue.push(street[i])

  # Process each building (except the last one, which is always destroyed)
  for i in range(n - 1):
    if street[i] < max_queue.max():
      spared[i] = True
    max_queue.pop()
  return spared

def spared_by_king_kong_nge(street):
  n = len(street)

  # Build NGE array using recipe
  nge = [-1] * n
  stack = []
  for i in range(n - 1, -1, -1):
    while stack and street[stack[-1]] <= street[i]:
      stack.pop()
    if stack:
      nge[i] = stack[-1]
    stack.append(i)

  # Building i is spared if it has a next greater element
  spared = [False] * n
  for i in range(n):
    if nge[i] != -1:
      spared[i] = True
  return spared

def spared_by_godzilla(street):
  new_street = [-h for h in street]
  new_street.reverse()
  res = spared_by_king_kong(new_street)
  res.reverse()
  return res

def spared(street):
  res1 = spared_by_king_kong(street)
  res2 = spared_by_godzilla(street)
  return [a and b for a, b in zip(res1, res2)]


def run_tests():
  tests = [
      # Example 1 from the book
      ([10, 20, 30, 15, 5],
       [True, True, False, False, False],    # King Kong
       [False, True, True, True, False],    # Godzilla
       [False, True, False, False, False]),  # Combined
      # Example 2 from the book
      ([10, 20, 30, 40, 50],
       [True, True, True, True, False],    # King Kong
       [False, True, True, True, True],    # Godzilla
       [False, True, True, True, False]),  # Combined
      # Example 3 from the book
      ([50, 40, 30, 20, 10],
       [False, False, False, False, False],   # King Kong
       [False, False, False, False, False],   # Godzilla
       [False, False, False, False, False]),  # Combined
      # Example 4 from the book
      ([1, 10, 5, 20],
       [True, True, True, False],    # King Kong
       [False, True, True, True],    # Godzilla
       [False, True, True, False]),  # Combined
      # Edge case - single element
      ([1],
       [False],   # King Kong
       [False],   # Godzilla
       [False]),  # Combined
      # Edge case - all same height
      ([5, 5, 5, 5, 5],
       [False, False, False, False, False],  # King Kong
       [False, False, False, False, False],  # Godzilla
       [False, False, False, False, False]),  # Combined
  ]
  for street, want_kong, want_godzilla, want in tests:
    got_kong = spared_by_king_kong(street)
    got_kong_nge = spared_by_king_kong_nge(street)

    assert got_kong == got_kong_nge, f"\nspared_by_king_kong({street}): using sliding window max: {got_kong}, using NGE: {got_kong_nge}\n"
    got_godzilla = spared_by_godzilla(street)
    got = spared(street)

    assert got_kong == want_kong, f"\nspared_by_king_kong({street}): got: {got_kong}, want: {want_kong}\n"
    assert got_godzilla == want_godzilla, f"\nspared_by_godzilla({street}): got: {got_godzilla}, want: {want_godzilla}\n"
    assert got == want, f"\nspared({street}): got: {got}, want: {want}\n"

run_tests()
