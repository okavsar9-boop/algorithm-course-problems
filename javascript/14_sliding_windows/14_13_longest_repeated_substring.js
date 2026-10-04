// 14.13 - Longest Repeated Substring
// Run: node 14_13_longest_repeated_substring.js

function longestRepeatedSubstring(s) {
  if (!s) {
    return "";
  }

  const LARGE_PRIME = 10 ** 9 + 7;

  function power(x) {
    // Returns 128^x % LARGE_PRIME.
    let res = 1;
    for (let i = 0; i < x; i++) {
      res = (res * 128) % LARGE_PRIME;
    }
    return res;
  }

  function strToHash(t) {
    // Returns the hash of the string t.
    // t is encoded as a number in base 128, where each ASCII character is a
    // digit.
    // We use the modulo to keep numbers within a reasonable range.
    let res = 0;
    for (const c of t) {
      res = (res * 128 + c.charCodeAt(0)) % LARGE_PRIME;
    }
    return res;
  }

  function nextHash(i, k, currentHash, firstPower) {
    // Assumes currentHash is hash of s[i:i+k].
    // Assumes that firstPower is 128^(k-1) % LARGE_PRIME.
    // Returns the hash of the next substring of s of length k: s[i+1:i+k+1].
    let res = currentHash;
    const toRemove = (s.charCodeAt(i) * firstPower) % LARGE_PRIME;
    res = (res - toRemove + LARGE_PRIME) % LARGE_PRIME;
    res = (res * 128) % LARGE_PRIME;
    return (res + s.charCodeAt(i + k)) % LARGE_PRIME;
  }

  function repeatedSubstringIndex(k) {
    // Incrementally computes the hash of every substring of length k.
    // Tracks a map from hashes to indices.
    // For each hash, checks if it has seen it before.
    // In case of a hash match, the actual substrings are compared char by char.
    // Chances of a hash collision are tiny, so if we have a hash match, we
    // probably found a repeated substring.
    if (k === 0) {
      return 0;
    }
    if (k > s.length) {
      return -1;
    }
    const firstPower = power(k - 1);

    let h = strToHash(s.slice(0, k));
    const seen = new Map();
    seen.set(h, [0]);
    for (let i = 0; i < s.length - k; i++) {
      h = nextHash(i, k, h, firstPower);
      const start = i + 1;
      if (seen.has(h)) {
        // Hash match!
        for (const prev of seen.get(h)) {
          if (s.slice(prev, prev + k) === s.slice(start, start + k)) {
            return start;
          }
        }
      }
      if (!seen.has(h)) {
        seen.set(h, []);
      }
      seen.get(h).push(start);
    }
    return -1;
  }

  // Before region: there are repeated substrings of length k.
  // After region: there are no repeated substrings of length k.
  function isBefore(k) {
    return repeatedSubstringIndex(k) >= 0;
  }

  // Binary search over the length of the longest repeated substring.
  let l = 0;
  let r = s.length;
  while (r - l > 1) {
    const mid = l + Math.floor((r - l) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  if (l === 0) {
    return "";
  }

  const idx = repeatedSubstringIndex(l);
  if (idx >= 0) {
    return s.slice(idx, idx + l);
  }
  return "";
}


function runTests() {
  const tests = [
    // Book examples
    ["murmur", "mur"],
    ["murmurmur", "murmur"],
    ["aaaa", "aaa"],
    // Edge case - empty string
    ["", ""],
    // Edge case - single character
    ["a", ""],
    // Edge case - no repeated substring
    ["abcd", ""],
    // Other examples
    ["abababab", "ababab"],
    ["abcdefghijkdlmno", "d"],
    ["longestrepeatedsubstring", "str"],
  ];
  for (const [s, want] of tests) {
    const got = longestRepeatedSubstring(s);
    // For this problem, there might be multiple valid answers
    // We only check if the length is correct and if it appears twice
    if (want) {
      if (got.length !== want.length) {
        throw new Error(
          `\nlongestRepeatedSubstring(${s}): got: ${got}, want ${want}\n`,
        );
      }
      const l = got.length;
      let count = 0;
      for (let i = 0; i <= s.length - l; i++) {
        if (s.slice(i, i + l) === got) {
          count++;
        }
      }
      if (count < 2) {
        throw new Error(
          `\nlongestRepeatedSubstring(${s}): result ${got} does not appear twice\n`,
        );
      }
    } else {
      if (got !== "") {
        throw new Error(
          `\nlongestRepeatedSubstring(${s}): got: ${got}, want empty string\n`,
        );
      }
    }
  }
}

runTests();
