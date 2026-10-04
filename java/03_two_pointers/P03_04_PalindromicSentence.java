// 3.4 - Palindromic Sentence
// Run: javac P03_04_PalindromicSentence.java && java P03_04_PalindromicSentence

import java.util.*;
import java.util.function.*;

class PalindromicSentence {
  public boolean solve(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
      if (!Character.isLetter(s.charAt(l))) {
        l++;
      } else if (!Character.isLetter(s.charAt(r))) {
        r--;
      } else {
        if (Character.toLowerCase(s.charAt(l)) != Character
        .toLowerCase(s.charAt(r))) {
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
      // Example from the book
      { "Bob wondered, 'Now, Bob?'", true },
      // Additional test cases
      { "", true },
      { "a", true },
      { "A man, a plan, a canal: Panama", true },
      { "race a car", false },
      { "Was it a car or a cat I saw?", true },
      { "hello", false },
      { ".,?!'", true },
    };

    PalindromicSentence solution = new PalindromicSentence();
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

public class P03_04_PalindromicSentence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
