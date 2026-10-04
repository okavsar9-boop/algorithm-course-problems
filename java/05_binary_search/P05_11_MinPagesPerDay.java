// 5.11 - Min Pages Per Day
// Run: javac P05_11_MinPagesPerDay.java && java P05_11_MinPagesPerDay

import java.util.*;
import java.util.function.*;

class MinPagesPerDay {
  private int[] pageCounts;
  private int days;

  public int solve(int[] pageCounts, int days) {
    this.pageCounts = pageCounts;
    this.days = days;

    int l = 0;
    // In case we have more days than max pages in any chapter,
    // we might need to read as little as 1 page per day.
    int r = Arrays.stream(pageCounts).max().getAsInt();

    // Binary search for the transition point from 'before' to 'after' region.
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    // Return the first value in the 'after' region, i.e., the smallest daily
    // limit
    // that allows us to finish the book in time.
    return r;
  }

  // How many days it takes to finish the book with a given daily page limit.
  private int daysToFinish(int dailyLimit) {
    int d = 0;
    for (int pages : pageCounts) {
      // Ceiling division to handle leftover pages.
      d += (pages + dailyLimit - 1) / dailyLimit;
    }
    return d;
  }

  // Defines a transition point over the range of # of pages per day
  // (dailyLimit).
  // In the 'before' region, the daily limit is not enough to finish the book in
  // time.
  // In the 'after' region, the daily limit is enough to finish the book in
  // time.
  private boolean isBefore(int dailyLimit) {
    return daysToFinish(dailyLimit) > days;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from book
        { new int[] { 20, 15, 17, 10 }, 5, 17 },
        { new int[] { 20, 15, 17, 10 }, 14, 5 },
        { new int[] { 20, 15, 17, 10 }, 17, 4 },
        // Edge case - single chapter
        { new int[] { 10 }, 5, 2 },
        // Edge case - days = chapters
        { new int[] { 1, 2, 3 }, 3, 3 },
        // Edge case - more days than max chapter pages
        { new int[] { 20 }, 21, 1 }
    };

    MinPagesPerDay solution = new MinPagesPerDay();
    for (Object[] test : tests) {
      int[] pageCounts = (int[]) test[0];
      int days = (int) test[1];
      int want = (int) test[2];
      int got = solution.solve(pageCounts, days);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %d, want: %d\n",
            Arrays.toString(pageCounts), days, got, want));
      }
    }
  }
}

public class P05_11_MinPagesPerDay {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
