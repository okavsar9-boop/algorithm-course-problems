// 17.6 - Time Traveler Max Year
// Run: javac P17_06_TimeTravelerMaxYear.java && java P17_06_TimeTravelerMaxYear

import java.util.*;
import java.util.function.*;

class CanReachYear {
  public boolean solve(int[] jumpingPoints, int k, int maxAging, int yearIdx) {
    // Easier Version of the problem:
    // Given a year = jumpingPoints[yearIdx], is it possible to reach it?
    List<Integer> gaps = new ArrayList<>();
    for (int i = 0; i < yearIdx; i++) {
      gaps.add(jumpingPoints[i + 1] - jumpingPoints[i]);
    }

    // Sort gaps by size (descending)
    gaps.sort(Collections.reverseOrder());

    int totalAging = gaps.size() > k
    ? gaps.subList(k, gaps.size()).stream().mapToInt(Integer::intValue)
    .sum()
    : 0;
    return totalAging <= maxAging;
  }
}

Example:
jumping_points = [1, 10, 30], k = 1, max_aging = 5
Output: 15

transition_point_recipe()
- the range is empty
- l is 'after'  (the whole range is 'after')
- r is 'before' (the whole range is 'before')

while l and r are not next to each other (r - l > 1)
mid = (l + r) / 2
if is_before(mid)
l = mid
else
r = mid

return l (the last 'before'), r (the first 'after'), or something else,
depending on the problem

