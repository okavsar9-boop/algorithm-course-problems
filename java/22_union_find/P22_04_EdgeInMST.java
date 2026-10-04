// 22.4 - Edge In MST
// Run: javac P22_04_EdgeInMST.java && java P22_04_EdgeInMST

import java.util.*;
import java.util.function.*;

class EdgeInMst {
  private int kruskal(int V, int[][] edges) {
    UnionFind uf = new UnionFind();
    int numCcs = V;
    for (int u = 0; u < V; u++) {
      uf.add(u);
    }
    int mstCost = 0;

    // Create a copy of edges to sort
    List<int[]> sortedEdges = new ArrayList<>();
    for (int[] edge : edges) {
      sortedEdges.add(edge);
    }
    Collections.sort(sortedEdges, (a, b) -> Integer.compare(a[2], b[2]));

    for (int[] edge : sortedEdges) {
      int u = edge[0], v = edge[1], weight = edge[2];
      int reprU = uf.find(u), reprV = uf.find(v);
      if (reprU != reprV) {
        uf.union(u, v);
        mstCost += weight;
        numCcs--;
      }
    }

    if (numCcs != 1) {
      return Integer.MAX_VALUE;
    }
    return mstCost;
  }

  public boolean solve(int V, int[][] edges, int i) {
    int mstCost = kruskal(V, edges);

    // Create edges without the i-th edge
    int[][] edgesWithoutI = new int[edges.length - 1][];
    int idx = 0;
    for (int j = 0; j < edges.length; j++) {
      if (j != i) {
        edgesWithoutI[idx++] = edges[j];
      }
    }

    int mstCostWithoutI = kruskal(V, edgesWithoutI);
    return mstCost != mstCostWithoutI;
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
        // Graph from the book
        { 4, new int[][] { { 0, 1, 5 }, { 1, 2, 5 }, { 2, 3, 20 },
            { 3, 0, 20 } }, 0, true },
        { 4, new int[][] { { 0, 1, 5 }, { 1, 2, 5 }, { 2, 3, 20 },
            { 3, 0, 20 } }, 1, true },
        { 4, new int[][] { { 0, 1, 5 }, { 1, 2, 5 }, { 2, 3, 20 },
            { 3, 0, 20 } }, 2, false },
        { 4, new int[][] { { 0, 1, 5 }, { 1, 2, 5 }, { 2, 3, 20 },
            { 3, 0, 20 } }, 3, false },
        // Edge case - single edge
        { 2, new int[][] { { 0, 1, 5 } }, 0, true },
        // Triangle graph - all edges same weight
        { 3, new int[][] { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 0, 1 } }, 0, false },
        // Square graph - one edge much heavier
        { 4, new int[][] { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 3, 1 },
            { 3, 0, 10 } }, 3, false },
        // Negative weights
        { 3, new int[][] { { 0, 1, -2 }, { 1, 2, 1 }, { 2, 0, 1 } }, 0, true }
    };

    EdgeInMst solution = new EdgeInMst();
    for (Object[] test : tests) {
      int V = (int) test[0];
      int[][] edges = (int[][]) test[1];
      int i = (int) test[2];
      boolean want = (boolean) test[3];
      boolean got = solution.solve(V, edges, i);

      if (got != want) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%d, %s, %d): got: %b, want: %b\n",
            V, edgesStr, i, got, want));
      }
    }
  }
}

public class P22_04_EdgeInMST {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
