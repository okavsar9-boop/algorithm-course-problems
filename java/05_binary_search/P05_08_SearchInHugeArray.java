// 5.8 - Search In Huge Array
// Run: javac P05_08_SearchInHugeArray.java && java P05_08_SearchInHugeArray

import java.util.*;
import java.util.function.*;

class FindThroughApi {
  public int solve(int target, Function<Integer, Integer> fetch) {
    int l = 0;
    if (!isBefore(l, target, fetch)) {
      if (fetch.apply(l) == target) {
        return l;
      }
      return -1;
    }

    // Step 1: Get the rightmost boundary
    int r = 1;
    while (isBefore(r, target, fetch)) {
      r *= 2;
    }

    // Step 2: Binary search
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, target, fetch)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    if (fetch.apply(r) == target) {
      return r;
    }
    return -1;
  }

  private boolean isBefore(int idx, int target,
  Function<Integer, Integer> fetch) {
    return fetch.apply(idx) != -1 && fetch.apply(idx) < target;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 - target exists
        { 5, 2, new int[] { 1, 3, 5, 7, 9 } },
        // Example 2 - target doesn't exist
        { 6, -1, new int[] { 1, 3, 5, 7, 9 } },
        // Edge case - target at start
        { 1, 0, new int[] { 1, 3, 5, 7, 9 } },
        // Edge case - target at end
        { 9, 4, new int[] { 1, 3, 5, 7, 9 } },
        // All duplicates
        { 1, 0, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 } },
        // Ensure we don't go out of bounds
        { 10, 9, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 } }
    };

    FindThroughApi solution = new FindThroughApi();
    for (Object[] test : tests) {
      int target = (int) test[0];
      int want = (int) test[1];
      int[] secretArray = (int[]) test[2];
      Function<Integer, Integer> fetch = makeFetchFunction(secretArray);
      int got = solution.solve(target, fetch);
      if (got != want) {
        throw new RuntimeException(String.format(
            "findThroughApi(%d): got %d, want %d",
            target, got, want));
      }
    }
  }

  private Function<Integer, Integer> makeFetchFunction(int[] secretArray) {
    return idx -> {
      if (idx >= secretArray.length || idx < 0) {
        return -1;
      }
      return secretArray[idx];
    };
  }
}

public class P05_08_SearchInHugeArray {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
