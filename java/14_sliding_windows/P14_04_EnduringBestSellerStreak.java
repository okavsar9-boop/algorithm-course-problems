// 14.4 - Enduring Best Seller Streak
// Run: javac P14_04_EnduringBestSellerStreak.java && java P14_04_EnduringBestSellerStreak

import java.util.*;
import java.util.function.*;

class HasEnduringBestSellerStreak {
  public boolean solve(String[] bestSeller, int k) {
    int l = 0, r = 0;
    Map<String, Integer> windowCounts = new HashMap<>();
    while (r < bestSeller.length) {
      windowCounts.merge(bestSeller[r], 1, Integer::sum);
      r++;
      if (r - l == k) {
        if (windowCounts.size() == 1) {
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

class HasEnduringBestSellerStreak2 {
  public boolean solve(String[] bestSeller, int k) {
    int l = 0, r = 0;
    while (r < bestSeller.length) {
      boolean canGrow = l == r || bestSeller[l].equals(bestSeller[r]);
      if (canGrow) {
        r++;
        if (r - l == k) {
          return true;
        }
      } else {
        l = r;
      }
    }
    return false;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new String[] { "book3", "book1", "book3", "book3", "book2" }, 3, false },
        // Example 2 from the book
        { new String[] { "book3", "book1", "book3", "book3", "book2" }, 2, true },
        // Example with mix of values
        { new String[] { "book1", "book1", "book2", "book1" }, 2, true },
        // Edge case - k=1
        { new String[] { "book1", "book2" }, 1, true },
        // Edge case - k=len(bestSeller)
        { new String[] { "book1", "book1", "book1" }, 3, true },
        // no same sequence possible
        { new String[] { "book1", "book2", "book1" }, 2, false },
    };

    HasEnduringBestSellerStreak solution = new HasEnduringBestSellerStreak();
    HasEnduringBestSellerStreak2 solution2 = new HasEnduringBestSellerStreak2();

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

      got = solution2.solve(bestSeller, k);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve2(%s, %d): got: %b, want: %b\n",
            Arrays.toString(bestSeller), k, got, want));
      }
    }
  }
}

public class P14_04_EnduringBestSellerStreak {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
