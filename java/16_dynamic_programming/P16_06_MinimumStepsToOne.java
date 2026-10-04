// 16.6 - Minimum Steps to One
// Run: javac P16_06_MinimumStepsToOne.java && java P16_06_MinimumStepsToOne

import java.util.*;
import java.util.function.*;

class MinimumStepsToOne {
  private Map<Integer, Integer> memo;

  public int solve(int n) {
    memo = new HashMap<>();
    return numSteps(n);
  }

  private int numSteps(int i) {
    if (i == 1) {
      return 0;
    }
    if (memo.containsKey(i)) {
      return memo.get(i);
    }
    int steps = numSteps(i - 1);
    if (i % 2 == 0) {
      steps = Math.min(steps, numSteps(i / 2));
    }
    if (i % 3 == 0) {
      steps = Math.min(steps, numSteps(i / 3));
    }
    memo.put(i, 1 + steps);
    return memo.get(i);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { 10, 3 },
        { 1, 0 },
        { 15, 4 },
        { 6, 2 },
        { 7, 3 },
        { 100, 7 }, // larger test case
    };

    MinimumStepsToOne solution = new MinimumStepsToOne();
    for (Object[] test : tests) {
      int n = (int) test[0];
      int want = (int) test[1];
      int got = solution.solve(n);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%d): got: %d, want: %d\n", n, got, want));
      }
    }
  }
}

public class P16_06_MinimumStepsToOne {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
