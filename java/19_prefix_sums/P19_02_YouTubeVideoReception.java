// 19.2 - YouTube Video Reception
// Run: javac P19_02_YouTubeVideoReception.java && java P19_02_YouTubeVideoReception

import java.util.*;
import java.util.function.*;

class GoodReceptionScores {
  public int[] solve(int[] likes, int[] dislikes, int[][] periods) {
    int[] positiveDays = new int[likes.length];
    for (int i = 0; i < likes.length; i++) {
      if (likes[i] > dislikes[i]) {
        positiveDays[i] = 1;
      }
    }
    // Range sum queries recipe
    int[] prefixSum = new int[positiveDays.length];
    prefixSum[0] = positiveDays[0];
    for (int i = 1; i < positiveDays.length; i++) {
      prefixSum[i] = prefixSum[i - 1] + positiveDays[i];
    }

    int[] res = new int[periods.length];
    for (int i = 0; i < periods.length; i++) {
      int l = periods[i][0], r = periods[i][1];
      if (l == 0) {
        res[i] = prefixSum[r];
      } else {
        res[i] = prefixSum[r] - prefixSum[l - 1];
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 6, 3, 4, 8, 7, 2, 6, 5, 0, 1 },
            new int[] { 6, 0, 8, 0, 0, 0, 1, 8, 0, 2 },
            new int[][] { { 0, 1 }, { 0, 5 }, { 5, 8 }, { 3, 3 } },
            new int[] { 1, 4, 2, 1 } },
        // Edge case: All days positive
        { new int[] { 10, 20, 30 },
            new int[] { 0, 0, 0 },
            new int[][] { { 0, 2 } },
            new int[] { 3 } },
        // Edge case: All days negative
        { new int[] { 0, 0, 0 },
            new int[] { 10, 20, 30 },
            new int[][] { { 0, 2 } },
            new int[] { 0 } },
        // Edge case: Mixed days
        { new int[] { 1, 2, 3 },
            new int[] { 3, 2, 1 },
            new int[][] { { 0, 2 } },
            new int[] { 1 } },
    };

    GoodReceptionScores solution = new GoodReceptionScores();
    for (Object[] test : tests) {
      int[] likes = (int[]) test[0];
      int[] dislikes = (int[]) test[1];
      int[][] periods = (int[][]) test[2];
      int[] want = (int[]) test[3];
      int[] got = solution.solve(likes, dislikes, periods);
      if (!java.util.Arrays.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s, %s): got: %s, want: %s\n",
            java.util.Arrays.toString(likes),
            java.util.Arrays.toString(dislikes),
            java.util.Arrays.deepToString(periods),
            java.util.Arrays.toString(got),
            java.util.Arrays.toString(want)));
      }
    }
  }
}

public class P19_02_YouTubeVideoReception {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
