// 19.1 - Channel Views
// Run: javac P19_01_ChannelViews.java && java P19_01_ChannelViews

import java.util.*;
import java.util.function.*;

class ChannelViews {
  public int[] solve(int[] views, int[][] periods) {
    if (views.length == 0 || periods.length == 0) {
      return new int[] {};
    }
    int[] prefixSum = new int[views.length];
    prefixSum[0] = views[0];
    for (int i = 1; i < views.length; i++) {
      prefixSum[i] = prefixSum[i - 1] + views[i];
    }
    int[] res = new int[periods.length];
    for (int i = 0; i < periods.length; i++) {
      int l = periods[i][0], r = periods[i][1];
      if (l == 0) {
        res[i] = prefixSum[r];
      } else {
        res[i] = prefixSum[r] - prefixSum[l - 1];
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 3, 5, 4, 8, 7, 2, 5, 3, 2, 3 },
            new int[][] { { 0, 1 }, { 0, 5 }, { 5, 8 }, { 3, 3 } },
            new int[] { 8, 29, 12, 8 } },
        // Edge case: Single day period
        { new int[] { 10, 20, 30 },
            new int[][] { { 1, 1 } },
            new int[] { 20 } },
        // Edge case: Full range
        { new int[] { 1, 2, 3, 4, 5 },
            new int[][] { { 0, 4 } },
            new int[] { 15 } },
        // Edge case: Empty views
        { new int[] {},
            new int[][] {},
            new int[] {} },
        // Edge case: Periods with zero-length
        { new int[] { 1, 2, 3, 4, 5 },
            new int[][] { { 2, 2 }, { 0, 0 } },
            new int[] { 3, 1 } },
    };

    ChannelViews solution = new ChannelViews();
    for (Object[] test : tests) {
      int[] views = (int[]) test[0];
      int[][] periods = (int[][]) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(views, periods);
      if (!java.util.Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            java.util.Arrays.toString(views),
            java.util.Arrays.deepToString(periods),
            java.util.Arrays.toString(got),
            java.util.Arrays.toString(want)));
      }
    }
  }
}

public class P19_01_ChannelViews {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
