// 12.14 - Multi-Exit Maze
// Run: javac P12_14_MultiExitMaze.java && java P12_14_MultiExitMaze

import java.util.*;
import java.util.function.*;

class ExitDistances {
  private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, 1 },
    { 0, -1 } };

  public int[][] solve(List<String> mazeRows) {
    char[][] maze = new char[mazeRows.size()][];
    for (int i = 0; i < mazeRows.size(); i++) {
      maze[i] = mazeRows.get(i).toCharArray();
    }

    int R = maze.length;
    int C = maze[0].length;
    int[][] distances = new int[R][C];
    for (int[] row : distances) {
      Arrays.fill(row, -1);
    }

    Queue<int[]> Q = new ArrayDeque<>();
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (maze[r][c] == 'O') {
          distances[r][c] = 0;
          Q.offer(new int[] { r, c });
        }
      }
    }

    while (!Q.isEmpty()) {
      int[] curr = Q.poll();
      int r = curr[0], c = curr[1];
      for (int[] dir : DIRECTIONS) {
        int nbrR = r + dir[0], nbrC = c + dir[1];
        if (0 <= nbrR && nbrR < R && 0 <= nbrC && nbrC < C &&
        maze[nbrR][nbrC] != 'X' && distances[nbrR][nbrC] == -1) {
          distances[nbrR][nbrC] = distances[r][c] + 1;
          Q.offer(new int[] { nbrR, nbrC });
        }
      }
    }
    return distances;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from book
        { new String[] {
            "...X.O",
            "OX.X..",
            "...X..",
            ".X....",
            "XOX.XX" },
            new int[][] {
                { 1, 2, 3, -1, 1, 0 },
                { 0, -1, 4, -1, 2, 1 },
                { 1, 2, 3, -1, 3, 2 },
                { 2, -1, 4, 5, 4, 3 },
                { -1, 0, -1, 6, -1, -1 } } },
        // Single exit
        { new String[] {
            "...",
            ".O.",
            "..." },
            new int[][] {
                { 2, 1, 2 },
                { 1, 0, 1 },
                { 2, 1, 2 } } },
        // Multiple exits
        { new String[] {
            "O.O",
            "...",
            "O.O" },
            new int[][] {
                { 0, 1, 0 },
                { 1, 2, 1 },
                { 0, 1, 0 } } },
        // Walls blocking direct paths
        { new String[] {
            "O.X.",
            "XX..",
            "...O" },
            new int[][] {
                { 0, 1, -1, 2 },
                { -1, -1, 2, 1 },
                { 3, 2, 1, 0 } } },
        // Single cell
        { new String[] { "O" },
            new int[][] { { 0 } } }
    };

    ExitDistances solution = new ExitDistances();
    for (Object[] test : tests) {
      String[] mazeArray = (String[]) test[0];
      List<String> maze = Arrays.stream(mazeArray).collect(Collectors.toList());
      int[][] want = (int[][]) test[1];
      int[][] got = solution.solve(maze);
      if (!Arrays.deepEquals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            maze, Arrays.deepToString(got), Arrays.deepToString(want)));
      }
    }
  }
}

public class P12_14_MultiExitMaze {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
