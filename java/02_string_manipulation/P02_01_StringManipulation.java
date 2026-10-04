// 2.1 - String Manipulation
// Run: javac P02_01_StringManipulation.java && java P02_01_StringManipulation

import java.util.*;
import java.util.function.*;

class Split {
  public String[] solve(String s, char c) {
    if (s == null || s.isEmpty()) {
      return new String[0];
    }

    List<String> res = new ArrayList<>();
    List<Character> current = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
      if (s.charAt(i) == c) {
        res.add(current.stream()
            .map(String::valueOf)
            .collect(Collectors.joining()));
        current.clear();
      } else {
        current.add(s.charAt(i));
      }
      i++;
    }
    res.add(current.stream()
        .map(String::valueOf)
        .collect(Collectors.joining()));
    return res.toArray(new String[0]);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { "split by space", ' ', new String[] { "split", "by", "space" } },
        // Example 2 from the book
        { "beekeeper needed", 'e',
            new String[] { "b", "", "k", "", "p", "r n", "", "d", "d" } },
        // Example 3 from the book
        { "/home/./..//Documents/", '/',
            new String[] { "", "home", ".", "..", "", "Documents", "" } },
        // Example 4 from the book
        { "", '?', new String[] {} },
        // Edge case - empty string with various delimiters
        { "", ' ', new String[] {} },
        { "", '\n', new String[] {} },
        { "", '\0', new String[] {} },
        // Edge case - single character string
        { "a", 'a', new String[] { "", "" } },
        { "a", 'b', new String[] { "a" } },
        // Edge case - no splits
        { "hello", 'x', new String[] { "hello" } },
        { "hello", '?', new String[] { "hello" } },
        // Edge case - all splits
        { "aaa", 'a', new String[] { "", "", "", "" } },
        // Edge case - special characters
        { "\n\n\n", '\n', new String[] { "", "", "", "" } },
        { "tab\tseparated\ttext", '\t',
            new String[] { "tab", "separated", "text" } },
        // Edge case - consecutive delimiters
        { "one,,two,,,three", ',',
            new String[] { "one", "", "two", "", "", "three" } },
        // Edge case - delimiter at start/end
        { ",start,middle,end,", ',',
            new String[] { "", "start", "middle", "end", "" } },
        // Edge case - mixed length strings
        { "short,medium string,very very long string", ',',
            new String[] { "short", "medium string",
                "very very long string" } },
        // Edge case - whitespace handling
        { "  leading space", ' ', new String[] { "", "", "leading", "space" } },
        { "trailing space  ", ' ',
            new String[] { "trailing", "space", "", "" } },
        // Edge case - numbers and special chars
        { "123,456,789", ',', new String[] { "123", "456", "789" } },
        { "!@#$%", '@', new String[] { "!", "#$%" } }
    };

    Split solution = new Split();
    for (Object[] test : tests) {
      String s = (String) test[0];
      char c = (char) test[1];
      String[] want = (String[]) test[2];
      String[] got = solution.solve(s, c);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(\"%s\", '%c'): got: %s, want: %s\n",
            s, c, Arrays.toString(got), Arrays.toString(want)));
      }
    }
  }
}

public class P02_01_StringManipulation {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
