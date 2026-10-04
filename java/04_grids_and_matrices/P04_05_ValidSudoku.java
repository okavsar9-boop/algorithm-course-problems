// 4.5 - Valid Sudoku
// Run: javac P04_05_ValidSudoku.java && java P04_05_ValidSudoku

import java.util.*;
import java.util.function.*;

class ValidSudoku {
  public boolean solve(int[][] board) {
    return validRows(board) && validCols(board) && validSubgrids(board);
  }

  private boolean validRows(int[][] board) {
    final int R = board.length, C = board[0].length;
    for (int r = 0; r < R; r++) {
      Set<Integer> seen = new HashSet<>();
      for (int c = 0; c < C; c++) {
        if (board[r][c] != 0) {
          if (seen.contains(board[r][c]))
            return false;
          seen.add(board[r][c]);
        }
      }
    }
    return true;
  }

  private boolean validCols(int[][] board) {
    final int R = board.length, C = board[0].length;
    for (int c = 0; c < C; c++) {
      Set<Integer> seen = new HashSet<>();
      for (int r = 0; r < R; r++) {
        if (board[r][c] != 0) {
          if (seen.contains(board[r][c]))
            return false;
          seen.add(board[r][c]);
        }
      }
    }
    return true;
  }

  private boolean validSubgrid(int[][] board, int r, int c) {
    Set<Integer> seen = new HashSet<>();
    for (int newR = r; newR < r + 3; newR++) {
      for (int newC = c; newC < c + 3; newC++) {
        if (board[newR][newC] != 0) {
          if (seen.contains(board[newR][newC]))
            return false;
          seen.add(board[newR][newC]);
        }
      }
    }
    return true;
  }

  private boolean validSubgrids(int[][] board) {
    for (int r = 0; r < 9; r += 3) {
      for (int c = 0; c < 9; c += 3) {
        if (!validSubgrid(board, r, c))
          return false;
      }
    }
    return true;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book - valid sudoku
        { new int[][] {
            { 5, 0, 0, 0, 0, 0, 0, 0, 6 },
            { 0, 0, 9, 0, 5, 0, 3, 0, 0 },
            { 0, 3, 0, 0, 0, 2, 0, 0, 0 },
            { 8, 0, 0, 7, 0, 0, 0, 0, 9 },
            { 0, 0, 2, 0, 0, 0, 8, 0, 0 },
            { 4, 0, 0, 0, 0, 6, 0, 0, 3 },
            { 0, 0, 0, 3, 0, 0, 0, 4, 0 },
            { 0, 0, 3, 0, 8, 0, 2, 0, 0 },
            { 9, 0, 0, 0, 0, 0, 0, 0, 7 } }, true },
        // Example 2 from book - invalid sudoku (duplicate 7 in bottom right subgrid)
        { new int[][] {
            { 5, 0, 0, 0, 0, 0, 0, 0, 6 },
            { 0, 0, 9, 0, 5, 0, 3, 0, 0 },
            { 0, 3, 0, 0, 0, 2, 0, 0, 0 },
            { 8, 0, 0, 7, 0, 0, 0, 0, 9 },
            { 0, 0, 2, 0, 0, 0, 8, 0, 0 },
            { 4, 0, 0, 0, 0, 6, 0, 0, 3 },
            { 0, 0, 0, 3, 0, 0, 0, 4, 0 },
            { 0, 0, 3, 0, 8, 0, 7, 0, 0 },
            { 9, 0, 0, 0, 0, 0, 0, 0, 7 } }, false },
        // Edge case - empty board
        { new int[9][9], true },
        // Edge case - full valid board
        { new int[][] {
            { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
            { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
            { 2, 3, 1, 5, 6, 4, 8, 9, 7 },
            { 5, 6, 4, 8, 9, 7, 2, 3, 1 },
            { 8, 9, 7, 2, 3, 1, 5, 6, 4 },
            { 3, 1, 2, 6, 4, 5, 9, 7, 8 },
            { 6, 4, 5, 9, 7, 8, 3, 1, 2 },
            { 9, 7, 8, 3, 1, 2, 6, 4, 5 } }, true },
    };

    ValidSudoku solution = new ValidSudoku();
    for (Object[] test : tests) {
      int[][] board = (int[][]) test[0];
      boolean want = (boolean) test[1];
      boolean got = solution.solve(board);
      if (got != want) {
        StringBuilder error = new StringBuilder("\nsolve(");
        error.append(arrayToString(board));
        error.append("): got: ");
        error.append(got);
        error.append(", want: ");
        error.append(want);
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

public class P04_05_ValidSudoku {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
