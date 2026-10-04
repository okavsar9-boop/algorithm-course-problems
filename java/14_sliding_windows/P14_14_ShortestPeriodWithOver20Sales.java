// 14.14 - Shortest Period With Over 20 Sales
// Run: javac P14_14_ShortestPeriodWithOver20Sales.java && java P14_14_ShortestPeriodWithOver20Sales

import java.util.*;
import java.util.function.*;

class ShortestOver20Sales {
  public int solve(int[] sales) {
    int l = 0, r = 0;
    int windowSum = 0;
    int curMin = Integer.MAX_VALUE;
    while (true) {
      boolean mustGrow = windowSum <= 20;
      if (mustGrow) {
        if (r == sales.length) {
          break;
        }
        windowSum += sales[r];
        r++;
      } else {
        curMin = Math.min(curMin, r - l);
        windowSum -= sales[l];
        l++;
      }
    }
    return curMin != Integer.MAX_VALUE ? curMin : -1;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 5, 10, 15, 5, 10 }, 2 },
        // Example 2 from the book
        { new int[] { 5, 10, 4, 5, 10 }, 4 },
        // Example 3 from the book
        { new int[] { 5, 5, 5, 5 }, -1 },
        // Edge case - empty array
        { new int[] {}, -1 },
        // Edge case - single element over 20
        { new int[] { 21 }, 1 },
        // Edge case - exactly 20 sales not enough
        { new int[] { 10, 10 }, -1 },
    };

    ShortestOver20Sales solution = new ShortestOver20Sales();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(sales);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(sales), got, want));
      }
    }
  }
}

public class P14_14_ShortestPeriodWithOver20Sales {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
