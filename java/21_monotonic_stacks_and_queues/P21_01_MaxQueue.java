// 21.1 - Max Queue
// Run: javac P21_01_MaxQueue.java && java P21_01_MaxQueue

import java.util.*;
import java.util.function.*;

q = MaxQueue()             []                          []
q.push(10)                 [10]                        [10]
q.push(30)                 [10, 30]                    [30]
q.push(20)                 [10, 30, 20]                [30, 20]
q.max() // Returns 30.     [10, 30, 20]                [30, 20]
q.pop() // Returns 10.     [30, 20]                    [30, 20]
q.max() // Returns 30.     [30, 20]                    [30, 20]
q.pop() // Returns 30.     [20]                        [20]
q.max() // Returns 20.     [20]                        [20]
q.push(50)                 [20, 50]                    [50]
q.push(30)                 [20, 50, 30]                [50, 30]
q.push(20)                 [20, 50, 30, 20]            [50, 30, 20]
q.push(10)                 [20, 50, 30, 20, 10]        [50, 30, 20, 10]
q.max() // Returns 50.     [20, 50, 30, 20, 10]        [50, 30, 20, 10]
q.push(50)                 [20, 50, 30, 20, 10, 50]    [50, 50]
q.max() // Returns 50.     [20, 50, 30, 20, 10, 50]    [50, 50]
q.pop() // Returns 20.     [50, 30, 20, 10, 50]        [50, 50]
q.pop() // Returns 50.     [30, 20, 10, 50]            [50]
q.max() // Returns 50.     [30, 20, 10, 50]            [50]

For instance, if mono_decr_deque = [50, 30, 20, 10] and we push 40, we iteratively pop 10, 20, and 30 until we get mono_decr_deque = [50], and then we add the new element.

class MaxQueue {
  private Queue<Integer> queue;
  private Deque<Integer> monoDecrDeque;

  public MaxQueue() {
    queue = new ArrayDeque<>();
    monoDecrDeque = new ArrayDeque<>();
  }

  public int peek() {
    return queue.peek();
  }

  public int size() {
    return queue.size();
  }

  public int max() {
    return monoDecrDeque.getFirst();
  }

  public int pop() {
    int val = queue.poll();
    // Check if we are popping the max
    if (val == monoDecrDeque.getFirst()) {
      monoDecrDeque.removeFirst();
    }
    return val;
  }

  public void push(int val) {
    queue.offer(val);
    // Remove elements from the monotonic deque that can never be the max
    while (!monoDecrDeque.isEmpty() && monoDecrDeque.getLast() < val) {
      monoDecrDeque.removeLast();
    }
    monoDecrDeque.addLast(val);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        {
            new Object[] { "push", 10 },
            new Object[] { "push", 30 },
            new Object[] { "push", 20 },
            new Object[] { "max", 30 },
            new Object[] { "pop", 10 },
            new Object[] { "max", 30 },
            new Object[] { "pop", 30 },
            new Object[] { "max", 20 },
            new Object[] { "push", 50 },
            new Object[] { "push", 30 },
            new Object[] { "push", 20 },
            new Object[] { "push", 10 },
            new Object[] { "max", 50 },
            new Object[] { "push", 50 },
            new Object[] { "max", 50 },
            new Object[] { "pop", 20 },
            new Object[] { "pop", 50 },
            new Object[] { "max", 50 }
        },
        // Edge cases
        {
            new Object[] { "push", 1 },
            new Object[] { "max", 1 },
            new Object[] { "pop", 1 },
            new Object[] { "push", 2 },
            new Object[] { "max", 2 }
        },
        // Multiple equal values
        {
            new Object[] { "push", 5 },
            new Object[] { "push", 5 },
            new Object[] { "max", 5 },
            new Object[] { "pop", 5 },
            new Object[] { "max", 5 }
        }
    };

    for (Object[] ops : tests) {
      MaxQueue q = new MaxQueue();
      for (Object op : ops) {
        Object[] operation = (Object[]) op;
        String cmd = (String) operation[0];
        int val = (int) operation[1];

        if (cmd.equals("push")) {
          q.push(val);
        } else if (cmd.equals("pop")) {
          int got = q.pop();
          if (got != val) {
            throw new RuntimeException(String.format(
                "\npop(): got: %d, want: %d\n", got, val));
          }
        } else if (cmd.equals("max")) {
          int got = q.max();
          if (got != val) {
            throw new RuntimeException(String.format(
                "\nmax(): got: %d, want: %d\n", got, val));
          }
        }
      }
    }
  }
}

public class P21_01_MaxQueue {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
