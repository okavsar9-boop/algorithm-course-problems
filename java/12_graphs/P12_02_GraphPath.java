// 12.2 - Graph Path
// Run: javac P12_02_GraphPath.java && java P12_02_GraphPath

import java.util.*;
import java.util.function.*;

class Path {
  private Map<Integer, Integer> predecessors;

  private void visit(int node, List<List<Integer>> graph) {
    for (int nbr : graph.get(node)) {
      if (!predecessors.containsKey(nbr)) {
        predecessors.put(nbr, node);
        visit(nbr, graph);
      }
    }
  }

  public List<Integer> solve(List<List<Integer>> graph, int node1, int node2) {
    predecessors = new HashMap<>();
    predecessors.put(node2, null);

    visit(node2, graph);

    if (!predecessors.containsKey(node1)) {
      return new ArrayList<>();
    }

    List<Integer> path = new ArrayList<>();
    path.add(node1);
    while (path.get(path.size() - 1) != node2) {
      path.add(predecessors.get(path.get(path.size() - 1)));
    }
    return path;
  }
}

def graph_BFS(graph, start):
  Q = Queue()
  Q.push(start)
  distances = {start: 0}
  while not Q.empty():
    node = Q.pop()
    for nbr in graph[node]:
      if nbr not in distances:
        distances[nbr] = distances[node] + 1
        Q.push(nbr)

  # Do something with distances.

class PathBfs {
  public List<Integer> solve(List<List<Integer>> graph, int node1, int node2) {
    Queue<Integer> Q = new ArrayDeque<>();
    Q.add(node2);
    Map<Integer, Integer> predecessors = new HashMap<>();
    predecessors.put(node2, null);
    while (!Q.isEmpty()) {
      int node = Q.poll();
      for (int nbr : graph.get(node)) {
        if (!predecessors.containsKey(nbr)) {
          predecessors.put(nbr, node);
          Q.add(nbr);
        }
      }
    }
    if (!predecessors.containsKey(node1)) {
      return new ArrayList<>();
    }
    List<Integer> path = new ArrayList<>();
    path.add(node1);
    while (path.get(path.size() - 1) != node2) {
      path.add(predecessors.get(path.get(path.size() - 1)));
    }
    return path;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book - graph from Figure 8
        {
            new int[][] {
                { 1 },
                { 0, 2, 5, 4 },
                { 1, 4, 5 },
                {},
                { 5, 2, 1 },
                { 1, 2, 4 }
            },
            0, 4,
            new int[] { 0, 1, 4 }
        },
        // Example 2 from book - graph from Figure 8, no path exists
        {
            new int[][] {
                { 1 },
                { 0, 2, 5, 4 },
                { 1, 4, 5 },
                {},
                { 5, 2, 1 },
                { 1, 2, 4 }
            },
            0, 3,
            new int[] {}
        },
        // Simple line graph
        {
            new int[][] {
                { 1 },
                { 0, 2 },
                { 1 }
            },
            0, 2,
            new int[] { 0, 1, 2 }
        },
        // Cycle graph
        {
            new int[][] {
                { 1, 3 },
                { 0, 2 },
                { 1, 3 },
                { 0, 2 }
            },
            0, 2,
            new int[] { 0, 1, 2 }
        },
        // Disconnected graph
        {
            new int[][] {
                { 1 },
                { 0 },
                { 3 },
                { 2 }
            },
            0, 2,
            new int[] {}
        },
        // Complete graph
        {
            new int[][] {
                { 1, 2 },
                { 0, 2 },
                { 0, 1 }
            },
            0, 2,
            new int[] { 0, 2 }
        }
    };

    Path solution = new Path();
    PathBfs solutionBfs = new PathBfs();

    for (Object[] test : tests) {
      int[][] adjList = (int[][]) test[0];
      int node1 = (int) test[1];
      int node2 = (int) test[2];
      int[] want = (int[]) test[3];

      // Convert adjacency list array to List<List<Integer>>
      List<List<Integer>> graph = new ArrayList<>();
      for (int[] neighbors : adjList) {
        List<Integer> nbrList = new ArrayList<>();
        for (int nbr : neighbors) {
          nbrList.add(nbr);
        }
        graph.add(nbrList);
      }

      List<Integer> got = solution.solve(graph, node1, node2);
      List<Integer> gotBfs = solutionBfs.solve(graph, node1, node2);

      // Convert want array to List for comparison
      List<Integer> wantList = new ArrayList<>();
      for (int node : want) {
        wantList.add(node);
      }

      // For this problem, there can be multiple valid paths
      // So we need to verify:
      // 1. If want is empty, got should be empty
      // 2. If want is not empty:
      // - got should start with node1 and end with node2
      // - got should be a valid path in the graph
      // - got should not have duplicates
      if (wantList.isEmpty()) {
        if (!got.isEmpty()) {
          throw new RuntimeException(String.format(
              "\nsolve(%s, %d, %d): got: %s, want empty path\n",
              graph, node1, node2, got));
        }
        if (!gotBfs.isEmpty()) {
          throw new RuntimeException(String.format(
              "\nsolve(%s, %d, %d): got: %s, want empty path\n",
              graph, node1, node2, gotBfs));
        }
        continue;
      }

      if (got.get(0) != node1 || got.get(got.size() - 1) != node2) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): path %s should start with %d and end with %d\n",
            graph, node1, node2, got, node1, node2));
      }
      if (gotBfs.get(0) != node1 || gotBfs.get(gotBfs.size() - 1) != node2) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): path %s should start with %d and end with %d\n",
            graph, node1, node2, gotBfs, node1, node2));
      }

      // Verify path is valid
      for (int i = 0; i < got.size() - 1; i++) {
        if (!graph.get(got.get(i)).contains(got.get(i + 1))) {
          throw new RuntimeException(String.format(
              "\nsolve(%s, %d, %d): invalid path %s - no edge between %d and %d\n",
              graph, node1, node2, got, got.get(i), got.get(i + 1)));
        }
      }
      for (int i = 0; i < gotBfs.size() - 1; i++) {
        if (!graph.get(gotBfs.get(i)).contains(gotBfs.get(i + 1))) {
          throw new RuntimeException(String.format(
              "\nsolve(%s, %d, %d): invalid path %s - no edge between %d and %d\n",
              graph, node1, node2, gotBfs, gotBfs.get(i), gotBfs.get(i + 1)));
        }
      }

      // Verify no duplicates
      Set<Integer> seen = new HashSet<>(got);
      if (seen.size() != got.size()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): path %s contains duplicates\n",
            graph, node1, node2, got));
      }
      Set<Integer> seenBfs = new HashSet<>(gotBfs);
      if (seenBfs.size() != gotBfs.size()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): path %s contains duplicates\n",
            graph, node1, node2, gotBfs));
      }
    }
  }
}

public class P12_02_GraphPath {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
