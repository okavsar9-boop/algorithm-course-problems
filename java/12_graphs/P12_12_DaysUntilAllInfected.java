// 12.12 - Days Until All Infected
// Run: javac P12_12_DaysUntilAllInfected.java && java P12_12_DaysUntilAllInfected

import java.util.*;
import java.util.function.*;

class AllInfected {
  public int solve(int[][] graph, int[] infected) {
    Queue<Integer> Q = new ArrayDeque<>();
    Map<Integer, Integer> distances = new HashMap<>();

    // Initialize queue with infected nodes
    for (int start : infected) {
      Q.add(start);
      distances.put(start, 0);
    }

    // Multisource BFS
    while (!Q.isEmpty()) {
      int node = Q.poll();
      for (int nbr : graph[node]) {
        if (!distances.containsKey(nbr)) {
          distances.put(nbr, distances.get(node) + 1);
          Q.add(nbr);
        }
      }
    }

    // If any node wasn't reached, graph is disconnected
    if (distances.size() < graph.length) {
      return -1;
    }

    // Return max distance - that's how many days it takes
    return distances.values().stream().mapToInt(Integer::intValue).max()
        .getAsInt();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        {
            new int[][] {
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
            },
            new int[] { 0, 8, 17 },
            3
        },
        {
            new int[][] { { 1, 2 }, { 0, 2 }, { 0, 1, 3 }, { 2 } },
            new int[] { 0 },
            2
        },
        // Single node graph
        {
            new int[][] { {} },
            new int[] { 0 },
            0
        },
        // Line graph
        {
            new int[][] { { 1 }, { 0, 2 }, { 1, 3 }, { 2 } },
            new int[] { 0 },
            3
        },
        // Multiple initial infected nodes
        {
            new int[][] { { 1, 2 }, { 0, 3 }, { 0, 3 }, { 1, 2 } },
            new int[] { 0, 3 },
            1
        }
    };

    AllInfected solution = new AllInfected();
    for (Object[] test : tests) {
      int[][] graph = (int[][]) test[0];
      int[] infected = (int[]) test[1];
      int want = (int) test[2];
      int got = solution.solve(graph, infected);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            java.util.Arrays.deepToString(graph),
            java.util.Arrays.toString(infected),
            got, want));
      }
    }
  }
}

public class P12_12_DaysUntilAllInfected {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
