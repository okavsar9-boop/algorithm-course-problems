# 13.2 - K Most Played
# Run: python3 13_02_k_most_played.py

import heapq
import random

def k_most_played_sort(songs, k):
  # Sort by plays in descending order, then take first k titles
  sorted_songs = sorted(songs, key=lambda x: x[1], reverse=True)
  return [song[0] for song in sorted_songs[:k]]

def k_most_played_max_heap(songs, k):
  # Convert to max-heap format (negate plays for max-heap behavior)
  max_heap = [(-song[1], song[0]) for song in songs]
  heapq.heapify(max_heap)  # O(n)

  res = []
  for _ in range(min(k, len(songs))):  # Pop k times or until empty
    song = heapq.heappop(max_heap)
    res.append(song[1])
  return res

def k_most_played_min_heap(songs, k):
  min_heap = []
  for song in songs:
    heapq.heappush(min_heap, (song[1], song[0]))
    if len(min_heap) > k:
      heapq.heappop(min_heap)
  return [song[1] for song in min_heap]

def quickselect(nums, k):
  """Returns the k'th smallest element"""
  if len(nums) == 1:
    return nums[0]

  pivot = random.choice(nums)
  larger, equal, smaller = [], [], []

  for x in nums:
    if x < pivot:
      smaller.append(x)
    elif x == pivot:
      equal.append(x)
    else:
      larger.append(x)

  S, E = len(smaller), len(equal)
  if k <= S:
    return quickselect(smaller, k)
  elif k <= S + E:
    return pivot
  else:
    return quickselect(larger, k - S - E)

def k_most_played_quickselect(songs, k):
  if not songs:
    return []

  if k >= len(songs):
    return [song[0] for song in songs]

  # Extract play counts
  play_counts = [song[1] for song in songs]

  # Find the kth largest play count
  kth_largest_plays = quickselect(play_counts, len(songs) - k)

  # Collect all songs with play counts > kth_largest_plays
  res = []
  for song in songs:
    if song[1] > kth_largest_plays:
      res.append(song[0])  # Only append the title

  # Add songs with exactly kth_largest_plays until we have k songs
  remaining = k - len(res)
  if remaining > 0:
    for song in songs:
      if song[1] == kth_largest_plays:
        res.append(song[0])
        remaining -= 1
        if remaining == 0:
          break
  return res


def run_tests():
  test_cases = [
      # Example from the book
      ([["All the Single Brackets", 132],
        ["Oops! I Broke Prod Again", 274],
        ["Coding In The Deep", 146],
        ["Boolean Rhapsody", 193],
        ["Here Comes The Bug", 291],
        ["All About That Base Case", 291]], 3,
       ["All About That Base Case", "Here Comes The Bug", "Oops! I Broke Prod Again"]),

      # Test with fewer songs than k
      ([["Song A", 100], ["Song B", 200]], 5,
       ["Song A", "Song B"]),

      # Test with exact k songs
      ([["Song A", 100], ["Song B", 200], ["Song C", 300]], 3,
       ["Song A", "Song B", "Song C"]),

      # Test with k = 1
      ([["Song A", 100], ["Song B", 200], ["Song C", 300]], 1,
       ["Song C"]),

      # Test with ties in play counts
      ([["Song A", 100], ["Song B", 100], ["Song C", 200], ["Song D", 200]], 2,
       ["Song C", "Song D"]),

      # Test empty input
      ([], 3, []),
  ]

  # Test all implementations
  implementations = [
      ("sort", k_most_played_sort),
      ("max_heap", k_most_played_max_heap),
      ("min_heap", k_most_played_min_heap),
      ("quickselect", k_most_played_quickselect),
  ]

  for solution_name, solution_func in implementations:
    for songs, k, want in test_cases:
      got = solution_func(songs, k)
      got.sort()
      want.sort()
      assert got == want, f"\n{solution_name}({songs}, {k}): got: {got}, want: {want}\n"

    # Also test tie breaking, any possible result is accepted
    got = solution_func([["Song A", 100], ["Song B", 100]], 1)
    assert got == ["Song A"] or got == [
        "Song B"], f"\n{solution_name}: got: {got}, want: ['Song A'] or ['Song B']\n"

run_tests()
