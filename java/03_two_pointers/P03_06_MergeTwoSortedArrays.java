// 3.6 - Merge Two Sorted Arrays
// Run: javac P03_06_MergeTwoSortedArrays.java && java P03_06_MergeTwoSortedArrays

import java.util.*;
import java.util.function.*;

class Merge {
  public List<Integer> solve(List<Integer> arr1, List<Integer> arr2) {
    int p1 = 0, p2 = 0;
    List<Integer> res = new ArrayList<>();
    while (p1 < arr1.size() && p2 < arr2.size()) {
      if (arr1.get(p1) < arr2.get(p2)) {
        res.add(arr1.get(p1));
        p1++;
      } else {
        res.add(arr2.get(p2));
        p2++;
      }
    }
    while (p1 < arr1.size()) {
      res.add(arr1.get(p1));
      p1++;
    }
    while (p2 < arr2.size()) {
      res.add(arr2.get(p2));
      p2++;
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 3, 4, 5 }, new int[] { 2, 4, 4 },
            new int[] { 1, 2, 3, 4, 4, 4, 5 } },
        // Example 2 from the book
        { new int[] { -1 }, new int[] {}, new int[] { -1 } },
        // Additional test cases
        { new int[] {}, new int[] {}, new int[] {} },
        { new int[] { 1 }, new int[] {}, new int[] { 1 } },
        { new int[] {}, new int[] { 1 }, new int[] { 1 } },
        { new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 },
            new int[] { 1, 2, 3, 4, 5, 6 } },
        { new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 },
            new int[] { 1, 1, 1, 1, 1, 1 } },
    };

    Merge solution = new Merge();
    for (Object[] test : tests) {
      int[] arr1Array = (int[]) test[0];
      int[] arr2Array = (int[]) test[1];
      int[] wantArray = (int[]) test[2];

      List<Integer> arr1 = new ArrayList<>();
      List<Integer> arr2 = new ArrayList<>();
      List<Integer> want = new ArrayList<>();

      for (int num : arr1Array) arr1.add(num);
      for (int num : arr2Array) arr2.add(num);
      for (int num : wantArray) want.add(num);

      List<Integer> got = solution.solve(arr1, arr2);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            arr1, arr2, got, want));
      }
    }
  }
}

public class P03_06_MergeTwoSortedArrays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
