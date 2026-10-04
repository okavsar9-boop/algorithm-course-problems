// 11.9 - Most Prolific Level
// Run: javac P11_09_MostProlificLevel.java && java P11_09_MostProlificLevel

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

class MostProlificLevel {
  private Map<Integer, Integer> levelCounts(Node root) {
    Queue<Map.Entry<Node, Integer>> queue = new LinkedList<>();
    queue.offer(new AbstractMap.SimpleEntry<>(root, 0));
    Map<Integer, Integer> levelCount = new HashMap<>();

    while (!queue.isEmpty()) {
      Map.Entry<Node, Integer> entry = queue.poll();
      Node node = entry.getKey();
      int depth = entry.getValue();
      if (node == null) {
        continue;
      }
      levelCount.put(depth, levelCount.getOrDefault(depth, 0) + 1);
      queue.offer(new AbstractMap.SimpleEntry<>(node.left, depth + 1));
      queue.offer(new AbstractMap.SimpleEntry<>(node.right, depth + 1));
    }
    return levelCount;
  }

  public int solve(Node root) {
    if (root == null) {
      return -1;
    }
    Map<Integer, Integer> levelCount = levelCounts(root);
    int res = -1;
    double maxProlificness = -1; // Less than any valid prolificness

    for (Map.Entry<Integer, Integer> entry : levelCount.entrySet()) {
      int level = entry.getKey();
      int count = entry.getValue();
      int nextLevelCount = levelCount.getOrDefault(level + 1, 0);
      double prolificness = (double) nextLevelCount / count;
      if (prolificness > maxProlificness ||
      (prolificness == maxProlificness && level < res)) {
        maxProlificness = prolificness;
        res = level;
      }
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

  private boolean contains(int[] arr, int value) {
    for (int v : arr) {
      if (v == value) {
        return true;
      }
    }
    return false;
  }

  public void runTests() {
    TestCase[] testCases = {
        // Test 1
        new TestCase(createNode(5,
            createNode(2,
                null,
                createNode(6, null, null)),
            createNode(9,
                createNode(9,
                    null,
                    createNode(1, null, null)),
                createNode(8, null, null))),
            new int[] { 0 }),
        // Test 2: Empty tree
        new TestCase(null, new int[] { -1 }),
        // Test 3: Single node
        new TestCase(createNode(1, null, null), new int[] { 0 }),
        // Test 4: Perfect binary tree
        new TestCase(createNode(1,
            createNode(2,
                createNode(4, null, null),
                createNode(5, null, null)),
            createNode(3,
                createNode(6, null, null),
                createNode(7, null, null))),
            new int[] { 0, 1 }), // Both level 0 and 1 are valid answers
        // Test 5: Unbalanced tree
        new TestCase(createNode(1,
            createNode(2,
                createNode(4,
                    createNode(8, null, null),
                    createNode(9, null, null)),
                createNode(5, null, null)),
            createNode(3, null, null)),
            new int[] { 0 }),
        // Test 6: Example from the book
        new TestCase(createNode(1,
            createNode(2,
                createNode(4,
                    createNode(8, null, null),
                    createNode(9, null, null)),
                createNode(5,
                    null,
                    createNode(11, null, null))),
            null),
            new int[] { 1 }),
        // Test 7
        new TestCase(createNode(1,
            createNode(2,
                createNode(4,
                    createNode(8, null, null),
                    createNode(9, null, null)),
                null),
            null),
            new int[] { 2 }),
    };

    MostProlificLevel solution = new MostProlificLevel();
    for (TestCase testCase : testCases) {
      int actual = solution.solve(testCase.root);
      if (!contains(testCase.validWants, actual)) {
        throw new RuntimeException(String.format(
            "\nmost_prolific_level(tree): got: %d, valid_wants: %s\n",
            actual, Arrays.toString(testCase.validWants)));
      }
    }
  }
}

public class P11_09_MostProlificLevel {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
