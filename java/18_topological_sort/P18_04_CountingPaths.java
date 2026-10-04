// 18.4 - Counting Paths
// Run: javac P18_04_CountingPaths.java && java P18_04_CountingPaths

import java.util.*;
import java.util.function.*;

class TopologicalSort {
  public List<Integer> solve(List<List<Integer>> graph) {
    // Initialization
    int V = graph.size();
    int[] inDegrees = new int[V];
    for (int node = 0; node < V; node++) {
      for (int nbr : graph.get(node)) {
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
      for (int nbr : graph.get(node)) {
        inDegrees[nbr]--;
        if (inDegrees[nbr] == 0) {
          degreeZero.add(nbr);
        }
      }
    }
    return topoOrder;
  }
}

class PathCount {
  public int[] solve(List<List<Integer>> graph, int start) {
    List<Integer> topoOrder = new TopologicalSort().solve(graph);

    int[] counts = new int[graph.size()];
    counts[start] = 1;
    for (int node : topoOrder) {
      for (int nbr : graph.get(node)) {
        counts[nbr] += counts[node];
      }
    }
    return counts;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        {
            new int[][] {
                { 1 },
                {},
                { 1 },
                { 4 },
                { 1, 2, 5 },
                { 2 }
            },
            4,
            new int[] { 0, 3, 2, 0, 1, 1 }
        },
        // Edge case: Single node graph
        {
            new int[][] { {} },
            0,
            new int[] { 1 }
        },
        {
            new int[][] { { 1 }, {} },
            0,
            new int[] { 1, 1 }
        },
        {
            new int[][] { { 1 }, { 2 }, {} },
            1,
            new int[] { 0, 1, 1 }
        },
        {
            new int[][] { { 1, 2 }, { 3 }, { 3 }, {} },
            0,
            new int[] { 1, 1, 1, 2 }
        }
    };

    PathCount solution = new PathCount();
    for (Object[] test : tests) {
      int[][] graphArray = (int[][]) test[0];
      List<List<Integer>> graph = Arrays.stream(graphArray)
          .map(row -> Arrays.stream(row)
              .boxed()
              .collect(Collectors.toList()))
          .collect(Collectors.toList());
      int start = (int) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(graph, start);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            graph, start, Arrays.toString(got), Arrays.toString(want)));
      }
    }
  }
}

public class P18_04_CountingPaths {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
