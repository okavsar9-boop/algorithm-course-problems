// 14.22 - Count Subarrays With Drops
// Run: javac P14_22_CountSubarraysWithDrops.java && java P14_22_CountSubarraysWithDrops

import java.util.*;
import java.util.function.*;

class CountAtMostKDrops {
  public long solve(int[] arr, int k) {
    int l = 0, r = 0;
    int windowDrops = 0;
    long count = 0;
    while (r < arr.length) {
      boolean canGrow = r == 0 || arr[r] >= arr[r - 1] || windowDrops < k;
      if (canGrow) {
        if (r > 0 && arr[r] < arr[r - 1]) {
          windowDrops++;
        }
        r++;
        count += r - l;
      } else {
        if (arr[l] > arr[l + 1]) {
          windowDrops--;
        }
        l++;
      }
    }
    return count;
  }
}

If k == 0, "at most 0" and "exactly 0" are the same.

class CountExactlyKDrops {
  public long solve(int k, long atMostKDrops, long atMostKMinus1Drops) {
    if (k == 0) {
      return atMostKDrops;
    }
    return atMostKDrops - atMostKMinus1Drops;
  }
}

If k == 0, "at least 0" just means every subarray, so we can return n * (n + 1) / 2.

class CountAtLeastKDrops {
  public long solve(int n, int k, long atMostKMinus1Drops) {
    long totalCount = (long) n * (n + 1) / 2;
    if (k == 0) {
      return totalCount;
    }
    return totalCount - atMostKMinus1Drops;
  }
}

class CountSubarraysWithDrops {
  public long[] solve(int[] arr, int k) {
    long atMostKDrops = new CountAtMostKDrops().solve(arr, k);
    long atMostKMinus1Drops = k == 0 ? 0
        : new CountAtMostKDrops().solve(arr, k - 1);
    return new long[] {
        atMostKDrops,
        new CountExactlyKDrops().solve(k, atMostKDrops, atMostKMinus1Drops),
        new CountAtLeastKDrops().solve(arr.length, k, atMostKMinus1Drops),
    };
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 2, 3 }, 1, new long[] { 6, 0, 0 } },
        // Example 2 from the book
        { new int[] { 3, 2, 1 }, 1, new long[] { 5, 2, 3 } },
        // Example 3
        { new int[] { 5, 4, 3, 2, 1 }, 2, new long[] { 12, 3, 6 } },
        // Edge case - empty array
        { new int[] {}, 1, new long[] { 0, 0, 0 } },
        // Edge case - single element
        { new int[] { 1 }, 1, new long[] { 1, 0, 0 } },
        // Edge case - k = 0
        { new int[] { 5, 3, 2, 1 }, 0, new long[] { 4, 4, 10 } },
        // Alternating
        { new int[] { 6, 2, 7, 3, 8, 4, 9, 5, 10, 6 }, 3,
            new long[] { 50, 8, 13 } },
    };

    CountSubarraysWithDrops solution = new CountSubarraysWithDrops();

    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      long[] want = (long[]) test[2];

      long[] got = solution.solve(arr, k);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nCountSubarraysWithDrops.solve(%s, %d): got: %s, want: %s\n",
            Arrays.toString(arr), k, Arrays.toString(got),
            Arrays.toString(want)));
      }
    }
  }
}

public class P14_22_CountSubarraysWithDrops {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
