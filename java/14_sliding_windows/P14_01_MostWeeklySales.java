// 14.1 - Most Weekly Sales
// Run: javac P14_01_MostWeeklySales.java && java P14_01_MostWeeklySales

import java.util.*;
import java.util.function.*;

class MostWeeklySales {
  public int solve(int[] sales) {
    int l = 0, r = 0;
    int windowSum = 0;
    int curMax = 0;
    while (r < sales.length) {
      windowSum += sales[r];
      r++;
      if (r - l == 7) {
        curMax = Math.max(curMax, windowSum);
        windowSum -= sales[l];
        l++;
      }
    }
    return curMax;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 0, 3, 7, 12, 10, 5, 0, 1, 0, 15, 12, 11, 1 }, 44 },
        // Example 2 from the book
        { new int[] { 0, 3, 7, 12 }, 0 },
        // Edge case - empty array
        { new int[] {}, 0 },
        // Edge case - exactly 7 days
        { new int[] { 1, 2, 3, 4, 5, 6, 7 }, 28 },
        // Edge case - all zeros
        { new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }, 0 },
    };

    MostWeeklySales solution = new MostWeeklySales();
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

public class P14_01_MostWeeklySales {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
