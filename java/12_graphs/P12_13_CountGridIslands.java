// 12.13 - Count Grid Islands
// Run: javac P12_13_CountGridIslands.java && java P12_13_CountGridIslands

import java.util.*;
import java.util.function.*;

def grid_dfs(grid, start_r, start_c):
  # Returns if (r, c) is in bounds, not visited, and "walkable".
  def is_valid(r, c):
    ...

  directions = [(-1, 0), (1, 0), (0, 1), (0, -1)]
  visited = {(start_r, start_c)}

  def visit(r, c):
    # Do something with (r, c).
    for dir_r, dir_c in directions:
      nbr_r, nbr_c = r + dir_r, c + dir_c
      if is_valid(nbr_r, nbr_c):
        visited.add((nbr_r, nbr_c))
        visit(nbr_r, nbr_c)

  visit(start_r, start_c)

class CountIslands {
  private int[][] grid;
  private Set<String> visited;
  private int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
  private int R, C;

  private boolean isValid(int r, int c) {
    return 0 <= r && r < R && 0 <= c && c < C &&
        !visited.contains(r + "," + c) && grid[r][c] == 1;
  }

  private void visit(int r, int c) {
    for (int[] dir : directions) {
      int nbrR = r + dir[0];
      int nbrC = c + dir[1];
      if (isValid(nbrR, nbrC)) {
        visited.add(nbrR + "," + nbrC);
        visit(nbrR, nbrC);
      }
    }
  }

  public int solve(int[][] grid) {
    R = grid.length;
    C = grid[0].length;
    this.grid = grid;
    visited = new HashSet<>();

    int count = 0;
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (grid[r][c] == 1 && !visited.contains(r + "," + c)) {
          visited.add(r + "," + c);
          visit(r, c);
          count++;
        }
      }
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[][] {
            { 0, 0, 1, 0 },
            { 1, 1, 0, 1 },
            { 0, 0, 1, 1 } }, 3 },
        // Example 2 from the book
        { new int[][] { {} }, 0 },
        // Edge case - single cell
        { new int[][] { { 1 } }, 1 },
        // Edge case - all water
        { new int[][] { { 0, 0 }, { 0, 0 } }, 0 },
        // Edge case - all land
        { new int[][] { { 1, 1 }, { 1, 1 } }, 1 },
        // Multiple islands
        { new int[][] {
            { 1, 0, 1 },
            { 0, 0, 0 },
            { 1, 0, 1 } }, 4 }
    };

    // Test CountIslands function
    CountIslands solution1 = new CountIslands();
    for (Object[] test : tests) {
      int[][] gridTemplate = (int[][]) test[0];
      int[][] grid = deepCopy(gridTemplate);
      int want = (int) test[1];
      int got = solution1.solve(grid);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            java.util.Arrays.deepToString(gridTemplate), got, want));
      }
    }

    // Test CountIslandsInPlace function
    CountIslandsInPlace solution2 = new CountIslandsInPlace();
    for (Object[] test : tests) {
      int[][] gridTemplate = (int[][]) test[0];
      int[][] grid = deepCopy(gridTemplate);
      int want = (int) test[1];
      int got = solution2.solve(grid);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            java.util.Arrays.deepToString(gridTemplate), got, want));
      }
    }
  }

  private int[][] deepCopy(int[][] original) {
    if (original.length == 0)
      return new int[0][0];
    int[][] copy = new int[original.length][original[0].length];
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[i].length; j++) {
        copy[i][j] = original[i][j];
      }
    }
    return copy;
  }
}

public class P12_13_CountGridIslands {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
