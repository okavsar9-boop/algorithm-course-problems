// 11.15 - BST Duplicate Detection
// Run: javac P11_15_BSTDuplicateDetection.java && java P11_15_BSTDuplicateDetection

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

class HasDuplicate {
  private static class State {
    long prevValue;
    boolean res;

    State() {
      prevValue = Long.MIN_VALUE;
      res = false;
    }
  }

  private void visit(Node node, State state) {
    if (node == null || state.res) {
      return;
    }
    visit(node.left, state);
    if (node.value == state.prevValue) {
      state.res = true;
    }
    state.prevValue = node.value;
    visit(node.right, state);
  }

  public boolean solve(Node root) {
    State state = new State();
    visit(root, state);
    return state.res;
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
    // Example 1 - BST with duplicates
    Node root1 = createNode(5,
    createNode(2,
    null,
    createNode(4, null, null)),
    createNode(9,
    createNode(9,
    null,
    createNode(9, null, null)),
    createNode(11, null, null)));

    // Example 2 - empty tree
    Node root2 = null;

    // Example 3 - single node
    Node root3 = createNode(1, null, null);

    // Example 4 - BST without duplicates
    Node root4 = createNode(5,
    createNode(2,
    createNode(1, null, null),
    createNode(4, null, null)),
    createNode(8,
    createNode(6, null, null),
    createNode(9, null, null)));

    TestCase[] testCases = {
      new TestCase(root1, true), // Has duplicates (9s)
      new TestCase(root2, false), // Empty tree has no duplicates
      new TestCase(root3, false), // Single node has no duplicates
      new TestCase(root4, false) // No duplicates
    };

    HasDuplicate solution = new HasDuplicate();
    for (TestCase testCase : testCases) {
      boolean actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nhas_duplicate(root): got: %b, want: %b\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_15_BSTDuplicateDetection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
