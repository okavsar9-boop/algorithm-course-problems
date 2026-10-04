// 21.2 - Sliding Window Maximum
// Run: javac P21_02_SlidingWindowMaximum.java && java P21_02_SlidingWindowMaximum

import java.util.*;
import java.util.function.*;

class MaxQueue {
  private Queue<Integer> queue;
  private Deque<Integer> monoDecrDeque;

  public MaxQueue() {
    queue = new ArrayDeque<>();
    monoDecrDeque = new ArrayDeque<>();
  }

  public int max() {
    return monoDecrDeque.getFirst();
  }

  public void pop() {
    int val = queue.remove();
    // Check if we are popping the max
    if (val == monoDecrDeque.getFirst()) {
      monoDecrDeque.removeFirst();
    }
  }

  public void push(int val) {
    queue.add(val);
    // Remove elements from the monotonic deque that can never be the max
    while (!monoDecrDeque.isEmpty() && monoDecrDeque.getLast() < val) {
      monoDecrDeque.removeLast();
    }
    monoDecrDeque.addLast(val);
  }
}

fixed_length_window(arr, k):
  initialize:
    - data structures to track window info
    - cur_best to 0
  while we can grow the window (r < len(arr))
    if the window has the correct length (r - l == k)
      update cur_best if needed
  return cur_best

class SlidingWindowMax {
  public List<Integer> solve(int[] arr, int k) {
    int l = 0, r = 0;
    MaxQueue maxQueue = new MaxQueue();
    List<Integer> res = new ArrayList<>();
    while (r < arr.length) {
      maxQueue.push(arr[r]);
      r++;
      if (r - l == k) {
        res.add(maxQueue.max());
        maxQueue.pop();
        l++;
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 10, 20, 30, 40, 30, 20, 10 }, 2,
            new Integer[] { 20, 30, 40, 40, 30, 20 } },
        // Example 2 from the book
        { new int[] { 10, 20, 30, 40, 30, 20, 10 }, 3,
            new Integer[] { 30, 40, 40, 40, 30 } },
        // Window size 1 just returns the array
        { new int[] { 1, 2, 3 }, 1,
            new Integer[] { 1, 2, 3 } },
        // Window size equals array length
        { new int[] { 5, 2, 1 }, 3,
            new Integer[] { 5 } },
        // Array with duplicates
        { new int[] { 1, 1, 1, 2, 2, 2 }, 2,
            new Integer[] { 1, 1, 2, 2, 2 } },
        // Decreasing sequence
        { new int[] { 5, 4, 3, 2, 1 }, 3,
            new Integer[] { 5, 4, 3 } },
        // Increasing sequence
        { new int[] { 1, 2, 3, 4, 5 }, 3,
            new Integer[] { 3, 4, 5 } },
        // Mixed sequence
        { new int[] { 1, 5, 2, 6, 3 }, 3,
            new Integer[] { 5, 6, 6 } }
    };

    SlidingWindowMax solution = new SlidingWindowMax();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      List<Integer> want = Arrays.asList((Integer[]) test[2]);
      List<Integer> got = solution.solve(arr, k);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            Arrays.toString(arr), k, got, want));
      }
    }
  }
}

public class P21_02_SlidingWindowMaximum {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
