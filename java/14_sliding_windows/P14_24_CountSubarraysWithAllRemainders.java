// 14.24 - Count Subarrays With All Remainders
// Run: javac P14_24_CountSubarraysWithAllRemainders.java && java P14_24_CountSubarraysWithAllRemainders

import java.util.*;
import java.util.function.*;

class CountAll3Groups {
  public long solve(int[] arr) {
    int n = arr.length;
    long totalCount = (long) n * (n + 1) / 2;
    return totalCount - new CountAtMost2Groups().solve(arr);
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

class CountAtMost2Groups {
  public long solve(int[] arr) {
    int l = 0, r = 0;
    Map<Integer, Integer> windowCounts = new HashMap<>();
    long count = 0;
    while (r < arr.length) {
      boolean canGrow = windowCounts.containsKey(arr[r] % 3) ||
          windowCounts.size() < 2;
      if (canGrow) {
        windowCounts.put(arr[r] % 3,
            windowCounts.getOrDefault(arr[r] % 3, 0) + 1);
        r++;
        count += r - l;
      } else {
        int rem = arr[l] % 3;
        windowCounts.put(rem, windowCounts.get(rem) - 1);
        if (windowCounts.get(rem) == 0) {
          windowCounts.remove(rem);
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
        { new int[] { 9, 8, 7 }, 1L },
        // Example 2 from the book
        { new int[] { 1, 2, 3, 4, 5 }, 6L },
        // Example 3 from the book
        { new int[] { 1, 3, 4, 6, 7, 9 }, 0L },
        // Edge case - empty array
        { new int[] {}, 0L },
        // Edge case - single element
        { new int[] { 3 }, 0L },
    };

    CountAll3Groups solution = new CountAll3Groups();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      long want = (long) test[1];
      long got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P14_24_CountSubarraysWithAllRemainders {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
