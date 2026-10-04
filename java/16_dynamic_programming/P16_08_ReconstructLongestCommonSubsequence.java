// 16.8 - Reconstruct Longest Common Subsequence
// Run: javac P16_08_ReconstructLongestCommonSubsequence.java && java P16_08_ReconstructLongestCommonSubsequence

import java.util.*;
import java.util.function.*;

class LcsReconstruction {
  private Map<String, String> memo;
  private String s1;
  private String s2;

  private String lcsRec(int i1, int i2) {
    if (i1 == s1.length() || i2 == s2.length()) {
      return "";
    }
    String key = i1 + "," + i2;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    if (s1.charAt(i1) == s2.charAt(i2)) {
      memo.put(key, s1.charAt(i1) + lcsRec(i1 + 1, i2 + 1));
    } else {
      String opt1 = lcsRec(i1 + 1, i2);
      String opt2 = lcsRec(i1, i2 + 1);
      memo.put(key, opt1.length() >= opt2.length() ? opt1 : opt2);
    }
    return memo.get(key);
  }

  public String solve(String s1, String s2) {
    this.s1 = s1;
    this.s2 = s2;
    this.memo = new HashMap<>();
    return lcsRec(0, 0);
  }
}

We build it one character at a time with the "parallel pointers" pattern from the Two Pointers chapter. We use two pointers, i1 and i2, initialized to 0. If s1[i1] and s2[i2] match, we can add that character to the solution and advance both pointers. Otherwise, we know that we need to advance one of the two pointers without adding anything to the solution. Which one? We use lcs_rec() to answer that. If lcs_rec(i1 + 1, i2) is higher than lcs_rec(i1, i2 + 1), it is better to advance i1. Otherwise, it's better to advance i2. We keep going until either pointer reaches the end of the string, which means we are done.

class LcsReconstructionOptimal {
  private Map<String, Integer> memo;
  private String s1;
  private String s2;

  private int lcsRec(int i1, int i2) {
    if (i1 == s1.length() || i2 == s2.length()) {
      return 0;
    }
    String key = i1 + "," + i2;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    if (s1.charAt(i1) == s2.charAt(i2)) {
      memo.put(key, 1 + lcsRec(i1 + 1, i2 + 1));
    } else {
      memo.put(key, Math.max(lcsRec(i1 + 1, i2), lcsRec(i1, i2 + 1)));
    }
    return memo.get(key);
  }

  public String solve(String s1, String s2) {
    this.s1 = s1;
    this.s2 = s2;
    this.memo = new HashMap<>();

    int i1 = 0, i2 = 0;
    StringBuilder res = new StringBuilder();
    while (i1 < s1.length() && i2 < s2.length()) {
      if (s1.charAt(i1) == s2.charAt(i2)) {
        res.append(s1.charAt(i1));
        i1++;
        i2++;
      } else if (lcsRec(i1 + 1, i2) >= lcsRec(i1, i2 + 1)) {
        i1++;
      } else {
        i2++;
      }
    }
    return res.toString();
  }
}


class RunTests {
  private boolean isSubsequence(String subseq, String s) {
    int i = 0;
    for (char c : s.toCharArray()) {
      if (i < subseq.length() && c == subseq.charAt(i)) {
        i++;
      }
    }
    return i == subseq.length();
  }

  public void runTests() {
    Object[][] tests = {
        { "HAHAH", "AAAAHH", 3 },
        { "", "AA", 0 },
        { "ABC", "BCA", 2 },
        { "ABCD", "ACBAD", 3 },
        { "", "", 0 },
        { "ABCDEFGHIJ", "ACBDEFGHIK", 8 },
        { "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAA", 13 },
        { "THEQUICKBROWNFOX", "THESLOWREDFOX", 8 },
        { "AAAAABBBBBCCCCCDDDDD", "BBBBBCCCCCDDDDDEEEEE", 15 },
    };

    LcsReconstructionOptimal solution = new LcsReconstructionOptimal();
    for (Object[] test : tests) {
      String s1 = (String) test[0];
      String s2 = (String) test[1];
      int wantLength = (int) test[2];
      String got = solution.solve(s1, s2);
      if (got.length() != wantLength) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction_optimal(%s, %s): got length: %d, want length: %d\n",
            s1, s2, got.length(), wantLength));
      }
      if (!isSubsequence(got, s1)) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction_optimal(%s, %s): result '%s' is not a subsequence of '%s'\n",
            s1, s2, got, s1));
      }
      if (!isSubsequence(got, s2)) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction_optimal(%s, %s): result '%s' is not a subsequence of '%s'\n",
            s1, s2, got, s2));
      }

      LcsReconstruction solution2 = new LcsReconstruction();
      String got2 = solution2.solve(s1, s2);
      if (got2.length() != wantLength) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction(%s, %s): got length: %d, want length: %d\n",
            s1, s2, got2.length(), wantLength));
      }
      if (!isSubsequence(got2, s1)) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction(%s, %s): result '%s' is not a subsequence of '%s'\n",
            s1, s2, got2, s1));
      }
      if (!isSubsequence(got2, s2)) {
        throw new RuntimeException(String.format(
            "\nlcs_reconstruction(%s, %s): result '%s' is not a subsequence of '%s'\n",
            s1, s2, got2, s2));
      }
    }
  }
}

public class P16_08_ReconstructLongestCommonSubsequence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
