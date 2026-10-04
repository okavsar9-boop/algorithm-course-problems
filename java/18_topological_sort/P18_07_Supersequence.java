// 18.7 - Supersequence
// Run: javac P18_07_Supersequence.java && java P18_07_Supersequence

import java.util.*;
import java.util.function.*;

class CanFormSupersequence {
  private boolean hasCycle(Map<Character, Set<Character>> graph) {
    // Use topological sort to return whether there is a cycle.

    // Initialization
    Map<Character, Integer> inDegree = new HashMap<>();
    for (char node : graph.keySet()) {
      inDegree.put(node, 0);
    }
    for (char node : graph.keySet()) {
      for (char nbr : graph.get(node)) {
        inDegree.put(nbr, inDegree.get(nbr) + 1);
      }
    }
    List<Character> degreeZero = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
      if (entry.getValue() == 0) {
        degreeZero.add(entry.getKey());
      }
    }

    // Main 'peel-off' loop
    List<Character> topoOrder = new ArrayList<>();
    while (!degreeZero.isEmpty()) {
      char node = degreeZero.remove(degreeZero.size() - 1);
      topoOrder.add(node);
      for (char nbr : graph.get(node)) {
        inDegree.put(nbr, inDegree.get(nbr) - 1);
        if (inDegree.get(nbr) == 0) {
          degreeZero.add(nbr);
        }
      }
    }

    return topoOrder.size() != graph.size();
  }

  public boolean solve(String[] arr) {
    // Build the graph
    Map<Character, Set<Character>> graph = new HashMap<>();
    for (String word : arr) {
      for (char c : word.toCharArray()) {
        if (!graph.containsKey(c)) {
          graph.put(c, new HashSet<>());
        }
      }
    }
    for (String word : arr) {
      for (int i = 0; i < word.length() - 1; i++) {
        graph.get(word.charAt(i)).add(word.charAt(i + 1));
      }
    }

    // Check for cycles using topological sort
    return !hasCycle(graph);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new String[] { "abc", "bde", "df", "cfe" }, true },
        // Cycle present
        { new String[] { "ab", "ba" }, false },
        // Edge case: Single letter
        { new String[] { "a" }, true },
        // Edge case: Empty array
        { new String[] {}, true },
        // Multiple words with no dependencies
        { new String[] { "a", "b", "c" }, true },
        // Long chain
        { new String[] { "ab", "bc", "cd", "de", "ef", "fg" }, true },
        // Cycle
        { new String[] { "abc", "bcd", "cda" }, false },
        // Same letter multiple times
        { new String[] { "aba", "bab" }, false },
    };

    CanFormSupersequence solution = new CanFormSupersequence();
    for (Object[] test : tests) {
      String[] arr = (String[]) test[0];
      boolean want = (boolean) test[1];
      boolean got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P18_07_Supersequence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
