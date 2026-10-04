// 3.11 - Interval Intersection
// Run: javac P03_11_IntervalIntersection.java && java P03_11_IntervalIntersection

import java.util.*;
import java.util.function.*;

class Intersection {
  public int[] solve(int[] int1, int[] int2) {
    int overlapStart = Math.max(int1[0], int2[0]);
    int overlapEnd = Math.min(int1[1], int2[1]);
    return new int[] {overlapStart, overlapEnd};
  }
}

class IntervalIntersection {
  public int[][] solve(int[][] arr1, int[][] arr2) {
    int p1 = 0, p2 = 0;
    int n1 = arr1.length, n2 = arr2.length;
    List<int[]> res = new ArrayList<>();

    while (p1 < n1 && p2 < n2) {
      int[] int1 = arr1[p1], int2 = arr2[p2];
      if (int1[1] < int2[0]) {
        p1++;
      } else if (int2[1] < int1[0]) {
        p2++;
      } else {
        res.add(new Intersection().solve(int1, int2));
        if (int1[1] < int2[1]) {
          p1++;
        } else {
          p2++;
        }
      }
    }
    return res.toArray(new int[0][]);

  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      {new int[][] {{0, 1}, {4, 6}, {7, 8}},
        new int[][] {{2, 3}, {5, 9}, {10, 11}},
        new int[][] {{5, 6}, {7, 8}}},
      // Example 2 from the book
      {new int[][] {{2, 4}, {5, 8}},
        new int[][] {{3, 3}, {4, 7}},
        new int[][] {{3, 3}, {4, 4}, {5, 7}}},
      // Additional test cases
      {new int[][] {}, new int[][] {}, new int[][] {}},
      {new int[][] {{1, 2}}, new int[][] {}, new int[][] {}},
      {new int[][] {{1, 3}}, new int[][] {{2, 4}}, new int[][] {{2, 3}}},
      {new int[][] {{1, 5}}, new int[][] {{2, 3}}, new int[][] {{2, 3}}},
      {new int[][] {{1, 2}, {3, 4}}, new int[][] {{2, 3}},
        new int[][] {{2, 2}, {3, 3}}},
    };

    IntervalIntersection solution = new IntervalIntersection();
    for (Object[] test : tests) {
      int[][] arr1 = (int[][]) test[0];
      int[][] arr2 = (int[][]) test[1];
      int[][] want = (int[][]) test[2];
      int[][] got = solution.solve(arr1, arr2);
      if (!Arrays.deepEquals(got, want)) {
        throw new RuntimeException(String.format(
        "\ninterval_intersection(%s, %s): got: %s, want: %s\n",
        Arrays.deepToString(arr1), Arrays.deepToString(arr2),
        Arrays.deepToString(got), Arrays.deepToString(want)));
      }
    }

  }
}

public class P03_11_IntervalIntersection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
