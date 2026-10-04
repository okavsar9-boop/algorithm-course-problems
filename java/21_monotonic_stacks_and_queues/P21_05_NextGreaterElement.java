// 21.5 - Next Greater Element
// Run: javac P21_05_NextGreaterElement.java && java P21_05_NextGreaterElement

import java.util.*;
import java.util.function.*;

class NextGreaterElement {
  public int[] solve(int[] arr) {
    int n = arr.length;
    int[] nge = new int[n];
    Arrays.fill(nge, -1);
    Deque<Integer> stack = new ArrayDeque<>();

    // Iterate right to left
    for (int i = n - 1; i >= 0; i--) {
      // Pop all NGE candidates from stack that are <= arr[i]
      while (!stack.isEmpty() && arr[stack.getLast()] <= arr[i]) {
        stack.removeLast();
      }

      // If stack not empty, top is NGE of i
      if (!stack.isEmpty()) {
        nge[i] = stack.getLast();
      }

      // Add i to stack as candidate for future elements
      stack.addLast(i);
    }

    return nge;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 5, 3, 10, 8, 8, 10 },
            new int[] { 2, 2, -1, 5, 5, -1 } },
        // Example 2 from the book
        { new int[] { 4, 2, 6, 4, 5, 2, 4, 7, 3, 7 },
            new int[] { 2, 2, 7, 4, 7, 6, 7, -1, 9, -1 } },
        // Example 3 from the book
        { new int[] { 5, 5, 5, 5, 5 },
            new int[] { -1, -1, -1, -1, -1 } },
        // Example 4 from the book
        { new int[] { 5, 6, 7, 8, 9 },
            new int[] { 1, 2, 3, 4, -1 } },
        // Edge case - empty array
        { new int[] {},
            new int[] {} },
        // Edge case - single element
        { new int[] { 1 },
            new int[] { -1 } }
    };

    NextGreaterElement solution = new NextGreaterElement();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int[] want = (int[]) test[1];
      int[] got = solution.solve(arr);

      if (!Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            Arrays.toString(arr), Arrays.toString(got),
            Arrays.toString(want)));
      }
    }
  }
}

public class P21_05_NextGreaterElement {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
