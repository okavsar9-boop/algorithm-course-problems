# 14.13 - Longest Repeated Substring
# Run: python3 14_13_longest_repeated_substring.py

def longest_repeated_substring(s):
  if not s:
    return ""

  LARGE_PRIME = 10**9 + 7

  def power(x):
    # Returns 128^x % LARGE_PRIME.
    res = 1
    for _ in range(x):
      res = (res * 128) % LARGE_PRIME
    return res

  def str_to_hash(t):
    # Returns the hash of the string t.
    # t is encoded as a number in base 128, where each ASCII character is a
    # digit.
    # We use the modulo to keep numbers within a reasonable range.
    res = 0
    for c in t:
      res = (res * 128 + ord(c)) % LARGE_PRIME
    return res

  def next_hash(i, k, current_hash, first_power):
    # Assumes current_hash is hash of s[i:i+k].
    # Assumes that first_power is 128^(k-1) % LARGE_PRIME.
    # Returns the hash of the next substring of s of length k: s[i+1:i+k+1].
    res = current_hash
    res = (res - ord(s[i]) * first_power) % LARGE_PRIME
    res = (res * 128) % LARGE_PRIME
    return (res + ord(s[i + k])) % LARGE_PRIME

  def repeated_substring_index(k):
    # Incrementally computes the hash of every substring of length k.
    # Tracks a map from hashes to indices.
    # For each hash, checks if it has seen it before.
    # In case of a hash match, the actual substrings are compared char by char.
    # Chances of a hash collision are tiny, so if we have a hash match, we
    # probably found a repeated substring.
    if k == 0:
      return 0
    if k > len(s):
      return -1
    first_power = power(k - 1)

    h = str_to_hash(s[:k])
    seen = {h: [0]}
    for i in range(len(s) - k):
      h = next_hash(i, k, h, first_power)
      start = i + 1
      if h in seen:
        # Hash match!
        for prev in seen[h]:
          if s[prev:prev + k] == s[start:start + k]:
            return start
      seen.setdefault(h, []).append(start)
    return -1

  # Before region: there are repeated substrings of length k.
  # After region: there are no repeated substrings of length k.
  def is_before(k):
    return repeated_substring_index(k) >= 0

  # Binary search over the length of the longest repeated substring.
  l, r = 0, len(s)
  while r - l > 1:
    mid = l + (r - l) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  if l == 0:
    return ""

  idx = repeated_substring_index(l)
  if idx >= 0:
    return s[idx:idx + l]
  return ""


def run_tests():
  tests = [
      # Book examples
      ("murmur", "mur"),
      ("murmurmur", "murmur"),
      ("aaaa", "aaa"),
      # Edge case - empty string
      ("", ""),
      # Edge case - single character
      ("a", ""),
      # Edge case - no repeated substring
      ("abcd", ""),
      # Other examples
      ("abababab", "ababab"),
      ("abcdefghijkdlmno", "d"),
      ("longestrepeatedsubstring", "str"),
  ]
  for s, want in tests:
    got = longest_repeated_substring(s)
    # For this problem, there might be multiple valid answers
    # We only check if the length is correct and if it appears twice
    if want:
      assert len(got) == len(want), (
          f"\nlongest_repeated_substring({s}): got: {got}, want {want}\n"
      )
      l = len(got)
      count = 0
      for i in range(len(s) - l + 1):
        if s[i:i + l] == got:
          count += 1
      assert count >= 2, (
          f"\nlongest_repeated_substring({s}): result {got} does not appear twice\n"
      )
    else:
      assert got == "", (
          f"\nlongest_repeated_substring({s}): got: {got}, want empty string\n"
      )

run_tests()
