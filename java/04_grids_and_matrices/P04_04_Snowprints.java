// 4.4 - Snowprints
// Run: javac P04_04_Snowprints.java && java P04_04_Snowprints

import java.util.*;
import java.util.function.*;

class DistanceToRiver {
  private static boolean hasFootprints(int[][] field, int r, int c) {
    return 0 <= r && r < field.length && 0 <= c && c < field[0].length
        && field[r][c] == 1;
  }

  public int solve(int[][] field) {
    final int R = field.length;
    final int C = field[0].length;

    // Find starting position in first column
    int r = 0;
    while (r < R && field[r][0] != 1) {
      r++;
    }

    int closest = r;
    int c = 0;

    // Track fox through remaining columns
    while (c < C - 1) { // Stop before last column
      boolean found = false;
      for (int dirR : new int[] { -1, 0, 1 }) { // Check up, same level, down
        int newR = r + dirR;
        int newC = c + 1;
        if (hasFootprints(field, newR, newC)) {
          r = newR;
          c = newC;
          closest = Math.min(closest, r);
          found = true;
          break;
        }
      }
      if (!found) { // No valid move found
        break;
      }
    }

    return closest;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from book
        { new int[][] {
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0, 0 },
            { 1, 1, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 1, 1 }
        }, 1 },
        // Edge case - top of grid
        { new int[][] {
            { 0, 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 1, 0 },
            { 1, 1, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0 }
        }, 0 },
        // Edge case - bottom of grid
        { new int[][] {
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1 }
        }, 3 },
        // Edge case - single column
        { new int[][] { { 0 }, { 1 } }, 1 },
        // Edge case - single row
        { new int[][] { { 1, 1, 1 } }, 0 },
        // Edge case - zigzag path
        { new int[][] {
            { 0, 0, 0 },
            { 1, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 1 }
        }, 1 },
    };

    DistanceToRiver solution = new DistanceToRiver();
    for (Object[] test : tests) {
      int[][] field = (int[][]) test[0];
      int want = (int) test[1];
      int got = solution.solve(field);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(field), got, want));
      }
    }
  }
}

public class P04_04_Snowprints {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
