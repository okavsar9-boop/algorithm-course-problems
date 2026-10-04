// 5.4 - 2-Array 2-Sum
// Run: javac P05_04_2Array2Sum.java && java P05_04_2Array2Sum

import java.util.*;
import java.util.function.*;

class TwoArrayTwoSum {
  public int[] solve(int[] sortedArr, int[] unsortedArr) {
    for (int i = 0; i < unsortedArr.length; i++) {
      int idx = binarySearch(sortedArr, -unsortedArr[i]);
      if (idx != -1) {
        return new int[] { idx, i };
      }
    }
    return new int[] { -1, -1 };
  }

  private int binarySearch(int[] arr, int target) {
    if (arr[0] > target || arr[arr.length - 1] < target) {
      return -1;
    }

    if (arr[0] == target) {
      return 0;
    }

    int l = 0, r = arr.length - 1;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, arr, target)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    if (arr[r] == target) {
      return r;
    }
    return -1;
  }

  private boolean isBefore(int i, int[] arr, int target) {
    return arr[i] < target;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from book
        { new int[] { -5, -4, -1, 4, 6, 6, 7 }, new int[] { -3, 7, 18, 4, 6 },
            new int[] { 1, 3 } },
        // no solution
        { new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 }, new int[] { -1, -1 } },
        { new int[] { 1 }, new int[] { -1 }, new int[] { 0, 0 } },
        { new int[] { 1, 2 }, new int[] { -2, -1 }, new int[] { 1, 0 } },
        { new int[] { 0, 1, 2, 3 }, new int[] { 3, 2, 1, 0 },
            new int[] { 0, 3 } }
    };

    TwoArrayTwoSum solution = new TwoArrayTwoSum();
    for (Object[] test : tests) {
      int[] sortedArr = (int[]) test[0];
      int[] unsortedArr = (int[]) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(sortedArr, unsortedArr);
      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            Arrays.toString(sortedArr), Arrays.toString(unsortedArr),
            Arrays.toString(got), Arrays.toString(want)));
      }
    }
  }
}

public class P05_04_2Array2Sum {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
