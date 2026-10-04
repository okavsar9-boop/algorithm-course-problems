// 5.5 - Target Count Divisible By K
// Run: javac P05_05_TargetCountDivisibleByK.java && java P05_05_TargetCountDivisibleByK

import java.util.*;
import java.util.function.*;

class TargetCountDivisibleByK {
  // Returns -1 if target is not in arr, or the first index of target in arr
  // otherwise.
  private int binarySearchFirst(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    if (arr[l] > target || arr[r] < target) {
      return -1;
    }
    if (arr[l] == target) {
      return l;
    }

    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (arr[mid] < target) { // 'before' range: < target
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

  // Assumes target is in arr. Returns the last index of target in arr.
  private int binarySearchLast(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    if (arr[r] == target) {
      return r;
    }

    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (arr[mid] <= target) { // 'before' range: <= target
        l = mid;
      } else {
        r = mid;
      }
    }
    return l;
  }

  public boolean solve(int[] arr, int target, int k) {
    int first = binarySearchFirst(arr, target);
    if (first == -1) {
      return true; // 0 is a multiple of any number.
    }
    int last = binarySearchLast(arr, target);
    int count = last - first + 1;
    return count % k == 0;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[] { 1, 2, 2, 2, 2, 2, 2, 3 }, 2, 3, true },
        // Example 2
        { new int[] { 1, 2, 2, 2, 2, 2, 2, 3 }, 2, 4, false },
        // Example 3: 0 occurrences, 0 is multiple of any number
        { new int[] { 1, 2, 2, 2, 2, 2, 2, 3 }, 4, 3, true },
        // Example 4
        { new int[] { 1, 1, 2, 2, 2 }, 1, 3, false },
        // single occurrence, at the start
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 1, 1, true },
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 1, 2, false },
        // single occurrence, at the end
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 19, 1, true },
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 19, 2, false },
        // single occurrence, in the middle
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 9, 1, true },
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 9, 2, false },
        // smaller than any elements
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 0, 1, true },
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 0, 2, true },
        // larger than any elements
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 20, 1, true },
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 }, 20, 2, true },
        // Edge case - every occurrence is target
        { new int[] { 5, 5, 5, 5, 5 }, 5, 5, true },
        { new int[] { 5, 5, 5, 5, 5 }, 5, 3, false },
    };

    TargetCountDivisibleByK solution = new TargetCountDivisibleByK();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int target = (int) test[1];
      int k = (int) test[2];
      boolean want = (boolean) test[3];
      boolean got = solution.solve(arr, target, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %d): got: %b, want: %b\n",
            Arrays.toString(arr), target, k, got, want));
      }
    }
  }
}

public class P05_05_TargetCountDivisibleByK {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
