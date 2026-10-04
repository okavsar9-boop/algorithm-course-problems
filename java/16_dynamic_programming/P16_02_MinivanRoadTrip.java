// 16.2 - Minivan Road Trip
// Run: javac P16_02_MinivanRoadTrip.java && java P16_02_MinivanRoadTrip

import java.util.*;
import java.util.function.*;

class MinivanRoadTrip {
  private Map<Integer, Integer> memo;
  private int[] times;
  private int n;
  private int k;

  private int delay(int i) {
    if (i >= n) {
      return 0;
    }
    if (i >= n - k - 1) {
      return times[i];
    }

    if (memo.containsKey(i)) {
      return memo.get(i);
    }
    int minDelay = Integer.MAX_VALUE;
    for (int p = 1; p <= k + 1; p++) {
      minDelay = Math.min(minDelay, delay(i + p));
    }
    memo.put(i, times[i] + minDelay);
    return memo.get(i);
  }

  public int solve(int[] t, int k) {
    times = t;
    n = times.length;
    this.k = k;
    memo = new HashMap<>();

    int minDelay = Integer.MAX_VALUE;
    for (int p = 0; p <= k; p++) {
      minDelay = Math.min(minDelay, delay(p));
    }
    return minDelay;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new int[] { 8, 1, 2, 3, 9, 6, 2, 4 }, 2, 6 },
        { new int[] { 8, 1, 2, 3, 9, 6, 2, 4 }, 3, 4 },
        { new int[] { 10, 10 }, 1, 10 },
        { new int[] { 10, 10 }, 2, 0 },
        { new int[] {}, 2, 0 },
        { new int[] { 5, 5, 5, 5, 5 }, 2, 5 }

    };

    MinivanRoadTrip solution = new MinivanRoadTrip();
    for (Object[] test : tests) {
      int[] times = (int[]) test[0];
      int k = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(times, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(times), k, got, want));
      }
    }
  }
}

public class P16_02_MinivanRoadTrip {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
