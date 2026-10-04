// 19.3 - Exclusive Product
// Run: javac P19_03_ExclusiveProduct.java && java P19_03_ExclusiveProduct

import java.util.*;
import java.util.function.*;

class ExclusiveProductArray {
  public int[] solve(int[] arr) {
    final int m = 1000000007; // 10^9 + 7
    int n = arr.length;
    int[] prefixProduct = new int[n];
    prefixProduct[0] = arr[0];
    for (int i = 1; i < n; i++) {
      prefixProduct[i] = (int) (((long) prefixProduct[i - 1] * arr[i]) % m);
    }

    int[] postfixProduct = new int[n];
    postfixProduct[n - 1] = arr[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      postfixProduct[i] = (int) (((long) postfixProduct[i + 1] * arr[i]) % m);
    }

    int[] res = new int[n];
    res[0] = postfixProduct[1];
    res[n - 1] = prefixProduct[n - 2];
    for (int i = 1; i < n - 1; i++) {
      res[i] = (int) (((long) prefixProduct[i - 1] * postfixProduct[i + 1])
          % m);
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 1, 3, 2, 1 }, new int[] { 6, 2, 3, 6 } },
        // Edge case: Contains zero
        { new int[] { 0, 1, 2, 3 }, new int[] { 6, 0, 0, 0 } },
        // Edge case: All ones
        { new int[] { 1, 1, 1, 1 }, new int[] { 1, 1, 1, 1 } },
        // Edge case: Large numbers
        { new int[] { 10000, 10000 }, new int[] { 10000, 10000 } },
    };

    ExclusiveProductArray solution = new ExclusiveProductArray();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int[] want = (int[]) test[1];
      int[] got = solution.solve(arr);
      if (!java.util.Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            java.util.Arrays.toString(arr),
            java.util.Arrays.toString(got),
            java.util.Arrays.toString(want)));
      }
    }
  }
}

public class P19_03_ExclusiveProduct {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
