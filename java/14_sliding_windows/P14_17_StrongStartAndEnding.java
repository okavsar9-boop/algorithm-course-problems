// 14.17 - Strong Start and Ending
// Run: javac P14_17_StrongStartAndEnding.java && java P14_17_StrongStartAndEnding

import java.util.*;
import java.util.function.*;

class MaxGoodDaysStartAndEndTwoPointers {
  public int solve(int[] projectedSales, int k) {
    int n = projectedSales.length;

    // Count total bad days
    int B = 0;
    for (int x : projectedSales) {
      if (x < 10) {
        B++;
      }
    }
    if (B <= k) {
      return n;
    }

    // General case: there are more than k bad days

    int suffixPtr = n; // suffix pointer
    int suffixBad = 0; // bad days in suffix

    for (int i = n - 1; i >= 0; i--) {
      if (projectedSales[i] < 10) {
        if (suffixBad == k) {
          suffixPtr = i + 1;
          break;
        } else {
          suffixBad++;
        }
      }
    }

    // Initial result: empty prefix + best suffix found
    int res = n - suffixPtr;

    int prefixBad = 0; // bad days in prefix
    for (int prefixPtr = 0; prefixPtr < n; prefixPtr++) {
      if (projectedSales[prefixPtr] < 10) {
        prefixBad++;
      }

      // Shrink suffix to maintain constraint:
      // total bad days in prefix + suffix <= k
      while (suffixPtr < n && prefixBad + suffixBad > k) {
        if (projectedSales[suffixPtr] < 10) {
          suffixBad--;
        }
        suffixPtr++;
      }

      if (prefixBad > k) {
        break;
      }

      // Calculate combined length
      int prefixLength = prefixPtr + 1;
      int suffixLength = n - suffixPtr;
      res = Math.max(res, prefixLength + suffixLength);
    }

    return res;
  }
}

"If we look for a subarray instead of for a prefix and a suffix, what property should the subarray have?"

minimum_window(arr):
  initialize:
    - data structures to track window info
    - cur_best to infinity
  while true
    if the window must grow to become valid
      if the window cannot grow (r == len(arr))
        break
    else
      update cur_best if needed
  return cur_best

class MaxGoodDaysStartAndEndSlidingWindow {
  public int solve(int[] projectedSales, int k) {
    int n = projectedSales.length;

    // Count total bad days
    int B = 0;
    for (int x : projectedSales) {
      if (x < 10) {
        B++;
      }
    }
    if (B <= k) {
      return n;
    }

    int targetBad = B - k;

    // Find minimum window containing target_bad bad days
    int l = 0, r = 0;
    int windowBad = 0;
    int minWindow = Integer.MAX_VALUE;

    while (true) {
      boolean mustGrow = windowBad < targetBad;
      if (mustGrow) {
        if (r == projectedSales.length) {
          break;
        }
        if (projectedSales[r] < 10) {
          windowBad++;
        }
        r++;
      } else {
        if (r - l < minWindow) {
          minWindow = r - l;
        }
        if (projectedSales[l] < 10) {
          windowBad--;
        }
        l++;
      }
    }

    // If we can't find a window with target_bad bad days
    if (minWindow == Integer.MAX_VALUE) {
      return projectedSales.length;
    }

    // Return length of prefix + suffix of good days
    return projectedSales.length - minWindow;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 10, 0, 0, 0, 10, 0, 0, 10 }, 2, 5 },
        // Example 2 from the book
        { new int[] { 0, 10, 0, 10 }, 1, 3 },
        // Example 3
        { new int[] { 5, 5, 5 }, 2, 2 },
        // Edge case - empty array
        { new int[] {}, 1, 0 },
        // Edge case - k=0
        { new int[] { 5, 10, 5 }, 0, 0 },
        // Edge case - all good days
        { new int[] { 10, 10, 10 }, 1, 3 },
        // Edge case - all bad days
        { new int[] { 5, 5, 5 }, 2, 2 },
        // Edge case - k >= number of bad days
        { new int[] { 5, 10, 5, 10 }, 3, 4 },
    };

    MaxGoodDaysStartAndEndTwoPointers solutionTwoPointers = new MaxGoodDaysStartAndEndTwoPointers();
    MaxGoodDaysStartAndEndSlidingWindow solutionSlidingWindow = new MaxGoodDaysStartAndEndSlidingWindow();
    for (Object[] test : tests) {
      int[] projectedSales = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];

      int gotTwoPointers = solutionTwoPointers.solve(projectedSales, k);
      if (gotTwoPointers != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(projectedSales), k, gotTwoPointers, want));
      }

      int gotSlidingWindow = solutionSlidingWindow.solve(projectedSales, k);
      if (gotSlidingWindow != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(projectedSales), k, gotSlidingWindow, want));
      }
    }
  }
}

public class P14_17_StrongStartAndEnding {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
