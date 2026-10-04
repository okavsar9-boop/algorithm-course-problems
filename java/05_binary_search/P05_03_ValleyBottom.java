// 5.3 - Valley Bottom
// Run: javac P05_03_ValleyBottom.java && java P05_03_ValleyBottom

import java.util.*;
import java.util.function.*;

class ValleyBottom {
  public int solve(int[] arr) {
    int l = 0, r = arr.length - 1;
    if (isBefore(r, arr)) {
      return arr[r];
    }

    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, arr)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return arr[l];
  }

  private boolean isBefore(int i, int[] arr) {
    return i == 0 || arr[i] < arr[i - 1];
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book
        { new int[] { 6, 5, 4, 7, 9 }, 4 },
        // Example 2 from book
        { new int[] { 5, 6, 7 }, 5 },
        // Example 3 from book
        { new int[] { 7, 6, 5 }, 5 },
        // Edge case - 2 elements
        { new int[] { 2, 1 }, 1 },
        // Edge case - 3 elements
        { new int[] { 3, 2, 4 }, 2 }
    };

    ValleyBottom solution = new ValleyBottom();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P05_03_ValleyBottom {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
