// 5.2 - CCTV Footage
// Run: javac P05_02_CCTVFootage.java && java P05_02_CCTVFootage

import java.util.*;
import java.util.function.*;

class FindBike {
  public int solve(int t1, int t2, Function<Integer, Boolean> isStolen) {
    int l = t1, r = t2;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid, isStolen)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return r;
  }

  private boolean isBefore(int t, Function<Integer, Boolean> isStolen) {
    return !isStolen.apply(t);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 - stolen at t=5
        { 1, 10, (Function<Integer, Boolean>) (t -> t >= 5), 5 },
        // Example 2 - stolen at start 
        { 1, 5, (Function<Integer, Boolean>) (t -> t >= 2), 2 },
        // Example 3 - stolen at end
        { 1, 5, (Function<Integer, Boolean>) (t -> t >= 5), 5 },
        // Edge case - two timestamps
        { 5, 6, (Function<Integer, Boolean>) (t -> t >= 6), 6 }
    };

    FindBike solution = new FindBike();
    for (Object[] test : tests) {
      int t1 = (int) test[0];
      int t2 = (int) test[1];
      @SuppressWarnings("unchecked")
      Function<Integer, Boolean> isStolen = (Function<Integer, Boolean>) test[2];
      int want = (int) test[3];
      int got = solution.solve(t1, t2, isStolen);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%d, %d): got: %d, want: %d\n",
            t1, t2, got, want));
      }
    }
  }
}

public class P05_02_CCTVFootage {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
