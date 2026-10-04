// 19.4 - Balance Point
// Run: javac P19_04_BalancePoint.java && java P19_04_BalancePoint

import java.util.*;
import java.util.function.*;

class BalancedIndex {
  public int solve(int[] arr) {
    int prefixSum = 0;
    int postfixSum = 0;
    for (int x : arr) {
      postfixSum += x;
    }
    postfixSum -= arr[0];

    for (int i = 0; i < arr.length; i++) {
      if (prefixSum == postfixSum) {
        return i;
      }
      prefixSum += arr[i];
      if (i + 1 < arr.length) {
        postfixSum -= arr[i + 1];
      }
    }
    return -1;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 3, 5, -2, 7, 2, 2, 2 }, 3 },
        // Edge case: No balance point
        { new int[] { 1, 2, 3 }, -1 },
        // Edge case: Balance at start
        { new int[] { 0, 1, -1 }, 0 },
        // Edge case: Balance at end
        { new int[] { 1, -1, 0 }, 2 },
    };

    BalancedIndex solution = new BalancedIndex();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            java.util.Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P19_04_BalancePoint {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
