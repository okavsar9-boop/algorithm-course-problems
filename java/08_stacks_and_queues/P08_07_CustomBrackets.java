// 8.7 - Custom Brackets
// Run: javac P08_07_CustomBrackets.java && java P08_07_CustomBrackets

import java.util.*;
import java.util.function.*;

class CustomBrackets {
  public boolean solve(String s, String[] brackets) {
    Map<Character, Character> openToClose = new HashMap<>();
    Set<Character> closeSet = new HashSet<>();
    for (String pair : brackets) {
      openToClose.put(pair.charAt(0), pair.charAt(1));
      closeSet.add(pair.charAt(1));
    }

    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (openToClose.containsKey(c)) {
        stack.push(openToClose.get(c));
      } else if (closeSet.contains(c)) {
        if (stack.isEmpty() || stack.pop() != c) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { "((a+b)*[c-d]-{e/f})", new String[] { "()", "[]", "{}" }, true },
        { "()[}", new String[] { "()", "[]", "{}" }, false },
        { "([)]", new String[] { "()", "[]", "{}" }, false },
        { "<div> hello :) </div>", new String[] { "<>", "()" }, false },
        { ")))(()((", new String[] { ")(" }, true },
    };
    CustomBrackets solution = new CustomBrackets();
    for (Object[] test : tests) {
      String s = (String) test[0];
      String[] brackets = (String[]) test[1];
      boolean want = (boolean) test[2];
      boolean got = solution.solve(s, brackets);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %b, want: %b\n",
            s, java.util.Arrays.toString(brackets), got, want));
      }
    }
  }

public class P08_07_CustomBrackets {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
