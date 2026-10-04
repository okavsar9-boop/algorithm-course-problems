// 13.8 - Sum of First K Prime Powers
// Run: javac P13_08_SumOfFirstKPrimePowers.java && java P13_08_SumOfFirstKPrimePowers

import java.util.*;
import java.util.function.*;

record Power(long value, long base) {
}

class SumOfPowers {
  public int solve(int[] primes, int k) {
    final int m = 1000000007;
    // Initialize min heap with first power of each prime
    PriorityQueue<Power> minHeap = new PriorityQueue<>(
    Comparator.comparingLong(Power::value));

    for (int p : primes) {
      minHeap.add(new Power(p, p));
    }

    int res = 0;
    for (int i = 0; i < k; i++) {
      Power power = minHeap.poll();
      res = (int) ((res + power.value()) % m);
      minHeap.add(new Power((power.value() * power.base()) % m, power.base()));
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 2 }, 1, 2 },
        // Example 2 from the book
        { new int[] { 5 }, 3, 155 },
        // Example 3 from the book
        { new int[] { 2, 3 }, 7, 69 },
        // k is 0
        { new int[] { 2, 3 }, 0, 0 },
        // k < primes.length
        { new int[] { 5, 7, 11, 13, 17, 19 }, 4, 36 },
        // prime order doesn't matter
        { new int[] { 19, 17, 13, 11, 7, 5 }, 4, 36 },
    };

    SumOfPowers solution = new SumOfPowers();
    for (Object[] test : tests) {
      int[] primes = (int[]) test[0];
      int n = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(primes, n);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(primes), n, got, want));
      }
    }
  }
}

public class P13_08_SumOfFirstKPrimePowers {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
