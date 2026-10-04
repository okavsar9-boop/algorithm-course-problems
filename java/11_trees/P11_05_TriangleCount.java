// 11.5 - Triangle Count
// Run: javac P11_05_TriangleCount.java && java P11_05_TriangleCount

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

class TriangleCount {
  private int res;

  private static class TreeSides {
    final int leftSide;
    final int rightSide;

    TreeSides(int leftSide, int rightSide) {
      this.leftSide = leftSide;
      this.rightSide = rightSide;
    }
  }

  private TreeSides visit(Node node) {
    if (node == null) {
      return new TreeSides(0, 0); // left_side, right_side counts
    }

    TreeSides left = visit(node.left); // Only care about left descendants
    TreeSides right = visit(node.right); // Only care about right descendants

    // Number of triangles with this node at the top is min of left and right
    // sides
    res += Math.min(left.leftSide, right.rightSide);

    // Return counts of consecutive left/right descendants
    return new TreeSides(left.leftSide + 1, right.rightSide + 1);
  }

  public int solve(Node root) {
    res = 0;
    visit(root);
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
      // Example
      new TestCase(createNode(1, null, null), 0), // Single node
      new TestCase(null, 0), // Empty tree

      // Example from the book
      new TestCase(createNode(1,
      createNode(2,
      createNode(4, null, null),
      createNode(5, null, null)),
      createNode(3,
      createNode(6, null, null),
      createNode(7, null, null))),
      4),

      // No triangles - only left children
      new TestCase(createNode(1,
      createNode(2,
      createNode(3, null, null),
      null),
      null),
      0),

      // No triangles - only right children
      new TestCase(createNode(1,
      null,
      createNode(2,
      null,
      createNode(3, null, null))),
      0),

      // Single triangle
      new TestCase(createNode(1,
      createNode(2, null, null),
      createNode(3, null, null)),
      1),
    };

    TriangleCount solution = new TriangleCount();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root);
      if (actual != testCase.expected) {
        throw new RuntimeException(String.format(
        "\ntriangle_count(root): got: %d, want: %d\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_05_TriangleCount {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
