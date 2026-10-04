// 14.13 - Longest Repeated Substring
// Run: javac P14_13_LongestRepeatedSubstring.java && java P14_13_LongestRepeatedSubstring

import java.util.*;
import java.util.function.*;

class LongestRepeatedSubstring {
  private static final long LARGE_PRIME = 1000000007;

  private long power(int x) {
    // Returns 128^x % LARGE_PRIME.
    long res = 1;
    for (int i = 0; i < x; i++) {
      res = (res * 128) % LARGE_PRIME;
    }
    return res;
  }

  private long strToHash(String t) {
    // Returns the hash of the string t.
    // t is encoded as a number in base 128, where each ASCII character is a
    // digit.
    // We use the modulo to keep numbers within a reasonable range.
    long res = 0;
    for (char c : t.toCharArray()) {
      res = (res * 128 + (int) c) % LARGE_PRIME;
    }
    return res;
  }

  private long nextHash(String s, int i, int k, long currentHash,
  long firstPower) {
    // Assumes currentHash is hash of s[i:i+k].
    // Assumes that firstPower is 128^(k-1) % LARGE_PRIME.
    // Returns the hash of the next substring of s of length k: s[i+1:i+k+1].
    long res = currentHash;
    long toRemove = ((long) s.charAt(i) * firstPower) % LARGE_PRIME;
    res = (res - toRemove + LARGE_PRIME) % LARGE_PRIME;
    res = (res * 128) % LARGE_PRIME;
    return (res + (long) s.charAt(i + k)) % LARGE_PRIME;
  }

  private int repeatedSubstringIndex(String s, int k) {
    // Incrementally computes the hash of every substring of length k.
    // Tracks a map from hashes to indices.
    // For each hash, checks if it has seen it before.
    // In case of a hash match, the actual substrings are compared char by char.
    // Chances of a hash collision are tiny, so if we have a hash match, we
    // probably found a repeated substring.
    if (k == 0) {
      return 0;
    }
    if (k > s.length()) {
      return -1;
    }
    long firstPower = power(k - 1);

    long h = strToHash(s.substring(0, k));
    Map<Long, List<Integer>> seen = new HashMap<>();
    seen.put(h, new ArrayList<>());
    seen.get(h).add(0);
    for (int i = 0; i < s.length() - k; i++) {
      h = nextHash(s, i, k, h, firstPower);
      int start = i + 1;
      if (seen.containsKey(h)) {
        // Hash match!
        for (int prev : seen.get(h)) {
          if (s.substring(prev, prev + k)
          .equals(s.substring(start, start + k))) {
            return start;
          }
        }
      }
      seen.putIfAbsent(h, new ArrayList<>());
      seen.get(h).add(start);
    }
    return -1;
  }

  public String solve(String s) {
    if (s.isEmpty()) {
      return "";
    }

    // Before region: there are repeated substrings of length k.
    // After region: there are no repeated substrings of length k.
    // Binary search over the length of the longest repeated substring.
    int l = 0, r = s.length();
    while (r - l > 1) {
      int mid = l + (r - l) / 2;
      if (repeatedSubstringIndex(s, mid) >= 0) {
        l = mid;
      } else {
        r = mid;
      }
    }

    if (l == 0) {
      return "";
    }

    int idx = repeatedSubstringIndex(s, l);
    if (idx >= 0) {
      return s.substring(idx, idx + l);
    }
    return "";
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Book examples
        { "murmur", "mur" },
        { "murmurmur", "murmur" },
        { "aaaa", "aaa" },
        // Edge case - empty string
        { "", "" },
        // Edge case - single character
        { "a", "" },
        // Edge case - no repeated substring
        { "abcd", "" },
        // Other examples
        { "abababab", "ababab" },
        { "abcdefghijkdlmno", "d" },
        { "longestrepeatedsubstring", "str" },
    };

    LongestRepeatedSubstring solution = new LongestRepeatedSubstring();
    for (Object[] test : tests) {
      String s = (String) test[0];
      String want = (String) test[1];
      String got = solution.solve(s);
      // For this problem, there might be multiple valid answers
      // We only check if the length is correct and if it appears twice
      if (!want.isEmpty()) {
        if (got.length() != want.length()) {
          throw new RuntimeException(String.format(
              "\nsolve(%s): got: %s, want %s\n", s, got, want));
        }
        int l = got.length();
        int count = 0;
        for (int i = 0; i <= s.length() - l; i++) {
          if (s.substring(i, i + l).equals(got)) {
            count++;
          }
        }
        if (count < 2) {
          throw new RuntimeException(String.format(
              "\nsolve(%s): result %s does not appear twice\n", s, got));
        }
      } else if (!got.isEmpty()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want empty string\n", s, got));
      }
    }
  }
}

public class P14_13_LongestRepeatedSubstring {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
