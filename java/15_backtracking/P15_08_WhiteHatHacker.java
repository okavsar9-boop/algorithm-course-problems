// 15.8 - White Hat Hacker
// Run: javac P15_08_WhiteHatHacker.java && java P15_08_WhiteHatHacker

import java.util.*;
import java.util.function.*;

class FindPassword {
  private String visit(String password, Function<String, Boolean> checkPassword,
  int maxLength) {
    if (password.length() > maxLength) {
      return null;
    }
    if (checkPassword.apply(password)) {
      return password;
    }
    for (char c = 'a'; c <= 'z'; c++) {
      if (password.indexOf(c) == -1) { // Only add if not already present
        String result = visit(password + c, checkPassword, maxLength);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }

  public String solve(Function<String, Boolean> checkPassword, int maxLength) {
    return visit("", checkPassword, maxLength);
  }
}


class RunTests {
  private Function<String, Boolean> checkPasswordFactory(
  String correctPassword) {
    return s -> s.equals(correctPassword);
  }

  public void runTests() {
    String[] tests = {
      "a",
      "bc",
      "def",
      "ghij"
      // Try higher numbers if you'd like to see how fast this stops working
      // but you'll need to edit the max_length variable in the find_password
      // function to make it longer. 8 characters may take a long time to
      // run.
      // "klmno",
      // "pqrstu",
      // "vwxyzab"
    };

    FindPassword solution = new FindPassword();
    for (String correctPassword : tests) {
      Function<String, Boolean> checkPassword = checkPasswordFactory(
      correctPassword);
      String got = solution.solve(checkPassword, 4);
      if (!correctPassword.equals(got)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got: %s, want: %s\n",
        correctPassword, got, correctPassword));
      }
    }
  }
}

public class P15_08_WhiteHatHacker {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
