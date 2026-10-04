// 14.9 - Ad Campaign Boost
// Run: javac P14_09_AdCampaignBoost.java && java P14_09_AdCampaignBoost

import java.util.*;
import java.util.function.*;

class MaxConsecutiveGoodDays {
  public int solve(int[] projectedSales, int k) {
    int l = 0, r = 0;
    int windowBadDays = 0;
    int curMax = 0;
    while (r < projectedSales.length) {
      boolean canGrow = projectedSales[r] >= 10 || windowBadDays < k;
      if (canGrow) {
        if (projectedSales[r] < 10) {
          windowBadDays++;
        }
        r++;
        curMax = Math.max(curMax, r - l);
      } else {
        if (projectedSales[l] < 10) {
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
        // Example 1 from the book
        { new int[] { 5, 0, 20, 0, 5 }, 2, 3 },
        // Example 2 from the book
        { new int[] { 0, 10, 0, 10 }, 1, 3 },
        // Edge case - empty array
        { new int[] {}, 1, 0 },
        // Edge case - k=0
        { new int[] { 5, 10, 5 }, 0, 1 },
        // Edge case - k=len(projectedSales)
        { new int[] { 5, 5, 5 }, 3, 3 },
    };

    MaxConsecutiveGoodDays solution = new MaxConsecutiveGoodDays();
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

public class P14_09_AdCampaignBoost {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
