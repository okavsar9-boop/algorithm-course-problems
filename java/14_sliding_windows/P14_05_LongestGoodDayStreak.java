// 14.5 - Longest Good Day Streak
// Run: javac P14_05_LongestGoodDayStreak.java && java P14_05_LongestGoodDayStreak

import java.util.*;
import java.util.function.*;

class MaxNoBadDays {
  public int solve(int[] sales) {
    int l = 0, r = 0;
    int curMax = 0;
    while (r < sales.length) {
      boolean canGrow = sales[r] >= 10;
      if (canGrow) {
        r++;
        curMax = Math.max(curMax, r - l);
      } else {
        l = r + 1;
        r = r + 1;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 0, 14, 7, 12, 10, 20 }, 3 },
        // Edge case - empty array
        { new int[] {}, 0 },
        // Edge case - all good days
        { new int[] { 10, 11, 12 }, 3 },
        // Edge case - all bad days
        { new int[] { 1, 2, 3 }, 0 },
        // alternating
        { new int[] { 10, 5, 10, 5 }, 1 },
    };

    MaxNoBadDays solution = new MaxNoBadDays();
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

public class P14_05_LongestGoodDayStreak {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
