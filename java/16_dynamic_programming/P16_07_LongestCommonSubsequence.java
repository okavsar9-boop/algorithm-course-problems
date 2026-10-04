// 16.7 - Longest Common Subsequence
// Run: javac P16_07_LongestCommonSubsequence.java && java P16_07_LongestCommonSubsequence

import java.util.*;
import java.util.function.*;

class LongestCommonSubsequence {
  private String s1;
  private String s2;
  private Map<String, Integer> memo;

  private int lcs(int i1, int i2) {
    if (i1 == s1.length() || i2 == s2.length()) {
      return 0;
    }

    String key = i1 + "," + i2;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }

    int result;
    if (s1.charAt(i1) == s2.charAt(i2)) {
      result = 1 + lcs(i1 + 1, i2 + 1);
    } else {
      result = Math.max(lcs(i1 + 1, i2), lcs(i1, i2 + 1));
    }
    memo.put(key, result);
    return result;
  }

  public int solve(String str1, String str2) {
    this.s1 = str1;
    this.s2 = str2;
    this.memo = new HashMap<>();
    return lcs(0, 0);
  }
}


class RunTests {
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
        { "AAAAABBBBBCCCCCDDDDD", "BBBBBCCCCCDDDDDEEEEE", 15 }
    };

    LongestCommonSubsequence solution = new LongestCommonSubsequence();
    for (Object[] test : tests) {
      String s1 = (String) test[0];
      String s2 = (String) test[1];
      int want = (int) test[2];
      int got = solution.solve(s1, s2);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            s1, s2, got, want));
      }
    }
  }
}

public class P16_07_LongestCommonSubsequence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
