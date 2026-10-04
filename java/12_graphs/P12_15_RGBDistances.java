// 12.15 - RGB Distances
// Run: javac P12_15_RGBDistances.java && java P12_15_RGBDistances

import java.util.*;
import java.util.function.*;

class RgbDistances {
  private List<int[]> getSources(List<String> screen, char target) {
    List<int[]> sources = new ArrayList<>();
    for (int i = 0; i < screen.size(); i++) {
      for (int j = 0; j < screen.get(0).length(); j++) {
        if (screen.get(i).charAt(j) == target) {
          sources.add(new int[] { i, j });
        }
      }
    }
    return sources;
  }

  private int[][] multisourceBfs(List<String> screen, List<int[]> sources) {
    int rows = screen.size();
    int cols = screen.get(0).length();
    int[][] distances = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      Arrays.fill(distances[i], -1);
    }
    Deque<int[]> Q = new ArrayDeque<>();

    // Initialize with sources
    for (int[] p : sources) {
      Q.add(p);
      distances[p[0]][p[1]] = 0;
    }

    // BFS
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    while (!Q.isEmpty()) {
      int[] p = Q.removeFirst();
      for (int[] dir : dirs) {
        int nr = p[0] + dir[0];
        int nc = p[1] + dir[1];
        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
        distances[nr][nc] == -1) {
          distances[nr][nc] = distances[p[0]][p[1]] + 1;
          Q.add(new int[] { nr, nc });
        }
      }
    }
    return distances;
  }

  public int[][] solve(List<String> screen) {
    int rows = screen.size();
    int cols = screen.get(0).length();
    int[][] output = new int[rows][cols];

    // Map each color to its target
    Map<Character, Character> targets = new HashMap<>();
    targets.put('R', 'G');
    targets.put('G', 'B');
    targets.put('B', 'R');

    // For each color, do multisource BFS from its target color
    for (Map.Entry<Character, Character> entry : targets.entrySet()) {
      char color = entry.getKey();
      char target = entry.getValue();
      List<int[]> sources = getSources(screen, target);
      int[][] distances = multisourceBfs(screen, sources);

      // Fill in distances for cells of current color
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (screen.get(i).charAt(j) == color) {
            output[i][j] = distances[i][j];
          }
        }
      }
    }
    return output;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new String[] {
            "RRRGRB",
            "BGRGRR",
            "RRRGRR",
            "RGRRRR",
            "GBGRGG"
        },
            new int[][] {
                { 2, 1, 1, 2, 1, 1 },
                { 1, 1, 1, 3, 1, 2 },
                { 2, 1, 1, 4, 1, 2 },
                { 1, 1, 1, 1, 1, 1 },
                { 1, 2, 1, 1, 3, 4 }
            }
        },
        // Single row
        { new String[] { "RGB" },
            new int[][] { { 1, 1, 2 } }
        },
        // Single column
        { new String[] { "R", "G", "B" },
            new int[][] { { 1 }, { 1 }, { 2 } }
        },
        // All colors adjacent
        { new String[] {
            "RGB",
            "BGR"
        },
            new int[][] {
                { 1, 1, 1 },
                { 1, 1, 1 }
            }
        }
    };

    RgbDistances solution = new RgbDistances();
    for (Object[] test : tests) {
      String[] screenArray = (String[]) test[0];
      List<String> screen = Arrays.stream(screenArray)
          .collect(Collectors.toList());
      int[][] want = (int[][]) test[1];
      int[][] got = solution.solve(screen);
      if (!Arrays.deepEquals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            screen, Arrays.deepToString(got), Arrays.deepToString(want)));
      }
    }
  }
}

public class P12_15_RGBDistances {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
