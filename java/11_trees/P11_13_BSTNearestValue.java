// 11.13 - BST Nearest Value
// Run: javac P11_13_BSTNearestValue.java && java P11_13_BSTNearestValue

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

class FindClosest {
  public long solve(Node root, int target) {
    Node curNode = root;
    long nextAbove = Long.MAX_VALUE;
    long nextBelow = Long.MIN_VALUE;

    while (curNode != null) {
      if (curNode.value == target) {
        return curNode.value;
      } else if (curNode.value > target) {
        nextAbove = curNode.value;
        curNode = curNode.left;
      } else {
        nextBelow = curNode.value;
        curNode = curNode.right;
      }
    }

    // If we only found values above or only values below, return the closest
    // one
    if (nextAbove == Long.MAX_VALUE) {
      return nextBelow;
    }
    if (nextBelow == Long.MIN_VALUE) {
      return nextAbove;
    }

    // Otherwise compare distances
    if (nextAbove - target < target - nextBelow) {
      return nextAbove;
    }
    return nextBelow;
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
    Node root1 = createNode(5,
    createNode(2,
    null,
    createNode(4, null, null)),
    createNode(9,
    createNode(9,
    null,
    createNode(9, null, null)),
    createNode(11, null, null)));

    // Single node
    Node root3 = createNode(1, null, null);

    // Perfect BST
    Node root4 = createNode(4,
    createNode(2,
    createNode(1, null, null),
    createNode(3, null, null)),
    createNode(6,
    createNode(5, null, null),
    createNode(7, null, null)));

    // Unbalanced BST
    Node root5 = createNode(5,
    createNode(3,
    createNode(2,
    createNode(1, null, null),
    null),
    createNode(4, null, null)),
    null);

    // Example from the book
    Node root6 = createNode(8,
    createNode(6,
    createNode(5,
    createNode(2, null, null),
    createNode(6, null, null)),
    createNode(8,
    createNode(8, null, null),
    createNode(8, null, null))),
    createNode(12,
    createNode(10,
    createNode(9, null, null),
    null),
    null));

    TestCase[] testCases = {
      new TestCase(root1, 6, 5L), // Closest to 6 is 5
      new TestCase(root1, 9, 9L), // Exact match
      new TestCase(root1, 3, 2L), // Closest to 3 is 2
      new TestCase(root1, 4, 4L), // Exact match
      new TestCase(root3, 1, 1L), // Single node, exact match
      new TestCase(root3, 2, 1L), // Single node, closest is 1
      new TestCase(root4, 5, 5L), // Perfect BST, exact match
      new TestCase(root4, 8, 7L), // Perfect BST, closest is 7
      new TestCase(root5, 1, 1L), // Unbalanced BST, exact match at leaf
      new TestCase(root5, 5, 5L), // Unbalanced BST, exact match at root
      new TestCase(root5, 6, 5L), // Unbalanced BST, closest is 5
      new TestCase(root6, 9, 9L),
      new TestCase(root6, 13, 12L),
      new TestCase(root6, 1, 2L),
      new TestCase(root6, 8, 8L),
      new TestCase(root6, 6, 6L),
      new TestCase(root6, 7, 6L),
      new TestCase(root6, 11, 10L),
      new TestCase(root6, 4, 5L),
    };

    FindClosest solution = new FindClosest();
    for (TestCase testCase : testCases) {
      long actual = solution.solve(testCase.root, testCase.target);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nfind_closest(root, %d): got: %d, want: %d\n",
        testCase.target, actual, testCase.expected));
      }
    }
  }
}

public class P11_13_BSTNearestValue {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
