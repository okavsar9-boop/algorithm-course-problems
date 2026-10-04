// 3.2 - Smaller Prefixes
// Run: javac P03_02_SmallerPrefixes.java && java P03_02_SmallerPrefixes

import java.util.*;
import java.util.function.*;

class SmallerPrefixes {
  public boolean solve(int[] arr) {
    int sp = 0, fp = 0;
    int slowSum = 0, fastSum = 0;
    while (fp < arr.length) {
      slowSum += arr[sp];
      fastSum += arr[fp] + arr[fp + 1];
      if (slowSum >= fastSum) {
        return false;
      }
      sp++;
      fp += 2;
    }
    return true;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { 1, 2, 2, -1 }, true },
      // Example 2 from the book
      { new int[] { 1, 2, -2, 1, 3, 5 }, false },
      // Additional test cases
      { new int[] { 0, 3, 7, 12, 10, 5, 0, 1 }, true },
      { new int[] {}, true },
      { new int[] { 1, 2 }, true },
      { new int[] { 2, 1 }, true },
      { new int[] { -2, 1, -4, 5, -3, 7 }, true },
      { new int[] { -2, 1, -14, 8, -3, 2 }, false },
    };

    SmallerPrefixes solution = new SmallerPrefixes();
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

public class P03_02_SmallerPrefixes {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
