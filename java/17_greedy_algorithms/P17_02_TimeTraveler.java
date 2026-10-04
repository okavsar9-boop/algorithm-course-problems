// 17.2 - Time Traveler
// Run: javac P17_02_TimeTraveler.java && java P17_02_TimeTraveler

import java.util.*;
import java.util.function.*;

class CanReachGoal {
  public boolean solve(int[] jumpingPoints, int k, int maxAging) {
    int n = jumpingPoints.length;
    int[] gaps = new int[n - 1];
    for (int i = 1; i < n; i++) {
      gaps[i - 1] = jumpingPoints[i] - jumpingPoints[i - 1];
    }
    Arrays.sort(gaps);
    int totalAging = 0;
    for (int i = 0; i < n - 1 - k; i++) {
      totalAging += gaps[i];
    }
    return totalAging <= maxAging;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[] { 2020, 2024 }, 0, 3, false },
        // Example 2
        { new int[] { 2020, 2024 }, 1, 1, true },
        // Example 3
        { new int[] { 1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001,
            2021 },
            4, 45, true },

        // Additional test cases
        // Edge case: No jumps allowed, but within aging limit
        { new int[] { 2000, 2001, 2002 }, 0, 2, true },
        // Edge case: No jumps allowed, exceeding aging limit
        { new int[] { 2000, 2005, 2010 }, 0, 4, false },
    };

    CanReachGoal solution = new CanReachGoal();
    for (Object[] test : tests) {
      int[] points = (int[]) test[0];
      int jumps = (int) test[1];
      int maxAging = (int) test[2];
      boolean want = (boolean) test[3];
      boolean got = solution.solve(points, jumps, maxAging);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): got: %b, want: %b\n",
            Arrays.toString(points), jumps, maxAging, got, want));
      }
    }
  }
}

public class P17_02_TimeTraveler {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
