// 17.1 - Most Non-Overlapping Intervals
// Run: javac P17_01_MostNonOverlappingIntervals.java && java P17_01_MostNonOverlappingIntervals

import java.util.*;
import java.util.function.*;

class MostNonOverlappingIntervals {
  public int solve(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
    int count = 0;
    int prevEnd = Integer.MIN_VALUE;
    for (int[] interval : intervals) {
      if (interval[0] > prevEnd) {
        count++;
        prevEnd = interval[1];
      }
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[][] { { 2, 3 }, { 1, 4 }, { 2, 3 }, { 3, 6 }, { 8, 9 } }, 2 },

        // Additional test cases
        // Edge case: No intervals
        { new int[][] {}, 0 },
        // Edge case: All intervals overlap
        { new int[][] { { 1, 5 }, { 2, 6 }, { 3, 7 } }, 1 },
        { new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 } }, 2 },
        // Edge case: Non-overlapping intervals (considering inclusive
        // endpoints)
        { new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } }, 3 },
        // Edge case: Single interval
        { new int[][] { { 1, 2 } }, 1 },
    };

    MostNonOverlappingIntervals solution = new MostNonOverlappingIntervals();
    for (Object[] test : tests) {
      int[][] intervals = (int[][]) test[0];
      int want = (int) test[1];
      int got = solution.solve(intervals);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(intervals), got, want));
      }
    }
  }
}

public class P17_01_MostNonOverlappingIntervals {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
