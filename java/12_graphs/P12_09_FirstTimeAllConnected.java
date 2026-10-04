// 12.9 - First Time All Connected
// Run: javac P12_09_FirstTimeAllConnected.java && java P12_09_FirstTimeAllConnected

import java.util.*;
import java.util.function.*;

class FirstTimeAllConnected {
  private void visit(List<List<Integer>> graph, java.util.Set<Integer> visited,
  int node) {
    for (int nbr : graph.get(node)) {
      if (!visited.contains(nbr)) {
        visited.add(nbr);
        visit(graph, visited, nbr);
      }
    }
  }

  private boolean isBefore(int cableIndex, int V, int[][] cables) {
    // Build adjacency list representation using ArrayList
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      graph.add(new ArrayList<>());
    }

    // Fill lists
    for (int i = 0; i <= cableIndex; i++) {
      int node1 = cables[i][0];
      int node2 = cables[i][1];
      graph.get(node1).add(node2);
      graph.get(node2).add(node1);
    }

    java.util.Set<Integer> visited = new java.util.HashSet<>();
    visited.add(0);
    visit(graph, visited, 0);
    return visited.size() < V;
  }

  public int solve(int V, int[][] cables) {
    // Base case - no cables
    if (cables.length == 0) {
      return -1;
    }

    int l = 0, r = cables.length - 1;
    if (isBefore(r, V, cables)) {
      return -1;
    }
    while (r - l > 1) {
      int mid = l + (r - l) / 2;
      if (isBefore(mid, V, cables)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return r;
  }
}

class FirstTimeAllConnectedUnionFind {
  public int solve(int V, int[][] cables) {
    UnionFind uf = new UnionFind();
    for (int x = 0; x < V; x++) {
      uf.add(x);
    }
    int groups = V;
    for (int i = 0; i < cables.length; i++) {
      int x = cables[i][0];
      int y = cables[i][1];

      // If x and y are not in the same group yet, union their groups
      if (uf.find(x) != uf.find(y)) {
        uf.union(x, y);
        groups--;
        if (groups == 1) {
          return i;
        }
      }
    }
    return -1;
  }
}

class UnionFind {
  private HashMap<Integer, Integer> parent;
  private HashMap<Integer, Integer> size;

  public UnionFind() {
    parent = new HashMap<>();
    size = new HashMap<>();
  }

  // Assumes x is not already in the UnionFind.
  public void add(int x) {
    parent.put(x, x);
    size.put(x, 1);
  }

  // Assumes x is already in the UnionFind.
  public int find(int x) {
    int root = parent.get(x);
    while (parent.get(root) != root) {
      root = parent.get(root);
    }

    // Path compression
    while (x != root) {
      int next = parent.get(x);
      parent.put(x, root);
      x = next;
    }

    return root;
  }

  // Assumes x and y are already in the UnionFind.
  public void union(int x, int y) {
    int reprX = find(x);
    int reprY = find(y);

    if (reprX == reprY) {
      return; // They are already in the same set
    }

    if (size.get(reprX) < size.get(reprY)) {
      size.put(reprY, size.get(reprY) + size.get(reprX));
      parent.put(reprX, reprY);
    } else {
      size.put(reprX, size.get(reprX) + size.get(reprY));
      parent.put(reprY, reprX);
    }
  }

  // Returns the size of the set containing x.
  // Assumes x is already in the UnionFind.
  public int getSetSize(int x) {
    return size.get(find(x));
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Case from picture - becomes connected after cables[2]
        { 4, new int[][] { { 0, 2 }, { 1, 3 }, { 0, 1 }, { 1, 2 } }, 2 },
        // Edge case - never gets fully connected
        { 3, new int[][] { { 0, 1 } }, -1 },
        // Edge case - gets connected with final cable
        { 3, new int[][] { { 0, 1 }, { 1, 2 } }, 1 },
        // Larger test case
        { 5, new int[][] { { 0, 1 }, { 2, 3 }, { 1, 2 }, { 3, 4 }, { 0, 4 } },
            3 },
        // Edge case - redundant cables don't affect result
        { 4, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 3 }, { 3, 0 } },
            3 },
        // No edges added
        { 4, new int[][] {}, -1 },
        // One edge added
        { 4, new int[][] { { 0, 1 } }, -1 }
    };

    FirstTimeAllConnected solution1 = new FirstTimeAllConnected();
    FirstTimeAllConnectedUnionFind solution2 = new FirstTimeAllConnectedUnionFind();

    for (Object[] test : tests) {
      int V = (int) test[0];
      int[][] cables = (int[][]) test[1];
      int want = (int) test[2];

      int got1 = solution1.solve(V, cables);
      if (got1 != want) {
        throw new RuntimeException(String.format(
            "\nFirstTimeAllConnected.solve(%d, %s): got: %d, want: %d\n",
            V, java.util.Arrays.deepToString(cables), got1, want));
      }

      int got2 = solution2.solve(V, cables);
      if (got2 != want) {
        throw new RuntimeException(String.format(
            "\nFirstTimeAllConnectedUnionFind.solve(%d, %s): got: %d, want: %d\n",
            V, java.util.Arrays.deepToString(cables), got2, want));
      }
    }
  }
}

public class P12_09_FirstTimeAllConnected {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
