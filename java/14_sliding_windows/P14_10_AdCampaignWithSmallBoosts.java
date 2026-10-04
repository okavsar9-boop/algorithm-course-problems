// 14.10 - Ad Campaign With Small Boosts
// Run: javac P14_10_AdCampaignWithSmallBoosts.java && java P14_10_AdCampaignWithSmallBoosts

import java.util.*;
import java.util.function.*;

class MaxConsecutiveGoodDaysWithSmallBoost {
  public int solve(int[] projectedSales, int k) {
    int l = 0, r = 0;
    int windowBetween5And9 = 0;
    int curMax = 0;
    while (r < projectedSales.length) {
      boolean canGrow = projectedSales[r] >= 10 ||
          (5 <= projectedSales[r] && projectedSales[r] < 10 &&
              windowBetween5And9 < k);
      if (canGrow) {
        if (projectedSales[r] < 10) {
          windowBetween5And9++;
        }
        r++;
        curMax = Math.max(curMax, r - l);
      } else if (l == r) {
        l++;
        r++;
      } else {
        if (5 <= projectedSales[l] && projectedSales[l] < 10) {
          windowBetween5And9--;
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
        { new int[] { 8, 4, 8 }, 3, 1 },
        // Example 2 from the book
        { new int[] { 10, 5, 8 }, 1, 2 },
        { new int[] { 8, 8, 8 }, 3, 3 }, // Example with mix of values
        { new int[] { 4, 8, 12, 3, 9 }, 2, 2 },
        // Edge case - empty array
        { new int[] {}, 1, 0 },
        // Edge case - k=0
        { new int[] { 5, 10, 5 }, 0, 1 },
        // Edge case - all values between 5-9
        { new int[] { 7, 8, 9 }, 3, 3 },
        // Edge case - values below 5 break sequence
        { new int[] { 8, 4, 8 }, 2, 1 },
    };

    MaxConsecutiveGoodDaysWithSmallBoost solution = new MaxConsecutiveGoodDaysWithSmallBoost();
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

public class P14_10_AdCampaignWithSmallBoosts {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
