// 12.5 - Reachability Queries
// Run: javac P12_05_ReachabilityQueries.java && java P12_05_ReachabilityQueries

import java.util.*;
import java.util.function.*;

class ConnectedComponentQueries {
  private Map<Integer, Integer> nodeToCc;

  private void visit(List<List<Integer>> graph, int node, int ccId) {
    if (nodeToCc.containsKey(node)) {
      return;
    }
    nodeToCc.put(node, ccId);
    for (int nbr : graph.get(node)) {
      visit(graph, nbr, ccId);
    }
  }

  public List<Boolean> solve(List<List<Integer>> graph,
  List<List<Integer>> queries) {
    nodeToCc = new HashMap<>();

    int ccId = 0;
    for (int node = 0; node < graph.size(); node++) {
      if (!nodeToCc.containsKey(node)) {
        visit(graph, node, ccId);
        ccId++;
      }
    }

    Boolean[] res = new Boolean[queries.size()];
    for (int i = 0; i < queries.size(); i++) {
      int node1 = queries.get(i).get(0);
      int node2 = queries.get(i).get(1);
      res[i] = nodeToCc.get(node1).equals(nodeToCc.get(node2));
    }
    return Arrays.asList(res);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Cycle graph with 6 nodes
        { new int[][] {
            { 1, 5 },
            { 0, 2, 4 },
            { 1, 3, 5 },
            { 2, 4 },
            { 1, 3, 5 },
            { 0, 2, 4 }
        },
            new int[][] {
                { 0, 4 },
                { 0, 3 }
            },
            new boolean[] { true, true }
        },
        // Example
        { new int[][] {
            { 1 },
            { 0, 2, 5, 4 },
            { 1, 4, 5 },
            {},
            { 5, 2, 1 },
            { 1, 2, 4 }
        },
            new int[][] {
                { 0, 4 },
                { 0, 3 }
            },
            new boolean[] { true, false }
        },
        // Simple line graph
        { new int[][] {
            { 1 },
            { 0, 2 },
            { 1 }
        },
            new int[][] {
                { 0, 2 },
                { 0, 1 }
            },
            new boolean[] { true, true }
        },
        // Disconnected components
        { new int[][] {
            { 1 },
            { 0 },
            { 3 },
            { 2 }
        },
            new int[][] {
                { 0, 1 },
                { 0, 2 },
                { 2, 3 }
            },
            new boolean[] { true, false, true }
        },
        // Complete graph
        { new int[][] {
            { 1, 2 },
            { 0, 2 },
            { 0, 1 }
        },
            new int[][] {
                { 0, 1 },
                { 1, 2 },
                { 0, 2 }
            },
            new boolean[] { true, true, true }
        },
        // Single node
        { new int[][] {
            {}
        },
            new int[][] {
                { 0, 0 }
            },
            new boolean[] { true }
        },
        // Empty queries
        { new int[][] {
            { 1 },
            { 0 }
        },
            new int[][] {},
            new boolean[] {}
        }
    };

    ConnectedComponentQueries solution = new ConnectedComponentQueries();
    for (Object[] test : tests) {
      int[][] graphArray = (int[][]) test[0];
      int[][] queriesArray = (int[][]) test[1];
      boolean[] wantArray = (boolean[]) test[2];

      List<List<Integer>> graph = Arrays.stream(graphArray)
          .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());
      List<List<Integer>> queries = Arrays.stream(queriesArray)
          .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());
      List<Boolean> want = new ArrayList<>();
      for (boolean b : wantArray) {
        want.add(b);
      }

      List<Boolean> got = solution.solve(graph, queries);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            graph, queries, got, want));
      }
    }
  }
}

public class P12_05_ReachabilityQueries {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
