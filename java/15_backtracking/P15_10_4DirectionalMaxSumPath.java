// 15.10 - 4-Directional Max-Sum Path
// Run: javac P15_10_4DirectionalMaxSumPath.java && java P15_10_4DirectionalMaxSumPath

import java.util.*;
import java.util.function.*;

class FourDirectionalMaxSumPath {
  private int[][] grid;
  private int bestSum;
  private Set<String> seen;
  private int R, C;

  private void visit(int r, int c, int pathSum) {
    // Process leaf/full solution
    if (r == R - 1 && c == C - 1) {
      if (pathSum > bestSum) {
        bestSum = pathSum;
      }
      return;
    }

    // Try each direction
    int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    for (int[] dir : directions) {
      int nr = r + dir[0];
      int nc = c + dir[1];
      String key = nr + "," + nc;
      if (!seen.contains(key) && nr >= 0 && nr < R && nc >= 0 && nc < C) {
        seen.add(key);
        visit(nr, nc, pathSum + grid[nr][nc]);
        seen.remove(key);
      }
    }
  }

  public int solve(int[][] inputGrid) {
    grid = inputGrid;
    R = grid.length;
    C = grid[0].length;
    bestSum = Integer.MIN_VALUE;
    seen = new HashSet<>();
    seen.add("0,0");

    // Start from top-left
    visit(0, 0, grid[0][0]);
    return bestSum;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[][] { { 1, -4, 3 },
            { -2, 7, -6 },
            { 5, -4, 9 } },
            12 },
        // Edge case - 1x1 grid
        { new int[][] { { 5 } },
            5 },
        // Edge case - 1xN grid
        { new int[][] { { 1, 2, 3 } },
            6 },
        // Edge case - Nx1 grid
        { new int[][] { { 1 }, { 2 }, { 3 } },
            6 },
        // 2x2 grid
        { new int[][] { { 1, 2 }, { 3, 4 } },
            8 }
    };

    FourDirectionalMaxSumPath solution = new FourDirectionalMaxSumPath();
    for (Object[] test : tests) {
      int[][] grid = (int[][]) test[0];
      int want = (int) test[1];
      int got = solution.solve(grid);

      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(grid), got, want));
      }
    }
  }
}

public class P15_10_4DirectionalMaxSumPath {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
