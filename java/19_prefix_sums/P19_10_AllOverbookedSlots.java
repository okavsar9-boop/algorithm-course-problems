// 19.10 - All Overbooked Slots
// Run: javac P19_10_AllOverbookedSlots.java && java P19_10_AllOverbookedSlots

import java.util.*;
import java.util.function.*;

class AllOverbookedSlots {
  public int solve(int[] slots, int[][] bookings, int cap) {
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

    int overbookedCount = 0;
    for (int i = 0; i < n; i++) {
      int totalBookings = prefixSum[i] + slots[i];
      if (totalBookings > cap) {
        overbookedCount++;
      }
    }
    return overbookedCount;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 0, 0, 0, 0, 0, 0 },
            new int[][] { { 0, 3, 4 }, { 2, 5, 1 }, { 4, 4, 3 } },
            5, 0 },
        // Edge case: Single slot overbooked
        { new int[] { 1, 1, 0, 0, 2, 3 },
            new int[][] { { 0, 3, 4 }, { 2, 5, 1 }, { 4, 4, 3 } },
            4, 5 },
        // Edge case: No bookings
        { new int[] { 1, 1, 1, 1, 1, 1 },
            new int[][] {},
            1, 0 },
        // Edge case: All slots overbooked
        { new int[] { 0, 0, 0, 0, 0, 0 },
            new int[][] { { 0, 5, 6 } },
            5, 6 },
    };

    AllOverbookedSlots solution = new AllOverbookedSlots();
    for (Object[] test : tests) {
      int[] slots = (int[]) test[0];
      int[][] bookings = (int[][]) test[1];
      int cap = (int) test[2];
      int want = (int) test[3];
      int got = solution.solve(slots, bookings, cap);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s, %d): got: %d, want: %d\n",
            java.util.Arrays.toString(slots),
            java.util.Arrays.deepToString(bookings),
            cap, got, want));
      }
    }
  }
}

public class P19_10_AllOverbookedSlots {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
