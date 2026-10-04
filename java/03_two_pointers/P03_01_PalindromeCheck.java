// 3.1 - Palindrome Check
// Run: javac P03_01_PalindromeCheck.java && java P03_01_PalindromeCheck

import java.util.*;
import java.util.function.*;

class Palindrome {
  public boolean solve(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
      if (s.charAt(l) != s.charAt(r)) {
        return false;
      }
      l++;
      r--;
    }
    return true;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example from the book
      { "level", true },
      { "naan", true },
      // Additional test cases
      { "", true },
      { "a", true },
      { "ab", false },
      { "abc", false },
      { "abba", true },
      { "abcba", true },
    };

    Palindrome solution = new Palindrome();
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

public class P03_01_PalindromeCheck {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
