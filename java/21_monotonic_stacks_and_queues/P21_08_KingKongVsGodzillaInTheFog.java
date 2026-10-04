// 21.8 - King Kong vs Godzilla In The Fog
// Run: javac P21_08_KingKongVsGodzillaInTheFog.java && java P21_08_KingKongVsGodzillaInTheFog

import java.util.*;
import java.util.function.*;

class MaxQueue {
  private Queue<Integer> queue;
  private Deque<Integer> monoDecrDeque;

  public MaxQueue() {
    queue = new ArrayDeque<>();
    monoDecrDeque = new ArrayDeque<>();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public int max() {
    return monoDecrDeque.getFirst();
  }

  public int size() {
    return queue.size();
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

next_greater_element(arr)
  initialize empty stack for NGE candidates
  for each index i from right to left
    if the stack is not empty
    else
      index i has no NGE
    add i to the stack
It's then straightforward to iterate over street and check if NGE[i] > i + k. If it is, then building i is spared by King Kong.

For example, [10, 20, 30, 15, 5] becomes [-5, -15, -30, -20, -10]. Since negative numbers can be confusing, let's add 30 to every element to make it easier to understand: [25, 15, 0, 10, 20] (this doesn't change the relative 'heights'). Given this array, King Kong would smash 25 and 15 and 20 and output spared = [False, False, True, True, False]. Reversing that, we get that Godzilla would spare [False, True, True, False, False].

class KingKongVsGodzilla {
  public boolean[] sparedByKingKong(int[] street, int k) {
    int n = street.length;
    boolean[] spared = new boolean[n];

    // Add the k buildings after the first one to the queue
    MaxQueue maxQueue = new MaxQueue();
    for (int i = 1; i < Math.min(k + 1, n); i++) {
      maxQueue.push(street[i]);
    }

    // Process each building (except the last one, which is never spared)
    for (int i = 0; i < n - 1; i++) {
      if (street[i] < maxQueue.max()) {
        spared[i] = true;
      }
      // Slide the window covered by maxQueue
      maxQueue.pop();
      if (i + k + 1 < n) {
        maxQueue.push(street[i + k + 1]);
      }
    }
    return spared;
  }

  public boolean[] sparedByKingKongNge(int[] street, int k) {
    int n = street.length;

    // Build NGE array using recipe
    int[] nge = new int[n];
    Arrays.fill(nge, -1);
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && street[stack.getLast()] <= street[i]) {
        stack.removeLast();
      }
      if (!stack.isEmpty()) {
        nge[i] = stack.getLast();
      }
      stack.addLast(i);
    }

    // Building i is spared if NGE[i] exists and is within k buildings
    boolean[] spared = new boolean[n];
    for (int i = 0; i < n; i++) {
      if (nge[i] != -1 && nge[i] <= i + k) {
        spared[i] = true;
      }
    }
    return spared;
  }

  public boolean[] sparedByGodzilla(int[] street, int k) {
    int[] newStreet = new int[street.length];
    for (int i = 0; i < street.length; i++) {
      newStreet[i] = -street[street.length - 1 - i];
    }
    boolean[] res = sparedByKingKong(newStreet, k);
    // Reverse the result
    for (int i = 0; i < res.length / 2; i++) {
      boolean temp = res[i];
      res[i] = res[res.length - 1 - i];
      res[res.length - 1 - i] = temp;
    }
    return res;
  }

  public boolean[] spared(int[] street, int k) {
    boolean[] res1 = sparedByKingKong(street, k);
    boolean[] res2 = sparedByGodzilla(street, k);
    boolean[] res = new boolean[street.length];
    for (int i = 0; i < street.length; i++) {
      res[i] = res1[i] && res2[i];
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { 10, 20, 30, 15, 5 }, 2,
        new boolean[] { true, true, false, false, false }, // King Kong
        new boolean[] { false, true, true, false, false }, // Godzilla
        new boolean[] { false, true, false, false, false } // Combined
      },
      // Example 2 from the book
      { new int[] { 10, 20, 30, 40, 50 }, 3,
        new boolean[] { true, true, true, true, false }, // King Kong
        new boolean[] { false, true, true, true, true }, // Godzilla
        new boolean[] { false, true, true, true, false } // Combined
      },
      // Example 3 from the book
      { new int[] { 50, 40, 30, 20, 10 }, 3,
        new boolean[] { false, false, false, false, false }, // King Kong
        new boolean[] { false, false, false, false, false }, // Godzilla
        new boolean[] { false, false, false, false, false } // Combined
      },
      // Example 4 from the book
      { new int[] { 1, 10, 5, 20 }, 2,
        new boolean[] { true, true, true, false }, // King Kong
        new boolean[] { false, true, true, true }, // Godzilla
        new boolean[] { false, true, true, false } // Combined
      },
      // Example 5 from the book
      { new int[] { 1, 10, 5, 20 }, 1,
        new boolean[] { true, false, true, false }, // King Kong
        new boolean[] { false, true, false, true }, // Godzilla
        new boolean[] { false, false, false, false } // Combined
      },
      // Edge case - single element
      { new int[] { 1 }, 1,
        new boolean[] { false }, // King Kong
        new boolean[] { false }, // Godzilla
        new boolean[] { false } // Combined
      },
      // Edge case - all same height
      { new int[] { 5, 5, 5, 5, 5, 5 }, 2,
        new boolean[] { false, false, false, false, false, false }, // King
        // Kong
        new boolean[] { false, false, false, false, false, false }, // Godzilla
        new boolean[] { false, false, false, false, false, false } // Combined
      }
    };

    KingKongVsGodzilla solution = new KingKongVsGodzilla();
    for (Object[] test : tests) {
      int[] street = (int[]) test[0];
      int k = (int) test[1];
      boolean[] wantKong = (boolean[]) test[2];
      boolean[] wantGodzilla = (boolean[]) test[3];
      boolean[] want = (boolean[]) test[4];

      boolean[] gotKong = solution.sparedByKingKong(street, k);
      boolean[] gotKongNge = solution.sparedByKingKongNge(street, k);
      if (!Arrays.equals(gotKong, gotKongNge)) {
        throw new RuntimeException(String.format(
        "\nsparedByKingKong(%s, %d): using sliding window max: %s, using NGE: %s\n",
        Arrays.toString(street), k, Arrays.toString(gotKong),
        Arrays.toString(gotKongNge)));
      }

      boolean[] gotGodzilla = solution.sparedByGodzilla(street, k);
      boolean[] got = solution.spared(street, k);

      if (!Arrays.equals(gotKong, wantKong)
      || !Arrays.equals(gotGodzilla, wantGodzilla)
      || !Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
        "\nspared(%s, %d): got: %s, want: %s\n",
        Arrays.toString(street), k, Arrays.toString(got),
        Arrays.toString(want)));
      }
    }
  }
}

public class P21_08_KingKongVsGodzillaInTheFog {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
