// 22.3 - MST Reconstruction
// Run: javac P22_03_MSTReconstruction.java && java P22_03_MSTReconstruction

import java.util.*;
import java.util.function.*;

class Kruskal {
  public int[][] solve(int V, int[][] edges) {
    if (V == 0)
      return new int[0][];

    UnionFind uf = new UnionFind();
    for (int u = 0; u < V; u++) {
      uf.add(u);
    }

    // Create a copy of edges to sort
    List<EdgeWithIndex> sortedEdges = new ArrayList<>();
    for (int i = 0; i < edges.length; i++) {
      sortedEdges.add(new EdgeWithIndex(edges[i], i));
    }
    Collections.sort(sortedEdges);

    List<int[]> mst = new ArrayList<>();
    int edgesInMst = 0;

    for (EdgeWithIndex edge : sortedEdges) {
      int u = edge.edge[0], v = edge.edge[1];
      int reprU = uf.find(u), reprV = uf.find(v);
      if (reprU != reprV) {
        uf.union(u, v);
        mst.add(edge.edge);
        edgesInMst++;
      }
    }

    // Check if we have a valid MST (n-1 edges)
    if (edgesInMst != V - 1) {
      return new int[0][];
    }

    return mst.toArray(new int[0][]);
  }

  private static class EdgeWithIndex implements Comparable<EdgeWithIndex> {
    int[] edge;
    int index;

    EdgeWithIndex(int[] edge, int index) {
      this.edge = edge;
      this.index = index;
    }

    @Override
    public int compareTo(EdgeWithIndex other) {
      return Integer.compare(this.edge[2], other.edge[2]);
    }
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
        // Example 1 from book
        { 9,
            new int[][] {
                { 0, 1, 3 }, { 1, 8, 9 }, { 8, 7, 5 }, { 7, 4, 13 },
                { 4, 3, 4 },
                { 3, 0, 5 }, { 1, 5, 8 }, { 5, 4, 2 }, { 4, 2, 3 },
                { 2, 1, -1 },
                { 2, 5, 10 }, { 5, 6, 11 }, { 6, 8, 0 }, { 6, 7, -2 }
            },
            new int[][] {
                { 0, 1, 3 }, { 1, 8, 9 }, { 4, 3, 4 }, { 5, 4, 2 }, { 4, 2, 3 },
                { 2, 1, -1 }, { 6, 8, 0 }, { 6, 7, -2 }
            } },
        // Example 2 - not connected
        { 3, new int[][] { { 0, 1, 1 } }, new int[][] {} },
        // Example 3 - not unique solution
        { 3, new int[][] { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 0, 1 } },
            new int[][] { { 0, 1, 1 }, { 1, 2, 1 } } },
        // Single edge
        { 2, new int[][] { { 0, 1, 5 } }, new int[][] { { 0, 1, 5 } } },
        // Triangle graph
        { 3, new int[][] { { 0, 1, 1 }, { 1, 2, 2 }, { 0, 2, 3 } },
            new int[][] { { 0, 1, 1 }, { 1, 2, 2 } } },
        // Empty graph
        { 0, new int[][] {}, new int[][] {} },
    };

    Kruskal solution = new Kruskal();
    for (Object[] test : tests) {
      int V = (int) test[0];
      int[][] edges = (int[][]) test[1];
      int[][] want = (int[][]) test[2];
      int[][] got = solution.solve(V, edges);

      // Check length
      if (got.length != want.length) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got wrong number of edges: %d, want: %d\n",
            V, edgesStr, got.length, want.length));
      }

      // Check total cost
      int gotCost = 0, wantCost = 0;
      for (int[] edge : got)
        gotCost += edge[2];
      for (int[] edge : want)
        wantCost += edge[2];

      if (gotCost != wantCost) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got wrong cost: %d, want: %d\n",
            V, edgesStr, gotCost, wantCost));
      }
    }
  }
}

public class P22_03_MSTReconstruction {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
