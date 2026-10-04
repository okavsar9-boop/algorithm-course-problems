// 16.1 - Road Trip
// Run: javac P16_01_RoadTrip.java && java P16_01_RoadTrip

import java.util.*;
import java.util.function.*;

class DelayInefficient {
  private int delayRec(int[] times, int i) {
    int n = times.length;
    if (i >= n - 3) {
      return times[i];
    }
    return times[i] + Math.min(Math.min(delayRec(times, i + 1),
        delayRec(times, i + 2)),
        delayRec(times, i + 3));
  }

  public int solve(int[] times) {
    int n = times.length;
    if (n < 3) {
      return 0;
    }
    return Math.min(Math.min(delayRec(times, 0),
        delayRec(times, 1)),
        delayRec(times, 2));
  }
}

memo = empty map

f(subproblem_id):
  if subproblem is base case:
    return result directly
  if subproblem in memo map:
    return cached result

  memo[subproblem_id] = recurrence relation formula
  return memo[subproblem_id]

return f(initial subproblem)

class DelayMemoized {
  private int[] memo;

  private int delayRec(int[] times, int i) {
    int n = times.length;
    if (i >= n - 3) {
      return times[i];
    }
    if (memo[i] != -1) {
      return memo[i];
    }
    memo[i] = times[i] + Math.min(Math.min(delayRec(times, i + 1),
        delayRec(times, i + 2)),
        delayRec(times, i + 3));
    return memo[i];
  }

  public int solve(int[] times) {
    int n = times.length;
    if (n < 3) {
      return 0;
    }
    memo = new int[n];
    Arrays.fill(memo, -1);
    return Math.min(Math.min(delayRec(times, 0),
        delayRec(times, 1)),
        delayRec(times, 2));
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      { new int[] { 8, 1, 2, 3, 9, 6, 2, 4 }, 6 },
      { new int[] { 8, 1, 2, 3, 9, 3, 2, 4 }, 5 },
      { new int[] { 10, 10 }, 0 },
      { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 12 },
      { new int[] { 5, 5, 5, 5, 5, 5, 5, 5, 5 }, 15 },
      { new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 3 },
      { new int[] { 1, 2, 3 }, 1 },
      { new int[] { 1, 2 }, 0 },
      { new int[] { 1 }, 0 },
      { new int[] {}, 0 }
    };

    DelayInefficient inefficient = new DelayInefficient();
    DelayMemoized memoized = new DelayMemoized();
    DelayTabulated tabulated = new DelayTabulated();
    DelayTabulatedWSpaceOptimization spaceOpt = new DelayTabulatedWSpaceOptimization();

    for (Object[] test : tests) {
      int[] times = (int[]) test[0];
      int want = (int) test[1];
      int gotInefficient = inefficient.solve(times);
      int gotMemoized = memoized.solve(times);
      int gotTabulated = tabulated.solve(times);
      int gotSpaceOpt = spaceOpt.solve(times);

      if (gotInefficient != gotMemoized || gotMemoized != gotTabulated ||
      gotTabulated != gotSpaceOpt || gotInefficient != want) {
        throw new RuntimeException(String.format(
        "\ndelay(%s): got inefficient: %d, got memoized: %d, " +
        "got tabulated: %d, got space opt: %d, want: %d\n",
        Arrays.toString(times), gotInefficient, gotMemoized,
        gotTabulated, gotSpaceOpt, want));
      }
    }
  }
}

public class P16_01_RoadTrip {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
