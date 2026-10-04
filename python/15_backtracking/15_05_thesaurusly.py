# 15.5 - Thesaurusly
# Run: python3 15_05_thesaurusly.py

def generate_sentences(sentence, synonyms):
  words = sentence.split()
  res = []
  cur_sentence = []

  def visit(i):
    if i == len(words):
      res.append(" ".join(cur_sentence))
      return

    if words[i] not in synonyms:
      choices = [words[i]]
    else:
      choices = synonyms.get(words[i], [])

    for choice in choices:
      cur_sentence.append(choice)
      visit(i + 1)
      cur_sentence.pop() # Undo change.

  visit(0)
  return res


def run_tests():
  tests = [
    # Example from the book
    ("one does not simply walk into mordor", {
      "walk": ["stroll", "hike", "wander"],
      "simply": ["just", "merely"]
    }, [
      "one does not just stroll into mordor",
      "one does not just hike into mordor",
      "one does not just wander into mordor",
      "one does not merely stroll into mordor",
      "one does not merely hike into mordor",
      "one does not merely wander into mordor"
    ]),
    # Edge case - no synonyms
    ("hello world", {}, ["hello world"]),
    # Single word with synonyms
    ("walk", {"walk": ["stroll", "hike"]}, ["stroll", "hike"]),
    # Multiple words, some with synonyms
    ("I walk to the park", {"walk": ["stroll", "hike"]}, [
      "I stroll to the park",
      "I hike to the park"
    ]),
  ]
  for sentence, synonyms, want in tests:
    got = generate_sentences(sentence, synonyms)
    got.sort()
    want.sort()
    assert got == want, f"\ngenerate_sentences({sentence}, {synonyms}): got: {got}, want: {want}\n"

run_tests()
