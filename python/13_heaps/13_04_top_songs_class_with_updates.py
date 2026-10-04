# 13.4 - Top Songs Class With Updates
# Run: python3 13_04_top_songs_class_with_updates.py

import heapq

class TopSongs:
  def __init__(self, k):
    self.k = k
    # Stores (-plays, title) tuples for max-heap behavior
    self.max_heap = []
    self.total_plays = {}

  def register_plays(self, title, plays):
    new_total_plays = plays
    if title in self.total_plays:
      new_total_plays += self.total_plays[title]
    self.total_plays[title] = new_total_plays
    heapq.heappush(self.max_heap, (-new_total_plays, title))

  def top_k(self):
    top_songs = []
    while len(top_songs) < self.k and self.max_heap:
      neg_plays, title = heapq.heappop(self.max_heap)
      plays = -neg_plays
      if self.total_plays[title] == plays:  # Not stale
        top_songs.append(title)

    # Restore the max-heap
    for title in top_songs:
      heapq.heappush(self.max_heap, (-self.total_plays[title], title))
    return top_songs


def run_tests():
  """Test TopSongs class with updates"""
  # Example from the book
  s = TopSongs(3)
  s.register_plays("Boolean Rhapsody", 100)
  s.register_plays("Boolean Rhapsody", 193)  # Total 293
  s.register_plays("Coding In The Deep", 75)
  s.register_plays("Coding In The Deep", 75)  # Total 150
  s.register_plays("All About That Base Case", 200)
  s.register_plays("All About That Base Case", 90)  # Total 290
  s.register_plays("All About That Base Case", 1)   # Total 291
  s.register_plays("Here Comes The Bug", 223)
  s.register_plays("Oops! I Broke Prod Again", 274)
  s.register_plays("All the Single Brackets", 132)
  got = s.top_k()
  want = ["All About That Base Case",
          "Boolean Rhapsody", "Oops! I Broke Prod Again"]
  assert set(got) == set(want), f"\ntop_k(): got: {got}, want: {want}\n"

  # Additional test cases
  # Test with fewer songs than k
  s = TopSongs(5)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 200)
  got = s.top_k()
  want = ["Song A", "Song B"]
  assert set(got) == set(want), f"\ntop_k() with fewer songs than k: got: {      got}, want: {want}\n"

  # Test with exact k songs
  s = TopSongs(3)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 200)
  s.register_plays("Song C", 300)
  got = s.top_k()
  want = ["Song A", "Song B", "Song C"]
  assert set(got) == set(want), f"\ntop_k() with exactly k songs: got: {      got}, want: {want}\n"

  # Test with ties in play counts
  s = TopSongs(2)
  s.register_plays("Song A", 100)
  s.register_plays("Song B", 100)
  s.register_plays("Song C", 100)
  s.register_plays("Song D", 100)
  got = s.top_k()
  assert len(got) == 2, f"\ntop_k() with tied play counts: got length {      len(got)}, want length 2\n"

run_tests()
