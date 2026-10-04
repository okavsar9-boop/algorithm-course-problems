// 11.10 - Zig-Zag Order
// Run: javac P11_10_ZigZagOrder.java && java P11_10_ZigZagOrder

import java.util.*;
import java.util.function.*;

class Node {
  public int value;
  public Node left;
  public Node right;

  public Node(int value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}

class ZigZagOrder {
  public List<Integer> solve(Node root) {
    if (root == null) {
      return Collections.emptyList();
    }

    List<Integer> res = new ArrayList<>();
    Queue<Map.Entry<Node, Integer>> queue = new LinkedList<>();
    queue.offer(new AbstractMap.SimpleEntry<>(root, 0));
    List<Integer> curLevel = new ArrayList<>();
    int curDepth = 0;

    while (!queue.isEmpty()) {
      Map.Entry<Node, Integer> entry = queue.poll();
      Node node = entry.getKey();
      int depth = entry.getValue();

      if (depth > curDepth) {
        if (curDepth % 2 == 0) {
          res.addAll(curLevel);
        } else {
          Collections.reverse(curLevel);
          res.addAll(curLevel);
        }
        curLevel = new ArrayList<>();
        curDepth = depth;
      }

      curLevel.add(node.value);
      if (node.left != null) {
        queue.offer(new AbstractMap.SimpleEntry<>(node.left, depth + 1));
      }
      if (node.right != null) {
        queue.offer(new AbstractMap.SimpleEntry<>(node.right, depth + 1));
      }
    }

    // Add the last level
    if (curDepth % 2 == 0) {
      res.addAll(curLevel);
    } else {
      Collections.reverse(curLevel);
      res.addAll(curLevel);
    }

    return res;
  }
}


class RunTests {
  private static Node createNode(int value) {
    return new Node(value);
  }

  private static Node createNode(int value, Node left, Node right) {
    Node node = new Node(value);
    node.left = left;
    node.right = right;
    return node;
  }

  public void runTests() {
    // Create test trees
    Node root1 = createNode(1,
        createNode(2,
            createNode(4, null, null),
            createNode(5, null, null)),
        createNode(3,
            createNode(6, null, null),
            createNode(7, null, null)));

    Node root4 = createNode(1,
        createNode(2,
            createNode(3,
                createNode(4, null, null),
                null),
            null),
        null);

    Node root5 = createNode(1,
        createNode(2,
            createNode(4,
                createNode(8, null, null),
                createNode(9, null, null)),
            createNode(5,
                createNode(10, null, null),
                createNode(11, null, null))),
        createNode(3,
            createNode(6,
                createNode(12, null, null),
                null),
            createNode(7,
                createNode(14, null, null),
                createNode(15, null, null))));

    TestCase[] testCases = {
        // Example 1 - basic tree
        new TestCase(root1,
            Arrays.asList(1, 3, 2, 4, 5, 6, 7)),
        // Example 2 - empty tree
        new TestCase(null,
            Collections.<Integer>emptyList()),
        // Example 3 - single node
        new TestCase(createNode(1),
            Arrays.asList(1)),
        // Example 4 - unbalanced tree
        new TestCase(root4,
            Arrays.asList(1, 2, 3, 4)),
        // Example 5 - complete binary tree
        new TestCase(root5,
            Arrays.asList(1, 3, 2, 4, 5, 6, 7, 15, 14, 12, 11, 10, 9, 8)),
    };

    ZigZagOrder solution = new ZigZagOrder();
    for (TestCase testCase : testCases) {
      List<Integer> actual = solution.solve(testCase.root);
      if (!actual.equals(testCase.expected)) {
        throw new RuntimeException(String.format(
            "\nzig_zag_order(tree): got: %s, want: %s\n",
            actual, testCase.expected));
      }
    }
  }
}

public class P11_10_ZigZagOrder {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
