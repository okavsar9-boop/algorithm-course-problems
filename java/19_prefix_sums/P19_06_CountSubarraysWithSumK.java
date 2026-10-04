// 19.6 - Count Subarrays With Sum K
// Run: javac P19_06_CountSubarraysWithSumK.java && java P19_06_CountSubarraysWithSumK

import java.util.*;
import java.util.function.*;

class CountSubarrays {
  public int solve(int[] arr, int k) {
    int[] prefixSum = new int[arr.length];
    prefixSum[0] = arr[0];
    for (int i = 1; i < arr.length; i++) {
      prefixSum[i] = prefixSum[i - 1] + arr[i];
    }

    Map<Integer, Integer> prefixSumToCount = new HashMap<>();
    prefixSumToCount.put(0, 1); // For the empty prefix.
    int count = 0;
    for (int val : prefixSum) {
      count += prefixSumToCount.getOrDefault(val - k, 0);
      prefixSumToCount.put(val, prefixSumToCount.getOrDefault(val, 0) + 1);
    }
    return count;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 1, 2, 3, 2, 1 }, 3, 3 },
        { new int[] { -1, -2, -3, 2, 1 }, -3, 4 },
        // Edge case: All zeros
        { new int[] { 0, 0, 0 }, 0, 6 },
        // Edge case: No subarray with sum k
        { new int[] { 1, 2, 3 }, 10, 0 },
    };

    CountSubarrays solution = new CountSubarrays();
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

public class P19_06_CountSubarraysWithSumK {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
