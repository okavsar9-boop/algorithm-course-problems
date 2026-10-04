// 11.12 - BST Search
// Run: javac P11_12_BSTSearch.java && java P11_12_BSTSearch

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

class ContainsTarget {
  public boolean solve(Node root, int target) {
    Node curNode = root;
    while (curNode != null) {
      if (curNode.value == target) {
        return true;
      } else if (curNode.value > target) {
        curNode = curNode.left;
      } else {
        curNode = curNode.right;
      }
    }
    return false;
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
    // Test 1
    Node root1 = createNode(5,
    createNode(2,
    null,
    createNode(4, null, null)),
    createNode(9,
    createNode(9,
    null,
    createNode(9, null, null)),
    createNode(11, null, null)));

    // Test 2: Empty tree
    Node root2 = null;

    // Test 3: Single node
    Node root3 = createNode(1, null, null);

    // Test 4: Perfect BST
    Node root4 = createNode(4,
    createNode(2,
    createNode(1, null, null),
    createNode(3, null, null)),
    createNode(6,
    createNode(5, null, null),
    createNode(7, null, null)));

    // Test 5: Unbalanced BST
    Node root5 = createNode(5,
    createNode(3,
    createNode(2,
    createNode(1, null, null),
    null),
    createNode(4, null, null)),
    null);

    TestCase[] testCases = {
      new TestCase(root1, 6, false),
      new TestCase(root1, 9, true),
      new TestCase(root1, 3, false),
      new TestCase(root1, 4, true),
      new TestCase(root2, 1, false), // Empty tree
      new TestCase(root3, 1, true), // Single node, target exists
      new TestCase(root3, 2, false), // Single node, target doesn't exist
      new TestCase(root4, 5, true), // Perfect BST, target exists
      new TestCase(root4, 8, false), // Perfect BST, target doesn't exist
      new TestCase(root5, 1, true), // Unbalanced BST, target exists at leaf
      new TestCase(root5, 5, true), // Unbalanced BST, target exists at root
      new TestCase(root5, 6, false) // Unbalanced BST, target doesn't exist
    };

    ContainsTarget solution = new ContainsTarget();
    for (TestCase testCase : testCases) {
      boolean actual = solution.solve(testCase.root, testCase.target);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\nfind(root, %d): got: %b, want: %b\n",
        testCase.target, actual, testCase.expected));
      }
    }
  }
}

public class P11_12_BSTSearch {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
