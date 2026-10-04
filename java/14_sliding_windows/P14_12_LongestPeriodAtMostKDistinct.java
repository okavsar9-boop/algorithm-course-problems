// 14.12 - Longest Period at Most k Distinct
// Run: javac P14_12_LongestPeriodAtMostKDistinct.java && java P14_12_LongestPeriodAtMostKDistinct

import java.util.*;
import java.util.function.*;

class MaxAtMostKDistinct {
  public int solve(String[] bestSeller, int k) {
    int l = 0, r = 0;
    Map<String, Integer> windowCounts = new HashMap<>();
    int curMax = 0;
    while (r < bestSeller.length) {
      boolean canGrow = windowCounts.containsKey(bestSeller[r]) ||
          windowCounts.size() + 1 <= k;
      if (canGrow) {
        windowCounts.merge(bestSeller[r], 1, Integer::sum);
        r++;
        curMax = Math.max(curMax, r - l);
      } else {
        windowCounts.merge(bestSeller[l], -1,
            (count, dec) -> count + dec == 0 ? null : count + dec);
        l++;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new String[] { "book1", "book1", "book2", "book1", "book3", "book1" },
            2, 4 },
        // Edge case - empty array
        { new String[] {}, 1, 0 },
        // Edge case - k=1
        { new String[] { "book1", "book2", "book1" }, 1, 1 },
        // Edge case - k=len(bestSeller)
        { new String[] { "book1", "book2", "book3" }, 3, 3 },
        // Edge case - all same book
        { new String[] { "book1", "book1", "book1" }, 1, 3 },
    };

    MaxAtMostKDistinct solution = new MaxAtMostKDistinct();
    for (Object[] test : tests) {
      String[] bestSeller = (String[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(bestSeller, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(bestSeller), k, got, want));
      }
    }
  }
}

public class P14_12_LongestPeriodAtMostKDistinct {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
