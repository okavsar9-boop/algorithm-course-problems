// 11.7 - Evaluate Expression Tree
// Run: javac P11_07_EvaluateExpressionTree.java && java P11_07_EvaluateExpressionTree

import java.util.*;
import java.util.function.*;

class Node {
  String kind; // One of "sum", "product", "max", "min", or "num"
  int num; // Only valid when kind is "num"
  List<Node> children; // Only valid when kind is not "num"

  Node(String kind, int num, List<Node> children) {
    this.kind = kind;
    this.num = num;
    this.children = children;
  }
}

class Evaluate {
  private int product(List<Integer> vals) {
    return vals.stream().reduce(1, (a, b) -> a * b);
  }

  public int solve(Node root) {
    if (root.kind.equals("num")) {
      return root.num;
    }
    List<Integer> childrenEvals = new ArrayList<>();
    for (Node child : root.children) {
      childrenEvals.add(solve(child));
    }
    switch (root.kind) {
      case "sum":
      return childrenEvals.stream().mapToInt(Integer::intValue).sum();
      case "product":
      return product(childrenEvals);
      case "max":
      return Collections.max(childrenEvals);
      case "min":
      return Collections.min(childrenEvals);
      default:
      throw new RuntimeException("Invalid node kind");
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Test 0: Example from the book
        { new Node("min", 0, Arrays.asList(
            new Node("max", 0, Arrays.asList(
                new Node("num", 4, null),
                new Node("num", 6, null),
                new Node("sum", 0, Arrays.asList(
                    new Node("num", 5, null),
                    new Node("num", 7, null))))),
            new Node("sum", 0, Arrays.asList(
                new Node("product", 0, Arrays.asList(
                    new Node("num", 6, null),
                    new Node("num", 8, null))))))),
            12 },
        // Test 1: Example - (2 + 3) * 4
        { new Node("product", 0, Arrays.asList(
            new Node("sum", 0, Arrays.asList(
                new Node("num", 2, null),
                new Node("num", 3, null))),
            new Node("num", 4, null))),
            20 },
        // Test 2: Single number node
        { new Node("num", 5, null), 5 },
        // Test 3: Empty sum node
        { new Node("sum", 0, new ArrayList<>()), 0 },
        // Test 4: Empty product node
        { new Node("product", 0, new ArrayList<>()), 1 },
        // Test 5: Complex expression with all operations
        // min(2, max(3,4)) + product(1,2,3)
        { new Node("sum", 0, Arrays.asList(
            new Node("min", 0, Arrays.asList(
                new Node("num", 2, null),
                new Node("max", 0, Arrays.asList(
                    new Node("num", 3, null),
                    new Node("num", 4, null))))),
            new Node("product", 0, Arrays.asList(
                new Node("num", 1, null),
                new Node("num", 2, null),
                new Node("num", 3, null))))),
            8 }
    };

    Evaluate solution = new Evaluate();
    for (int i = 0; i < tests.length; i++) {
      Node root = (Node) tests[i][0];
      int want = (int) tests[i][1];
      int got = solution.solve(root);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nevaluate(root%d): got: %d, want: %d\n",
            i + 1, got, want));
      }
    }
  }
}

public class P11_07_EvaluateExpressionTree {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
