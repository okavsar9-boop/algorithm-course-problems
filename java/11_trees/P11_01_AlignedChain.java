// 11.1 - Aligned Chain
// Run: javac P11_01_AlignedChain.java && java P11_01_AlignedChain

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

class LongestAlignedChain {
  private int res = 0;

  private int visit(Node node, int depth) {
    if (node == null) {
      return 0;
    }
    int leftChain = visit(node.left, depth + 1);
    int rightChain = visit(node.right, depth + 1);
    int currentChain = 0;
    if (node.value == depth) {
      currentChain = 1 + Math.max(leftChain, rightChain);
      res = Math.max(res, currentChain);
    }
    return currentChain;
  }

  public int solve(Node root) {
    res = 0;
    visit(root, 0);
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
    TestCase[] testCases = {
      // Test 1: from the book
      new TestCase(createNode(7,
      createNode(1,
      createNode(2,
      createNode(4, null, null),
      createNode(3, null, null)),
      createNode(8, null, null)),
      createNode(3,
      createNode(2,
      createNode(3, null, null),
      null),
      null)),
      3),
      // Test 2
      new TestCase(createNode(0,
      createNode(1,
      createNode(2,
      createNode(3, null, null),
      null),
      createNode(4, null, null)),
      createNode(5, null, null)),
      4),
      // Test 3: Empty tree
      new TestCase(null, 0),
      // Test 4: Single node aligned at root
      new TestCase(createNode(0, null, null), 1),
      // Test 5: Single node not aligned
      new TestCase(createNode(1, null, null), 0),
      // Test 6: Multiple valid chains, should return longest
      new TestCase(createNode(0,
      createNode(1,
      createNode(2,
      createNode(4, null, null),
      null),
      createNode(2,
      createNode(3, null, null),
      null)),
      null),
      4),
      // Test 7: No aligned nodes
      new TestCase(createNode(5,
      createNode(4,
      createNode(3, null, null),
      createNode(3, null, null)),
      createNode(2, null, null)),
      0),
      // Test 8
      new TestCase(createNode(0,
      createNode(1, null, null),
      createNode(1, null, null)),
      2),
    };

    LongestAlignedChain solution = new LongestAlignedChain();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\naligned_chain(tree): got: %d, want: %d\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_01_AlignedChain {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
