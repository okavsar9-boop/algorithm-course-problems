// 3.7 - 2-Sum
// Run: javac P03_07_2Sum.java && java P03_07_2Sum

import java.util.*;
import java.util.function.*;

class TwoSum {
  public boolean solve(int[] arr) {
    int l = 0, r = arr.length - 1;
    while (l < r) {
      if (arr[l] + arr[r] > 0) {
        r--;
      } else if (arr[l] + arr[r] < 0) {
        l++;
      } else {
        return true;
      }
    }
    return false;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { -5, -2, -1, 1, 1, 10 }, true },
      // Example 2 from the book
      { new int[] { -3, 0, 0, 1, 2 }, true },
      // Example 3 from the book
      { new int[] { -5, -3, -1, 0, 2, 4, 6 }, false },
      // Additional test cases
      { new int[] {}, false },
      { new int[] { 0 }, false },
      { new int[] { -1, 1 }, true },
      { new int[] { -2, -1, 0, 1 }, true },
      { new int[] { 1, 2, 3, 4 }, false },
    };

    TwoSum solution = new TwoSum();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      boolean want = (boolean) test[1];
      boolean got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got: %b, want: %b\n",
        Arrays.toString(arr), got, want));
      }
    }

  }
}

public class P03_07_2Sum {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
