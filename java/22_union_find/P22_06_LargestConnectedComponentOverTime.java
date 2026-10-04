// 22.6 - Largest Connected Component Over Time
// Run: javac P22_06_LargestConnectedComponentOverTime.java && java P22_06_LargestConnectedComponentOverTime

import java.util.*;
import java.util.function.*;

class MaxCcAtTimes {
  public int[] solve(int n, int[][] edges, int[] times) {
    // Sort edges by time
    List<int[]> sortedEdges = new ArrayList<>();
    for (int[] edge : edges) {
      sortedEdges.add(edge);
    }
    Collections.sort(sortedEdges, (a, b) -> Integer.compare(a[2], b[2]));

    UnionFind uf = new UnionFind();
    for (int u = 0; u < n; u++) {
      uf.add(u);
    }
    int maxCc = 1;
    int timesI = 0;
    int[] res = new int[times.length];

    for (int[] edge : sortedEdges) {
      int u = edge[0], v = edge[1], time = edge[2];
      while (timesI < times.length && time > times[timesI]) {
        res[timesI] = maxCc;
        timesI++;
      }
      int reprU = uf.find(u), reprV = uf.find(v);
      if (reprU == reprV) {
        continue;
      }
      uf.union(u, v);
      maxCc = Math.max(maxCc, uf.getSetSize(u));
    }

    while (timesI < times.length) {
      res[timesI] = maxCc;
      timesI++;
    }
    return res;
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
        // Example from the book
        { 4, new int[][] { { 0, 1, 60 }, { 0, 3, 180 }, { 2, 3, 120 } },
            new int[] { 30, 120, 210 }, new int[] { 1, 2, 4 } },
        // Edge case - no edges
        { 3, new int[][] {}, new int[] { 10, 20 }, new int[] { 1, 1 } },
        // Edge case - single node
        { 1, new int[][] {}, new int[] { 5 }, new int[] { 1 } },
        // Multiple edges at same time
        { 4, new int[][] { { 0, 1, 10 }, { 2, 3, 10 }, { 1, 2, 20 } },
            new int[] { 5, 15, 25 }, new int[] { 1, 2, 4 } },
        // All edges after last query time
        { 3, new int[][] { { 0, 1, 100 }, { 1, 2, 200 } },
            new int[] { 10, 20 }, new int[] { 1, 1 } },
    };

    MaxCcAtTimes solution = new MaxCcAtTimes();
    for (Object[] test : tests) {
      int V = (int) test[0];
      int[][] edges = (int[][]) test[1];
      int[] times = (int[]) test[2];
      int[] want = (int[]) test[3];
      int[] got = solution.solve(V, edges, times);

      if (!java.util.Arrays.equals(got, want)) {
        StringBuilder edgesStr = new StringBuilder("[");
        for (int j = 0; j < edges.length; j++) {
          if (j > 0)
            edgesStr.append(", ");
          edgesStr.append(String.format("[%d, %d, %d]",
              edges[j][0], edges[j][1], edges[j][2]));
        }
        edgesStr.append("]");

        StringBuilder timesStr = new StringBuilder("[");
        for (int j = 0; j < times.length; j++) {
          if (j > 0)
            timesStr.append(", ");
          timesStr.append(times[j]);
        }
        timesStr.append("]");

        StringBuilder gotStr = new StringBuilder("[");
        for (int j = 0; j < got.length; j++) {
          if (j > 0)
            gotStr.append(", ");
          gotStr.append(got[j]);
        }
        gotStr.append("]");

        StringBuilder wantStr = new StringBuilder("[");
        for (int j = 0; j < want.length; j++) {
          if (j > 0)
            wantStr.append(", ");
          wantStr.append(want[j]);
        }
        wantStr.append("]");

        throw new RuntimeException(String.format(
            "\nsolve(%d, %s, %s): got: %s, want: %s\n",
            V, edgesStr, timesStr, gotStr, wantStr));
      }
    }
  }
}

public class P22_06_LargestConnectedComponentOverTime {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
