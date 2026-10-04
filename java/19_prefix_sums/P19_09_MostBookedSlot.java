// 19.9 - Most Booked Slot
// Run: javac P19_09_MostBookedSlot.java && java P19_09_MostBookedSlot

import java.util.*;
import java.util.function.*;

class MostBookedSlot {
  public int solve(int[] slots, int[][] bookings) {
    int n = slots.length;
    int[] diff = new int[n];
    for (int[] booking : bookings) {
      int l = booking[0], r = booking[1], c = booking[2];
      diff[l] += c;
      if (r + 1 < n) {
        diff[r + 1] -= c;
      }
    }

    // Recipe 1.
    int[] prefixSum = new int[n];
    prefixSum[0] = diff[0];
    for (int i = 1; i < n; i++) {
      prefixSum[i] = prefixSum[i - 1] + diff[i];
    }

    int maxBookings = 0, maxIndex = -1;
    for (int i = 0; i < n; i++) {
      int totalBookings = prefixSum[i] + slots[i];
      if (totalBookings > maxBookings) {
        maxBookings = totalBookings;
        maxIndex = i;
      }
    }
    return maxIndex;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 0, 0, 0, 0, 0, 0 },
            new int[][] { { 0, 3, 4 }, { 2, 5, 1 }, { 4, 4, 3 } },
            2 },
        { new int[] { 1, 1, 0, 0, 2, 3 },
            new int[][] { { 0, 3, 4 }, { 2, 5, 1 }, { 4, 4, 3 } },
            4 },
        // Edge case: No bookings
        { new int[] { 1, 1, 1, 1, 1, 1 },
            new int[][] {},
            0 },
        // Edge case: All slots booked equally
        { new int[] { 0, 0, 0, 0, 0, 0 },
            new int[][] { { 0, 5, 1 } },
            0 },
    };

    MostBookedSlot solution = new MostBookedSlot();
    for (Object[] test : tests) {
      int[] slots = (int[]) test[0];
      int[][] bookings = (int[][]) test[1];
      int want = (int) test[2];
      int got = solution.solve(slots, bookings);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            java.util.Arrays.toString(slots),
            java.util.Arrays.deepToString(bookings),
            got, want));
      }
    }
  }
}

public class P19_09_MostBookedSlot {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
