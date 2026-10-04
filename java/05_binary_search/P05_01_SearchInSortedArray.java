// 5.1 - Search In Sorted Array
// Run: javac P05_01_SearchInSortedArray.java && java P05_01_SearchInSortedArray

import java.util.*;
import java.util.function.*;

class SearchInSortedArray {
  public int solve(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    while (l <= r) {
      int mid = (l + r) / 2;
      if (arr[mid] == target) {
        return mid;
      } else if (arr[mid] < target) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    return -1;
  }
}

transition_point_recipe()
    - the range is empty
    - l is 'after'  (the whole range is 'after')
    - r is 'before' (the whole range is 'before')

  while l and r are not next to each other (r - l > 1)
    mid = (l + r) / 2
    if is_before(mid)
      l = mid
    else
      r = mid

  return l (the last 'before'), r (the first 'after'), or something else,
         depending on the problem
We define the 'before' region as the elements < target, and the 'after' region as the elements >= target.

class SearchInSortedArrayWithTransitionPoint {
  public int solve(int[] arr, int target) {
    // Handle empty array first
    if (arr.length == 0) {
      return -1;
    }

    int l = 0, r = arr.length - 1;

    // Handle edge cases to ensure l is in the before region
    // and r is in the after region
    if (!isBefore(l, arr, target)) {
      if (arr[l] == target) {
        return l;
      }
      return -1;
    }
    if (isBefore(r, arr, target)) {
      return -1;
    }

    // Main binary search loop
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
        // Example 1 from book
        { new int[] { -2, 0, 3, 4, 7, 9, 11 }, 3, 2 },
        // Example 2 from book
        { new int[] { -2, 0, 3, 4, 7, 9, 11 }, 2, -1 },
        // Edge case - empty array
        { new int[] {}, 5, -1 },
        // Edge case - target at start
        { new int[] { 1, 2, 3 }, 1, 0 },
        // Edge case - target at end
        { new int[] { 1, 2, 3 }, 3, 2 },
        // Edge case - single element
        { new int[] { 5 }, 5, 0 },
        // Edge case - not found
        { new int[] { 1, 3, 5 }, 2, -1 }
    };

    SearchInSortedArray solution1 = new SearchInSortedArray();
    SearchInSortedArrayWithTransitionPoint solution2 = new SearchInSortedArrayWithTransitionPoint();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int target = (int) test[1];
      int want = (int) test[2];
      int got = solution1.solve(arr, target);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(arr), target, got, want));
      }
      got = solution2.solve(arr, target);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(arr), target, got, want));
      }
    }
  }
}

public class P05_01_SearchInSortedArray {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
