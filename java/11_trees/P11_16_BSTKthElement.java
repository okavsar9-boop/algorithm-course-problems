// 11.16 - BST Kth Element
// Run: javac P11_16_BSTKthElement.java && java P11_16_BSTKthElement

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

class KthElement {
  private int steps = 0;
  private int res = -1;

  private void visit(Node node, int k) {
    if (node == null || res != -1) {
      return;

    }
    visit(node.left, k);
    if (steps == k) {
      res = node.value;
    }
    steps++;
    visit(node.right, k);
  }

  public int solve(Node root, int k) {
    steps = 0;
    res = -1;
    visit(root, k);
    return res;
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
    Node root = createNode(5,
    createNode(2,
    createNode(1, null, null),
    createNode(4, null, null)),
    createNode(8,
    createNode(6, null, null),
    createNode(9, null, null)));

    TestCase[] testCases = {
      new TestCase(createNode(5,
      createNode(2,
      null,
      createNode(4, null, null)),
      createNode(9,
      createNode(9, null, null),
      createNode(11, null, null))),
      4, 9),
      new TestCase(createNode(1, null, null), 0, 1), // Single node
      new TestCase(root, 0, 1),
      new TestCase(root, 1, 2),
      new TestCase(root, 2, 4),
      new TestCase(root, 3, 5),
      new TestCase(root, 4, 6),
      new TestCase(root, 5, 8),
      new TestCase(root, 6, 9),
    };

    KthElement solution = new KthElement();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root, testCase.k);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nkth_element(root, %d): got: %d, want: %d\n",
        testCase.k, actual, testCase.expected));
      }
    }
  }
}

public class P11_16_BSTKthElement {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
