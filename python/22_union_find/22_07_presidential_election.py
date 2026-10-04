# 22.7 - Presidential Election
# Run: python3 22_07_presidential_election.py

import heapq

def winner(candidates, votes):
  total_votes = sum(votes)
  # Create min heap entries with (votes, candidate) tuples
  # Note: We negate candidate name to simulate max heap for alphabetical order
  min_heap = [(votes[i], -ord(candidates[i][0]), candidates[i])
              for i in range(len(candidates))]
  heapq.heapify(min_heap)

  for i in range(len(candidates)):
    if votes[i] > total_votes / 2:
      return candidates[i]

  while True:
    smallest = heapq.heappop(min_heap)
    second_smallest = heapq.heappop(min_heap)
    coalition_votes = smallest[0] + second_smallest[0]
    coalition_candidate = second_smallest[2]

    # Handle ties
    while min_heap and min_heap[0][0] == second_smallest[0]:
      next_party = heapq.heappop(min_heap)
      coalition_votes += next_party[0]
      coalition_candidate = min(coalition_candidate, next_party[2])

    if coalition_votes > total_votes / 2:
      return coalition_candidate

    heapq.heappush(min_heap, (coalition_votes, -ord(coalition_candidate[0]),
                              coalition_candidate))


def run_tests():
  tests = [
      # Example from the book
      (["Ale", "Bloop", "Chip", "Dart", "Zing"], [10, 20, 30, 15, 25], "Dart"),

      # Two parties
      (["Alice", "Bob"], [40, 50], "Bob"),
      (["Alice", "Bob"], [60, 60], "Alice"),

      # Single party
      (["Alice"], [10], "Alice"),

      # Three way tie for second lowest
      (["A", "E", "C", "D"], [20, 5, 5, 5], "A"),
      (["A", "E", "C", "D"], [10, 5, 5, 5], "C"),

      # All parties have equal votes
      (["X", "Y", "Z"], [10, 10, 10], "X")
  ]

  for candidates, votes, want in tests:
    got = winner(candidates, votes)
    assert got == want, f"\nwinner({candidates}, {votes}): got: {        got}, want: {want}\n"

run_tests()
