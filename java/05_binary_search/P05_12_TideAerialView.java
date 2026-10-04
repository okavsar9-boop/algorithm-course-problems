// 5.12 - Tide Aerial View
// Run: javac P05_12_TideAerialView.java && java P05_12_TideAerialView

import java.util.*;
import java.util.function.*;

class TideAerialView {
  // Time: O((log k) * n log n)
  // Space: O(1)
  public int solve(int[][][] pictures) {
    if (!isBefore(pictures[0])) {
      return 0;
    }
    if (isBefore(pictures[pictures.length - 1])) {
      return pictures.length - 1;
    }

    int l = 0, r = pictures.length - 1;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBefore(pictures[mid])) {
        l = mid;
      } else {
        r = mid;
      }
    }

    // Return the closest one to the midpoint, or l in case of a tie
    int lWater = getOnesInPicture(pictures[l]);
    int rWater = getOnesInPicture(pictures[r]);
    double midPoint = pictures[0][0].length * pictures[0][0].length / 2.0;
    return Math.abs(lWater - midPoint) <= Math.abs(rWater - midPoint) ? l : r;
  }

  // Time: O(n log n)
  // Space: O(1)
  private int getOnesInPicture(int[][] picture) {
    int ones = 0;
    for (int[] row : picture) {
      ones += getOnesInRow(row);
    }
    return ones;
  }

  // Time: O(n log n)
  // Space: O(1)
  private boolean isBefore(int[][] picture) {
    int water = getOnesInPicture(picture);
    int total = picture[0].length * picture[0].length;
    return (double) water / total < 0.5;
  }

  // Time: O(log n)
  // Space: O(1)
  private int getOnesInRow(int[] row) {
    if (row[0] == 0) {
      return 0;
    }
    if (row[row.length - 1] == 1) {
      return row.length;
    }

    int l = 0, r = row.length;
    while (r - l > 1) {
      int mid = (l + r) / 2;
      if (isBeforeRow(mid, row)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return r;
  }

  private boolean isBeforeRow(int idx, int[] row) {
    return row[idx] == 1;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[][][] {
            {
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }
            },
            {
                { 1, 0, 0 },
                { 0, 0, 0 },
                { 1, 0, 0 }
            },
            {
                { 1, 1, 0 },
                { 0, 0, 0 },
                { 1, 0, 0 }
            },
            {
                { 1, 1, 0 },
                { 1, 1, 1 },
                { 1, 0, 0 }
            },
            {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 0 }
            }
        }, 2 },
        // 3 pictures with increasing water
        { new int[][][] {
            {
                { 1, 0, 0 },
                { 1, 0, 0 },
                { 1, 0, 0 }
            },
            {
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 1, 0, 0 }
            },
            {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 0, 0 }
            }
        }, 1 },
        // 2 pictures
        { new int[][][] {
            { { 1, 0 }, { 0, 0 } },
            { { 1, 1 }, { 1, 0 } }
        }, 0 },
        // Incremental progression
        { new int[][][] {
            { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } },
            { { 1, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } },
            { { 1, 0, 0 }, { 1, 0, 0 }, { 0, 0, 0 } },
            { { 1, 1, 0 }, { 1, 0, 0 }, { 0, 0, 0 } },
            { { 1, 1, 1 }, { 1, 0, 0 }, { 0, 0, 0 } },
            { { 1, 1, 1 }, { 1, 1, 0 }, { 0, 0, 0 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 0, 0, 0 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 0, 0 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }
        }, 4 },
        // Edge case - single picture
        { new int[][][] {
            { { 1, 1 }, { 0, 0 } }
        }, 0 },
        // Edge case - all water
        { new int[][][] {
            { { 1, 1 }, { 1, 1 } }
        }, 0 },
        // Edge case - all land
        { new int[][][] {
            { { 0, 0 }, { 0, 0 } }
        }, 0 }
    };

    TideAerialView solution = new TideAerialView();
    for (Object[] test : tests) {
      int[][][] pictures = (int[][][]) test[0];
      int want = (int) test[1];
      int got = solution.solve(pictures);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.deepToString(pictures), got, want));
      }
    }
  }
}

public class P05_12_TideAerialView {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
