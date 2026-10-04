# 3.18 - Shift Word to Back
# Run: python3 03_18_shift_word_to_back.py

def move_word(arr, word):
  seeker, writer = 0, 0
  i = 0
  while seeker < len(arr):
    if i < len(word) and arr[seeker] == word[i]:
      seeker += 1
      i += 1
    else:
      arr[writer] = arr[seeker]
      seeker += 1
      writer += 1
  for c in word:
    arr[writer] = c
    writer += 1


def run_tests():
  tests = [ # Example 1 from the book
    (list("seekerandwriter"), "edit", list("sekeranwreredit")), # Example 2 from the book
    (list("bacb"), "ab", list("bcab")), # Example 3 from the book
    (list("babc"), "b", list("abcb")), # Additional test cases
    ([], "", []),
    (list("a"), "a", list("a")),
    (list("abc"), "", list("abc")),
    (list("hello"), "ho", list("ellho")),
    (list("abcabc"), "abc", list("abcabc")),
    ]
  for arr, word, want in tests:
    arr_copy = arr.copy() # Make a copy since move_word modifies in place
    move_word(arr_copy, word)
    assert arr_copy == want, f"\nmove_word({arr}, {word}): got: {arr_copy}, want: {want}\n"

run_tests()
