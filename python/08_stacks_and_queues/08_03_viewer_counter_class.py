# 8.3 - Viewer Counter Class
# Run: python3 08_03_viewer_counter_class.py

from collections import deque

class ViewerCounter:
  def __init__(self, window):
    self.queues = {"guest": deque(), "follower": deque(), "subscriber": deque()}
    self.window = window

  def join(self, t, v):
    self._remove_old_viewers(t)
    self.queues[v].append(t)

  def get_viewers(self, t, v):
    self._remove_old_viewers(t)
    return len(self.queues[v])

  def _remove_old_viewers(self, t):
    for queue in self.queues.values():
      while queue and queue[0] < t - self.window:
        queue.popleft()

class ViewerCounterOptimized:
  def __init__(self, window):
    self.queues = {"guest": deque(), "follower": deque(), "subscriber": deque()}
    self.window = window

  def join(self, t, v):
    self._remove_old_viewers(t)
    queue = self.queues[v]
    if queue and queue[-1][0] == t:
      queue[-1][1] += 1
    else:
      queue.append([t, 1])

  def get_viewers(self, t, v):
    self._remove_old_viewers(t)
    return sum(count for _, count in self.queues[v])

  def _remove_old_viewers(self, t):
    for queue in self.queues.values():
      while queue and queue[0][0] < t - self.window:
        queue.popleft()


def run_tests():
  # Test unoptimized version
  counter = ViewerCounter(10)
  counter.join(1, "subscriber")
  counter.join(1, "guest")
  counter.join(2, "follower")
  counter.join(2, "follower")
  counter.join(2, "follower")
  counter.join(3, "follower")
  assert counter.get_viewers(10, "subscriber") == 1
  assert counter.get_viewers(10, "guest") == 1
  assert counter.get_viewers(10, "follower") == 4
  assert counter.get_viewers(13, "follower") == 1

  # Test optimized version
  counter = ViewerCounterOptimized(10)
  counter.join(1, "subscriber")
  counter.join(1, "guest")
  counter.join(2, "follower")
  counter.join(2, "follower")
  counter.join(2, "follower")
  counter.join(3, "follower")
  assert counter.get_viewers(10, "subscriber") == 1
  assert counter.get_viewers(10, "guest") == 1
  assert counter.get_viewers(10, "follower") == 4
  assert counter.get_viewers(13, "follower") == 1

run_tests()
