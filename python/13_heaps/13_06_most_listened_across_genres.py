# 13.6 - Most Listened Across Genres
# Run: python3 13_06_most_listened_across_genres.py

import heapq

def top_k_across_genres(genres, k):
  # Use negative plays for max heap behavior.
  initial_elems = []  # (-plays, genre_index, song_index) tuples.
  for genre_index, song_list in enumerate(genres):
    plays = song_list[0][1]
    heapq.heappush(initial_elems, (-plays, genre_index, 0))

  top_k = []
  while len(top_k) < k and initial_elems:
    plays, genre_index, song_index = heapq.heappop(initial_elems)
    song_name = genres[genre_index][song_index][0]
    top_k.append(song_name)

    song_index += 1
    if song_index < len(genres[genre_index]):
      plays = genres[genre_index][song_index][1]
      heapq.heappush(initial_elems, (-plays, genre_index, song_index))

  return top_k


def run_tests():
  """Test top_k_across_genres function"""
  tests = [
      # Example from the book
      {
          "genres": [
              [
                  ["Coding In The Deep", 123],
                  ["Someone Like GNU", 99],
                  ["Hello World", 98]
              ],
              [
                  ["Ring Of Firewalls", 217]
              ],
              [
                  ["Boolean Rhapsody", 184],
                  ["Merge Together", 119],
                  ["Hey Queue", 102]
              ]
          ],
          "k": 5,
          "want": ["Ring Of Firewalls", "Boolean Rhapsody", "Coding In The Deep", "Merge Together", "Hey Queue"]
      },

      # Test with fewer songs than k
      {
          "genres": [
              [["Song A", 100]],
              [["Song B", 200]]
          ],
          "k": 5,
          "want": ["Song B", "Song A"]
      },

      # Test with exact k songs
      {
          "genres": [
              [["Song A", 100]],
              [["Song B", 200]],
              [["Song C", 300]]
          ],
          "k": 3,
          "want": ["Song C", "Song B", "Song A"]
      },

      # Test with ties in play counts
      {
          "genres": [
              [["Song A", 100]],
              [["Song B", 100]],
              [["Song C", 100]],
              [["Song D", 100]]
          ],
          "k": 2,
          "want_length": 2
      },

      # Test with empty genres
      {
          "genres": [],
          "k": 3,
          "want": []
      },

      # Test with k=1
      {
          "genres": [
              [["Song A", 50], ["Song B", 30]],
              [["Song C", 100], ["Song D", 80]],
              [["Song E", 75]]
          ],
          "k": 1,
          "want": ["Song C"]
      },

      # Test with descending play counts within genres
      {
          "genres": [
              [["Song A", 300], ["Song B", 200], ["Song C", 100]],
              [["Song D", 250], ["Song E", 150], ["Song F", 50]]
          ],
          "k": 4,
          "want": ["Song A", "Song D", "Song B", "Song E"]
      }
  ]

  for test in tests:
    genres = test["genres"]
    k = test["k"]
    got = top_k_across_genres(genres, k)

    if "want_length" in test:
      assert len(
          got) == test["want_length"], f"\ntop_k_across_genres() with tied play counts: got length {len(got)}, want length {test['want_length']}\n"
    else:
      want = test["want"]
      assert got == want, f"\ntop_k_across_genres(): got: {got}, want: {want}\n"

run_tests()
