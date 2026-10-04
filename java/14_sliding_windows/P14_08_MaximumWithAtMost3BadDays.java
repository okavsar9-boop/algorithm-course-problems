// 14.8 - Maximum With at Most 3 Bad Days
// Run: javac P14_08_MaximumWithAtMost3BadDays.java && java P14_08_MaximumWithAtMost3BadDays

import java.util.*;
import java.util.function.*;

class MaxAtMost3BadDays {
  public int solve(int[] sales) {
    int l = 0, r = 0;
    int windowBadDays = 0;
    int curMax = 0;
    while (r < sales.length) {
      boolean canGrow = sales[r] >= 10 || windowBadDays < 3;
      if (canGrow) {
        if (sales[r] < 10) {
          windowBadDays++;
        }
        r++;
        curMax = Math.max(curMax, r - l);
      } else {
        if (sales[l] < 10) {
          windowBadDays--;
        }
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
        { new int[] { 0, 14, 7, 9, 0, 20, 10, 0, 10 }, 6 },
        // Edge case - empty array
        { new int[] {}, 0 },
        // Edge case - single element
        { new int[] { 5 }, 1 },
        // all good days
        { new int[] { 10, 11, 12 }, 3 },
        // all bad days
        { new int[] { 1, 2, 3 }, 3 },
        // exactly 3 bad days
        { new int[] { 5, 10, 5, 10, 5 }, 5 },
        // More than 3 bad days
        { new int[] { 5, 10, 5, 5, 10, 5 }, 5 },
    };

    MaxAtMost3BadDays solution = new MaxAtMost3BadDays();
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

public class P14_08_MaximumWithAtMost3BadDays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
