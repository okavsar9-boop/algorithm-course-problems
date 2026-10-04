// 3.9 - Sort Valley-Shaped Array
// Run: javac P03_09_SortValleyShapedArray.java && java P03_09_SortValleyShapedArray

import java.util.*;
import java.util.function.*;

class SortValleyArray {
  public int[] solve(int[] arr) {
    if (arr.length == 0) {
      return new int[] {};
    }
    int l = 0, r = arr.length - 1;
    int[] res = new int[arr.length];
    int i = arr.length - 1;
    while (l < r) {
      if (arr[l] >= arr[r]) {
        res[i] = arr[l];
        l++;
        i--;
      } else {
        res[i] = arr[r];
        r--;
        i--;
      }
    }
    res[0] = arr[l];
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 8, 4, 2, 6 }, new int[] { 2, 4, 6, 8 } },
        // Example 2 from the book
        { new int[] { 1, 2 }, new int[] { 1, 2 } },
        // Example 3 from the book
        { new int[] { 2, 2, 1, 1 }, new int[] { 1, 1, 2, 2 } },
        // Additional test cases
        { new int[] {}, new int[] {} },
        { new int[] { 1 }, new int[] { 1 } },
        { new int[] { 3, 2, 1, 4 }, new int[] { 1, 2, 3, 4 } },
        { new int[] { 5, 4, 3, 2, 1, 2, 3 },
            new int[] { 1, 2, 2, 3, 3, 4, 5 } },
        { new int[] { 1, 1, 1, 1 }, new int[] { 1, 1, 1, 1 } },
    };

    SortValleyArray solution = new SortValleyArray();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int[] want = (int[]) test[1];
      int[] got = solution.solve(arr);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            Arrays.toString(arr), Arrays.toString(got), Arrays.toString(want)));
      }
    }
  }
}

public class P03_09_SortValleyShapedArray {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
