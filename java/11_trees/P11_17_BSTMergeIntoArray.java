// 11.17 - BST Merge Into Array
// Run: javac P11_17_BSTMergeIntoArray.java && java P11_17_BSTMergeIntoArray

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

class MergeIntoArray {
  private void inorder(Node root, List<Integer> arr) {
    if (root == null) {
      return;
    }
    inorder(root.left, arr);
    arr.add(root.value);
    inorder(root.right, arr);
  }

  public List<Integer> solve(Node root1, Node root2) {
    List<Integer> arr1 = new ArrayList<>();
    List<Integer> arr2 = new ArrayList<>();
    inorder(root1, arr1);
    inorder(root2, arr2);

    // Merge sorted arrays
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < arr1.size() && j < arr2.size()) {
      if (arr1.get(i) <= arr2.get(j)) {
        result.add(arr1.get(i));
        i++;
      } else {
        result.add(arr2.get(j));
        j++;
      }
    }

    // Add remaining elements
    result.addAll(arr1.subList(i, arr1.size()));
    result.addAll(arr2.subList(j, arr2.size()));
    return result;
  }
}


class RunTests {
  private Node createNode(int value) {
    return new Node(value);
  }

  private Node createNode(int value, Node left,
  Node right) {
    Node node = new Node(value);
    node.left = left;
    node.right = right;
    return node;
  }

  public void runTests() {
    // Create test trees
    Node root1 = createNode(5,
    createNode(2, null, createNode(4)),
    createNode(9, createNode(9), createNode(11)));

    Node root2 = createNode(3,
    createNode(2, createNode(1), null),
    createNode(7, createNode(6), createNode(8)));

    Node root3 = createNode(2,
    createNode(2),
    createNode(2));

    Node root4 = createNode(2,
    createNode(2),
    createNode(2));

    TestCase[] testCases = {
      // Example 1 from the book
      new TestCase(root1, root2,
      Arrays.asList(1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9, 11)),
      // Example 2 from the book
      new TestCase(root3, root4,
      Arrays.asList(2, 2, 2, 2, 2, 2)),
      // Edge cases
      new TestCase(null, null,
      Collections.<Integer>emptyList()),
      new TestCase(createNode(1), null,
      Arrays.asList(1)),
      new TestCase(null, createNode(1),
      Arrays.asList(1)),
      new TestCase(createNode(1), createNode(2),
      Arrays.asList(1, 2)),
    };

    MergeIntoArray solution = new MergeIntoArray();
    for (TestCase testCase : testCases) {
      List<Integer> actual = solution.solve(testCase.root1, testCase.root2);
      if (!actual.equals(testCase.expected)) {
        throw new RuntimeException(String.format(
        "\nmerge_into_array(tree1, tree2): got: %s, want: %s\n",
        actual, testCase.expected));
      }
    }
  }
}

public class P11_17_BSTMergeIntoArray {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
