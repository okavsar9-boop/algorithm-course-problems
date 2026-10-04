// 9.2 - Nested Array Sum
// Run: javac P09_02_NestedArraySum.java && java P09_02_NestedArraySum

import java.util.*;
import java.util.function.*;

// Lazy checking: check if the argument is an Integer at the start of
// each call. This handles integer elements encountered during recursion.
class NestedArraySum {
  @SuppressWarnings("unchecked")
  public int solve(Object arr) {
    if (arr instanceof Integer) {
      return (Integer) arr;
    }
    int res = 0;
    for (Object elem : (List<?>) arr) {
      res += solve(elem);
    }
    return res;
  }
}

// Eager checking: check if each element is an Integer before recursing.
// This avoids recursing on integers entirely.
class NestedArraySumEager {
  @SuppressWarnings("unchecked")
  public int solve(List<?> arr) {
    int res = 0;
    for (Object elem : arr) {
      if (elem instanceof Integer) {
        res += (Integer) elem;
      } else {
        res += solve((List<?>) elem);
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book
        { Arrays.asList(1, Arrays.asList(2, 3),
            Arrays.asList(4, Arrays.asList(5)), 6), 21 },
        // Example 2 from book
        { Arrays.asList(Arrays.asList(Arrays.asList(Arrays.asList(1))), 2), 3 },
        // Example 3 from book
        { new ArrayList<>(), 0 },
        // Edge case - all nested single numbers
        { Arrays.asList(
            Arrays.asList(Arrays.asList(Arrays.asList(Arrays.asList(1))))), 1 },
        // Edge case - multiple empty arrays
        { Arrays.asList(new ArrayList<>(), new ArrayList<>(),
            new ArrayList<>()), 0 },
        // Edge case - mixed empty and non-empty arrays
        { Arrays.asList(new ArrayList<>(), Arrays.asList(1, 2),
            new ArrayList<>(), Arrays.asList(3)), 6 },
        // Edge case - deeply nested mixed arrays
        { Arrays.asList(1,
            Arrays.asList(2, new ArrayList<>(),
                Arrays.asList(3, new ArrayList<>())),
            Arrays.asList(4, Arrays.asList(5))), 15 },
        // Edge case - all zeros
        { Arrays.asList(0, Arrays.asList(0, 0),
            Arrays.asList(0, Arrays.asList(0)), 0), 0 },
        // Edge case - negative numbers
        { Arrays.asList(-1, Arrays.asList(-2, 3),
            Arrays.asList(4, Arrays.asList(-5)), 6), 5 },
    };

    // Test both implementations to verify they produce the same results
    NestedArraySum solution = new NestedArraySum();
    NestedArraySumEager solutionEager = new NestedArraySumEager();
    for (Object[] test : tests) {
      List<?> arr = (List<?>) test[0];
      int want = (Integer) test[1];
      int got = solution.solve(arr);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            arr, got, want));
      }
      int gotEager = solutionEager.solve(arr);
      if (gotEager != want) {
        throw new RuntimeException(String.format(
            "\nsolveEager(%s): got: %d, want: %d\n",
            arr, gotEager, want));
      }
    }
  }
}

public class P09_02_NestedArraySum {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
