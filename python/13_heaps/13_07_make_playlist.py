# 13.7 - Make Playlist
# Run: python3 13_07_make_playlist.py

import heapq
import math

def make_playlist_heap(songs):
  # Group songs by artist
  artist_to_songs = {}
  for song, artist in songs:
    artist_to_songs.setdefault(artist, []).append(song)

  # Negative length turns heapq's min-heap into a max-heap
  heap = [(-len(song_list), artist, song_list)
          for artist, song_list in artist_to_songs.items()]
  heapq.heapify(heap)

  res = []
  last_artist = None
  while heap:
    _, artist, song_list = heapq.heappop(heap)
    if artist != last_artist:
      res.append(song_list.pop())
      last_artist = artist
      if song_list:
        # If the artist has more songs, re-add it
        heapq.heappush(heap, (-len(song_list), artist, song_list))
    else:
      # We need to find a different artist
      if not heap:
        return []  # No valid solution
      _, artist2, song_list2 = heapq.heappop(heap)
      res.append(song_list2.pop())
      last_artist = artist2
      # Re-add the artists we popped
      if song_list2:
        heapq.heappush(heap, (-len(song_list2), artist2, song_list2))
      heapq.heappush(heap, (-len(song_list), artist, song_list))

  return res

def make_playlist_greedy(songs):
  if not songs:
    return []

  # Group songs by artist
  artist_to_songs = {}
  for song, artist in songs:
    if artist not in artist_to_songs:
      artist_to_songs[artist] = []
    artist_to_songs[artist].append(song)

  # Find the most popular artist
  most_popular_artist = max(artist_to_songs.keys(),
                            key=lambda artist: len(artist_to_songs[artist]))
  max_count = len(artist_to_songs[most_popular_artist])

  # Check if solution is possible
  if max_count > math.ceil(len(songs) / 2):
    return []

  # Place most popular artist's songs at even indices
  res = [None] * len(songs)
  index = 0
  for song in artist_to_songs[most_popular_artist]:
    res[index] = song
    index += 2

  # Continue filling even indices with other artists
  for artist, songs_list in artist_to_songs.items():
    if artist == most_popular_artist:
      continue
    for song in songs_list:
      if index >= len(songs):
        index = 1  # Wrap to odd indices
      res[index] = song
      index += 2

  return res


def run_tests():
  """Test make_playlist function"""

  def validate_solution(songs, got, expected_empty=False):
    """Returns error message or empty string if valid"""

    def get_artist_for_song(song_name):
      for s, artist in songs:
        if s == song_name:
          return artist
      return None

    if expected_empty:
      if got != []:
        return f"Expected empty result, got: {got}"
      return ""

    # Check length
    if len(got) != len(songs):
      return f"Expected length {len(songs)}, got length {len(got)}"

    # Check no consecutive songs by same artist
    for i in range(1, len(got)):
      got_artist = get_artist_for_song(got[i])
      prev_artist = get_artist_for_song(got[i - 1])
      if got_artist == prev_artist:
        return f"Consecutive songs by same artist '{got_artist}' at indices {i - 1} and {i}"

    # Check all songs are present
    got_songs = set(got)
    expected_songs = set(song for song, _ in songs)
    if got_songs != expected_songs:
      return f"Song mismatch. Got: {got_songs}, Expected: {expected_songs}"

    return ""

  test_cases = [
      # Example from the book
      ([
          ["Coding In The Deep", "A Dell"],
          ["Hello World", "A Dell"],
          ["Someone Like GNU", "A Dell"],
          ["Make You Read My Logs", "A Dell"],
          ["Hey Queue", "The Bugs"],
          ["Here Comes the Bug", "The Bugs"],
          ["Merge Together", "The Bugs"],
          ["Dirty Data", "Michael JSON"],
          ["Man in the Middle Attack", "Michael JSON"],
          ["Ring Of Firewall", "Johnny Cache"]
      ], False),

      # Test with no songs
      ([], False),

      # Test with one song
      ([["Single Song", "Solo Artist"]], False),

      # Test with two songs by different artists
      ([["Song A", "Artist 1"], ["Song B", "Artist 2"]], False),

      # Test with two songs by the same artist (impossible)
      ([["Song A", "Artist 1"], ["Song B", "Artist 1"]], True),

      # Test with more songs by one artist than ceiling(n/2)
      ([
          ["Song 1", "Artist 1"], ["Song 2", "Artist 1"], ["Song 3", "Artist 1"],
          ["Song 4", "Artist 2"]
      ], True),

      # Test with exactly ceiling(n/2) songs by one artist (should work)
      ([
          ["Song 1", "Artist 1"], ["Song 2", "Artist 1"], ["Song 3", "Artist 1"],
          ["Song 4", "Artist 2"], ["Song 5", "Artist 3"]
      ], False)
  ]

  for i, (songs, want_empty) in enumerate(test_cases):
    got1 = make_playlist_heap(songs)
    got2 = make_playlist_greedy(songs)
    error1 = validate_solution(songs, got1, want_empty)
    assert not error1, f"\nTest case {i + 1} failed: {error1}\nInput: {songs}\nGot: {got1}\n"
    error2 = validate_solution(songs, got2, want_empty)
    assert not error2, f"\nTest case {i + 1} failed: {error2}\nInput: {songs}\nGot: {got2}\n"

run_tests()
