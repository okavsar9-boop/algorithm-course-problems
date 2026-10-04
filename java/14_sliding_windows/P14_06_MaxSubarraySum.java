// 14.6 - Max Subarray Sum
// Run: javac P14_06_MaxSubarraySum.java && java P14_06_MaxSubarraySum

import java.util.*;
import java.util.function.*;

class MaxSubarraySum {
  public int solve(int[] arr) {
    int maxVal = Arrays.stream(arr).max().orElse(0);
    if (maxVal <= 0) { // Edge case without positive values
      return maxVal;
    }

    int r = 0; // We don't need the l pointer
    int windowSum = 0;
    int curMax = 0;
    while (r < arr.length) {
      boolean canGrow = windowSum + arr[r] >= 0;
      if (canGrow) {
        windowSum += arr[r];
        r++;
        curMax = Math.max(curMax, windowSum);
      } else {
        windowSum = 0;
        r = r + 1;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 2, 3, -2, 1 }, 6 },
        // Example 2 from the book
        { new int[] { 1, 2, 3, -2, 7 }, 11 },
        // Example 3 from the book
        { new int[] { 1, 2, 3, -8, 7 }, 7 },
        // Example 4 from the book
        { new int[] { -2, -3, -4 }, -2 },
        // Edge case - single element
        { new int[] { 5 }, 5 },
        // Edge case - all positive
        { new int[] { 1, 2, 3 }, 6 },
    };

    MaxSubarraySum solution = new MaxSubarraySum();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P14_06_MaxSubarraySum {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
