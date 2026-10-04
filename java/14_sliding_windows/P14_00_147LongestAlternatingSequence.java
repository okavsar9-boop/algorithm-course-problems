//  - 14.7 Longest Alternating Sequence
// Run: javac P14_00_147LongestAlternatingSequence.java && java P14_00_147LongestAlternatingSequence

import java.util.*;
import java.util.function.*;

class LongestAlternatingSequence {
  public int solve(int[] sales) {
    int l = 0, r = 0;
    int curMax = 0;
    while (r < sales.length) {
      boolean canGrow = l == r || (sales[r - 1] >= 10) != (sales[r] >= 10);
      if (canGrow) {
        r++;
        curMax = Math.max(curMax, r - l);
      } else {
        l = r;
        r++;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 8, 9, 20, 0, 9 }, 3 },
        // Example 2 from the book
        { new int[] { 0, 0, 0 }, 1 },
        // Edge case - empty array
        { new int[] {}, 0 },
        // Edge case - single element
        { new int[] { 10 }, 1 },
        // perfect alternation
        { new int[] { 5, 10, 5, 10 }, 4 },
        // all good days
        { new int[] { 10, 11, 12 }, 1 },
    };

    LongestAlternatingSequence solution = new LongestAlternatingSequence();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(sales);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(sales), got, want));
      }
    }
  }
}

public class P14_00_147LongestAlternatingSequence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
