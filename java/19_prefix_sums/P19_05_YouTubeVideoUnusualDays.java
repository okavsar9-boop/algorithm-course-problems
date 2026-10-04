// 19.5 - YouTube Video Unusual Days
// Run: javac P19_05_YouTubeVideoUnusualDays.java && java P19_05_YouTubeVideoUnusualDays

import java.util.*;
import java.util.function.*;

class MaxTotalDeviation {
  public int solve(int[] likes, int[] dislikes) {
    int n = likes.length;
    int[] scores = new int[n];
    for (int i = 0; i < n; i++) {
      scores[i] = likes[i] - dislikes[i];
    }
    java.util.Arrays.sort(scores);

    int[] prefixSum = new int[n];
    prefixSum[0] = scores[0];
    for (int i = 1; i < n; i++) {
      prefixSum[i] = prefixSum[i - 1] + scores[i];
    }

    int maxDeviation = 0;
    for (int i = 0; i < n; i++) {
      int left = 0, right = 0;
      if (i > 0) {
        left = i * scores[i] - prefixSum[i - 1];
      }
      if (i < n - 1) {
        right = prefixSum[n - 1] - prefixSum[i] - (n - i - 1) * scores[i];
      }
      maxDeviation = Math.max(maxDeviation, left + right);
    }
    return maxDeviation;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 3, 6, 1 },
            new int[] { 0, 1, 9 },
            24 },
        // Edge case: All same scores
        { new int[] { 1, 1, 1 },
            new int[] { 1, 1, 1 },
            0 },
        // Edge case: Increasing scores
        { new int[] { 1, 2, 3 },
            new int[] { 0, 0, 0 },
            3 },
        // Edge case: Decreasing scores
        { new int[] { 3, 2, 1 },
            new int[] { 0, 0, 0 },
            3 },
    };

    MaxTotalDeviation solution = new MaxTotalDeviation();
    for (Object[] test : tests) {
      int[] likes = (int[]) test[0];
      int[] dislikes = (int[]) test[1];
      int want = (int) test[2];
      int got = solution.solve(likes, dislikes);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %d, want: %d\n",
            java.util.Arrays.toString(likes),
            java.util.Arrays.toString(dislikes),
            got, want));
      }
    }
  }
}

public class P19_05_YouTubeVideoUnusualDays {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
