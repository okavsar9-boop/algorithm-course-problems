// 3.16 - Dutch Flag Problem
// Run: javac P03_16_DutchFlagProblem.java && java P03_16_DutchFlagProblem

import java.util.*;
import java.util.function.*;

class SortColors {
  public void solve(char[] arr) {
    // Count occurrences of each color
    int rCount = 0;
    int wCount = 0;
    for (char c : arr) {
      if (c == 'R') rCount++;
      if (c == 'W') wCount++;
    }

    // Rewrite array with the right number of each color
    int i = 0;
    while (rCount-- > 0) {
      arr[i++] = 'R';
    }
    while (wCount-- > 0) {
      arr[i++] = 'W';
    }
    while (i < arr.length) {
      arr[i++] = 'B';
    }

  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example from the book
      {"RWBBWRW".toCharArray(), "RRWWWBB".toCharArray()},
      // Additional test cases
      {"".toCharArray(), "".toCharArray()},
      {"R".toCharArray(), "R".toCharArray()},
      {"W".toCharArray(), "W".toCharArray()},
      {"B".toCharArray(), "B".toCharArray()},
      {"RW".toCharArray(), "RW".toCharArray()},
      {"WR".toCharArray(), "RW".toCharArray()},
      {"RWB".toCharArray(), "RWB".toCharArray()},
      {"RRRWWBBB".toCharArray(), "RRRWWBBB".toCharArray()},
      {"BBBWWRRR".toCharArray(), "RRRWWBBB".toCharArray()},
    };

    SortColors solution = new SortColors();
    for (Object[] test : tests) {
      char[] arr = Arrays.copyOf((char[]) test[0], ((char[]) test[0]).length);
      char[] want = (char[]) test[1];
      solution.solve(arr);
      if (!Arrays.equals(arr, want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got: %s, want: %s\n",
        Arrays.toString((char[]) test[0]), Arrays.toString(arr),
        Arrays.toString(want)));
      }
    }

  }
}

public class P03_16_DutchFlagProblem {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
