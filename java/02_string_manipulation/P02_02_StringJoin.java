// 2.2 - String Join
// Run: javac P02_02_StringJoin.java && java P02_02_StringJoin

import java.util.*;
import java.util.function.*;

class Join {
  // Function allowed by the problem statement.
  private String arrayToString(ArrayList<Character> arr) {
    StringBuilder sb = new StringBuilder(arr.size());
    for (char c : arr) {
      sb.append(c);
    }
    return sb.toString();
  }

  public String solve(String[] arr, String s) {
    ArrayList<Character> res = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
      if (i != 0) {
        for (char c : s.toCharArray()) {
          res.add(c);
        }
      }
      for (char c : arr[i].toCharArray()) {
        res.add(c);
      }
    }
    return arrayToString(res);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new String[] { "join", "by", "space" }, " ", "join by space" },
      // Example 2 from the book
      { new String[] { "b", "", "k", "", "p", "r n", "", "d", "d!!" }, "ee",
        "beeeekeeeepeer neeeedeed!!" },
      // Edge case - empty arrays
      { new String[] {}, "x", "" },
      { new String[] {}, "", "" },
      { new String[] {}, "long separator", "" },
      // Edge case - single element arrays
      { new String[] { "a" }, "x", "a" },
      { new String[] { "" }, "x", "" },
      { new String[] { "multiple words" }, "x", "multiple words" },
      // two element arrays
      { new String[] { "a", "b" }, "", "ab" },
      { new String[] { "a", "b" }, " ", "a b" },
      { new String[] { "", "" }, ",", "," },
      // Edge case - empty strings in array
      { new String[] { "", "", "" }, ",", ",," },
      { new String[] { "hello", "", "world" }, " ", "hello  world" },
      // special characters
      { new String[] { "\n", "\t" }, ",", "\n,\t" },
      { new String[] { "tab", "separated" }, "\t", "tab\tseparated" },
      // long separators
      { new String[] { "short", "strings" }, "very long separator",
        "shortvery long separatorstrings" },
      // mixed content
      { new String[] { "123", "abc", "!@#", " " }, "|", "123|abc|!@#| " },
      // whitespace handling
      { new String[] { " leading", "trailing ", " both " }, "|",
        " leading|trailing | both " },
      // numbers and special chars
      { new String[] { "123", "456" }, "-", "123-456" },
      { new String[] { "!@#", "$%^" }, "&", "!@#&$%^" },
    };

    Join solution = new Join();
    for (Object[] test : tests) {
      String[] arr = (String[]) test[0];
      String s = (String) test[1];
      String want = (String) test[2];
      String got = solution.solve(arr, s);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %s): got: %s, want: %s\n",
        java.util.Arrays.toString(arr), s, got, want));
      }
    }

  }
}

public class P02_02_StringJoin {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
