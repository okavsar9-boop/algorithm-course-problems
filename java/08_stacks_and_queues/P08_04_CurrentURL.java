// 8.4 - Current URL
// Run: javac P08_04_CurrentURL.java && java P08_04_CurrentURL

import java.util.*;
import java.util.function.*;

class CurrentUrl {
  public String solve(String[][] actions) {
    Stack<String> stack = new Stack<>();
    for (String[] action : actions) {
      if (action[0].equals("go")) {
        stack.push(action[1]);
      } else {
        int backSteps = Integer.parseInt(action[1]);
        while (stack.size() > 1 && backSteps > 0) {
          stack.pop();
          backSteps--;
        }
      }
    }
    return stack.peek();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new String[][] { { "go", "google.com" }, { "go", "wikipedia.com" },
            { "go", "amazon.com" }, { "back", "4" }, { "go", "youtube.com" },
            { "go", "netflix.com" }, { "back", "1" } }, "youtube.com" },
        { new String[][] { { "go", "example.com" }, { "back", "1" } },
            "example.com" },
        { new String[][] { { "go", "site1.com" }, { "go", "site2.com" },
            { "back", "1" }, { "back", "1" } }, "site1.com" },
    };
    CurrentUrl solution = new CurrentUrl();
    for (Object[] test : tests) {
      String[][] actions = (String[][]) test[0];
      String want = (String) test[1];
      String got = solution.solve(actions);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            java.util.Arrays.deepToString(actions), got, want));
      }
    }
  }
}

public class P08_04_CurrentURL {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
