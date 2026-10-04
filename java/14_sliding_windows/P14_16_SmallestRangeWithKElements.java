// 14.16 - Smallest Range With k Elements
// Run: javac P14_16_SmallestRangeWithKElements.java && java P14_16_SmallestRangeWithKElements

import java.util.*;
import java.util.function.*;

class SmallestRangeWithKElements {
  public int[] solve(int[] arr, int k) {
    Arrays.sort(arr);
    int l = 0, r = 0;
    int bestLow = 0, bestHigh = Integer.MAX_VALUE;
    while (true) {
      boolean mustGrow = (r - l) < k;
      if (mustGrow) {
        if (r == arr.length) {
          break;
        }
        r++;
      } else {
        if (arr[r - 1] - arr[l] < bestHigh - bestLow) {
          bestLow = arr[l];
          bestHigh = arr[r - 1];
        }
        l++;
      }
    }
    return new int[] { bestLow, bestHigh };
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 2, 5, 7, 8 }, 3, new int[] { 5, 8 } },
        // Example 2 from the book - both [2,5] and [5,8] are valid
        { new int[] { 5, 5, 2, 2, 8, 8 }, 3, new int[] { 2, 5 } },
        // Example 3 from the book
        { new int[] { 0 }, 1, new int[] { 0, 0 } },
        // Edge case - k=len(arr)
        { new int[] { 1, 5, 10 }, 3, new int[] { 1, 10 } },
        // Edge case - all same number
        { new int[] { 5, 5, 5 }, 2, new int[] { 5, 5 } },
    };

    SmallestRangeWithKElements solution = new SmallestRangeWithKElements();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(arr, k);

      // Count elements in range
      int count = 0;
      for (int x : arr) {
        if (want[0] <= x && x <= want[1]) {
          count++;
        }
      }

      if (count < k) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): range %s contains fewer than %d elements\n",
            Arrays.toString(arr), k, Arrays.toString(got), k));
      }

      if (got[1] - got[0] > want[1] - want[0]) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): range %s is larger than %s\n",
            Arrays.toString(arr), k, Arrays.toString(got),
            Arrays.toString(want)));
      }
    }
  }
}

public class P14_16_SmallestRangeWithKElements {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
