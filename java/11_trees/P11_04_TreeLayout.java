// 11.4 - Tree Layout
// Run: javac P11_04_TreeLayout.java && java P11_04_TreeLayout

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

class MostStacked {
  private Map<String, Integer> posToCount;

  private void visit(Node node, int r, int c) {
    if (node == null) {
      return;
    }
    String key = r + "," + c;
    posToCount.merge(key, 1, Integer::sum);
    visit(node.left, r + 1, c);
    visit(node.right, r, c + 1);
  }

  public int solve(Node root) {
    posToCount = new HashMap<>();
    visit(root, 0, 0);
    int maxCount = 0;
    for (int count : posToCount.values()) {
      maxCount = Math.max(maxCount, count);
    }
    return maxCount;
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

  public void runTests() {
    TestCase[] testCases = {
      // Test 1: Example from the book - two nodes stacked
      new TestCase(createNode(1,
      createNode(2,
      createNode(4,
      null,
      createNode(7, null, null)),
      createNode(5, null, null)),
      createNode(3,
      createNode(6,
      createNode(8, null, null),
      createNode(9, null, null)),
      null)),
      2),
      // Test 2: Single node
      new TestCase(createNode(1, null, null), 1),
      // Test 3: Simple tree
      new TestCase(createNode(1,
      createNode(2, null, null),
      createNode(3, null, null)),
      1),
      // Test 4: Perfect binary tree of depth 4
      new TestCase(createNode(1,
      createNode(2,
      createNode(4,
      createNode(8, null, null),
      createNode(9,
      null,
      createNode(16, null, null))),
      createNode(5,
      createNode(10,
      null,
      createNode(17, null, null)),
      createNode(11,
      createNode(18, null, null),
      null))),
      createNode(3,
      createNode(6,
      createNode(12, null, null),
      createNode(13, null, null)),
      createNode(7,
      createNode(14,
      createNode(19, null, null),
      null),
      createNode(15,
      createNode(20, null, null),
      null)))),
      4),
    };

    MostStacked solution = new MostStacked();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nmost_stacked(tree): got: %d, want: %d\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_04_TreeLayout {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
