// 14.23 - Count Subarrays With Bad Days in Range
// Run: javac P14_23_CountSubarraysWithBadDaysInRange.java && java P14_23_CountSubarraysWithBadDaysInRange

import java.util.*;
import java.util.function.*;

class CountBadDaysRange {
  public long solve(int[] sales, int k1, int k2) {
    if (k1 == 0) {
      return new CountAtMostKBadDays().solve(sales, k2);
    }
    return new CountAtMostKBadDays().solve(sales, k2) -
        new CountAtMostKBadDays().solve(sales, k1 - 1);
  }
}

maximum_window(arr):
  initialize:
    - data structures to track window info
    - cur_best to 0
  while we can grow the window (r < len(arr))
    if the window would still be valid with one more element
      update cur_best if needed
    else if the window is empty  # skip this case if empty windows are always valid
      advance both l and r
    else
  return cur_best

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
        // Example 1 from the book
        { new int[] { 0, 20, 5 }, 2, 2, 1L },
        // Example 2 from the book
        { new int[] { 0, 20, 5 }, 1, 2, 5L },
        // Edge case - empty array
        { new int[] {}, 1, 2, 0L },
        // Edge case - k1 = k2 = 0
        { new int[] { 0, 20, 5 }, 0, 0, 1L },
        // Edge case - all good days
        { new int[] { 10, 20, 30 }, 1, 2, 0L },
    };

    CountBadDaysRange solution = new CountBadDaysRange();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      int k1 = (int) test[1];
      int k2 = (int) test[2];
      long want = (long) test[3];
      long got = solution.solve(sales, k1, k2);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): got: %d, want: %d\n",
            Arrays.toString(sales), k1, k2, got, want));
      }
    }
  }
}

public class P14_23_CountSubarraysWithBadDaysInRange {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
