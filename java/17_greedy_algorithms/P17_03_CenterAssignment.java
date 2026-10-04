// 17.3 - Center Assignment
// Run: javac P17_03_CenterAssignment.java && java P17_03_CenterAssignment

import java.util.*;
import java.util.function.*;

Counterexample:

points = [[0, 0], [3, 0]]
center1 = [2, 0]
center2 = [5, 0]
For this input, the optimal solution is to assign [0, 0] to center1 and [3, 0] to center2, with a total distance of 2 + 2 = 4.

The greedy choice would assign [3, 0] to center1 because it is closer than [0, 0], and then [0, 0] would have to be assigned to center2. The total distance would be 1 + 5 = 6.

Counterexample: none.

class MinimizeDistance {
  private double dist(double[] point1, double[] point2) {
    return Math.sqrt(Math.pow(point1[0] - point2[0], 2) +
        Math.pow(point1[1] - point2[1], 2));
  }

  public double solve(double[][] points, double[] center1, double[] center2) {
    int n = points.length;
    int[] assignment = new int[n];
    double baseline = 0;
    for (int i = 0; i < n; i++) {
      if (dist(points[i], center1) <= dist(points[i], center2)) {
        assignment[i] = 1;
        baseline += dist(points[i], center1);
      } else {
        assignment[i] = 2;
        baseline += dist(points[i], center2);
      }
    }

    int c1Count = 0;
    for (int a : assignment) {
      if (a == 1)
        c1Count++;
    }
    if (c1Count == n / 2) {
      return baseline;
    }

    List<Double> switchCosts = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (assignment[i] == 1 && c1Count > n / 2) {
        switchCosts.add(dist(points[i], center2) - dist(points[i], center1));
      }
      if (assignment[i] == 2 && c1Count < n / 2) {
        switchCosts.add(dist(points[i], center1) - dist(points[i], center2));
      }
    }

    double res = baseline;
    Collections.sort(switchCosts);
    for (int i = 0; i < Math.abs(c1Count - n / 2); i++) {
      res += switchCosts.get(i);
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        {
            new double[][] { { 0.0, 1.0 }, { 1.0, 0.0 }, { -1.0, 0.0 },
                { 0.0, -1.0 } },
            new double[] { 0.0, 0.0 },
            new double[] { 1.0, 1.0 },
            4.0
        },
        // Example 2
        {
            new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } },
            new double[] { 0.0, 0.0 },
            new double[] { 1.0, 1.0 },
            1.414
        },
        // Example 3
        {
            new double[][] { { 0.0, 0.5 }, { 1.0, 0.5 } },
            new double[] { 0.0, 0.0 },
            new double[] { 1.0, 1.0 },
            1.0
        },
        // Example 4
        {
            new double[][] {},
            new double[] { 0.3, -3.3 },
            new double[] { -1.6, 4.6 },
            0.0
        },
        // Example 5
        {
            new double[][] { { 0.0, 0.0 }, { 3.0, 0.0 } },
            new double[] { 2.0, 0.0 },
            new double[] { 5.0, 0.0 },
            4.0
        },
        // Additional test cases
        // Edge case: All points are the same
        {
            new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 },
                { 0.0, 0.0 } },
            new double[] { 0.0, 0.0 },
            new double[] { 0.0, 0.0 },
            0.0
        },
    };

    MinimizeDistance solution = new MinimizeDistance();
    for (Object[] test : tests) {
      double[][] points = (double[][]) test[0];
      double[] center1 = (double[]) test[1];
      double[] center2 = (double[]) test[2];
      double want = (double) test[3];
      double got = solution.solve(points, center1, center2);
      if (Math.abs(got - want) >= 1e-3) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s, %s): got: %f, want: %f\n",
            Arrays.deepToString(points),
            Arrays.toString(center1),
            Arrays.toString(center2),
            got,
            want));
      }
    }
  }
}

public class P17_03_CenterAssignment {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
