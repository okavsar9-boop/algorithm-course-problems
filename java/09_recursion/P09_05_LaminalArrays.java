// 9.5 - Laminal Arrays
// Run: javac P09_05_LaminalArrays.java && java P09_05_LaminalArrays

import java.util.*;
import java.util.function.*;

class MaxLaminalSumInefficient {
  public int solve(int[] arr) { // O(n log n)
    return maxLaminalSumRec(arr, 0, arr.length);
  }

  // Returns the max sum for a laminal array in arr[l:r].
  private int maxLaminalSumRec(int[] arr, int l, int r) {
    if (r - l == 1) {
      return arr[l];
    }
    int mid = (l + r) / 2;
    int option1 = maxLaminalSumRec(arr, l, mid);
    int option2 = maxLaminalSumRec(arr, mid, r);
    int option3 = 0;
    for (int i = l; i < r; i++) {
      option3 += arr[i];
    }
    return Math.max(Math.max(option1, option2), option3);
  }
}
class MaxLaminalSum {
  public int solve(int[] arr) { // O(n)
    return maxLaminalSumRec(arr, 0, arr.length)[0];
  }

  // Returns the max sum for a laminal array in arr[l:r] and the sum of
  // arr[l:r].
  private int[] maxLaminalSumRec(int[] arr, int l, int r) {
    if (r - l == 1) {
      return new int[] { arr[l], arr[l] };
    }

    int mid = (l + r) / 2;
    int[] left = maxLaminalSumRec(arr, l, mid);
    int[] right = maxLaminalSumRec(arr, mid, r);
    int option1 = left[0];
    int option2 = right[0];
    int option3 = left[1] + right[1];
    return new int[] { Math.max(Math.max(option1, option2), option3), option3 };
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book
        { new int[] { 3, -9, 2, 4, -1, 5, 5, -4 }, 6 },
        // Example 2 from book
        { new int[] { 1 }, 1 },
        // Example 3 from book
        { new int[] { -1, -2 }, -1 },
        // Additional test case
        { new int[] { 1, 2, 3, 4 }, 10 },
        // Additional test case with all negatives
        { new int[] { -2, -1, -4, -3 }, -1 },
        // Large test case
        { new int[] { 1, -2, 3, -4, 5, -6, 7, -8, 9, -10, 11, -12, 13, -14, 15,
            -16 }, 15 },
    };

    MaxLaminalSum solution = new MaxLaminalSum();
    MaxLaminalSumInefficient solutionInefficient = new MaxLaminalSumInefficient();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int want = (Integer) test[1];
      int got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            java.util.Arrays.toString(arr), got, want));
      }
      int gotInefficient = solutionInefficient.solve(arr);
      if (gotInefficient != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            java.util.Arrays.toString(arr), gotInefficient, want));
      }
    }
  }
}

public class P09_05_LaminalArrays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
