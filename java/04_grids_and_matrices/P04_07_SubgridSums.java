// 4.7 - Subgrid Sums
// Run: javac P04_07_SubgridSums.java && java P04_07_SubgridSums

import java.util.*;
import java.util.function.*;

class SubgridSums {
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
          res[r][c] += res[r + 1][c];
        }
        if (c + 1 < C) {
          res[r][c] += res[r][c + 1];
        }
        if (r + 1 < R && c + 1 < C) { // subtract doublecounted subgrid
          res[r][c] -= res[r + 1][c + 1];
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
            { -1, 2, 3 },
            { 4, 0, 0 },
            { -2, 0, 9 }
        }, new int[][] {
            { 15, 14, 12 },
            { 11, 9, 9 },
            { 7, 9, 9 }
        } },
        // Edge case - 1x1 grid
        { new int[][] { { 5 } }, new int[][] { { 5 } } },
        // Edge case - single row
        { new int[][] { { 1, 2, 3 } }, new int[][] { { 6, 5, 3 } } },
        // Edge case - single column
        { new int[][] { { 1 }, { 2 }, { 3 } },
            new int[][] { { 6 }, { 5 }, { 3 } } },
        // Edge case - all zeros
        { new int[][] {
            { 0, 0 },
            { 0, 0 }
        }, new int[][] {
            { 0, 0 },
            { 0, 0 }
        } },
    };

    SubgridSums solution = new SubgridSums();
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

public class P04_07_SubgridSums {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
