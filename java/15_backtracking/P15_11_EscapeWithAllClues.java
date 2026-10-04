// 15.11 - Escape with All Clues
// Run: javac P15_11_EscapeWithAllClues.java && java P15_11_EscapeWithAllClues

import java.util.*;
import java.util.function.*;

def visit(partial_solution):
if full_solution(partial_solution):
# Process leaf/full solution.
else:
for choice in choices(partial_solution):
# Prune children where possible.
child = apply_choice(partial_solution)
visit(child)

class EscapeWithAllClues {
  private int[][] room;
  private Set<String> allClues;
  private List<int[]> shortestPath;

  // State of the current partial solution
  private List<int[]> currentPath;
  private Set<String> currentVisited;
  private Set<String> currentCluesLeft;

  public EscapeWithAllClues(int[][] room) {
    this.room = room;
  }

  private List<int[]> getClues() {
    List<int[]> clues = new ArrayList<>();
    for (int i = 0; i < room.length; i++) {
      for (int j = 0; j < room[0].length; j++) {
        if (room[i][j] == 2) {
          clues.add(new int[] { i, j });
        }
      }
    }
    return clues;
  }

  private List<int[]> validMoves(int[] pos) {
    List<int[]> moves = new ArrayList<>();
    int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    for (int[] dir : dirs) {
      int nbrI = pos[0] + dir[0];
      int nbrJ = pos[1] + dir[1];
      if (nbrI >= 0 && nbrI < room.length &&
      nbrJ >= 0 && nbrJ < room[0].length &&
      room[nbrI][nbrJ] != 1) {
        moves.add(new int[] { nbrI, nbrJ });
      }
    }
    return moves;
  }

  private void visit() {
    // Prune if path is already longer than shortest found
    if (shortestPath != null && currentPath.size() >= shortestPath.size()) {
      return;
    }

    // Found valid solution
    if (currentCluesLeft.isEmpty()) {
      if (shortestPath == null || currentPath.size() < shortestPath.size()) {
        shortestPath = new ArrayList<>(currentPath);
      }
      return;
    }

    // Try each valid move
    int[] cur = currentPath.get(currentPath.size() - 1);
    for (int[] nextPos : validMoves(cur)) {
      String nextPosStr = nextPos[0] + "," + nextPos[1];
      if (currentVisited.contains(nextPosStr)) {
        continue; // Don't revisit cells
      }

      // Modify state
      currentPath.add(nextPos);
      currentVisited.add(nextPosStr);
      if (allClues.contains(nextPosStr)) {
        currentCluesLeft.remove(nextPosStr);
      }

      visit();

      // Undo state changes
      currentPath.remove(currentPath.size() - 1);
      currentVisited.remove(nextPosStr);
      if (allClues.contains(nextPosStr)) {
        currentCluesLeft.add(nextPosStr);
      }
    }
  }

  public List<int[]> solve() {
    List<int[]> clues = getClues();
    allClues = new HashSet<>();
    for (int[] clue : clues) {
      allClues.add(clue[0] + "," + clue[1]);
    }
    shortestPath = null;

    // State of the current partial solution
    currentPath = new ArrayList<>();
    currentPath.add(new int[] { 0, 0 });
    currentVisited = new HashSet<>();
    currentVisited.add("0,0");
    currentCluesLeft = new HashSet<>(allClues);
    if (currentCluesLeft.contains("0,0")) {
      currentCluesLeft.remove("0,0");
    }

    visit();
    return shortestPath != null ? shortestPath : new ArrayList<>();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[][] { { 0, 1, 0 },
          { 0, 2, 0 },
          { 0, 0, 2 } },
        new int[][] { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 2, 2 } } },

      // Example 2 from the book
      { new int[][] { { 0, 0, 0 },
          { 2, 1, 2 } },
        new int[][] {} },

      // Example 3 from the book
      { new int[][] { { 0, 0, 1, 2 },
          { 0, 1, 0, 0 } },
        new int[][] {} },

      // single clue
      { new int[][] { { 0, 2 } },
        new int[][] { { 0, 0 }, { 0, 1 } } },

      // no valid path
      { new int[][] { { 0, 1 },
          { 1, 2 } },
        new int[][] {} },

      // multiple clues in a line
      { new int[][] { { 0, 2, 2 } },
        new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 } } },

      // 2x2: cannot reach both clues without revisiting the first square
      { new int[][] { { 0, 2 },
          { 2, 1 } },
        new int[][] {} },

      // Shortest path doesn't start by going to the closest clue
      { new int[][] { { 0, 0, 0, 0, 0 },
          { 1, 1, 0, 1, 1 },
          { 2, 0, 0, 0, 0 },
          { 0, 0, 2, 0, 2 },
          { 0, 0, 0, 0, 0 } },
        new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 },
          { 2, 1 }, { 2, 0 },
          { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 3, 4 } } }
    };

    EscapeWithAllClues solution;
    for (Object[] test : tests) {
      int[][] room = (int[][]) test[0];
      int[][] want = (int[][]) test[1];
      solution = new EscapeWithAllClues(room);
      List<int[]> got = solution.solve();
      if (!Arrays.deepEquals(got.toArray(new int[0][]), want) &&
      !(got.size() > 0 && want.length > 0 && got.size() == want.length)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got: %s, want: %s\n",
        Arrays.deepToString(room),
        Arrays.deepToString(got.toArray(new int[0][])),
        Arrays.deepToString(want)));
      }
    }
  }
}

public class P15_11_EscapeWithAllClues {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
