// 12.4 - Spanning Tree
// Run: javac P12_04_SpanningTree.java && java P12_04_SpanningTree

import java.util.*;
import java.util.function.*;

class SpanningTree {
  private Set<Integer> visited;
  private Map<Integer, Integer> predecessors;

  public List<List<Integer>> solve(List<List<Integer>> graph) {
    visited = new HashSet<>();
    predecessors = new HashMap<>();

    // Start DFS from node 0
    dfs(0, graph);

    // Convert predecessors map to list of edges
    List<List<Integer>> edges = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : predecessors.entrySet()) {
      int node = entry.getKey();
      int pred = entry.getValue();
      edges.add(List.of(pred, node));
    }

    return edges;
  }

  private void dfs(int node, List<List<Integer>> graph) {
    visited.add(node);
    for (int nbr : graph.get(node)) {
      if (!visited.contains(nbr)) {
        predecessors.put(nbr, node);
        dfs(nbr, graph);
      }
    }
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
      // Example from the book
      new TestCase(List.of(
      List.of(1),
      List.of(0, 2, 5),
      List.of(1, 3, 4),
      List.of(2),
      List.of(2, 5),
      List.of(1, 4))),
      // Single edge
      new TestCase(List.of(
      List.of(1),
      List.of(0))),
      // Line graph
      new TestCase(List.of(
      List.of(1),
      List.of(0, 2),
      List.of(1))),
      // Star graph
      new TestCase(List.of(
      List.of(1, 2, 3),
      List.of(0),
      List.of(0),
      List.of(0))),
      // Complete graph
      new TestCase(List.of(
      List.of(1, 2),
      List.of(0, 2),
      List.of(0, 1))),
      // Single node graph
      new TestCase(List.of(List.of()))
    };

    SpanningTree solution = new SpanningTree();
    for (TestCase test : tests) {
      List<List<Integer>> got = solution.solve(test.graph());

      // Since there can be multiple valid spanning trees,
      // we check that:
      // 1. We have V-1 edges
      // 2. Each edge connects valid nodes
      // 3. The edges form a tree (no cycles)
      int V = test.graph().size();
      if (got.size() != V - 1) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got wrong number of edges: %s",
        test.graph(), got));
      }

      // Check edges are valid
      for (List<Integer> edge : got) {
        int u = edge.get(0), v = edge.get(1);
        if (u < 0 || u >= V || v < 0 || v >= V) {
          throw new RuntimeException(String.format(
          "\nsolve(%s): invalid node in edge %s",
          test.graph(), edge));
        }
        if (!test.graph().get(u).contains(v)
        || !test.graph().get(v).contains(u)) {
          throw new RuntimeException(String.format(
          "\nsolve(%s): invalid edge %s",
          test.graph(), edge));
        }
      }

      // Check no cycles by counting reachable nodes
      List<List<Integer>> adj = new ArrayList<>();
      for (int i = 0; i < V; i++) {
        adj.add(new ArrayList<>());
      }
      for (List<Integer> edge : got) {
        int u = edge.get(0), v = edge.get(1);
        adj.get(u).add(v);
        adj.get(v).add(u);
      }

      Set<Integer> visited = new HashSet<>();
      if (!dfsCheck(0, -1, visited, adj) || visited.size() != V) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): edges do not form a tree: %s",
        test.graph(), got));
      }
    }
  }

  private boolean dfsCheck(int node, int parent, Set<Integer> visited,
  List<List<Integer>> adj) {
    visited.add(node);
    for (int nbr : adj.get(node)) {
      if (nbr != parent) {
        if (visited.contains(nbr)) {
          return false;
        }
        if (!dfsCheck(nbr, node, visited, adj)) {
          return false;
        }
      }
    }
    return true;
  }
}

public class P12_04_SpanningTree {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
