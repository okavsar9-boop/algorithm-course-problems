// 4.1 - Chess Moves
// Run: javac P04_01_ChessMoves.java && java P04_01_ChessMoves

import java.util.*;
import java.util.function.*;

class ChessMoves {
  private static boolean isValid(int[][] board, int r, int c) {
    return 0 <= r && r < board.length && 0 <= c && c < board[0].length
        && board[r][c] != 1;
  }

  public List<int[]> solve(int[][] board, String piece, int r, int c) {
    List<int[]> moves = new ArrayList<>();
    int[][] kingDirections = {
        { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, // Vertical and horizontal
        { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } // Diagonals
    };
    int[][] knightDirections = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 },
        { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };

    int[][] directions = piece.equals("knight") ? knightDirections
        : kingDirections;

    for (int[] dir : directions) {
      int newR = r + dir[0];
      int newC = c + dir[1];
      if (piece.equals("queen")) {
        while (isValid(board, newR, newC)) {
          if (board[newR][newC] == 1) { // Stop at obstacles
            break;
          }
          moves.add(new int[] { newR, newC });
          newR += dir[0];
          newC += dir[1];
        }
      } else if (isValid(board, newR, newC)) {
        moves.add(new int[] { newR, newC });
      }
    }
    return moves;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book - king moves
        { new int[][] {
            { 0, 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0, 0 },
            { 0, 1, 0, 1, 1, 0 },
            { 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 0 }
        }, "king", 3, 5,
            new int[][] { { 2, 5 }, { 3, 4 }, { 4, 4 }, { 4, 5 } } },
        // Example 2 from the book - knight moves
        { new int[][] {
            { 0, 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0, 0 },
            { 0, 1, 0, 1, 1, 0 },
            { 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 0 }
        }, "knight", 4, 3,
            new int[][] { { 2, 2 }, { 3, 5 }, { 5, 5 } } },
        // Example 3 from the book - queen moves
        { new int[][] {
            { 0, 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0, 0 },
            { 0, 1, 0, 1, 1, 0 },
            { 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 0 }
        }, "queen", 4, 4,
            new int[][] { { 3, 4 }, { 3, 5 }, { 4, 0 }, { 4, 1 }, { 4, 2 },
                { 4, 3 }, { 4, 5 },
                { 5, 3 }, { 5, 4 }, { 5, 5 } } },
        // Edge case - 1x1 board
        { new int[][] { { 0 } }, "queen", 0, 0, new int[][] {} },
        // Edge case - all occupied except current position
        { new int[][] { { 1, 1 }, { 1, 0 } }, "knight", 1, 1, new int[][] {} },
    };

    ChessMoves solution = new ChessMoves();
    for (Object[] test : tests) {
      int[][] board = (int[][]) test[0];
      String piece = (String) test[1];
      int r = (int) test[2];
      int c = (int) test[3];
      int[][] want = (int[][]) test[4];

      List<int[]> gotList = solution.solve(board, piece, r, c);
      int[][] got = gotList.toArray(new int[0][]);

      // Sort both arrays for consistent comparison
      Arrays.sort(got, Comparator.comparingInt((int[] a) -> a[0])
          .thenComparingInt(a -> a[1]));
      Arrays.sort(want, Comparator.comparingInt((int[] a) -> a[0])
          .thenComparingInt(a -> a[1]));

      if (!Arrays.deepEquals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s, %d, %d): got: %s, want: %s\n",
            Arrays.deepToString(board), piece, r, c,
            Arrays.deepToString(got), Arrays.deepToString(want)));
      }
    }
  }
}

public class P04_01_ChessMoves {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
