// 14.3 - Unique Best Seller Streak
// Run: javac P14_03_UniqueBestSellerStreak.java && java P14_03_UniqueBestSellerStreak

import java.util.*;
import java.util.function.*;

class HasUniqueKDays {
  public boolean solve(String[] bestSeller, int k) {
    int l = 0, r = 0;
    Map<String, Integer> windowCounts = new HashMap<>();
    while (r < bestSeller.length) {
      windowCounts.merge(bestSeller[r], 1, Integer::sum);
      r++;
      if (r - l == k) {
        if (windowCounts.size() == k) {
          return true;
        }
        windowCounts.merge(bestSeller[l], -1,
            (count, dec) -> count + dec == 0 ? null : count + dec);
        l++;
      }
    }
    return false;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new String[] { "book3", "book1", "book3", "book3", "book2", "book3",
            "book4", "book3" }, 3, true },
        // Example 2 from the book
        { new String[] { "book3", "book1", "book3", "book3", "book2", "book3",
            "book4", "book3" }, 4, false },
        // Edge case - k=1
        { new String[] { "book1", "book2" }, 1, true },
        // Edge case - k=len(bestSeller)
        { new String[] { "book1", "book2", "book3" }, 3, true },
        // no unique sequence possible
        { new String[] { "book1", "book1", "book1" }, 2, false },
    };

    HasUniqueKDays solution = new HasUniqueKDays();
    for (Object[] test : tests) {
      String[] bestSeller = (String[]) test[0];
      int k = (int) test[1];
      boolean want = (boolean) test[2];
      boolean got = solution.solve(bestSeller, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %b, want: %b\n",
            Arrays.toString(bestSeller), k, got, want));
      }
    }
  }
}

public class P14_03_UniqueBestSellerStreak {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
