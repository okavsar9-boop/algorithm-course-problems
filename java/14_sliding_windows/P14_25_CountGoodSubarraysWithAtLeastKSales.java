// 14.25 - Count Good Subarrays With at Least k Sales
// Run: javac P14_25_CountGoodSubarraysWithAtLeastKSales.java && java P14_25_CountGoodSubarraysWithAtLeastKSales

import java.util.*;
import java.util.function.*;

class CountGoodSubarraysWithAtLeastKSales {
  public long solve(int[] sales, int k) {
    // First find maximal subarrays without bad days
    List<int[]> goodSubarrays = new ArrayList<>();
    int start = 0;
    for (int i = 0; i < sales.length; i++) {
      if (sales[i] < 10) {
        if (i > start) {
          goodSubarrays.add(Arrays.copyOfRange(sales, start, i));
        }
        start = i + 1;
      }
    }
    if (start < sales.length) {
      goodSubarrays.add(Arrays.copyOfRange(sales, start, sales.length));
    }

    // Then count subarrays with at least k total sales in each good subarray
    long total = 0;
    CountAtLeastKTotalSales counter = new CountAtLeastKTotalSales();
    for (int[] sub : goodSubarrays) {
      total += counter.solve(sub, k);
    }
    return total;
  }
}

If k == 0, "at least 0" just means every subarray, so we can return n * (n + 1) / 2.

class CountAtLeastKTotalSales {
  public long solve(int[] arr, int k) {
    int n = arr.length;
    long totalSubarrays = (long) n * (n + 1) / 2;
    if (k == 0) {
      return totalSubarrays;
    }
    return totalSubarrays - new CountAtMostKTotalSales().solve(arr, k - 1);
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

class CountAtMostKTotalSales {
  public long solve(int[] arr, int k) {
    int l = 0, r = 0;
    long windowSum = 0;
    long count = 0;
    while (r < arr.length) {
      windowSum += arr[r];
      r++;
      while (l < r && windowSum > k) {
        windowSum -= arr[l];
        l++;
      }
      count += r - l;
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example with mix of good and bad days
        { new int[] { 15, 20, 5, 30, 25 }, 50, 1L },
        // Edge case - empty array
        { new int[] {}, 10, 0L },
        // Edge case - all good days
        { new int[] { 10, 20, 30 }, 40, 2L },
        // Edge case - all bad days
        { new int[] { 0, 5, 8 }, 10, 0L },
        // Edge case - k = 0
        { new int[] { 10, 20, 5, 30 }, 0, 4L },
    };

    CountGoodSubarraysWithAtLeastKSales solution = new CountGoodSubarraysWithAtLeastKSales();
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

public class P14_25_CountGoodSubarraysWithAtLeastKSales {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
