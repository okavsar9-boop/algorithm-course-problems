// 17.4 - Minimum Triplet Medians
// Run: javac P17_04_MinimumTripletMedians.java && java P17_04_MinimumTripletMedians

import java.util.*;
import java.util.function.*;

class MinimizeMiddleSum {
  public int solve(int[] arr) {
    Arrays.sort(arr);
    int middleSum = 0;
    for (int i = 0; i < arr.length / 3; i++) {
      middleSum += arr[i * 2 + 1];
    }
    return middleSum;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[] { 6, 5, 8, 2, 1, 9 }, 8 },
        // Example 2
        { new int[] { 6, 5, 8, 2, 1, 9, 12, 15, 14 }, 17 },

        // Additional test cases
        // Edge case: Single triplet
        { new int[] { 1, 2, 3 }, 2 },
        // Test with 6 elements
        { new int[] { 10, 20, 60, 30, 40, 50 }, 60 },
        // Test with 9 elements
        { new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17 }, 21 },
        // Test with 12 elements
        { new int[] { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24 }, 40 },
        // Test with 15 elements
        { new int[] { 10, 11, 12, 13, 14, 15, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 30 },
        // Test with 18 elements
        { new int[] { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75,
            80,
            85, 90 }, 210 },
    };

    MinimizeMiddleSum solution = new MinimizeMiddleSum();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int want = (int) test[1];
      int[] input = arr.clone();
      int got = solution.solve(input);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P17_04_MinimumTripletMedians {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
