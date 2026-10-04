// 5.6 - Race Overtaking
// Run: javac P05_06_RaceOvertaking.java && java P05_06_RaceOvertaking

import java.util.*;
import java.util.function.*;

class RaceOvertaking {
  public int solve(int[] p1, int[] p2) {
    int l = 0, r = p1.length - 1;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, p1, p2)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return r;
  }

  private boolean isBefore(int i, int[] p1, int[] p2) {
    return p1[i] > p2[i];
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book
        { new int[] { 2, 4, 6, 8, 10 }, new int[] { 1, 3, 5, 9, 11 }, 3 },
        // Example
        { new int[] { 2, 3, 4, 5, 6 }, new int[] { 1, 2, 3, 6, 7 }, 3 },
        // Example
        { new int[] { 3, 4, 5 }, new int[] { 2, 5, 6 }, 1 },
        // Edge case - overtake at start
        { new int[] { 2, 3 }, new int[] { 1, 4 }, 1 }
    };

    RaceOvertaking solution = new RaceOvertaking();
    for (Object[] test : tests) {
      int[] p1 = (int[]) test[0];
      int[] p2 = (int[]) test[1];
      int want = (int) test[2];
      int got = solution.solve(p1, p2);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            Arrays.toString(p1), Arrays.toString(p2), got, want));
      }
    }
  }
}

public class P05_06_RaceOvertaking {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
