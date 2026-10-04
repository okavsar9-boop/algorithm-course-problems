// 4.2 - Queen's Reach
// Run: javac P04_02_QueensReach.java && java P04_02_QueensReach

import java.util.*;
import java.util.function.*;

class SafeCells {
  private boolean isValid(int[][] board, int r, int c) {
    return 0 <= r && r < board.length && 0 <= c && c < board[0].length &&
        board[r][c] != 1;
  }

  private void markReachableCells(int[][] board, int r, int c, int[][] res) {
    int[][] directions = {
        { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, // Vertical and horizontal
        { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } // Diagonals
    };
    for (int[] dir : directions) {
      int newR = r + dir[0], newC = c + dir[1];
      while (isValid(board, newR, newC)) {
        res[newR][newC] = 1;
        newR += dir[0];
        newC += dir[1];
      }
    }
  }

  public int[][] solve(int[][] board) {
    if (board.length == 0)
      return new int[0][0];
    int n = board.length;
    int[][] res = new int[n][n];
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < n; c++) {
        if (board[r][c] == 1) {
          res[r][c] = 1;
          markReachableCells(board, r, c, res);
        }
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new int[][] {
            { 0, 0, 0, 1 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 1, 0, 0, 0 } },
            new int[][] {
                { 1, 1, 1, 1 },
                { 1, 0, 1, 1 },
                { 1, 1, 0, 1 },
                { 1, 1, 1, 1 } } },
        // Edge case - 1x1 board with queen
        { new int[][] { { 1 } }, new int[][] { { 1 } } },
        // Edge case - 1x1 board without queen
        { new int[][] { { 0 } }, new int[][] { { 0 } } },
        // Edge case - no queens
        { new int[][] { { 0, 0 }, { 0, 0 } },
            new int[][] { { 0, 0 }, { 0, 0 } } },
    };

    SafeCells solution = new SafeCells();
    for (Object[] test : tests) {
      int[][] board = (int[][]) test[0];
      int[][] want = (int[][]) test[1];
      int[][] got = solution.solve(board);

      boolean equal = got.length == want.length;
      if (equal) {
        for (int i = 0; i < got.length && equal; i++) {
          if (got[i].length != want[i].length) {
            equal = false;
            break;
          }
          for (int j = 0; j < got[i].length && equal; j++) {
            if (got[i][j] != want[i][j]) {
              equal = false;
              break;
            }
          }
        }
      }

      if (!equal) {
        StringBuilder error = new StringBuilder("\nsolve(");
        error.append(arrayToString(board));
        error.append("): got: ");
        error.append(arrayToString(got));
        error.append(", want: ");
        error.append(arrayToString(want));
        error.append("\n");
        throw new RuntimeException(error.toString());
      }
    }
  }

  private String arrayToString(int[][] arr) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < arr.length; i++) {
      sb.append("[");
      for (int j = 0; j < arr[i].length; j++) {
        sb.append(arr[i][j]);
        if (j < arr[i].length - 1)
          sb.append(", ");
      }
      sb.append("]");
      if (i < arr.length - 1)
        sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }
}

public class P04_02_QueensReach {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
