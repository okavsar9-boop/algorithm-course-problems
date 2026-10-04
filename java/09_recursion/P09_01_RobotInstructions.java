// 9.1 - Robot Instructions
// Run: javac P09_01_RobotInstructions.java && java P09_01_RobotInstructions

import java.util.*;
import java.util.function.*;

class Moves {
  public String solve(String seq) {
    ArrayList<Character> res = new ArrayList<>();
    movesRec(seq, 0, res);
    StringBuilder sb = new StringBuilder();
    for (char c : res) {
      sb.append(c);
    }
    return sb.toString();
  }

  private void movesRec(String seq, int pos, ArrayList<Character> res) {
    if (pos == seq.length()) {
      return;
    }
    if (seq.charAt(pos) == '2') {
      movesRec(seq, pos + 1, res);
      movesRec(seq, pos + 2, res);
    } else {
      res.add(seq.charAt(pos));
      movesRec(seq, pos + 1, res);
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from book
        { "LL", "LL" },
        // Example 2 from book
        { "2LR", "LRR" },
        // Example 3 from book
        { "2L", "L" },
        // Example 4 from book
        { "22LR", "LRRLR" },
        // Example 5 from book
        { "LL2R2L", "LLRLL" },
        // Edge case - empty string
        { "", "" },
        // Edge case - single character
        { "L", "L" },
        // Multiple 2s in a row
        { "2222LR", "LRRLRLRRLRRLR" },
    };

    Moves solution = new Moves();
    for (Object[] test : tests) {
      String seq = (String) test[0];
      String want = (String) test[1];
      String got = solution.solve(seq);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            seq, got, want));
      }
    }
  }
}

public class P09_01_RobotInstructions {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
