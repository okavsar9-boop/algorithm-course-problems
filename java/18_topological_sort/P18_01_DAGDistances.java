// 18.1 - DAG Distances
// Run: javac P18_01_DAGDistances.java && java P18_01_DAGDistances

import java.util.*;
import java.util.function.*;

record Edge(int node, int weight) {
}

class TopologicalSort {
  public List<Integer> solve(List<List<Edge>> graph) {
    // Initialization
    int V = graph.size();
    int[] inDegrees = new int[V];
    for (int node = 0; node < V; node++) {
      for (Edge edge : graph.get(node)) {
        inDegrees[edge.node()]++;
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
      for (Edge edge : graph.get(node)) {
        inDegrees[edge.node()]--;
        if (inDegrees[edge.node()] == 0) {
          degreeZero.add(edge.node());
        }
      }

    }

    if (topoOrder.size() < V) {
      return new ArrayList<>(); // There is a cycle; some nodes couldn't be
      // peeled off
    }
    return topoOrder;
  }
}

class Distance {
  public double[] solve(List<List<Edge>> graph, int start) {
    List<Integer> topoOrder = new TopologicalSort().solve(graph);

    double[] distances = new double[graph.size()];
    Arrays.fill(distances, Double.POSITIVE_INFINITY);
    distances[start] = 0;
    for (int node : topoOrder) {
      if (distances[node] == Double.POSITIVE_INFINITY)
      continue;
      for (Edge edge : graph.get(node)) {
        if (distances[node] + edge.weight() < distances[edge.node()]) {
          distances[edge.node()] = distances[node] + edge.weight();
        }
      }

    }

    return distances;
  }
}


class RunTests {
  private List<List<Edge>> makeGraph(int[][][] edges) {
    List<List<Edge>> graph = new ArrayList<>();
    for (int i = 0; i < edges.length; i++) {
      graph.add(new ArrayList<>());
      for (int[] edge : edges[i]) {
        graph.get(i).add(new Edge(edge[0], edge[1]));
      }
    }
    return graph;
  }

  public void runTests() {
    Object[][] tests = {
        // Example from the book
        {
            new int[][][] {
                { { 1, 10 } },
                {},
                { { 1, 10 } },
                { { 4, 12 } },
                { { 1, 11 }, { 2, 21 }, { 5, 14 } },
                { { 2, -30 } }
            },
            4,
            new double[] { Double.POSITIVE_INFINITY, -6, -16,
                Double.POSITIVE_INFINITY, 0, 14 }
        },
        // Edge case: Single node graph
        { new int[][][] { {} }, 0, new double[] { 0 } },
        // Edge case: Disconnected graph
        {
            new int[][][] { { { 1, 5 } }, {}, { { 3, 2 } }, {} },
            0,
            new double[] { 0, 5, Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY }
        },
        // Edge case: Graph with negative weights
        {
            new int[][][] { { { 1, -1 } }, { { 2, -2 } }, {} },
            0,
            new double[] { 0, -1, -3 }
        },
        // Edge case: Start node with no outgoing edges
        {
            new int[][][] { { { 1, 2 } }, { { 2, 3 } }, {} },
            2,
            new double[] { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                0 }
        }
    };

    Distance solution = new Distance();
    for (Object[] test : tests) {
      int[][][] edgeArrays = (int[][][]) test[0];
      int start = (int) test[1];
      double[] want = (double[]) test[2];

      List<List<Edge>> graph = makeGraph(edgeArrays);
      double[] got = solution.solve(graph, start);

      if (!Arrays.equals(got, want)) {
        StringBuilder graphStr = new StringBuilder("[");
        for (int i = 0; i < edgeArrays.length; i++) {
          graphStr.append("[");
          for (int j = 0; j < edgeArrays[i].length; j++) {
            if (j > 0)
              graphStr.append(", ");
            graphStr.append(Arrays.toString(edgeArrays[i][j]));
          }
          graphStr.append("]");
          if (i < edgeArrays.length - 1)
            graphStr.append(", ");
        }
        graphStr.append("]");

        StringBuilder gotStr = new StringBuilder("[");
        StringBuilder wantStr = new StringBuilder("[");
        for (int i = 0; i < got.length; i++) {
          if (i > 0) {
            gotStr.append(", ");
            wantStr.append(", ");
          }
          gotStr.append(got[i] == Double.POSITIVE_INFINITY ? "inf" : got[i]);
          wantStr.append(want[i] == Double.POSITIVE_INFINITY ? "inf" : want[i]);
        }
        gotStr.append("]");
        wantStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            graphStr.toString(), start, gotStr.toString(), wantStr.toString()));
      }
    }
  }
}

public class P18_01_DAGDistances {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
