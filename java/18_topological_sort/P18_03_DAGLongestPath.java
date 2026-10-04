// 18.3 - DAG Longest Path
// Run: javac P18_03_DAGLongestPath.java && java P18_03_DAGLongestPath

import java.util.*;
import java.util.function.*;

record Edge(int node, int weight) {
}

class TopologicalSort {
  public List<Integer> solve(List<List<Edge>> graph) {
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
      return new ArrayList<>();
    }
    return topoOrder;
  }
}

class LongestPath {
  public int[] solve(List<List<Edge>> graph, int start) {
    TopologicalSort topoSort = new TopologicalSort();
    List<Integer> topoOrder = topoSort.solve(graph);

    int[] lengths = new int[graph.size()];
    Arrays.fill(lengths, Integer.MIN_VALUE);
    lengths[start] = 0;
    for (int node : topoOrder) {
      if (lengths[node] == Integer.MIN_VALUE)
      continue;
      for (Edge edge : graph.get(node)) {
        if (lengths[node] + edge.weight() > lengths[edge.node()]) {
          lengths[edge.node()] = lengths[node] + edge.weight();
        }
      }
    }

    return lengths;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example from the book
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, 10)),
                new ArrayList<>(),
                Arrays.asList(new Edge(1, 10)),
                Arrays.asList(new Edge(4, 12)),
                Arrays.asList(new Edge(1, 11), new Edge(2, 21),
                    new Edge(5, 14)),
                Arrays.asList(new Edge(2, -30))),
            4,
            new int[] { Integer.MIN_VALUE, 31, 21, Integer.MIN_VALUE, 0, 14 }),

        // Edge case: Single node graph
        new TestCase(
            Arrays.asList(new ArrayList<>()),
            0,
            new int[] { 0 }),

        // Edge case: Disconnected graph
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, 5)),
                new ArrayList<>(),
                Arrays.asList(new Edge(3, 2)),
                new ArrayList<>()),
            0,
            new int[] { 0, 5, Integer.MIN_VALUE, Integer.MIN_VALUE }),

        // Edge case: Graph with negative weights
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, -1)),
                Arrays.asList(new Edge(2, -2)),
                new ArrayList<>()),
            0,
            new int[] { 0, -1, -3 }),

        // Edge case: Start node with no outgoing edges
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, 2)),
                Arrays.asList(new Edge(2, 3)),
                new ArrayList<>()),
            2,
            new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, 0 })
    };

    LongestPath solution = new LongestPath();
    for (TestCase test : tests) {
      int[] got = solution.solve(test.graph(), test.start());
      if (!Arrays.equals(got, test.want())) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            test.graph(), test.start(), Arrays.toString(got),
            Arrays.toString(test.want())));
      }
    }
  }
}

public class P18_03_DAGLongestPath {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
