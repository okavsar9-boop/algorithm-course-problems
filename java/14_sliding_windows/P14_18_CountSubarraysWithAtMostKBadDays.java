// 14.18 - Count Subarrays With at Most k Bad Days
// Run: javac P14_18_CountSubarraysWithAtMostKBadDays.java && java P14_18_CountSubarraysWithAtMostKBadDays

import java.util.*;
import java.util.function.*;

class CountAtMostKBadDays {
  public long solve(int[] sales, int k) {
    int l = 0, r = 0;
    int windowBadDays = 0;
    long count = 0;
    while (r < sales.length) {
      boolean canGrow = sales[r] >= 10 || windowBadDays < k;
      if (canGrow) {
        if (sales[r] < 10) {
          windowBadDays++;
        }
        r++;
        count += r - l;
      } else {
        if (sales[l] < 10) {
          windowBadDays--;
        }
        l++;
      }
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 0, 20, 5 }, 1, 5L },
        // Edge case - empty array
        { new int[] {}, 1, 0L },
        // Edge case - k = 0
        { new int[] { 0, 20, 5 }, 0, 1L },
        // Edge case - all good days
        { new int[] { 10, 20, 30 }, 1, 6L },
        // Edge case - all bad days
        { new int[] { 0, 5, 8 }, 2, 5L },
    };

    CountAtMostKBadDays solution = new CountAtMostKBadDays();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      int k = (int) test[1];
      long want = (long) test[2];
      long got = solution.solve(sales, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(sales), k, got, want));
      }
    }
  }
}

public class P14_18_CountSubarraysWithAtMostKBadDays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
