// 3.13 - Parity Sorting
// Run: javac P03_13_ParitySorting.java && java P03_13_ParitySorting

import java.util.*;
import java.util.function.*;

class SortEven {
  public void solve(int[] arr) {
    int l = 0, r = arr.length - 1;
    while (l < r) {
      if (arr[l] % 2 == 0) {
        l++;
      } else if (arr[r] % 2 == 1) {
        r--;
      } else {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
        l++;
        r--;
      }
    }
  }
}


class RunTests {
  public void solve() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { 1, 2, 3, 4, 5 }, new int[] { 2, 4, 1, 3, 5 } },
      // Example 2 from the book
      { new int[] { 5, 1, 3, 1, 5 }, new int[] { 5, 1, 3, 1, 5 } },
      // Additional test cases
      { new int[] {}, new int[] {} },
      { new int[] { 1 }, new int[] { 1 } },
      { new int[] { 2 }, new int[] { 2 } },
      { new int[] { 1, 2 }, new int[] { 2, 1 } },
      { new int[] { 2, 1 }, new int[] { 2, 1 } },
      { new int[] { 1, 3, 2, 4 }, new int[] { 2, 4, 1, 3 } },
    };

    SortEven sorter = new SortEven();
    IsValidSolution validator = new IsValidSolution();

    for (Object[] test : tests) {
      int[] arr = Arrays.copyOf((int[]) test[0], ((int[]) test[0]).length);
      int[] example_solution = (int[]) test[1];
      sorter.solve(arr);
      if (!validator.solve(arr, (int[]) test[0])) {
        throw new RuntimeException(String.format(
        "\nsort_even(%s): got: %s, example solution: %s\n",
        Arrays.toString((int[]) test[0]), Arrays.toString(arr),
        Arrays.toString(example_solution)));
      }
    }

  }
}

public class P03_13_ParitySorting {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
