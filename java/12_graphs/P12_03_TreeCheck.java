// 12.3 - Tree Check
// Run: javac P12_03_TreeCheck.java && java P12_03_TreeCheck

import java.util.*;
import java.util.function.*;

class IsTree {
  private Map<Integer, Integer> predecessors;
  private boolean foundCycle;
  private int[][] graph;

  public IsTree(int[][] g) {
    this.graph = g;
    this.foundCycle = false;
    this.predecessors = new HashMap<>();
    this.predecessors.put(0, -1);
  }

  public boolean solve() {
    visit(0);
    boolean connected = predecessors.size() == graph.length;
    return !foundCycle && connected;
  }

  private void visit(int node) {
    if (foundCycle) {
      return;
    }
    for (int nbr : graph[node]) {
      if (!predecessors.containsKey(nbr)) {
        predecessors.put(nbr, node);
        visit(nbr);
      } else if (nbr != predecessors.get(node)) {
        foundCycle = true;
      }
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[][] { { 2 }, { 2, 5 }, { 0, 1, 3, 4 }, { 2 }, { 2 }, { 1 } },
            true },
        // Example 2 from the book
        { new int[][] { { 2 }, { 5 }, { 0, 3 }, { 2 }, {}, { 1 } }, false },
        // Example 3 from the book
        { new int[][] { { 1 }, { 0, 2, 5 }, { 1, 3, 4 }, { 2 }, { 2, 5 },
            { 1, 4 } }, false },
        // Single node
        { new int[][] { {} }, true },
        // Two nodes connected
        { new int[][] { { 1 }, { 0 } }, true },
        // Two nodes disconnected
        { new int[][] { {}, {} }, false },
        // Line graph (valid tree)
        { new int[][] { { 1 }, { 0, 2 }, { 1, 3 }, { 2 } }, true },
        // Cycle
        { new int[][] { { 1, 3 }, { 2, 0 }, { 3, 1 }, { 0, 2 } }, false },
        // Complete graph K4 (not a tree)
        { new int[][] { { 1, 2, 3 }, { 0, 2, 3 }, { 0, 1, 3 }, { 0, 1, 2 } },
            false },
        // Star graph
        { new int[][] { { 1, 2, 3, 4 }, { 0 }, { 0 }, { 0 }, { 0 } }, true },
    };

    for (Object[] test : tests) {
      int[][] graph = (int[][]) test[0];
      boolean want = (boolean) test[1];
      IsTree solution = new IsTree(graph);
      boolean got = solution.solve();
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n",
            java.util.Arrays.deepToString(graph), got, want));
      }
    }
  }
}

public class P12_03_TreeCheck {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
