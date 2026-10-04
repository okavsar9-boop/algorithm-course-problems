// 19.8 - Segmented Video Votes
// Run: javac P19_08_SegmentedVideoVotes.java && java P19_08_SegmentedVideoVotes

import java.util.*;
import java.util.function.*;

class RangeUpdates {
  public int[] solve(int n, int[][] votes) {
    int[] diff = new int[n];
    for (int[] vote : votes) {
      int l = vote[0], r = vote[1], v = vote[2];
      diff[l] += v;
      if (r + 1 < n) {
        diff[r + 1] -= v;
      }
    }

    // Recipe 1.
    int[] prefixSum = new int[n];
    prefixSum[0] = diff[0];
    for (int i = 1; i < n; i++) {
      prefixSum[i] = prefixSum[i - 1] + diff[i];
    }
    return prefixSum;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { 6,
            new int[][] { { 3, 4, 1 }, { 0, 0, 1 }, { 1, 3, 1 }, { 0, 5, -1 } },
            new int[] { 0, 0, 0, 1, 0, -1 } },
        // Edge case: No votes
        { 5,
            new int[][] {},
            new int[] { 0, 0, 0, 0, 0 } },
        // Edge case: All likes
        { 3,
            new int[][] { { 0, 2, 1 } },
            new int[] { 1, 1, 1 } },
        // Edge case: All dislikes
        { 3,
            new int[][] { { 0, 2, -1 } },
            new int[] { -1, -1, -1 } },
    };

    RangeUpdates solution = new RangeUpdates();
    for (Object[] test : tests) {
      int n = (int) test[0];
      int[][] votes = (int[][]) test[1];
      int[] want = (int[]) test[2];
      int[] got = solution.solve(n, votes);
      if (!java.util.Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%d, %s): got: %s, want: %s\n",
            n,
            java.util.Arrays.deepToString(votes),
            java.util.Arrays.toString(got),
            java.util.Arrays.toString(want)));
      }
    }
  }
}

public class P19_08_SegmentedVideoVotes {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
