// 14.2 - Most Sales in K Days
// Run: javac P14_02_MostSalesInKDays.java && java P14_02_MostSalesInKDays

import java.util.*;
import java.util.function.*;

class MostSalesInKDays {
  public int solve(int[] sales, int k) {
    int l = 0, r = 0;
    int windowSum = 0;
    int curMax = 0;
    int bestStart = 0;
    while (r < sales.length) {
      windowSum += sales[r];
      r++;
      if (r - l == k) {
        if (windowSum > curMax) {
          curMax = windowSum;
          bestStart = l;
        }
        windowSum -= sales[l];
        l++;
      }
    }
    return bestStart;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 8, 1, 3, 7 }, 2, 2 },
        // Edge case - k=1
        { new int[] { 5, 10, 15, 5 }, 1, 2 },
        // Edge case - k=len(sales)
        { new int[] { 1, 2, 3 }, 3, 0 },
        // Edge case - multiple valid answers, return first
        { new int[] { 10, 5, 10 }, 2, 0 },
    };

    MostSalesInKDays solution = new MostSalesInKDays();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(sales, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(sales), k, got, want));
      }
    }
  }
}

public class P14_02_MostSalesInKDays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
