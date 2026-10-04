// 16.5 - Magic Blackjack
// Run: javac P16_05_MagicBlackjack.java && java P16_05_MagicBlackjack

import java.util.*;
import java.util.function.*;

class NumWays {
  private Map<Integer, Integer> memo = new HashMap<>();

  private int numWaysRec(int i) {
    if (i > 21) {
      return 1;
    }
    if (i >= 16 && i <= 21) {
      return 0;
    }
    if (memo.containsKey(i)) {
      return memo.get(i);
    }
    int res = 0;
    for (int card = 1; card <= 10; card++) {
      res += numWaysRec(i + card);
    }
    memo.put(i, res);
    return res;
  }

  public int solve() {
    return numWaysRec(0);
  }
}


class RunTests {
  public void runTests() {
    final int TOTAL_POSSIBLE_BUSTS = 100081;
    NumWays solution = new NumWays();
    int got = solution.solve();
    if (got != TOTAL_POSSIBLE_BUSTS) {
      throw new RuntimeException(String.format(
          "\nsolve(<no input>): got: %d, want: %d\n",
          got, TOTAL_POSSIBLE_BUSTS));
    }
  }
}

public class P16_05_MagicBlackjack {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
