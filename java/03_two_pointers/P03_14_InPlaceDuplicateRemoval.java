// 3.14 - In-Place Duplicate Removal
// Run: javac P03_14_InPlaceDuplicateRemoval.java && java P03_14_InPlaceDuplicateRemoval

import java.util.*;
import java.util.function.*;

class RemoveDuplicates {
  public int solve(int[] arr) {
    int s = 0, w = 0;
    while (s < arr.length) {
      boolean mustKeep = s == 0 || arr[s] != arr[s - 1];
      if (mustKeep) {
        arr[w] = arr[s];
        w++;
      }
      s++;
    }
    return w;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 1, 2, 2, 3, 3, 3, 5 }, 4,
            new int[] { 1, 2, 3, 5 } },
        // Additional test cases
        { new int[] {}, 0, new int[] {} },
        { new int[] { 1 }, 1, new int[] { 1 } },
        { new int[] { 1, 1 }, 1, new int[] { 1 } },
        { new int[] { 1, 2 }, 2, new int[] { 1, 2 } },
        { new int[] { 1, 1, 1 }, 1, new int[] { 1 } },
        { new int[] { 1, 2, 2, 2, 3 }, 3, new int[] { 1, 2, 3 } },
    };

    RemoveDuplicates solution = new RemoveDuplicates();
    for (Object[] test : tests) {
      int[] arr = Arrays.copyOf((int[]) test[0], ((int[]) test[0]).length);
      int wantLen = (int) test[1];
      int[] wantPrefix = (int[]) test[2];
      int gotLen = solution.solve(arr);
      if (gotLen != wantLen) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got length: %d, want length: %d\n",
            Arrays.toString((int[]) test[0]), gotLen, wantLen));
      }
      if (!Arrays.equals(Arrays.copyOf(arr, wantLen), wantPrefix)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got prefix: %s, want prefix: %s\n",
            Arrays.toString((int[]) test[0]),
            Arrays.toString(Arrays.copyOf(arr, wantLen)),
            Arrays.toString(wantPrefix)));
      }
    }
  }
}

public class P03_14_InPlaceDuplicateRemoval {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
