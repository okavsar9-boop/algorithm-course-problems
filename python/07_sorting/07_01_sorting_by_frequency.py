# 7.1 - Sorting by Frequency
# Run: python3 07_01_sorting_by_frequency.py

def letter_occurrences(word):
  letter_to_count = dict()
  for c in word:
    if c not in letter_to_count:
      letter_to_count[c] = 0
    letter_to_count[c] += 1

  tuples = []
  for letter, count in letter_to_count.items():
    tuples.append((letter, count))
  tuples.sort(key=lambda x: (-x[1], x[0]))
  res = []
  for letter, _ in tuples:
    res.append(letter)
  return res

def letter_occurrences_lambda(word):
  letter_to_count = dict()
  res = []
  for c in word:
    if c not in letter_to_count:
      letter_to_count[c] = 0
      res.append(c)
    letter_to_count[c] += 1
  res.sort(key=lambda x: (-letter_to_count[x], x))
  return res


def run_tests():
  tests = [
      # Example from the book
      ("supercalifragilisticexpialidocious",
       ['i', 'a', 'c', 'l', 's', 'e', 'o', 'p', 'r', 'u', 'd', 'f', 'g', 't', 'x']),
      # Edge case - empty string
      ("", []),
      # Edge case - single character
      ("a", ["a"]),
      # Edge case - all same frequency
      ("abc", ["a", "b", "c"]),
      # Multiple frequencies with ties
      ("aabbbcccc", ["c", "b", "a"]),
      # All same character
      ("zzzzz", ["z"]),
      # Alternating characters
      ("ababab", ["a", "b"]),
      # Reverse alphabetical order but same frequency
      ("zyxwv", ["v", "w", "x", "y", "z"]),
      # Long string with many frequencies
      ("aaaaabbbbbbbcccccccccdddddddddddeeeeeeeeeeee",
          ["e", "d", "c", "b", "a"]),
  ]
  for word, want in tests:
    got1 = letter_occurrences(word)
    assert got1 == want, (
        f"\nletter_occurrences({word}): got: {got1}, want: {want}\n")
    got2 = letter_occurrences_lambda(word)
    assert got2 == want, (
        f"\nletter_occurrences_lambda({word}): got: {got2}, want: {want}\n")

run_tests()
