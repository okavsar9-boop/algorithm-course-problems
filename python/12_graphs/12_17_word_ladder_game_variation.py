# 12.17 - Word Ladder Game Variation
# Run: python3 12_17_word_ladder_game_variation.py

def can_transform(word1, word2):
  """Returns whether word1 can be transformed into word2 by adding/removing one letter."""
  if abs(len(word1) - len(word2)) != 1:
    return False

  # Make word1 the shorter word
  if len(word1) > len(word2):
    word1, word2 = word2, word1

  # Try removing each letter from word2
  for i in range(len(word2)):
    if word2[:i] + word2[i + 1:] == word1:
      return True
  return False

def build_graph(words, l1, l2):
  """Builds a graph connecting words of lengths l1 and l2."""
  graph = {}

  # Initialize nodes
  for word in words:
    if len(word) in (l1, l2):
      graph[word] = []

  # Add edges
  for word1 in graph:
    for word2 in graph:
      if word1 != word2 and can_transform(word1, word2):
        graph[word1].append(word2)

  return graph

def has_path(graph, start, end, visited=None):
  """Returns whether there is a path from start to end alternating word lengths."""
  if visited is None:
    visited = set()

  # Don't allow paths to the same word
  if start == end and len(visited) == 0:
    return False

  if start == end and len(visited) > 0:
    return True

  visited.add(start)
  for nbr in graph[start]:
    if nbr not in visited:
      if has_path(graph, nbr, end, visited):
        return True
  return False

def word_ladder_game(word1, word2, words):
  # Try path using words of length l and l+1
  l = len(word1)
  graph1 = build_graph(words, l, l + 1)
  if word2 in graph1 and has_path(graph1, word1, word2):
    return True

  # Try path using words of length l and l-1
  graph2 = build_graph(words, l, l - 1)
  if word2 in graph2 and has_path(graph2, word1, word2):
    return True

  return False


def run_tests():
  tests = [
      # Example 1 from the book
      (
          "leap",
          "hop",
          [
              "fare", "hug", "car", "vibes", "once", "sop", "far", "ounce", "slap",
              "sap", "cart", "hung", "art", "shop", "fart", "lap", "soap", "are",
              "hop", "care", "leap", "bounce", "beyond", "cracking"
          ],
          True
      ),
      # Example 2 from the book
      (
          "car",
          "cart",
          [
              "fare", "hug", "car", "vibes", "once", "sop", "far", "ounce", "slap",
              "sap", "cart", "hung", "art", "shop", "fart", "lap", "soap", "are",
              "hop", "care", "leap", "bounce", "beyond", "cracking"
          ],
          True
      ),
      # Invalid - double removal
      (
          "bounce",
          "once",
          ["bounce", "ounce", "once"],
          False
      ),
      # Invalid - reordered letters
      (
          "car",
          "race",
          ["car", "race"],
          False
      ),
      # No path exists
      (
          "cat",
          "dog",
          ["cat", "cot", "dot", "dog"],
          False
      )
  ]

  for word1, word2, words, want in tests:
    got = word_ladder_game(word1, word2, words)
    assert got == want, \
        f"\nword_ladder_game({word1}, {word2}, {words}): got: {            got}, want: {want}\n"

run_tests()
