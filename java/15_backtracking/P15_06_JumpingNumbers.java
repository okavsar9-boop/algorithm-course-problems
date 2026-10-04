// 15.6 - Jumping Numbers
// Run: javac P15_06_JumpingNumbers.java && java P15_06_JumpingNumbers

import java.util.*;
import java.util.function.*;

def visit(partial_solution):
  if full_solution(partial_solution):
    # Process leaf/full solution.
  else:
    for choice in choices(partial_solution):
      # Prune children where possible.
      child = apply_choice(partial_solution)
      visit(child)

class JumpingNumbers {
  private List<Integer> res;
  private int n;

  private void visit(int num) {
    if (num >= n) {
      return;
    }
    res.add(num);
    int lastDigit = num % 10;
    if (lastDigit > 0) {
      visit(num * 10 + (lastDigit - 1));
    }
    if (lastDigit < 9) {
      visit(num * 10 + (lastDigit + 1));
    }
  }

  public List<Integer> solve(int n) {
    this.n = n;
    res = new ArrayList<>();
    for (int num = 1; num < 10; num++) {
      visit(num);
    }
    Collections.sort(res);
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { 34, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32 } },
        // Edge case - n is 1
        { 1, new int[] {} },
        { 10, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } },
        // Larger n
        { 50, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32, 34, 43,
            45 } },
        { 102,
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32, 34, 43,
                45, 54, 56, 65, 67, 76, 78, 87, 89, 98, 101 } },
    };

    JumpingNumbers solution = new JumpingNumbers();
    for (Object[] test : tests) {
      int n = (int) test[0];
      int[] wantArray = (int[]) test[1];
      List<Integer> want = Arrays.stream(wantArray)
          .boxed()
          .collect(Collectors.toList());
      List<Integer> got = solution.solve(n);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%d): got: %s, want: %s\n",
            n, got, want));
      }
    }
  }
}

public class P15_06_JumpingNumbers {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
