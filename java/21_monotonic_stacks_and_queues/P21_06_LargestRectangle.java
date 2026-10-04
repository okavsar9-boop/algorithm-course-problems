// 21.6 - Largest Rectangle
// Run: javac P21_06_LargestRectangle.java && java P21_06_LargestRectangle

import java.util.*;
import java.util.function.*;

class LargestRectangle {
  private int[] nextSmallerElement(int[] arr) {
    int n = arr.length;
    int[] nse = new int[n];
    Arrays.fill(nse, n);
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && arr[stack.getLast()] >= arr[i]) {
        stack.removeLast();
      }
      if (!stack.isEmpty()) {
        nse[i] = stack.getLast();
      }
      stack.addLast(i);
    }
    return nse;
  }

  private int[] prevSmallerElement(int[] arr) {
    int n = arr.length;
    int[] pse = new int[n];
    Arrays.fill(pse, -1);
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.getLast()] >= arr[i]) {
        stack.removeLast();
      }
      if (!stack.isEmpty()) {
        pse[i] = stack.getLast();
      }
      stack.addLast(i);
    }
    return pse;
  }

  public int solve(int[] tiles) {
    int n = tiles.length;
    int[] nse = nextSmallerElement(tiles);
    int[] pse = prevSmallerElement(tiles);

    int maxArea = 0;
    for (int i = 0; i < n; i++) {
      int width = nse[i] - pse[i] - 1;
      int area = width * tiles[i];
      maxArea = Math.max(maxArea, area);
    }
    return maxArea;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 2, 3 }, 4 },
        // Example 2 from the book
        { new int[] { 2, 1, 2 }, 3 },
        // Example 3 from the book
        { new int[] { 1, 2, 5, 2, 1 }, 6 },
        // Edge cases
        { new int[] { 1 }, 1 },
        { new int[] { 0 }, 0 },
        { new int[] { 5, 5, 5, 5, 5 }, 25 },
        { new int[] { 0, 0, 0, 0, 0 }, 0 },
        { new int[] { 1, 0, 1 }, 1 }
    };

    LargestRectangle solution = new LargestRectangle();
    for (Object[] test : tests) {
      int[] tiles = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(tiles);

      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(tiles), got, want));
      }
    }
  }
}

public class P21_06_LargestRectangle {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
