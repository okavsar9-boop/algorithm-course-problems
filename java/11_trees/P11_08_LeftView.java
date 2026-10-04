// 11.8 - Left View
// Run: javac P11_08_LeftView.java && java P11_08_LeftView

import java.util.*;
import java.util.function.*;

node_depth_queue_recipe(root):
  Q = Queue()
  Q.add((root, 0))
  while not Q.empty():
    node, depth = Q.pop()
    if not node:
      continue
    # Do something with node and depth.
    Q.add((node.left, depth+1))
    Q.add((node.right, depth+1))

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

class LeftView {
  public List<Integer> solve(Node root) {
    if (root == null) {
      return Collections.emptyList();
    }

    List<Integer> res = new ArrayList<>();
    Queue<Map.Entry<Node, Integer>> queue = new LinkedList<>();
    queue.offer(new AbstractMap.SimpleEntry<>(root, 0));
    int currentDepth = -1;

    while (!queue.isEmpty()) {
      Map.Entry<Node, Integer> entry = queue.poll();
      Node node = entry.getKey();
      int depth = entry.getValue();
      if (node == null) {
        continue;
      }
      if (depth == currentDepth + 1) {
        res.add(node.value);
        currentDepth++;
      }
      queue.offer(new AbstractMap.SimpleEntry<>(node.left, depth + 1));
      queue.offer(new AbstractMap.SimpleEntry<>(node.right, depth + 1));
    }
    return res;
  }
}


class RunTests {
  private static Node createNode(int value) {
    return new Node(value);
  }

  private static Node createNode(int value, Node left, Node right) {
    Node node = new Node(value);
    node.left = left;
    node.right = right;
    return node;
  }

  public void runTests() {
    TestCase[] testCases = {
        // Test 1
        new TestCase(createNode(1,
            createNode(2,
                createNode(4, null, null),
                createNode(5, null, null)),
            createNode(3,
                null,
                createNode(6, null, null))),
            Arrays.asList(1, 2, 4)),
        // Test 2: Empty tree
        new TestCase(null,
            Collections.<Integer>emptyList()),
        // Test 3: Single node
        new TestCase(createNode(1, null, null),
            Arrays.asList(1)),
        // Test 4: Only right children
        new TestCase(createNode(1,
            null,
            createNode(2,
                null,
                createNode(3, null, null))),
            Arrays.asList(1, 2, 3)),
        // Test 5: Only left children
        new TestCase(createNode(1,
            createNode(2,
                createNode(3, null, null),
                null),
            null),
            Arrays.asList(1, 2, 3)),
        // Test 6: Example from the book
        new TestCase(createNode(5,
            createNode(2,
                null,
                createNode(6, null, null)),
            createNode(9,
                createNode(9,
                    null,
                    createNode(1, null, null)),
                createNode(8, null, null))),
            Arrays.asList(5, 2, 6, 1)),
    };

    LeftView solution = new LeftView();
    for (TestCase testCase : testCases) {
      List<Integer> actual = solution.solve(testCase.root);
      if (!actual.equals(testCase.expected)) {
        throw new RuntimeException(String.format(
            "\nleft_view(tree): got: %s, want: %s\n",
            actual, testCase.expected));
      }
    }
  }
}

public class P11_08_LeftView {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
