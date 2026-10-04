// 8.5 - Current URL with Forward
// Run: javac P08_05_CurrentURLWithForward.java && java P08_05_CurrentURLWithForward

import java.util.*;
import java.util.function.*;

class CurrentUrlWithForward {
  public String solve(String[][] actions) {
    Stack<String> stack = new Stack<>();
    Stack<String> forwardStack = new Stack<>();

    for (String[] action : actions) {
      if (action[0].equals("go")) {
        stack.push(action[1]);
        forwardStack.clear();
      } else if (action[0].equals("back")) {
        int backSteps = Integer.parseInt(action[1]);
        while (stack.size() > 1 && backSteps > 0) {
          forwardStack.push(stack.pop());
          backSteps--;
        }
      } else {
        int forwardSteps = Integer.parseInt(action[1]);
        while (!forwardStack.isEmpty() && forwardSteps > 0) {
          stack.push(forwardStack.pop());
          forwardSteps--;
        }
      }
    }
    return stack.peek();
  }
}

class CurrentUrlWithForwardEfficient {
  public String solve(String[][] actions) {
    ArrayList<String> urls = new ArrayList<>();
    int current = -1;

    for (String[] action : actions) {
      if (action[0].equals("go")) {
        current++;
        if (current < urls.size()) {
          urls.subList(current, urls.size()).clear();
        }
        urls.add(action[1]);
      } else if (action[0].equals("back")) {
        current = Math.max(0, current - Integer.parseInt(action[1]));
      } else {
        current = Math.min(urls.size() - 1,
            current + Integer.parseInt(action[1]));
      }
    }

    return urls.get(current);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new String[][] { { "go", "google.com" }, { "go", "wikipedia.com" },
            { "back", "1" }, { "forward", "1" }, { "back", "3" },
            { "go", "netflix.com" }, { "forward", "3" } }, "netflix.com" },
        { new String[][] { { "go", "example.com" }, { "forward", "1" } },
            "example.com" },
        { new String[][] { { "go", "site1.com" }, { "go", "site2.com" },
            { "back", "1" }, { "forward", "1" }, { "back", "1" } },
            "site1.com" },
    };
    CurrentUrlWithForward solution = new CurrentUrlWithForward();
    CurrentUrlWithForwardEfficient solution2 = new CurrentUrlWithForwardEfficient();
    for (Object[] test : tests) {
      String[][] actions = (String[][]) test[0];
      String want = (String) test[1];
      String got = solution.solve(actions);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            java.util.Arrays.deepToString(actions), got, want));
      }
      String got2 = solution2.solve(actions);
      if (!got2.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            java.util.Arrays.deepToString(actions), got2, want));
      }
    }
  }
}

public class P08_05_CurrentURLWithForward {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
