// 11.2 - Hidden Message
// Run: javac P11_02_HiddenMessage.java && java P11_02_HiddenMessage

import java.util.*;
import java.util.function.*;

class Node {
  public String text;
  public Node left;
  public Node right;

  public Node(String text) {
    this.text = text;
    this.left = null;
    this.right = null;
  }
}

class HiddenMessage {
  private StringBuilder message;

  private void visit(Node node) {
    if (node == null) {
      return;
    }
    if (node.text.charAt(0) == 'b') {
      message.append(node.text.charAt(1));
      visit(node.left);
      visit(node.right);
    } else if (node.text.charAt(0) == 'i') {
      visit(node.left);
      message.append(node.text.charAt(1));
      visit(node.right);
    } else {
      visit(node.left);
      visit(node.right);
      message.append(node.text.charAt(1));
    }
  }

  public String solve(Node root) {
    message = new StringBuilder();
    visit(root);
    return message.toString();
  }
}


class RunTests {
  private static Node createNode(String text) {
    return new Node(text);
  }

  private static Node createNode(String text, Node left, Node right) {
    Node node = new Node(text);
    node.left = left;
    node.right = right;
    return node;
  }

  public void runTests() {
    TestCase[] testCases = {
        // Test 1: Example from the book - "nice_try!"
        new TestCase(createNode("bn",
            createNode("i_",
                createNode("ae",
                    createNode("bi", null, null),
                    createNode("bc", null, null)),
                createNode("it", null, null)),
            createNode("a!",
                createNode("br", null, null),
                createNode("ay", null, null))),
            "nice_try!"),
        // Test 2: Empty tree
        new TestCase(null, ""),
        // Test 3: Single TreeNode with before order
        new TestCase(createNode("bx", null, null), "x"),
        // Test 4: Single TreeNode with in order
        new TestCase(createNode("ix", null, null), "x"),
        // Test 5: Single TreeNode with after order
        new TestCase(createNode("ax", null, null), "x"),
        // Test 6: All before order TreeNodes
        new TestCase(createNode("b1",
            createNode("b2",
                createNode("b4", null, null),
                createNode("b5", null, null)),
            createNode("b3",
                createNode("b6", null, null),
                createNode("b7", null, null))),
            "1245367"),
        // Test 7: All in order TreeNodes
        new TestCase(createNode("i1",
            createNode("i2",
                createNode("i4", null, null),
                createNode("i5", null, null)),
            createNode("i3",
                createNode("i6", null, null),
                createNode("i7", null, null))),
            "4251637"),
        // Test 8: All after order TreeNodes
        new TestCase(createNode("a1",
            createNode("a2",
                createNode("a4", null, null),
                createNode("a5", null, null)),
            createNode("a3",
                createNode("a6", null, null),
                createNode("a7", null, null))),
            "4526731"),
        // Test 9: Mixed orders forming "hello"
        new TestCase(createNode("bh",
            createNode("be",
                createNode("bl", null, null),
                createNode("il", null, null)),
            createNode("ao", null, null)),
            "hello"),
    };

    HiddenMessage solution = new HiddenMessage();
    for (TestCase testCase : testCases) {
      String actual = solution.solve(testCase.root());
      if (!actual.equals(testCase.expected())) {
        throw new RuntimeException(String.format(
            "\nhidden_message(tree): got: %s, want: %s\n",
            actual, testCase.expected()));
      }

    }
  }
}

public class P11_02_HiddenMessage {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
