// 6.7 - Word Expansion Class
// Run: javac P06_07_WordExpansionClass.java && java P06_07_WordExpansionClass

import java.util.*;
import java.util.function.*;

class Checker {
  private String s;

  public Checker(String s) {
    this.s = s;
  }

  public boolean expandsInto(String s2) {
    if (s2.length() != s.length() + 1) {
      return false;
    }

    Map<Character, Integer> freq = new HashMap<>();
    for (char c : s2.toCharArray()) {
      freq.put(c, freq.getOrDefault(c, 0) + 1);
    }

    for (char c : s.toCharArray()) {
      if (!freq.containsKey(c)) {
        return false;
      }
      freq.put(c, freq.get(c) - 1);
      if (freq.get(c) == 0) {
        freq.remove(c);
      }
    }

    return freq.size() == 1 && freq.values().iterator().next() == 1;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 
        {
            "tea",
            new Object[][] {
                { "tea", false },
                { "team", true },
                { "seam", false }
            }
        },
        // Example 2 
        {
            "on",
            new Object[][] {
                { "nooo", false },
                { "not", true },
                { "now", true }
            }
        },
        // Additional test cases
        {
            "",
            new Object[][] {
                { "a", true },
                { "", false },
                { "ab", false }
            }
        },
        {
            "xyz",
            new Object[][] {
                { "wxyz", true },
                { "xyzw", true },
                { "xyza", true },
                { "xyz", false }
            }
        }
    };

    for (Object[] test : tests) {
      String s = (String) test[0];
      Object[][] checks = (Object[][]) test[1];
      Checker checker = new Checker(s);
      for (Object[] check : checks) {
        String s2 = (String) check[0];
        boolean want = (boolean) check[1];
        boolean got = checker.expandsInto(s2);
        if (got != want) {
          throw new RuntimeException(String.format(
              "\nChecker(%s).expandsInto(%s): got: %b, want: %b\n",
              s, s2, got, want));
        }
      }
    }
  }
}

public class P06_07_WordExpansionClass {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
