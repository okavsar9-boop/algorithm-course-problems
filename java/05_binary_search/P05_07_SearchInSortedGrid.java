// 5.7 - Search In Sorted Grid
// Run: javac P05_07_SearchInSortedGrid.java && java P05_07_SearchInSortedGrid

import java.util.*;
import java.util.function.*;

class SearchInSortedGrid {
  public int[] solve(int[][] grid, int target) {
    int R = grid.length;
    int C = grid[0].length;

    // Ensure the first element is 'before' and the last is 'after'
    if (grid[0][0] > target || grid[R - 1][C - 1] < target) {
      return new int[] { -1, -1 };
    }
    if (grid[0][0] == target) {
      return new int[] { 0, 0 };
    }
    if (grid[R - 1][C - 1] == target) {
      return new int[] { R - 1, C - 1 };
    }

    // Binary search for the transition point
    int l = 0, r = R * C - 1;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, grid, target)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    int row = r / C;
    int col = r % C;
    if (grid[row][col] == target) {
      return new int[] { row, col };
    }
    return new int[] { -1, -1 };
  }

  // Before is < target, after is >= target
  private boolean isBefore(int i, int[][] grid, int target) {
    int C = grid[0].length;
    int row = i / C;
    int col = i % C;
    return grid[row][col] < target;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[][] { { 1, 3, 5 }, { 7, 9, 11 }, { 13, 15, 17 } }, 9,
            new int[] { 1, 1 } },
        // Example 2
        { new int[][] { { 1, 3, 5 }, { 7, 9, 11 } }, 4, new int[] { -1, -1 } },
        // 2x2 grid, all grid after
        { new int[][] { { 2, 3 }, { 4, 5 } }, 1, new int[] { -1, -1 } },
        // 2x2 grid, all grid before
        { new int[][] { { 1, 2 }, { 3, 4 } }, 5, new int[] { -1, -1 } },
        // 3x2 grid, first element
        { new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } }, 1, new int[] { 0, 0 } },
        // 2x3 grid, last element
        { new int[][] { { 1, 2, 3 }, { 4, 5, 6 } }, 6, new int[] { 1, 2 } },
        // Single element edge case
        { new int[][] { { 7 } }, 7, new int[] { 0, 0 } },
        // Single element edge case (not found)
        { new int[][] { { 7 } }, 6, new int[] { -1, -1 } }
    };

    SearchInSortedGrid solution = new SearchInSortedGrid();
    for (Object[] test : tests) {
      int[][] grid = (int[][]) test[0];
      int target = (int) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(grid, target);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            Arrays.deepToString(grid), target, Arrays.toString(got),
            Arrays.toString(want)));
      }
    }
  }
}

public class P05_07_SearchInSortedGrid {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
