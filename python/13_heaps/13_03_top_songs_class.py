# 13.3 - Top Songs Class
# Run: python3 13_03_top_songs_class.py

import heapq

class TopSongs:
  def __init__(self, k):
    self.k = k
    self.min_heap = []

  def register_plays(self, title, plays):
    heapq.heappush(self.min_heap, (plays, title))
    if len(self.min_heap) > self.k:
      heapq.heappop(self.min_heap)

  def top_k(self):
    top_songs = []
    for _, title in self.min_heap:
      top_songs.append(title)
    return top_songs


def run_tests():
  """Test TopSongs class"""
  # Example from the book
  s = TopSongs(3)
  s.register_plays("Boolean Rhapsody", 193)
  s.register_plays("Coding In The Deep", 146)
  result = s.top_k()
  assert set(result) == set([
      "Boolean Rhapsody",
      "Coding In The Deep"
  ]), f"Test failed for TopSongs with initial songs"

  s.register_plays("All About That Base Case", 291)
  s.register_plays("Here Comes The Bug", 223)
  s.register_plays("Oops! I Broke Prod Again", 274)
  s.register_plays("All the Single Brackets", 132)
  result = s.top_k()
  assert set(result) == set([
      "All About That Base Case",
      "Here Comes The Bug",
      "Oops! I Broke Prod Again"
  ]), f"Test failed for TopSongs after more songs"

  # Additional test cases
  # Test with fewer songs than k
  s = TopSongs(5)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 200)
  result = s.top_k()
  assert set(result) == set([
      "Song A",
      "Song B"
  ]), f"Test failed for TopSongs with fewer songs than k"

  # Test with exact k songs
  s = TopSongs(3)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 200)
  s.register_plays("Song C", 300)
  result = s.top_k()
  assert set(result) == set([
      "Song A",
      "Song B",
      "Song C"
  ]), f"Test failed for TopSongs with exact k songs"

  # Test with ties in play counts
  s = TopSongs(2)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 100)
  s.register_plays("Song C", 100)
  s.register_plays("Song D", 100)
  result = s.top_k()
  assert len(result) == 2, f"Test failed for TopSongs with ties in play counts"

run_tests()
