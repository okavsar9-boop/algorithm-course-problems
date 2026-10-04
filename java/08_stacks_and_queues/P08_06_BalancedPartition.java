// 8.6 - Balanced Partition
// Run: javac P08_06_BalancedPartition.java && java P08_06_BalancedPartition

import java.util.*;
import java.util.function.*;

class MaxBalancedPartition {
  public int solve(String s) {
    int height = 0;
    int res = 0;
    for (char c : s.toCharArray()) {
      if (c == '(') {
        height++;
      } else {
        height--;
        if (height == 0) {
          res++;
        }
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { "((()))(()())()(()(()))", 4 },
        { "()()()", 3 },
        { "(((())))", 1 },
        { "", 0 },
        { "()", 1 },
    };
    MaxBalancedPartition solution = new MaxBalancedPartition();
    for (Object[] test : tests) {
      String s = (String) test[0];
      int want = (int) test[1];
      int got = solution.solve(s);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            s, got, want));
      }
    }
  }
}

public class P08_06_BalancedPartition {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
