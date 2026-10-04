// 18.2 - DAG Path Reconstruction
// Run: javac P18_02_DAGPathReconstruction.java && java P18_02_DAGPathReconstruction

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
      return List.of(); // There is a cycle; some nodes couldn't be peeled off
    }
    return topoOrder;
  }
}

class ShortestPath {
  public List<Integer> solve(List<List<Edge>> graph, int start, int goal) {
    List<Integer> topoOrder = new TopologicalSort().solve(graph);

    Map<Integer, Integer> distances = new HashMap<>();
    Map<Integer, Integer> predecessors = new HashMap<>();
    distances.put(start, 0);

    for (int node : topoOrder) {
      if (!distances.containsKey(node))
      continue;
      for (Edge edge : graph.get(node)) {
        if (!distances.containsKey(edge.node()) ||
        distances.get(node) + edge.weight() < distances.get(edge.node())) {
          distances.put(edge.node(), distances.get(node) + edge.weight());
          predecessors.put(edge.node(), node);
        }
      }
    }

    if (!distances.containsKey(goal)) {
      return List.of();
    }

    List<Integer> path = new ArrayList<>();
    path.add(goal);
    while (path.get(path.size() - 1) != start) {
      path.add(predecessors.get(path.get(path.size() - 1)));
    }
    Collections.reverse(path);
    return path;
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
            4, 1, Arrays.asList(4, 5, 2, 1)),

        // Edge case: Single node graph
        new TestCase(
            Arrays.asList(new ArrayList<>()),
            0, 0, Arrays.asList(0)),

        // Edge case: Disconnected graph
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, 5)),
                new ArrayList<>(),
                Arrays.asList(new Edge(3, 2)),
                new ArrayList<>()),
            0, 3, Arrays.asList()),

        // Edge case: Graph with negative weights
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, -1)),
                Arrays.asList(new Edge(2, -2)),
                new ArrayList<>()),
            0, 2, Arrays.asList(0, 1, 2)),

        // Edge case: Start node with no outgoing edges
        new TestCase(
            Arrays.asList(
                Arrays.asList(new Edge(1, 2)),
                Arrays.asList(new Edge(2, 3)),
                new ArrayList<>()),
            2, 0, Arrays.asList())
    };

    ShortestPath solution = new ShortestPath();
    for (TestCase test : tests) {
      List<Integer> got = solution.solve(test.graph(), test.start(),
          test.goal());
      if (!got.equals(test.want())) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): got: %s, want: %s\n",
            test.graph(), test.start(), test.goal(), got, test.want()));
      }
    }
  }
}

public class P18_02_DAGPathReconstruction {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
