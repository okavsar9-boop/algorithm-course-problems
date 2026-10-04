// 18.6 - Longest Path of Increasing Degrees
// Run: javac P18_06_LongestPathOfIncreasingDegrees.java && java P18_06_LongestPathOfIncreasingDegrees

import java.util.*;
import java.util.function.*;

class LongestPathOfIncreasingDegrees {
  private List<List<Integer>> graph;

  private List<Integer> dagNeighbors(int node) {
    List<Integer> nbrs = new ArrayList<>();
    for (int nbr : graph.get(node)) {
      if (graph.get(nbr).size() > graph.get(node).size()) {
        nbrs.add(nbr);
      }
    }
    return nbrs;
  }

  private List<Integer> topologicalSort() {
    // Initialization
    int V = graph.size();
    int[] inDegrees = new int[V];
    for (int node = 0; node < V; node++) {
      for (int nbr : dagNeighbors(node)) {
        inDegrees[nbr]++;
      }
    }

    List<Integer> degreeZero = new ArrayList<>();
    for (int node = 0; node < V; node++) {
      if (inDegrees[node] == 0) {
        degreeZero.add(node);
      }
    }

    // Main 'peel-off' loop
    List<Integer> topoOrder = new ArrayList<>();
    while (!degreeZero.isEmpty()) {
      int node = degreeZero.remove(degreeZero.size() - 1);
      topoOrder.add(node);
      for (int nbr : dagNeighbors(node)) {
        inDegrees[nbr]--;
        if (inDegrees[nbr] == 0) {
          degreeZero.add(nbr);
        }
      }
    }

    return topoOrder;
  }

  public int solve(int V, List<int[]> edges) {
    graph = new ArrayList<>(V);
    for (int i = 0; i < V; i++) {
      graph.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
      graph.get(edge[0]).add(edge[1]);
      graph.get(edge[1]).add(edge[0]);
    }

    List<Integer> topoOrder = topologicalSort();

    int[] lengths = new int[V];
    for (int node : topoOrder) {
      for (int nbr : dagNeighbors(node)) {
        if (lengths[node] + 1 > lengths[nbr]) {
          lengths[nbr] = lengths[node] + 1;
        }
      }
    }

    int maxLength = 0;
    for (int length : lengths) {
      maxLength = Math.max(maxLength, length);
    }
    return maxLength;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example from the book.
        new TestCase(
            8,
            Arrays.asList(
                new int[] { 0, 1 }, new int[] { 1, 2 }, new int[] { 2, 3 },
                new int[] { 0, 2 },
                new int[] { 0, 4 }, new int[] { 2, 6 }, new int[] { 3, 7 },
                new int[] { 2, 7 },
                new int[] { 4, 5 }, new int[] { 5, 6 }, new int[] { 6, 7 }),
            2),
        // Edge case: Single node.
        new TestCase(1, Arrays.asList(), 0),
        // Can do 0 -> 1 or 2 -> 1.
        new TestCase(3, Arrays.asList(new int[] { 0, 1 }, new int[] { 1, 2 }),
            1),
        // Cycle graph.
        new TestCase(
            4,
            Arrays.asList(
                new int[] { 0, 1 }, new int[] { 1, 2 }, new int[] { 2, 3 },
                new int[] { 3, 0 }),
            0),
        // Star graph.
        new TestCase(
            4,
            Arrays.asList(new int[] { 0, 1 }, new int[] { 0, 2 },
                new int[] { 0, 3 }),
            1),
        // Can do 3 -> 2 -> 1 -> 0.
        new TestCase(
            10,
            Arrays.asList(
                new int[] { 0, 1 }, new int[] { 1, 2 }, new int[] { 2, 3 },
                new int[] { 2, 4 },
                new int[] { 3, 5 }, new int[] { 3, 6 }, new int[] { 3, 7 }),
            3)
    };

    LongestPathOfIncreasingDegrees solution = new LongestPathOfIncreasingDegrees();
    for (TestCase test : tests) {
      int got = solution.solve(test.V(), test.edges());
      if (got != test.want()) {
        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got: %d, want: %d\n",
            test.V(), Arrays.deepToString(test.edges().toArray()), got,
            test.want()));
      }
    }
  }
}

public class P18_06_LongestPathOfIncreasingDegrees {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
