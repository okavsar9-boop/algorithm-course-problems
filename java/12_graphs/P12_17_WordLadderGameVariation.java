// 12.17 - Word Ladder Game Variation
// Run: javac P12_17_WordLadderGameVariation.java && java P12_17_WordLadderGameVariation

import java.util.*;
import java.util.function.*;

class WordLadderGame {
  private boolean canTransform(String word1, String word2) {
    if (Math.abs(word1.length() - word2.length()) != 1) {
      return false;
    }

    // Make word1 the shorter word
    if (word1.length() > word2.length()) {
      String temp = word1;
      word1 = word2;
      word2 = temp;
    }

    // Try removing each letter from word2
    for (int i = 0; i < word2.length(); i++) {
      if ((word2.substring(0, i) + word2.substring(i + 1)).equals(word1)) {
        return true;
      }
    }
    return false;
  }

  private Map<String, List<String>> buildGraph(List<String> words, int l1,
  int l2) {
    Map<String, List<String>> graph = new HashMap<>();

    // Initialize nodes
    for (String word : words) {
      if (word.length() == l1 || word.length() == l2) {
        graph.put(word, new ArrayList<>());
      }
    }

    // Add edges
    for (String word1 : graph.keySet()) {
      for (String word2 : graph.keySet()) {
        if (!word1.equals(word2) && canTransform(word1, word2)) {
          graph.get(word1).add(word2);
        }
      }
    }

    return graph;
  }

  private boolean hasPath(Map<String, List<String>> graph, String start,
  String end,
  Set<String> visited) {
    // Don't allow paths to the same word
    if (start.equals(end) && visited.isEmpty()) {
      return false;
    }

    if (start.equals(end) && !visited.isEmpty()) {
      return true;
    }

    visited.add(start);
    for (String nbr : graph.get(start)) {
      if (!visited.contains(nbr)) {
        if (hasPath(graph, nbr, end, visited)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean solve(String word1, String word2, List<String> words) {
    // Try path using words of length l and l+1
    int l = word1.length();
    Map<String, List<String>> graph1 = buildGraph(words, l, l + 1);
    if (graph1.containsKey(word2)
    && hasPath(graph1, word1, word2, new HashSet<>())) {
      return true;
    }

    // Try path using words of length l and l-1
    Map<String, List<String>> graph2 = buildGraph(words, l, l - 1);
    if (graph2.containsKey(word2)
    && hasPath(graph2, word1, word2, new HashSet<>())) {
      return true;
    }

    return false;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example 1 from the book
        new TestCase(
            "leap",
            "hop",
            List.of("fare", "hug", "car", "vibes", "once", "sop", "far",
                "ounce", "slap", "sap", "cart", "hung", "art", "shop", "fart",
                "lap", "soap", "are", "hop", "care", "leap", "bounce", "beyond",
                "cracking"),
            true),
        // Example 2 from the book
        new TestCase(
            "car",
            "cart",
            List.of("fare", "hug", "car", "vibes", "once", "sop", "far",
                "ounce", "slap", "sap", "cart", "hung", "art", "shop", "fart",
                "lap", "soap", "are", "hop", "care", "leap", "bounce", "beyond",
                "cracking"),
            true),
        // Invalid - double removal
        new TestCase(
            "bounce",
            "once",
            List.of("bounce", "ounce", "once"),
            false),
        // Invalid - reordered letters
        new TestCase(
            "car",
            "race",
            List.of("car", "race"),
            false),
        // No path exists
        new TestCase(
            "cat",
            "dog",
            List.of("cat", "cot", "dot", "dog"),
            false)
    };

    WordLadderGame solution = new WordLadderGame();
    for (TestCase test : tests) {
      boolean got = solution.solve(test.word1(), test.word2(), test.words());
      if (got != test.want()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s, %s): got: %b, want: %b\n",
            test.word1(), test.word2(), test.words(), got, test.want()));
      }
    }
  }
}

public class P12_17_WordLadderGameVariation {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
