// 14.11 - Boosting Days Multiple Times
// Run: javac P14_11_BoostingDaysMultipleTimes.java && java P14_11_BoostingDaysMultipleTimes

import java.util.*;
import java.util.function.*;

class MaxConsecutiveWithKBoosts {
  public int solve(int[] projectedSales, int k) {
    int l = 0, r = 0;
    int usedBoosts = 0;
    int curMax = 0;
    while (r < projectedSales.length) {
      boolean canGrow = usedBoosts + Math.max(10 - projectedSales[r], 0) <= k;
      if (canGrow) {
        usedBoosts += Math.max(10 - projectedSales[r], 0);
        r++;
        curMax = Math.max(curMax, r - l);
      } else if (l == r) {
        r++;
        l++;
      } else {
        usedBoosts -= Math.max(10 - projectedSales[l], 0);
        l++;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 5, 5, 15, 0, 10 }, 12, 3 },
        // Example 2 from the book
        { new int[] { 5, 5, 15, 0, 10 }, 15, 4 },
        // Edge case - empty array
        { new int[] {}, 5, 0 },
        // Edge case - k=0
        { new int[] { 5, 10, 5 }, 0, 1 },
        // all values need max boost
        { new int[] { 0, 0, 0 }, 30, 3 },
        // all values need max boost
        { new int[] { 0, 0, 0 }, 29, 2 },
    };

    MaxConsecutiveWithKBoosts solution = new MaxConsecutiveWithKBoosts();
    for (Object[] test : tests) {
      int[] projectedSales = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(projectedSales, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(projectedSales), k, got, want));
      }
    }
  }
}

public class P14_11_BoostingDaysMultipleTimes {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
