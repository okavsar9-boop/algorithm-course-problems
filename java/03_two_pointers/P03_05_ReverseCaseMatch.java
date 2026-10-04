// 3.5 - Reverse Case Match
// Run: javac P03_05_ReverseCaseMatch.java && java P03_05_ReverseCaseMatch

import java.util.*;
import java.util.function.*;

class ReverseCaseMatch {
  public boolean solve(String s) {
    int l = 0, r = s.length() - 1;
    while (l < s.length() && r >= 0) {
      if (!Character.isLowerCase(s.charAt(l))) {
        l++;
      } else if (!Character.isUpperCase(s.charAt(r))) {
        r--;
      } else {
        if (s.charAt(l) != Character.toLowerCase(s.charAt(r))) {
          return false;
        }
        l++;
        r--;
      }
    }
    return true;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { "haDrRAHd", true },
        // Example 2 from the book
        { "haHrARDd", false },
        // Additional test cases
        { "", true },
        { "aA", true },
        { "Aa", true },
        { "BbbB", true },
        { "abAB", false },
        { "abBA", true },
        { "helloworldHELLOWORLD", false },
    };

    ReverseCaseMatch solution = new ReverseCaseMatch();
    for (Object[] test : tests) {
      String s = (String) test[0];
      boolean want = (boolean) test[1];
      boolean got = solution.solve(s);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n",
            s, got, want));
      }
    }
  }
}

public class P03_05_ReverseCaseMatch {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
