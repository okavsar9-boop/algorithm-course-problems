// 22.1 - Minimum Spanning Tree
// Run: javac P22_01_MinimumSpanningTree.java && java P22_01_MinimumSpanningTree

import java.util.*;
import java.util.function.*;

class BuildAdjacencyList {
  public List<List<int[]>> solve(int[][] edges, int V) {
    List<List<int[]>> adjList = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      adjList.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
      int u = edge[0];
      int v = edge[1];
      int w = edge[2];
      adjList.get(u).add(new int[] { v, w });
      adjList.get(v).add(new int[] { u, w });
    }
    return adjList;
  }
}

class Prim {
  public int solve(int V, int[][] edges) { // Assumes the graph is connected.
    BuildAdjacencyList builder = new BuildAdjacencyList();
    List<List<int[]>> adjList = builder.solve(edges, V);
    int[] minEdge = new int[V];
    for (int i = 0; i < V; i++) {
      minEdge[i] = Integer.MAX_VALUE;
    }
    minEdge[0] = 0;
    boolean[] vis = new boolean[V];
    PriorityQueue<int[]> PQ = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    PQ.add(new int[] { 0, 0 });
    int mstCost = 0;
    while (!PQ.isEmpty()) {
      int u = PQ.poll()[1]; // Only need the node, not the edge weight
      if (vis[u])
        continue; // Not first extraction -- obsolete copy
      vis[u] = true;
      mstCost += minEdge[u];
      for (int[] edge : adjList.get(u)) {
        int v = edge[0];
        int w = edge[1];
        if (!vis[v] && w < minEdge[v]) {
          minEdge[v] = w;
          PQ.add(new int[] { w, v });
        }
      }
    }
    return mstCost;
  }
}
class Kruskal {
  public int solve(int V, int[][] edges) { // Assumes the graph is connected.
    UnionFind uf = new UnionFind();
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
      }
    }
    return mstCost;
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
        // Example from book
        { 9,
            new int[][] {
                { 0, 1, 3 }, { 1, 8, 9 }, { 8, 7, 5 }, { 7, 4, 13 },
                { 4, 3, 4 },
                { 3, 0, 5 }, { 1, 5, 8 }, { 5, 4, 2 }, { 4, 2, 3 },
                { 2, 1, -1 },
                { 2, 5, 10 }, { 5, 6, 11 }, { 6, 8, 0 }, { 6, 7, -2 }
            },
            18 },
        // Single edge
        { 2, new int[][] { { 0, 1, 5 } }, 5 },
        // Triangle graph
        { 3, new int[][] { { 0, 1, 1 }, { 1, 2, 2 }, { 0, 2, 3 } }, 3 },
        // Square graph
        { 4, new int[][] { { 0, 1, 1 }, { 1, 2, 2 }, { 2, 3, 3 }, { 3, 0, 4 } },
            6 },
        // Negative weights
        { 3, new int[][] { { 0, 1, -2 }, { 1, 2, -3 }, { 0, 2, 1 } }, -5 },
    };

    Prim primSolution = new Prim();
    Kruskal kruskalSolution = new Kruskal();
    for (Object[] test : tests) {
      int V = (int) test[0];
      int[][] edges = (int[][]) test[1];
      int want = (int) test[2];
      int gotPrim = primSolution.solve(V, edges);
      if (gotPrim != want) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        throw new RuntimeException(String.format(
            "\nprim(%d, %s): got: %d, want: %d\n",
            V, edgesStr, gotPrim, want));
      }
      int gotKruskal = kruskalSolution.solve(V, edges);
      if (gotKruskal != want) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got: %d, want: %d\n",
            V, edgesStr, gotKruskal, want));
      }
    }
  }
}

public class P22_01_MinimumSpanningTree {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
