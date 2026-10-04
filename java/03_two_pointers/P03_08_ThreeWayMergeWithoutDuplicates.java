// 3.8 - Three-Way Merge Without Duplicates
// Run: javac P03_08_ThreeWayMergeWithoutDuplicates.java && java P03_08_ThreeWayMergeWithoutDuplicates

import java.util.*;
import java.util.function.*;

class ThreeWayMerge {
  public List<Integer> solve(List<Integer> arr1, List<Integer> arr2, List<Integer> arr3) {
    int p1 = 0, p2 = 0, p3 = 0;
    List<Integer> res = new ArrayList<>();
    while (p1 < arr1.size() || p2 < arr2.size() || p3 < arr3.size()) {
      // Find the smallest value among current positions
      int minVal = Integer.MAX_VALUE;
      if (p1 < arr1.size()) {
        minVal = Math.min(minVal, arr1.get(p1));
      }
      if (p2 < arr2.size()) {
        minVal = Math.min(minVal, arr2.get(p2));
      }
      if (p3 < arr3.size()) {
        minVal = Math.min(minVal, arr3.get(p3));
      }

      // Skip duplicates of minVal in all arrays
      if (p1 < arr1.size() && arr1.get(p1) == minVal) {
        p1++;
      }
      if (p2 < arr2.size() && arr2.get(p2) == minVal) {
        p2++;
      }
      if (p3 < arr3.size() && arr3.get(p3) == minVal) {
        p3++;
      }

      // Only add if we haven't added this value before
      if (res.isEmpty() || res.get(res.size() - 1) != minVal) {
        res.add(minVal);
      }
    }

    return res;

  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example from the book
      { new int[] { 2, 3, 3, 4, 5, 7 }, new int[] { 3, 3, 9 },
        new int[] { 3, 3, 9 }, new int[] { 2, 3, 4, 5, 7, 9 } },
      // Additional test cases
      { new int[] {}, new int[] {}, new int[] {}, new int[] {} },
      { new int[] { 1 }, new int[] {}, new int[] {}, new int[] { 1 } },
      { new int[] { 1 }, new int[] { 1 }, new int[] { 1 }, new int[] { 1 } },
      { new int[] { 1, 2, 3 }, new int[] { 2, 3, 4 }, new int[] { 3, 4, 5 },
        new int[] { 1, 2, 3, 4, 5 } },
      { new int[] { 1, 1, 1 }, new int[] { 1, 1 }, new int[] { 1 },
        new int[] { 1 } },
      { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 7, 8, 9 },
        new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } },
    };

    ThreeWayMerge solution = new ThreeWayMerge();
    for (Object[] test : tests) {
      int[] arr1Array = (int[]) test[0];
      int[] arr2Array = (int[]) test[1];
      int[] arr3Array = (int[]) test[2];
      int[] wantArray = (int[]) test[3];

      List<Integer> arr1 = new ArrayList<>();
      List<Integer> arr2 = new ArrayList<>();
      List<Integer> arr3 = new ArrayList<>();
      List<Integer> want = new ArrayList<>();

      for (int num : arr1Array) arr1.add(num);
      for (int num : arr2Array) arr2.add(num);
      for (int num : arr3Array) arr3.add(num);
      for (int num : wantArray) want.add(num);

      List<Integer> got = solution.solve(arr1, arr2, arr3);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %s, %s): got: %s, want: %s\n",
        arr1, arr2, arr3, got, want));
      }
    }

  }
}

public class P03_08_ThreeWayMergeWithoutDuplicates {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
