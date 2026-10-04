// 18.5 - Parallel Compilation
// Run: javac P18_05_ParallelCompilation.java && java P18_05_ParallelCompilation

import java.util.*;
import java.util.function.*;

class TopologicalSort {
  public List<Integer> solve(List<List<Integer>> graph) {
    // Initialization
    int V = graph.size();
    int[] inDegrees = new int[V];
    for (int node = 0; node < V; node++) {
      for (int nbr : graph.get(node)) {
        inDegrees[nbr]++;
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
      for (int nbr : graph.get(node)) {
        inDegrees[nbr]--;
        if (inDegrees[nbr] == 0) {
          degreeZero.add(nbr);
        }
      }
    }
    return topoOrder;
  }
}

class CompileTime {
  public int solve(int[] seconds, List<List<Integer>> imports) {
    int V = seconds.length;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      graph.add(new ArrayList<>());
    }
    for (int pkg = 0; pkg < V; pkg++) {
      for (int importedPkg : imports.get(pkg)) {
        graph.get(importedPkg).add(pkg);
      }
    }

    List<Integer> topoOrder = new TopologicalSort().solve(graph); // Recipe 1.
    Map<Integer, Integer> durations = new HashMap<>();
    for (int node : topoOrder) {
      if (!durations.containsKey(node)) {
        durations.put(node, seconds[node]);
      }
      for (int nbr : graph.get(node)) {
        if (!durations.containsKey(nbr)) {
          durations.put(nbr, 0);
        }
        durations.put(nbr, Math.max(
            durations.get(nbr),
            seconds[nbr] + durations.get(node)));
      }
    }
    return Collections.max(durations.values());
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example from the book
        new TestCase(
            new int[] { 10, 20, 30 },
            Arrays.asList(
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                Arrays.asList(0, 1)),
            50),
        // Example from the book
        new TestCase(
            new int[] { 10, 20, 30 },
            Arrays.asList(
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>()),
            30),
        // Single package
        new TestCase(
            new int[] { 10 },
            Arrays.asList(new ArrayList<Integer>()),
            10),
        // Linear dependency
        new TestCase(
            new int[] { 10, 20, 30 },
            Arrays.asList(
                Arrays.asList(1),
                Arrays.asList(2),
                new ArrayList<Integer>()),
            60),
        // Complex dependencies
        new TestCase(
            new int[] { 5, 10, 15, 20 },
            Arrays.asList(
                new ArrayList<Integer>(),
                Arrays.asList(0),
                Arrays.asList(1),
                Arrays.asList(0, 2)),
            50)
    };

    CompileTime solution = new CompileTime();
    for (TestCase test : tests) {
      int got = solution.solve(test.seconds(), test.imports());
      if (got != test.want()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            Arrays.toString(test.seconds()), test.imports(), got, test.want()));
      }
    }
  }
}

public class P18_05_ParallelCompilation {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
