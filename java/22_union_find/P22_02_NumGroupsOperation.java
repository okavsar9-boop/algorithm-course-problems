// 22.2 - Num Groups Operation
// Run: javac P22_02_NumGroupsOperation.java && java P22_02_NumGroupsOperation

import java.util.*;
import java.util.function.*;

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

class CustomUnionFind {
  private HashMap<Integer, Integer> parent;
  private HashMap<Integer, Integer> sizeMap;

  public CustomUnionFind() {
    parent = new HashMap<>();
    sizeMap = new HashMap<>();
  }

  public void add(int x) {
    parent.put(x, x);
    sizeMap.put(x, 1);
  }

  public int find(int x) {
    int root = parent.get(x);
    while (parent.get(root) != root) {
      root = parent.get(root);
    }
    while (x != root) {
      int next = parent.get(x);
      parent.put(x, root);
      x = next;
    }
    return root;
  }

  public void union(int x, int y) {
    int reprX = find(x);
    int reprY = find(y);
    if (reprX == reprY) {
      return;
    }
    if (sizeMap.get(reprX) < sizeMap.get(reprY)) {
      sizeMap.put(reprY, sizeMap.get(reprY) + sizeMap.get(reprX));
      parent.put(reprX, reprY);
      sizeMap.remove(reprX);
    } else {
      sizeMap.put(reprX, sizeMap.get(reprX) + sizeMap.get(reprY));
      parent.put(reprY, reprX);
      sizeMap.remove(reprY);
    }
  }

  public int size() {
    return parent.size();
  }

  public int numGroups() {
    return sizeMap.size();
  }
}


class RunTests {
  public void runTests() {
    // Test basic operations
    CustomUnionFind uf = new CustomUnionFind();
    uf.add(1);
    uf.add(2);
    uf.add(3);
    if (uf.numGroups() != 3) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 3\n", uf.numGroups()));
    }
    uf.union(1, 2);
    if (uf.numGroups() != 2) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 2\n", uf.numGroups()));
    }
    uf.union(2, 3);
    if (uf.numGroups() != 1) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 1\n", uf.numGroups()));
    }
    uf.add(4);
    uf.add(5);
    if (uf.numGroups() != 3) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 3\n", uf.numGroups()));
    }
    uf.union(4, 5);
    if (uf.numGroups() != 2) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 2\n", uf.numGroups()));
    }

    // Test find after unions
    CustomUnionFind uf2 = new CustomUnionFind();
    uf2.add(1);
    uf2.add(2);
    uf2.add(3);
    uf2.union(1, 2);
    uf2.union(2, 3);
    if (uf2.find(1) != uf2.find(3)) {
      throw new RuntimeException("\nuf.find(1) != uf.find(3)\n");
    }

    // Test multiple unions of same elements
    CustomUnionFind uf3 = new CustomUnionFind();
    uf3.add(1);
    uf3.add(2);
    uf3.union(1, 2);
    uf3.union(1, 2);
    if (uf3.numGroups() != 1) {
      throw new RuntimeException(String.format(
          "\ngot: %d, want: 1\n", uf3.numGroups()));
    }
  }
}

public class P22_02_NumGroupsOperation {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
