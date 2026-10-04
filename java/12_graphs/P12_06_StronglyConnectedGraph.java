// 12.6 - Strongly Connected Graph
// Run: javac P12_06_StronglyConnectedGraph.java && java P12_06_StronglyConnectedGraph

import java.util.*;
import java.util.function.*;

class StronglyConnected {
  private void visit(List<List<Integer>> graph, Set<Integer> visited,
  int node) {
    if (visited.contains(node)) {
      return;
    }
    visited.add(node);
    for (int nbr : graph.get(node)) {
      visit(graph, visited, nbr);
    }
  }

  public boolean solve(List<List<Integer>> graph) {
    int V = graph.size();
    Set<Integer> visited = new HashSet<>();
    visit(graph, visited, 0);
    if (visited.size() < V) {
      return false;
    }

    List<List<Integer>> reverseGraph = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      reverseGraph.add(new ArrayList<>());
    }
    for (int node = 0; node < V; node++) {
      for (int nbr : graph.get(node)) {
        reverseGraph.get(nbr).add(node);
      }
    }

    Set<Integer> reverseVisited = new HashSet<>();
    visit(reverseGraph, reverseVisited, 0);
    return reverseVisited.size() == V;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example strongly connected
        new TestCase(
            List.of(List.of(1), List.of(2), List.of(0)),
            true),
        // Example not strongly connected
        new TestCase(
            List.of(List.of(1), List.of(2), List.of()),
            false),
        // Single node
        new TestCase(
            List.of(List.of()),
            true),
        // Two nodes, strongly connected
        new TestCase(
            List.of(List.of(1), List.of(0)),
            true),
        // Two nodes, not strongly connected
        new TestCase(
            List.of(List.of(1), List.of()),
            false),
        // Cycle of 4 nodes
        new TestCase(
            List.of(List.of(1), List.of(2), List.of(3), List.of(0)),
            true),
        // Almost cycle of 4 nodes, missing one edge
        new TestCase(
            List.of(List.of(1), List.of(2), List.of(3), List.of()),
            false),
        // Complete graph
        new TestCase(
            List.of(List.of(1, 2), List.of(0, 2), List.of(0, 1)),
            true)
    };

    StronglyConnected solution = new StronglyConnected();
    for (TestCase test : tests) {
      boolean got = solution.solve(test.graph());
      if (got != test.want()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n",
            test.graph(), got, test.want()));
      }
    }
  }
}

public class P12_06_StronglyConnectedGraph {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
