// 19.7 - Longest Subarray With Sum K
// Run: javac P19_07_LongestSubarrayWithSumK.java && java P19_07_LongestSubarrayWithSumK

import java.util.*;
import java.util.function.*;

class LongestSubarrayWithSumK {
  public int solve(int[] arr, int k) {
    int[] prefixSum = new int[arr.length];
    prefixSum[0] = arr[0];
    for (int i = 1; i < arr.length; i++) {
      prefixSum[i] = prefixSum[i - 1] + arr[i];
    }

    Map<Integer, Integer> prefixSumToIndex = new HashMap<>();
    prefixSumToIndex.put(0, -1); // For the empty prefix.
    int res = -1;
    for (int r = 0; r < prefixSum.length; r++) {
      int val = prefixSum[r];
      if (prefixSumToIndex.containsKey(val - k)) {
        int l = prefixSumToIndex.get(val - k);
        res = Math.max(res, r - l);
      }
      if (!prefixSumToIndex.containsKey(val)) {
        prefixSumToIndex.put(val, r);
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 1, 2, 3, 2, 1 }, 3, 2 },
        { new int[] { -1, -2, -3, 2, 1 }, -3, 5 },
        // Edge case: All zeros
        { new int[] { 0, 0, 0 }, 0, 3 },
        // Edge case: No subarray with sum k
        { new int[] { 1, 2, 3 }, 10, -1 },
    };

    LongestSubarrayWithSumK solution = new LongestSubarrayWithSumK();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(arr, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            java.util.Arrays.toString(arr), k, got, want));
      }
    }
  }
}

public class P19_07_LongestSubarrayWithSumK {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
