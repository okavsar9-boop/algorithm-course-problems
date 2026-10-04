// 21.3 - Largest Temperature Change
// Run: javac P21_03_LargestTemperatureChange.java && java P21_03_LargestTemperatureChange

import java.util.*;
import java.util.function.*;

class MaxMinQueue {
  private Queue<Integer> queue;
  private Deque<Integer> monoDecrDeque; // For max
  private Deque<Integer> monoIncrDeque; // For min

  public MaxMinQueue() {
    queue = new ArrayDeque<>();
    monoDecrDeque = new ArrayDeque<>();
    monoIncrDeque = new ArrayDeque<>();
  }

  public int max() {
    return monoDecrDeque.getFirst();
  }

  public int min() {
    return monoIncrDeque.getFirst();
  }

  public void pop() {
    // Check if we are popping the max
    if (queue.peek() == monoDecrDeque.getFirst()) {
      monoDecrDeque.removeFirst();
    }
    // Check if we are popping the min
    if (queue.peek() == monoIncrDeque.getFirst()) {
      monoIncrDeque.removeFirst();
    }
    queue.remove();
  }

  public void push(int val) {
    queue.add(val);
    // Remove elements from the decreasing deque that can never be the max
    while (!monoDecrDeque.isEmpty() && monoDecrDeque.getLast() < val) {
      monoDecrDeque.removeLast();
    }
    monoDecrDeque.addLast(val);
    // Remove elements from the increasing deque that can never be the min
    while (!monoIncrDeque.isEmpty() && monoIncrDeque.getLast() > val) {
      monoIncrDeque.removeLast();
    }
    monoIncrDeque.addLast(val);
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

class LargestTemperatureChange {
  public int solve(int[] arr, int k) {
    int l = 0, r = 0;
    MaxMinQueue maxMinQueue = new MaxMinQueue();
    int res = Integer.MIN_VALUE;
    while (r < arr.length) {
      maxMinQueue.push(arr[r]);
      r++;
      if (r - l == k) {
        res = Math.max(res, maxMinQueue.max() - maxMinQueue.min());
        maxMinQueue.pop();
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
        { new int[] { 12, 13, 12, 13, 13, 12, 11, 12 }, 3, 2 },
        // Example 2 from the book
        { new int[] { 10, 30 }, 2, 20 },
        // all same temperature
        { new int[] { 10, 10, 10, 10 }, 2, 0 },
        // strictly increasing
        { new int[] { 10, 20, 30, 40 }, 3, 20 },
        // strictly decreasing
        { new int[] { 40, 30, 20, 10 }, 3, 20 },
        // k equals length
        { new int[] { 15, 10, 25 }, 3, 15 },
        // Mixed sequence
        { new int[] { 22, 18, 25, 20, 15, 21, 16 }, 4, 10 }
    };

    LargestTemperatureChange solution = new LargestTemperatureChange();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(arr, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(arr), k, got, want));
      }
    }
  }
}

public class P21_03_LargestTemperatureChange {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
