// 17.5 - Fewest Script Runs
// Run: javac P17_05_FewestScriptRuns.java && java P17_05_FewestScriptRuns

import java.util.*;
import java.util.function.*;

class MinimumScriptRuns {
  public int solve(int[][] meetings) {
    // Sort meetings by end time
    Arrays.sort(meetings, Comparator.comparingInt(a -> a[1]));

    int count = 0;
    int prevEnd = Integer.MIN_VALUE;
    for (int[] meeting : meetings) {
      if (meeting[0] > prevEnd) {
        count++;
        prevEnd = meeting[1];
      }
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[][] { { 2, 3 }, { 1, 4 }, { 2, 3 }, { 3, 6 }, { 8, 10 } },
            2 },
        // Example 2 - Counterexample from solution
        { new int[][] { { 1, 3 }, { 2, 5 }, { 3, 6 }, { 4, 7 }, { 5, 8 },
            { 7, 9 } },
            2 },
        // Edge case: No meetings
        { new int[][] {}, 0 },
        // Edge case: All meetings overlap
        { new int[][] { { 1, 5 }, { 2, 6 }, { 3, 7 } }, 1 },
        // Edge case: Non-overlapping meetings
        { new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } }, 3 },
        // Edge case: Single meeting
        { new int[][] { { 1, 2 } }, 1 },
    };

    MinimumScriptRuns solution = new MinimumScriptRuns();
    for (Object[] test : tests) {
      int[][] meetings = (int[][]) test[0];
      int want = (int) test[1];
      int got = solution.solve(meetings);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(meetings), got, want));
      }
    }
  }
}

public class P17_05_FewestScriptRuns {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
