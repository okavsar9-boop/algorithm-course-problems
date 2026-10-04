// 21.4 - Longest Stable Period
// Run: javac P21_04_LongestStablePeriod.java && java P21_04_LongestStablePeriod

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

maximum_window(arr):
  initialize:
    - data structures to track window info
    - cur_best to 0
  while we can grow the window (r < len(arr))
    if the window would still be valid with one more element
      update cur_best if needed
    else if the window is empty  # skip this case if empty windows are always valid
      advance both l and r
    else
  return cur_best

class LongestStablePeriod {
  public int solve(int[] temperatures, int t) {
    int l = 0, r = 0;
    MaxMinQueue maxMinQueue = new MaxMinQueue();
    int curBest = 0;
    while (r < temperatures.length) {
      boolean canGrow = l == r
          || (Math.max(maxMinQueue.max(), temperatures[r]) -
              Math.min(maxMinQueue.min(), temperatures[r]) <= t);
      if (canGrow) {
        maxMinQueue.push(temperatures[r]);
        r++;
        curBest = Math.max(curBest, r - l);
      } else {
        maxMinQueue.pop();
        l++;
      }
    }
    return curBest;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 12, 16, 14, 15, 13, 17 }, 3, 4 },
        // Example 2 from the book
        { new int[] { 30, 10 }, 100, 2 },
        // Example 3 from the book
        { new int[] { 30, 10 }, 1, 1 },
        // All same temperature
        { new int[] { 10, 10, 10, 10 }, 0, 4 },
        // Strictly increasing
        { new int[] { 10, 20, 30, 40 }, 5, 1 },
        // Strictly decreasing
        { new int[] { 40, 30, 20, 10 }, 5, 1 },
        // Mixed sequence
        { new int[] { 22, 18, 25, 20, 15, 21, 16 }, 4, 2 }
    };

    LongestStablePeriod solution = new LongestStablePeriod();
    for (Object[] test : tests) {
      int[] temperatures = (int[]) test[0];
      int t = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(temperatures, t);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(temperatures), t, got, want));
      }
    }
  }
}

public class P21_04_LongestStablePeriod {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
