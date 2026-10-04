// 9.4 - Lego Castle
// Run: javac P09_04_LegoCastle.java && java P09_04_LegoCastle

import java.util.*;
import java.util.function.*;

class BlocksRec {
  private long roof(int i) {
    if (i == 1) {
      return 1;
    }
    return roof(i - 1) * 2 + 1;
  }

  private long blocksRec(int n) {
    if (n == 1) {
      return 1;
    }
    return blocksRec(n - 1) * 2 + roof(n);
  }

  public long solve(int n) {
    return blocksRec(n);
  }
}
class BlocksMemoized {
  private HashMap<Integer, Long> memo;

  public long solve(int n) {
    memo = new HashMap<>();
    return blocksRec(n);
  }

  private long roof(int i) {
    if (i == 1) {
      return 1;
    }
    if (memo.containsKey(i)) {
      return memo.get(i);
    }
    long result = roof(i - 1) * 2 + 1;
    memo.put(i, result);
    return result;
  }

  private long blocksRec(int n) {
    if (n == 1) {
      return 1;
    }
    return blocksRec(n - 1) * 2 + roof(n);
  }
}

class BlocksIterative {
  public long solve(int n) {
    long blocks = 1;
    for (int i = 2; i <= n; i++) {
      long roof = (1L << i) - 1;
      blocks = blocks * 2 + roof;
    }
    return blocks;
  }
}

class BlocksMath {
  public long solve(int n) {
    long power = 1L << n;
    return n * power - (power - 1);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { 1, 1L },
        { 2, 5L },
        { 3, 17L },
        { 4, 49L },
        { 5, 129L },
        { 6, 321L },
        { 7, 769L },
        { 8, 1793L },
        { 9, 4097L },
        { 10, 9217L },
    };

    BlocksRec solutionRec = new BlocksRec();
    BlocksMemoized solutionMemoized = new BlocksMemoized();
    BlocksIterative solutionIterative = new BlocksIterative();
    BlocksMath solutionMath = new BlocksMath();
    for (Object[] test : tests) {
      int n = (Integer) test[0];
      long want = (Long) test[1];
      long gotRec = solutionRec.solve(n);
      long gotMemoized = solutionMemoized.solve(n);
      long gotIterative = solutionIterative.solve(n);
      long gotMath = solutionMath.solve(n);
      if (gotRec != want) {
        throw new RuntimeException(String.format(
            "\nBlocksRec.solve(%d): got: %d, want: %d\n",
            n, gotRec, want));
      }
      if (gotMemoized != want) {
        throw new RuntimeException(String.format(
            "\nBlocksMemoized.solve(%d): got: %d, want: %d\n",
            n, gotMemoized, want));
      }
      if (gotIterative != want) {
        throw new RuntimeException(String.format(
            "\nBlocksIterative.solve(%d): got: %d, want: %d\n",
            n, gotIterative, want));
      }
      if (gotMath != want) {
        throw new RuntimeException(String.format(
            "\nBlocksMath.solve(%d): got: %d, want: %d\n",
            n, gotMath, want));
      }
    }
  }
}

public class P09_04_LegoCastle {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
