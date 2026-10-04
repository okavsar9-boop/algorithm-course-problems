// 8.8 - Longest Balanced Subsequence
// Run: javac P08_08_LongestBalancedSubsequence.java && java P08_08_LongestBalancedSubsequence

import java.util.*;
import java.util.function.*;

class LongestBalancedSubsequence {
  public String solve(String s) {
    Set<Integer> invalidIndices = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') {
        stack.push(i);
      } else if (stack.isEmpty()) {
        invalidIndices.add(i);
      } else {
        stack.pop();
      }
    }

    while (!stack.isEmpty()) {
      invalidIndices.add(stack.pop());
    }

    StringBuilder res = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (!invalidIndices.contains(i)) {
        res.append(s.charAt(i));
      }
    }
    return res.toString();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { "))(())(()", new String[] { "(())()" } },
        { "(()()", new String[] { "()()", "(())" } },
        { "(()(()(", new String[] { "()()", "(())" } },
        { "())(()", new String[] { "()()" } },
        { "(", new String[] { "" } },
        { "", new String[] { "" } }
    };
    LongestBalancedSubsequence solution = new LongestBalancedSubsequence();
    for (Object[] test : tests) {
      String s = (String) test[0];
      String[] want = (String[]) test[1];
      String got = solution.solve(s);
      boolean found = false;
      for (String w : want) {
        if (got.equals(w)) {
          found = true;
          break;
        }
      }
      if (!found) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            s, got, String.join(" or ", want)));
      }
    }
  }
}

public class P08_08_LongestBalancedSubsequence {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