class LatestReachableYearBinarySearch {
  public int solve(int[] jumpingPoints, int k, int maxAging) {
    int n = jumpingPoints.length;

    List<Integer> sortedGaps = new ArrayList<>();
    for (int i = 0; i < n - 1; i++) {
      sortedGaps.add(i);
    }
    Collections.sort(sortedGaps, (a, b) -> Integer
    .compare(gapSize(jumpingPoints, b), gapSize(jumpingPoints, a)));

    int l = 0;
    int r = n - 1;
    if (isBefore(jumpingPoints, sortedGaps, k, maxAging, r)) {
      // Edge case: we can reach the last year.
      // We skip the k largest gaps and age naturally through the rest.
      return yearReached(jumpingPoints,
      sortedGaps.subList(0, Math.min(k, sortedGaps.size())), maxAging);
    }

    // Binary search over year indices (0 to n-1).
    // Goal: find the transition point from the last year we can reach to the
    // first year we can't (if any).
    // Before region: we can reach the end of year yearIdx.
    // After region: we can't reach the end of year yearIdx.
    // At the end, l and r are next to each other.
    // O(n log n) time.
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(jumpingPoints, sortedGaps, k, maxAging, mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    // Now we know we can reach year l, but not year l+1.
    // Thus, we should skip the largest k gaps up to l.
    List<Integer> sortedGapsUpToL = new ArrayList<>();
    for (int i = 0; i < l; i++) {
      sortedGapsUpToL.add(i);
    }
    Collections.sort(sortedGapsUpToL, (a, b) -> Integer
    .compare(gapSize(jumpingPoints, b), gapSize(jumpingPoints, a)));
    return yearReached(jumpingPoints,
    sortedGapsUpToL.subList(0, Math.min(k, sortedGapsUpToL.size())),
    maxAging);
  }

  private int gapSize(int[] jumpingPoints, int yearIdx) {
    // Gap from jumpingPoints[yearIdx] to jumpingPoints[yearIdx + 1]
    return jumpingPoints[yearIdx + 1] - jumpingPoints[yearIdx];
  }

  private int yearReached(int[] jumpingPoints, List<Integer> gapIndicesToSkip,
  int maxAging) {
    // Year we reach if we skip the gaps in gapIndicesToSkip
    // (assuming we can reach them).
    int sum = 0;
    for (int i : gapIndicesToSkip) {
      sum += gapSize(jumpingPoints, i);
    }
    return jumpingPoints[0] + maxAging + sum;
  }

  // Returns whether we can reach the end of gap idx.
  // Takes O(n) time by leveraging the sortedGaps array.
  private boolean canReachYearLinear(int[] jumpingPoints,
  List<Integer> sortedGaps, int k, int maxAging, int yearIdx) {
    int totalAging = 0;
    int jumpsUsed = 0;
    for (int idx : sortedGaps) {
      if (idx >= yearIdx) {
        continue;
      }
      if (jumpsUsed < k) {
        jumpsUsed++;
      } else {
        totalAging += gapSize(jumpingPoints, idx);
        if (totalAging > maxAging) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isBefore(int[] jumpingPoints, List<Integer> sortedGaps,
  int k, int maxAging, int yearIdx) {
    return canReachYearLinear(jumpingPoints, sortedGaps, k, maxAging, yearIdx);
  }
}

"can I get here with the available jumps?"

jumping_points = [1, 3, 6, 7, 11, 16, 17, 19], k = 2, max_aging = 4

class LatestReachableYearGreedy {
  public int solve(int[] jumpingPoints, int k, int maxAging) {
    int[] gaps = new int[jumpingPoints.length - 1];
    for (int i = 1; i < jumpingPoints.length; i++) {
      gaps[i - 1] = jumpingPoints[i] - jumpingPoints[i - 1];
    }

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    int totalGapSum = 0;
    int sumHeap = 0;

    for (int i = 0; i < gaps.length; i++) {
      int aged = totalGapSum - sumHeap;
      minHeap.offer(gaps[i]);
      sumHeap += gaps[i];
      totalGapSum += gaps[i];
      if (minHeap.size() > k) {
        int smallestJump = minHeap.poll();
        sumHeap -= smallestJump;
      }
      int newAged = totalGapSum - sumHeap;
      if (newAged > maxAging) {
        // We can't reach the end of gap i.
        // We get to jumpingPoints[i] and age naturally from there.
        int remainingAging = maxAging - aged;
        return jumpingPoints[i] + remainingAging;
      }
    }

    // Reached the last jumping point
    int aged = totalGapSum - sumHeap;
    int remainingAging = maxAging - aged;
    return jumpingPoints[jumpingPoints.length - 1] + remainingAging;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        { new int[] { 2020, 2024 }, 0, 2, 2022 },
        // Example 2
        { new int[] { 2020, 2024 }, 1, 1, 2025 },
        // Example 3
        { new int[] { 1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001,
            2021 },
            4, 45, 2021 },
        // Example 4
        { new int[] { 1, 10, 30 }, 1, 5, 15 },
        // Example 5
        { new int[] { 1, 3, 6, 7, 11, 16, 17, 19 }, 2, 4, 12 },

        { new int[] { 1, 5, 10 }, 1, 2, 7 },
        { new int[] { 1, 3, 10, 20 }, 1, 3, 11 },
        { new int[] { 1, 4, 15 }, 1, 4, 16 },

        // Additional test cases
        // Edge case: No jumps allowed, but within aging limit
        { new int[] { 2000, 2001, 2002 }, 0, 2, 2002 },
        // Edge case: No jumps allowed, exceeding aging limit
        { new int[] { 2000, 2005, 2010 }, 0, 4, 2004 },
    };

    LatestReachableYearBinarySearch binarySearchSolution = new LatestReachableYearBinarySearch();
    LatestReachableYearGreedy greedySolution = new LatestReachableYearGreedy();
    for (Object[] test : tests) {
      int[] points = (int[]) test[0];
      int jumps = (int) test[1];
      int maxAging = (int) test[2];
      int want = (int) test[3];

      int got = binarySearchSolution.solve(points, jumps, maxAging);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nlatestReachableYearBinarySearch(%s, %d, %d): got: %d, want: %d\n",
            Arrays.toString(points), jumps, maxAging, got, want));
      }

      got = greedySolution.solve(points, jumps, maxAging);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nlatestReachableYear(%s, %d, %d): got: %d, want: %d\n",
            Arrays.toString(points), jumps, maxAging, got, want));
      }
    }
  }
}

public class P17_06_TimeTravelerMaxYear {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
