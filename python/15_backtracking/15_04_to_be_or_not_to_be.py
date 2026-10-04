# 15.4 - To Be or Not to Be
# Run: python3 15_04_to_be_or_not_to_be.py

def shakespearify(sentence):
  words = sentence.split()
  res = []
  current_sentence = []

  def visit(i):
    if i == len(words):
      res.append(' '.join(current_sentence))
      return
    # Choice 1: include the word
    current_sentence.append(words[i])
    visit(i + 1)
    current_sentence.pop()  # Cleanup work: undo choice 1
    # Choice 2: exclude the word
    visit(i + 1)

  visit(0)
  return res


def run_tests():
  tests = [
    # Example from the book
    ("I love dogs", ["", "I", "love", "dogs", "I love", "I dogs", "love dogs", "I love dogs"]),
    # Edge case - empty sentence
    ("", [""]),
    # Single word
    ("hello", ["", "hello"]),
    # Two words
    ("hello world", ["", "hello", "world", "hello world"]),
  ]
  for sentence, want in tests:
    got = shakespearify(sentence)
    got.sort()
    want.sort()
    assert got == want, f"\nshakespearify({sentence}): got: {got}, want: {want}\n"

run_tests()
