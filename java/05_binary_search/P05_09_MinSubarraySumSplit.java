// 5.9 - Min-Subarray-Sum Split
// Run: javac P05_09_MinSubarraySumSplit.java && java P05_09_MinSubarraySumSplit

import java.util.*;
import java.util.function.*;

class MinSubarraySumSplit {
  public int solve(int[] arr, int k) {
    int l = Arrays.stream(arr).max().getAsInt();
    int r = Arrays.stream(arr).sum();

    if (!isBefore(arr, k, l)) {
      return l;
    }

    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(arr, k, mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    return r;
  }

  // "Is it impossible to split arr into at most k subarrays,
  // each with sum <= max_sum?"
  private boolean isBefore(int[] arr, int k, int maxSum) {
    int splitsRequired = getSplitsRequired(arr, maxSum);
    return splitsRequired > k;
  }

  // Returns the minimum number of subarrays with a given maximum sum.
  // Assumes that max_sum >= max(arr).
  private int getSplitsRequired(int[] arr, int maxSum) {
    int splitsRequired = 1;
    int currentSum = 0;

    for (int num : arr) {
      if (currentSum + num > maxSum) {
        splitsRequired++;
        currentSum = num; // Start a new subarray with the current number.
      } else {
        currentSum += num;
      }
    }

    return splitsRequired;
  }
}
If we modeled this problem as a decision tree, we could start with an empty list of subarrays. Then, we decide which subarray should be the first in the split. For instance, if arr is [1, 2, 3, 4], we could start with [1], [1, 2], [1, 2, 3], or [1, 2, 3, 4]. Then, at each step, we decide which subarray to add next. For instance, if we picked [1, 2] as the first choice, the next one can be [3] or [3, 4]. Once we have added k - 1 subarrays, we don't have any more choice. All remaining elements must go in the final subarray.

3. Base cases:

If x == 1, we must put all remaining elements in a single subarray: min_split(i, 1) = sum(arr[i:n]).
4. General case:

The sum of that array, sum(arr[i:p]), and
5. Original problem: min_split(0, k).

memo = empty map

f(subproblem_id):
  if subproblem is base case:
    return result directly
  if subproblem in memo map:
    return cached result

  memo[subproblem_id] = recurrence relation formula
  return memo[subproblem_id]

return f(initial subproblem)

class MinSubarraySumSplitMemoization {
  public int solve(int[] arr, int k) {
    int n = arr.length;
    Integer[][] memo = new Integer[n][k + 1];
    return minSplitRec(arr, 0, k, n, memo);
  }

  private int minSplitRec(int[] arr, int i, int x, int n, Integer[][] memo) {
    if (memo[i][x] != null) {
      return memo[i][x];
    }

    // Base cases
    if (n - i == x) { // Put each element in its own subarray
      int max = arr[i];
      for (int j = i + 1; j < n; j++) {
        max = Math.max(max, arr[j]);
      }
      memo[i][x] = max;
    } else if (x == 1) { // Put all elements in one subarray
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += arr[j];
      }
      memo[i][x] = sum;
    } else { // General case
      int currentSum = 0;
      int res = Integer.MAX_VALUE;
      for (int p = i; p < n - x + 1; p++) {
        currentSum += arr[p];
        res = Math.min(res,
            Math.max(currentSum, minSplitRec(arr, p + 1, x - 1, n, memo)));
      }
      memo[i][x] = res;
    }

    return memo[i][x];
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 10, 5, 8, 9, 11 }, 3, 17 },
        // Example 2 from the book
        { new int[] { 10, 10, 10, 10, 10 }, 2, 30 },
        // Extra example
        { new int[] { 9, 12, 13 }, 3, 13 },
        // Edge case - k=1
        { new int[] { 1, 2, 3 }, 1, 6 },
        // Edge case - k=length
        { new int[] { 1, 2, 3 }, 3, 3 },
        // Edge case - single element
        { new int[] { 5 }, 1, 5 }
    };

    MinSubarraySumSplit solution = new MinSubarraySumSplit();
    MinSubarraySumSplitMemoization memoSolution = new MinSubarraySumSplitMemoization();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(arr, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nminSubarraySumSplit(%s, %d): got: %d, want: %d\n",
            Arrays.toString(arr), k, got, want));
      }
      int gotMemo = memoSolution.solve(arr, k);
      if (gotMemo != want) {
        throw new RuntimeException(String.format(
            "\nMinSubarraySumSplitMemoization(%s, %d): got: %d, want: %d\n",
            Arrays.toString(arr), k, gotMemo, want));
      }
    }
  }
}

public class P05_09_MinSubarraySumSplit {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
