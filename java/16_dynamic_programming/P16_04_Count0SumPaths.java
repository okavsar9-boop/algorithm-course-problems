// 16.4 - Count 0-Sum Paths
// Run: javac P16_04_Count0SumPaths.java && java P16_04_Count0SumPaths

import java.util.*;
import java.util.function.*;

class Count0SumPaths {
  private final int[][] grid;
  private final int R, C;
  private final Map<String, Integer> memo;

  public Count0SumPaths(int[][] grid) {
    this.grid = grid;
    this.R = grid.length;
    this.C = grid[0].length;
    this.memo = new HashMap<>();
  }

  private int numPaths(int r, int c) {
    if (r >= R || c >= C || grid[r][c] == 1) {
      return 0;
    }
    String key = r + "," + c;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    if (r == R - 1 && c == C - 1) {
      return 1;
    }
    int result = numPaths(r + 1, c) + numPaths(r, c + 1)
        + numPaths(r + 1, c + 1);
    memo.put(key, result);
    return result;
  }

  public int solve() {
    return numPaths(0, 0);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new int[][] { { 0, 1, 1 },
            { 0, 0, 0 },
            { 1, 0, 0 } }, 7 },
        { new int[][] { { 1 } }, 0 },
        { new int[][] { { 0, 0 },
            { 0, 0 } }, 3 },
        { new int[][] { { 0 } }, 1 },
        { new int[][] { { 0, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 0 } }, 4 },
    };

    for (Object[] test : tests) {
      int[][] grid = (int[][]) test[0];
      int want = (int) test[1];
      Count0SumPaths solution = new Count0SumPaths(grid);
      int got = solution.solve();
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(grid), got, want));
      }
    }
  }
}

public class P16_04_Count0SumPaths {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
