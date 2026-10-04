// 11.14 - BST Validation
// Run: javac P11_14_BSTValidation.java && java P11_14_BSTValidation

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

class IsBst {
  private static class State {
    long prevValue;
    boolean res;

    State() {
      prevValue = Long.MIN_VALUE;
      res = true;
    }
  }

  private void visit(Node node, State state) {
    if (node == null || !state.res) {
      return;
    }
    visit(node.left, state);
    if (node.value < state.prevValue) {
      state.res = false;
    } else {
      state.prevValue = node.value;
    }
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
    // Example 1 - valid BST
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

    // Example 4 - invalid BST (right child smaller than parent)
    Node root4 = createNode(5,
    createNode(2, null, null),
    createNode(4, null, null));

    // Example 5 - invalid BST (left child larger than parent)
    Node root5 = createNode(5,
    createNode(6, null, null),
    createNode(7, null, null));

    TestCase[] testCases = {
      new TestCase(root1, true), // Valid BST
      new TestCase(root2, true), // Empty tree is valid
      new TestCase(root3, true), // Single node is valid
      new TestCase(root4, false), // Invalid - right child smaller than parent
      new TestCase(root5, false) // Invalid - left child larger than parent
    };

    IsBst solution = new IsBst();
    for (TestCase testCase : testCases) {
      boolean actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nis_bst(root): got: %b, want: %b\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_14_BSTValidation {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
