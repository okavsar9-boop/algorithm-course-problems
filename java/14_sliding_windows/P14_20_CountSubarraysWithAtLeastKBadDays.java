// 14.20 - Count Subarrays With at Least k Bad Days
// Run: javac P14_20_CountSubarraysWithAtLeastKBadDays.java && java P14_20_CountSubarraysWithAtLeastKBadDays

import java.util.*;
import java.util.function.*;

class CountAtLeastKBadDays {
  public long solve(int[] sales, int k) {
    int n = sales.length;
    long totalSubarrays = (long) n * (n + 1) / 2;
    if (k == 0) {
      return totalSubarrays;
    }
    return totalSubarrays - new CountAtMostKBadDays().solve(sales, k - 1);
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
        // Example from the book
        { new int[] { 0, 20, 5 }, 1, 5L },
        // Edge case - empty array
        { new int[] {}, 1, 0L },
        // Edge case - k = 0
        { new int[] { 0, 20, 5 }, 0, 6L },
        // all good days
        { new int[] { 10, 20, 30 }, 1, 0L },
        // all bad days
        { new int[] { 0, 5, 8 }, 2, 3L },
    };

    CountAtLeastKBadDays solution = new CountAtLeastKBadDays();
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

public class P14_20_CountSubarraysWithAtLeastKBadDays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
