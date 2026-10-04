// 5.10 - Water Refilling
// Run: javac P05_10_WaterRefilling.java && java P05_10_WaterRefilling

import java.util.*;
import java.util.function.*;

class NumRefills {
  public int solve(int a, int b) {
    // "Can we pour 'numPours' times?"
    int k = 1;
    while (k * 2 * b <= a) {
      k *= 2;
    }

    int l = k;
    int r = k * 2;
    while (r - l > 1) {
      int gap = r - l;
      int halfGap = gap >> 1; // Bit shift instead of division
      int mid = l + halfGap;
      if (mid * b <= a) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return l;
  }
}


class RunTests {
  public void runTests() {
    int[][] tests = {
        // Basic cases
        { 10, 2, 5 },
        { 10, 3, 3 },
        { 10, 4, 2 },
        { 10, 5, 2 },
        // Large numbers
        { 1000000, 1, 1000000 },
        // Large numbers with multiple refills
        { 1000000, 500000, 2 },
        // Random cases
        { 18, 5, 3 },
        { 182983, 90, 2033 }
    };

    NumRefills solution = new NumRefills();
    for (int[] test : tests) {
      int a = test[0];
      int b = test[1];
      int want = test[2];
      int got = solution.solve(a, b);
      if (got != want) {
        throw new RuntimeException(
            String.format("\nsolve(%d, %d): got: %d, want: %d\n",
                a, b, got, want));
      }
    }
  }
}

public class P05_10_WaterRefilling {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
