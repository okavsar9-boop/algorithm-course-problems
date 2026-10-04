// 18.8 - Number of Palindromic Splits
// Run: javac P18_08_NumberOfPalindromicSplits.java && java P18_08_NumberOfPalindromicSplits

import java.util.*;
import java.util.function.*;

class CountPalindromicSplitsDp {
  public int solve(String s) {
    int n = s.length();
    if (n == 0) {
      return 0;
    }

    // Preprocessing: compute all palindromic substrings
    List<int[]> palindromes = FindPalindromes.solve(s);
    Set<String> palindromeSet = new HashSet<>();
    for (int[] p : palindromes) {
      palindromeSet.add(p[0] + "," + p[1]);
    }

    Map<Integer, Integer> memo = new HashMap<>();

    return countSplits(0, s, n, palindromeSet, memo);
  }

  private int countSplits(int i, String s, int n, Set<String> palindromeSet,
  Map<Integer, Integer> memo) {
    // Base case: reached the end of string
    if (i == n) {
      return 1;
    }

    if (memo.containsKey(i)) {
      return memo.get(i);
    }

    // Try all possible palindromic substrings starting at i
    int total = 0;
    for (int j = i; j < n; j++) {
      if (palindromeSet.contains(i + "," + j)) {
        total += countSplits(j + 1, s, n, palindromeSet, memo);
      }
    }

    memo.put(i, total);
    return total;
  }
}

class FindPalindromes {
  public static List<int[]> solve(String s) {
    int n = s.length();
    List<int[]> palindromes = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int l = i, r = i;
      while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
        palindromes.add(new int[] { l, r });
        l--;
        r++;
      }
      l = i;
      r = i + 1;
      while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
        palindromes.add(new int[] { l, r });
        l--;
        r++;
      }
    }
    return palindromes;
  }
}

We can think of s as a path we need to traverse. We can go character by character, or we can take a 'shortcut' over a palindromic substring in a single step. We can model this as a DAG (Directed Acyclic Graph) with a node for each character in s, and an edge from s[i] to s[j] if s[i:j] is a palindrome. A single character is a palindrome, so there is an edge from s[0] to s[1], from s[1] to s[2], and so on. There are additional edges for each palindromic substring of length > 1. For instance, if the string is "abacdc", one of the edges would be from the first 'a' to the first 'c'. All edges go from left to right, meaning that there is no cycle.

compute a topological ordering (Kahn's peel-off algorithm)
for node in topological ordering
for each edge node -> nbr
update some information about nbr

topo_order = empty list
while not every node is in topo_order:
pick a node with in-degree 0
add it to topo_order

if not all nodes were peeled off, there is a cycle

class CountPalindromicSplits {
  private List<Integer> topologicalSort(List<List<Integer>> graph) {
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

  public int solve(String s) {
    List<int[]> palindromes = FindPalindromes.solve(s);
    int n = s.length();
    if (n == 0) {
      return 0;
    }

    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      graph.add(new ArrayList<>());
    }
    for (int[] p : palindromes) {
      graph.get(p[0]).add(p[1] + 1);
    }

    List<Integer> topoOrder = topologicalSort(graph);

    long[] counts = new long[n + 1];
    counts[0] = 1;
    for (int node : topoOrder) {
      for (int nbr : graph.get(node)) {
        counts[nbr] += counts[node];
      }
    }
    return (int) counts[n];
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { "abbaab", 6 },
        { "aabaa", 6 },
        { "a", 1 },
        { "aa", 2 },
        { "aaa", 4 },
        { "aaaa", 8 },
        { "aaaaa", 16 },
        { "abc", 1 },
        { "aaba", 3 },
        { "abcdedcba", 5 },
        { "", 0 },
    };

    CountPalindromicSplits solution = new CountPalindromicSplits();
    CountPalindromicSplitsDp solutionDp = new CountPalindromicSplitsDp();
    for (Object[] test : tests) {
      String s = (String) test[0];
      int want = (int) test[1];
      int got = solution.solve(s);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nTopological sort: solve(\"%s\"): got: %d, want: %d\n",
            s, got, want));
      }
      int gotDp = solutionDp.solve(s);
      if (gotDp != want) {
        throw new RuntimeException(String.format(
            "\nDP: solve(\"%s\"): got: %d, want: %d\n",
            s, gotDp, want));
      }
    }
  }
}

public class P18_08_NumberOfPalindromicSplits {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
