// 11.11 - Most Protected Node
// Run: javac P11_11_MostProtectedNode.java && java P11_11_MostProtectedNode

import java.util.*;
import java.util.function.*;

class Node {
  public Integer value;
  public Node left;
  public Node right;

  public Node(Integer value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}

class MostProtectedNode {
  private Map<Node, Integer> protection;
  private Map<Integer, Integer> nodesPerLevel;
  private Map<Node, Integer> nodeToIndexInLevel;

  private int dfs(Node node, int depth) {
    if (node == null) {
      return -1;
    }
    int height = 1
        + Math.max(dfs(node.left, depth + 1), dfs(node.right, depth + 1));

    protection.put(node, Math.min(protection.get(node), height));

    // Update protection with right counts
    protection.put(node,
        Math.min(protection.get(node),
            nodesPerLevel.get(depth) - nodeToIndexInLevel.get(node) - 1));

    return height;
  }

  public int solve(Node root) {
    if (root == null) {
      return 0;
    }

    // Reset all maps
    protection = new HashMap<>();
    nodesPerLevel = new HashMap<>();
    nodeToIndexInLevel = new HashMap<>();

    // First pass: BFS to get depth and position in level
    Queue<Map.Entry<Node, Integer>> Q = new LinkedList<>();
    Q.add(new SimpleEntry<>(root, 0));

    while (!Q.isEmpty()) {
      Map.Entry<Node, Integer> entry = Q.poll();
      Node node = entry.getKey();
      int depth = entry.getValue();
      if (node == null) {
        continue;
      }

      // Add node to its level
      nodesPerLevel.merge(depth, 1, Integer::sum);
      // Store node's position in its level
      nodeToIndexInLevel.put(node, nodesPerLevel.get(depth) - 1);

      // Update protection with min of ancestor count (depth) and left count
      protection.put(node, Math.min(depth, nodeToIndexInLevel.get(node)));

      Q.add(new SimpleEntry<>(node.left, depth + 1));
      Q.add(new SimpleEntry<>(node.right, depth + 1));
    }

    // Second pass: DFS to get descendant heights
    dfs(root, 0);

    // Return highest protection level
    return protection.values().stream().max(Integer::compareTo).orElse(0);
  }
}


class RunTests {
  private static Node createNode(int value) {
    return new Node(value);
  }

  private static Node createNode(int value, Node left,
  Node right) {
    Node node = new Node(value);
    node.left = left;
    node.right = right;
    return node;
  }

  private static Node perfectTree(int height) {
    if (height == 1) {
      return createNode(1, null, null);
    }
    return createNode(1, perfectTree(height - 1), perfectTree(height - 1));
  }

  public void runTests() {
    Node root = createNode(1,
    createNode(2,
    createNode(3,
    createNode(4,
    createNode(5,
    createNode(6, null, null),
    null),
    createNode(7,
    createNode(8, null, null),
    createNode(9, null, null))),
    createNode(10,
    createNode(11, null, null),
    null)),
    createNode(12,
    createNode(13,
    createNode(14,
    createNode(15, null, null),
    createNode(16, null, null)),
    null),
    null)),
    createNode(17,
    createNode(18,
    createNode(19,
    createNode(20,
    createNode(21, null, null),
    null),
    null),
    null),
    createNode(22,
    createNode(23,
    createNode(24,
    createNode(25, null, null),
    null),
    null),
    null)));

    TestCase[] testCases = {
      new TestCase(root, 2),
      new TestCase(createNode(1, null, null), 0), // Single node
      new TestCase(createNode(1,
      createNode(2,
      createNode(3,
      createNode(4, null, null),
      null),
      null),
      null),
      0), // Linear tree
      new TestCase(perfectTree(1), 0),
      new TestCase(perfectTree(2), 0),
      new TestCase(perfectTree(3), 0),
      new TestCase(perfectTree(4), 1),
      new TestCase(perfectTree(5), 1),
      new TestCase(perfectTree(6), 2),
      new TestCase(perfectTree(7), 3),
      new TestCase(perfectTree(8), 3),
      new TestCase(perfectTree(9), 4),
    };

    MostProtectedNode solution = new MostProtectedNode();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nmost_protected_node(tree): got: %d, want: %d\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_11_MostProtectedNode {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
