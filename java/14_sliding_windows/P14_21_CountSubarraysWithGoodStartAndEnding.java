// 14.21 - Count Subarrays With Good Start and Ending
// Run: javac P14_21_CountSubarraysWithGoodStartAndEnding.java && java P14_21_CountSubarraysWithGoodStartAndEnding

import java.util.*;
import java.util.function.*;

class CountSubarraysWithGoodStartAndEnding {
  public long solve(int[] sales) {
    int goodDays = 0;
    for (int x : sales) {
      if (x >= 10) {
        goodDays++;
      }
    }
    return (long) goodDays * (goodDays + 1) / 2;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example with mix of good and bad days
        { new int[] { 0, 20, 5, 15, 10 }, 6L },
        // Edge case - empty array
        { new int[] {}, 0L },
        // Edge case - all good days
        { new int[] { 10, 20, 30 }, 6L },
        // Edge case - all bad days
        { new int[] { 0, 5, 8 }, 0L },
        // Edge case - single good day
        { new int[] { 10 }, 1L },
    };

    CountSubarraysWithGoodStartAndEnding solution = new CountSubarraysWithGoodStartAndEnding();
    for (Object[] test : tests) {
      int[] sales = (int[]) test[0];
      long want = (long) test[1];
      long got = solution.solve(sales);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(sales), got, want));
      }
    }
  }
}

public class P14_21_CountSubarraysWithGoodStartAndEnding {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
