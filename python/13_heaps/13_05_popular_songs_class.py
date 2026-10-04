# 13.5 - Popular Songs Class
# Run: python3 13_05_popular_songs_class.py

import heapq

class PopularSongs:
  def __init__(self):
    # Max-heap for the lower half (use negated values)
    self.lower_max_heap = []
    # Min-heap for the upper half
    self.upper_min_heap = []
    self.play_counts = {}

  def register_plays(self, title, plays):
    self.play_counts[title] = plays
    if not self.upper_min_heap or plays >= self.upper_min_heap[0]:
      heapq.heappush(self.upper_min_heap, plays)
    else:
      heapq.heappush(self.lower_max_heap, -plays)

    # Distribute elements if they are off by more than one.
    if len(self.lower_max_heap) > len(self.upper_min_heap):
      heapq.heappush(self.upper_min_heap, -heapq.heappop(self.lower_max_heap))
    elif len(self.upper_min_heap) > len(self.lower_max_heap) + 1:
      heapq.heappush(self.lower_max_heap, -heapq.heappop(self.upper_min_heap))

  def is_popular(self, title):
    if title not in self.play_counts:
      return False
    if len(self.lower_max_heap) == len(self.upper_min_heap):
      median = (self.upper_min_heap[0] + (-self.lower_max_heap[0])) / 2
    else:
      median = self.upper_min_heap[0]
    return self.play_counts[title] > median


def run_tests():
  """Test PopularSongs class"""
  # Example from the book
  p = PopularSongs()
  p.register_plays("Boolean Rhapsody", 193)
  assert not p.is_popular("Boolean Rhapsody"), "Fail: Boolean Rhapsody"
  p.register_plays("Coding In The Deep", 140)
  p.register_plays("All the Single Brackets", 132)
  assert p.is_popular("Boolean Rhapsody"), "Fail: Boolean Rhapsody (2)"
  assert not p.is_popular("Coding In The Deep"), "Fail: Coding In The Deep"
  assert not p.is_popular(
      "All the Single Brackets"), "Fail: All the Single Brackets"

  p.register_plays("All About That Base Case", 291)
  p.register_plays("Oops! I Broke Prod Again", 274)
  p.register_plays("Here Comes The Bug", 223)
  assert not p.is_popular(
      "Boolean Rhapsody"), "Fail: Boolean Rhapsody after more plays"
  assert p.is_popular("Here Comes The Bug"), "Fail: Here Comes The Bug"

  # Additional test cases
  # Test with no songs
  p = PopularSongs()
  assert not p.is_popular("Nonexistent Song"), "Fail: nonexistent song"

  # Test with one song
  p.register_plays("Single Song", 100)
  assert not p.is_popular(
      "Single Song"), "Fail: single song should not be popular"

  # Test with two songs
  p.register_plays("Song A", 100)
  p.register_plays("Song B", 200)
  assert not p.is_popular("Song A"), "Fail: Song A"
  assert p.is_popular("Song B"), "Fail: Song B"

  # Test with three songs
  p.register_plays("Song C", 150)
  assert not p.is_popular("Song A"), "Fail: Song A with three songs"
  assert p.is_popular("Song B"), "Fail: Song B with three songs"
  assert p.is_popular("Song C"), "Fail: Song C with three songs"

run_tests()
