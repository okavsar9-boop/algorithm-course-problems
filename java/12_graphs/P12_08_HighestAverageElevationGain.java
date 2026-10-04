// 12.8 - Highest Average Elevation Gain
// Run: javac P12_08_HighestAverageElevationGain.java && java P12_08_HighestAverageElevationGain

import java.util.*;
import java.util.function.*;

record Edge(int node1, int node2, int gain) {
}

record WeightedNeighbor(int nodeId, int gain) {
}

class ComponentLabeling {
  Map<Integer, Integer> nodeToComponent;
  int componentCount;

  ComponentLabeling(Map<Integer, Integer> nodeToComponent, int componentCount) {
    this.nodeToComponent = nodeToComponent;
    this.componentCount = componentCount;
  }
}

class HighestAverageElevationGain {
  private List<List<WeightedNeighbor>> buildAdjList(int V, List<Edge> edges) {
    List<List<WeightedNeighbor>> graph = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      graph.add(new ArrayList<>());
    }
    for (Edge edge : edges) {
      graph.get(edge.node1())
      .add(new WeightedNeighbor(edge.node2(), edge.gain()));
      graph.get(edge.node2())
      .add(new WeightedNeighbor(edge.node1(), edge.gain()));
    }
    return graph;
  }

  private void dfs(int node, int ccId, Map<Integer, Integer> ccIds,
  List<List<WeightedNeighbor>> graph) {
    ccIds.put(node, ccId);
    for (WeightedNeighbor nbr : graph.get(node)) {
      if (!ccIds.containsKey(nbr.nodeId())) {
        dfs(nbr.nodeId(), ccId, ccIds, graph);
      }
    }

  }

  private ComponentLabeling labelComponents(
  List<List<WeightedNeighbor>> graph) {
    Map<Integer, Integer> ccIds = new HashMap<>();
    int ccId = 0;
    for (int node = 0; node < graph.size(); node++) {
      if (!ccIds.containsKey(node)) {
        dfs(node, ccId, ccIds, graph);
        ccId++;
      }
    }
    return new ComponentLabeling(ccIds, ccId);
  }

  public double solve(int V, List<Edge> edges) {
    if (edges.isEmpty()) {
      return 0;
    }

    // Build adjacency list with weights
    List<List<WeightedNeighbor>> graph = buildAdjList(V, edges);

    // Label nodes with component IDs
    ComponentLabeling components = labelComponents(graph);
    Map<Integer, Integer> ccIds = components.nodeToComponent;
    int numComponents = components.componentCount;

    // Calculate average gain for each component
    double[] ccGains = new double[numComponents];
    int[] ccEdges = new int[numComponents];
    for (int node = 0; node < V; node++) {
      for (WeightedNeighbor nbr : graph.get(node)) {
        int nbrId = nbr.nodeId();
        int gain = nbr.gain();
        if (node < nbrId) { // Only count each edge once.
          int ccId = ccIds.get(node);
          ccGains[ccId] += gain;
          ccEdges[ccId]++;
        }
      }
    }

    // Find max average gain
    double maxAvg = 0;
    for (int ccId = 0; ccId < numComponents; ccId++) {
      if (ccEdges[ccId] > 0) {
        double avg = ccGains[ccId] / ccEdges[ccId];
        maxAvg = Math.max(maxAvg, avg);
      }
    }

    return maxAvg;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example from the book
        new TestCase(
            4,
            List.of(
                new Edge(0, 1, 3),
                new Edge(1, 2, 2),
                new Edge(2, 3, 1),
                new Edge(3, 0, 2)),
            2.0),

        // Single edge
        new TestCase(
            2,
            List.of(new Edge(0, 1, 5)),
            5.0),

        // No edges
        new TestCase(
            3,
            List.of(),
            0.0),

        // Multiple components
        new TestCase(
            6,
            List.of(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2),
                new Edge(3, 4, 3),
                new Edge(4, 5, 5)),
            4.0),

        // Single node component
        new TestCase(
            3,
            List.of(new Edge(0, 1, 2)),
            2.0)
    };

    HighestAverageElevationGain solution = new HighestAverageElevationGain();
    for (TestCase test : tests) {
      double got = solution.solve(test.V(), test.edges());
      if (Math.abs(got - test.want()) >= 1e-6) {
        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got: %f, want: %f\n",
            test.V(), test.edges(), got, test.want()));
      }
    }
  }
}

public class P12_08_HighestAverageElevationGain {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
