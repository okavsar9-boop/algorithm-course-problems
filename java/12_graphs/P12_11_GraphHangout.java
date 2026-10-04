// 12.11 - Graph Hangout
// Run: javac P12_11_GraphHangout.java && java P12_11_GraphHangout

import java.util.*;
import java.util.function.*;

class WalkingDistanceToCoffee {

  private Map<Integer, Integer> bfs(int[][] graph, int start) {
    Queue<Integer> Q = new ArrayDeque<>();
    Q.add(start);
    Map<Integer, Integer> distances = new HashMap<>();
    distances.put(start, 0);
    while (!Q.isEmpty()) {
      int node = Q.poll();
      for (int nbr : graph[node]) {
        if (!distances.containsKey(nbr)) {
          distances.put(nbr, distances.get(node) + 1);
          Q.add(nbr);
        }
      }
    }
    return distances;
  }

  public int solve(int[][] graph, int node1, int node2, int node3) {
    Map<Integer, Integer> distances1 = bfs(graph, node1);
    Map<Integer, Integer> distances2 = bfs(graph, node2);
    Map<Integer, Integer> distances3 = bfs(graph, node3);
    int res = Integer.MAX_VALUE;

    for (int i = 0; i < graph.length; i++) {
      res = Math.min(res,
          distances1.get(i) + distances2.get(i) + distances3.get(i));
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[][] {
            { 1, 14 }, // 0: Outer ring connections
            { 0, 2 }, // 1
            { 1, 3 }, // 2
            { 2, 4 }, // 3
            { 3, 5, 19 }, // 4: Connector from outer to inner ring
            { 4, 6 }, // 5
            { 5, 7 }, // 6
            { 6, 8 }, // 7
            { 7, 9, 21 }, // 8: Connector from outer to inner ring
            { 8, 10 }, // 9
            { 9, 11 }, // 10
            { 10, 12 }, // 11
            { 11, 13 }, // 12
            { 12, 14 }, // 13
            { 0, 13, 15 }, // 14: Connector from outer to inner ring
            { 14, 16 }, // 15
            { 15, 17 }, // 16
            { 16, 18, 20 }, // 17: Center node connections
            { 17, 19 }, // 18
            { 18, 4 }, // 19
            { 17, 21 }, // 20
            { 8, 20 } // 21
        }, 14, 4, 8, 9 },
        // Cycle with 5 nodes
        { new int[][] { { 1, 4 }, { 0, 2 }, { 1, 3 }, { 2, 4 }, { 0, 3 } }, 0,
            2, 4, 3 },
        // Simple line graph
        { new int[][] { { 1 }, { 0, 2 }, { 1 } }, 0, 1, 2, 2 },
        // Star graph - optimal meeting point is center
        { new int[][] { { 1 }, { 0, 2, 3, 4 }, { 1 }, { 1 }, { 1 } }, 0, 2, 3,
            3 },
        // Complete graph - can meet at any node
        { new int[][] { { 1, 2, 3 }, { 0, 2, 3 }, { 0, 1, 3 }, { 0, 1, 2 } }, 0,
            1, 2, 2 },
        // Edge case - all start at same node
        { new int[][] { { 1 }, { 0 } }, 0, 0, 0, 0 },
        // Edge case - two start at same node
        { new int[][] { { 1 }, { 0, 2 }, { 1 } }, 0, 0, 2, 2 },
    };

    WalkingDistanceToCoffee solution = new WalkingDistanceToCoffee();
    for (Object[] test : tests) {
      int[][] graph = (int[][]) test[0];
      int node1 = (int) test[1];
      int node2 = (int) test[2];
      int node3 = (int) test[3];
      int want = (int) test[4];
      int got = solution.solve(graph, node1, node2, node3);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d, %d): got: %d, want: %d\n",
            java.util.Arrays.deepToString(graph), node1, node2, node3, got,
            want));
      }
    }
  }
}

public class P12_11_GraphHangout {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
