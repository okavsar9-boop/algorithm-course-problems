// 3.3 - Array Intersection
// Run: javac P03_03_ArrayIntersection.java && java P03_03_ArrayIntersection

import java.util.*;
import java.util.function.*;

class CommonElements {
  public int[] solve(int[] arr1, int[] arr2) {
    int p1 = 0, p2 = 0;
    List<Integer> res = new ArrayList<>();
    while (p1 < arr1.length && p2 < arr2.length) {
      if (arr1[p1] == arr2[p2]) {
        res.add(arr1[p1]);
        p1++;
        p2++;
      } else if (arr1[p1] < arr2[p2]) {
        p1++;
      } else {
        p2++;
      }
    }
    return res.stream().mapToInt(i -> i).toArray();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { 1, 2, 3 }, new int[] { 1, 3, 5 }, new int[] { 1, 3 } },
      // Example 2 from the book
      { new int[] { 1, 1, 1 }, new int[] { 1, 1 }, new int[] { 1, 1 } },
      // Additional test cases
      { new int[] {}, new int[] {}, new int[] {} },
      { new int[] { 1 }, new int[] {}, new int[] {} },
      { new int[] {}, new int[] { 1 }, new int[] {} },
      { new int[] { 1 }, new int[] { 1 }, new int[] { 1 } },
      { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] {} },
      { new int[] { 1, 2, 2, 3 }, new int[] { 2, 2, 3 },
        new int[] { 2, 2, 3 } },
    };

    CommonElements solution = new CommonElements();
    for (Object[] test : tests) {
      int[] arr1 = (int[]) test[0];
      int[] arr2 = (int[]) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(arr1, arr2);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %s): got: %s, want: %s\n",
        Arrays.toString(arr1), Arrays.toString(arr2),
        Arrays.toString(got), Arrays.toString(want)));
      }
    }

  }
}

public class P03_03_ArrayIntersection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
