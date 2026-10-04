// 4.6 - Subgrid Maximums
// Run: javac P04_06_SubgridMaximums.java && java P04_06_SubgridMaximums

import java.util.*;
import java.util.function.*;

class SubgridMaximums {
  public int[][] solve(int[][] grid) {
    final int R = grid.length;
    final int C = grid[0].length;
    int[][] res = new int[R][C];
    for (int r = 0; r < R; r++) {
      res[r] = Arrays.copyOf(grid[r], C);
    }

    for (int r = R - 1; r >= 0; r--) {
      for (int c = C - 1; c >= 0; c--) {
        if (r + 1 < R) {
          res[r][c] = Math.max(res[r][c], res[r + 1][c]);
        }
        if (c + 1 < C) {
          res[r][c] = Math.max(res[r][c], res[r][c + 1]);
        }
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from book
        { new int[][] {
            { 1, 5, 3 },
            { 4, -1, 0 },
            { 2, 0, 2 }
        }, new int[][] {
            { 5, 5, 3 },
            { 4, 2, 2 },
            { 2, 2, 2 }
        } },
        // Edge case - 1x1 grid
        { new int[][] { { 5 } }, new int[][] { { 5 } } },
        // Edge case - single row
        { new int[][] { { 1, 2, 3 } }, new int[][] { { 3, 3, 3 } } },
        // Edge case - single column
        { new int[][] { { 1 }, { 2 }, { 3 } },
            new int[][] { { 3 }, { 3 }, { 3 } } },
        // Edge case - negative numbers
        { new int[][] {
            { -1, -2 },
            { -3, -4 }
        }, new int[][] {
            { -1, -2 },
            { -3, -4 }
        } },
    };

    SubgridMaximums solution = new SubgridMaximums();
    for (Object[] test : tests) {
      int[][] grid = (int[][]) test[0];
      int[][] want = (int[][]) test[1];
      int[][] got = solution.solve(grid);
      if (!Arrays.deepEquals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            Arrays.deepToString(grid), Arrays.deepToString(got),
            Arrays.deepToString(want)));
      }
    }
  }
}

public class P04_06_SubgridMaximums {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
