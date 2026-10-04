// 12.10 - Shortest-Path Queries
// Run: javac P12_10_ShortestPathQueries.java && java P12_10_ShortestPathQueries

import java.util.*;
import java.util.function.*;

class ShortestPathQueries {
  public List<List<Integer>> solve(List<List<Integer>> graph, int start,
  List<Integer> queries) {
    Deque<Integer> Q = new ArrayDeque<>();
    Q.add(start);
    Map<Integer, Integer> predecessors = new HashMap<>();
    predecessors.put(start, null);

    while (!Q.isEmpty()) {
      int node = Q.removeFirst();
      for (int nbr : graph.get(node)) {
        if (!predecessors.containsKey(nbr)) {
          predecessors.put(nbr, node);
          Q.add(nbr);
        }
      }
    }

    List<List<Integer>> res = new ArrayList<>();
    for (int node : queries) {
      if (!predecessors.containsKey(node)) {
        res.add(new ArrayList<>());
      } else {
        List<Integer> path = new ArrayList<>();
        path.add(node);
        while (path.get(path.size() - 1) != start) {
          path.add(predecessors.get(path.get(path.size() - 1)));
        }
        List<Integer> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
          reversedPath.add(path.get(i));
        }
        res.add(reversedPath);
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        {
            new int[][] {
                { 1 },
                { 0, 2, 5, 4 },
                { 1, 4, 5 },
                {},
                { 5, 2, 1 },
                { 1, 2, 4 }
            },
            0,
            new int[] { 1, 0, 3, 4 },
            new int[][] {
                { 0, 1 },
                { 0 },
                {},
                { 0, 1, 4 }
            }
        },
        // Simple line graph
        {
            new int[][] {
                { 1 },
                { 0, 2 },
                { 1 }
            },
            0,
            new int[] { 1, 2 },
            new int[][] {
                { 0, 1 },
                { 0, 1, 2 }
            }
        },
        // Disconnected components
        {
            new int[][] {
                { 1 },
                { 0 },
                { 3 },
                { 2 }
            },
            0,
            new int[] { 1, 2, 3 },
            new int[][] {
                { 0, 1 },
                {},
                {}
            }
        },
        // Complete graph
        {
            new int[][] {
                { 1, 2 },
                { 0, 2 },
                { 0, 1 }
            },
            0,
            new int[] { 1, 2 },
            new int[][] {
                { 0, 1 },
                { 0, 2 }
            }
        },
        // Single node
        {
            new int[][] {
                {}
            },
            0,
            new int[] { 0 },
            new int[][] {
                { 0 }
            }
        },
        // Empty queries
        {
            new int[][] {
                { 1 },
                { 0 }
            },
            0,
            new int[] {},
            new int[][] {}
        }
    };

    ShortestPathQueries solution = new ShortestPathQueries();
    for (Object[] test : tests) {
      int[][] graphArray = (int[][]) test[0];
      int start = (int) test[1];
      int[] queriesArray = (int[]) test[2];
      int[][] wantArray = (int[][]) test[3];

      List<List<Integer>> graph = Arrays.stream(graphArray)
          .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());
      List<Integer> queries = Arrays.stream(queriesArray).boxed()
          .collect(Collectors.toList());
      List<List<Integer>> want = Arrays.stream(wantArray)
          .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());

      List<List<Integer>> got = solution.solve(graph, start, queries);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %s): got: %s, want: %s\n",
            graph, start, queries, got, want));
      }
    }
  }
}

public class P12_10_ShortestPathQueries {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
