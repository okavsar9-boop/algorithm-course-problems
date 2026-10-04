// 16.3 - Restaurant Ratings
// Run: javac P16_03_RestaurantRatings.java && java P16_03_RestaurantRatings

import java.util.*;
import java.util.function.*;

class RestaurantRatings {
  private double[] ratings;
  private Map<Integer, Double> memo;
  private int n;

  private double ratingSum(int i) {
    if (i >= n) {
      return 0;
    }
    if (memo.containsKey(i)) {
      return memo.get(i);
    }
    memo.put(i, Math.max(ratings[i] + ratingSum(i + 2), ratingSum(i + 1)));
    return memo.get(i);
  }

  public double solve(double[] inputRatings) {
    ratings = inputRatings;
    n = ratings.length;
    memo = new HashMap<>();
    return ratingSum(0);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        { new double[] { 8.0, 1.0, 3.0, 9.0, 5.0, 2.0, 1.0 }, 19.0 },
        { new double[] { 8.0, 1.0, 3.0, 7.0, 5.0, 2.0, 4.0 }, 20.0 },
        { new double[] { 10.0, 10.0, 10.0, 10.0, 10.0 }, 30.0 },
        { new double[] {}, 0.0 },
        { new double[] { 5.0, 5.0, 5.0, 5.0, 5.0 }, 15.0 },
    };

    RestaurantRatings solution = new RestaurantRatings();
    for (Object[] test : tests) {
      double[] ratings = (double[]) test[0];
      double want = (double) test[1];
      double got = solution.solve(ratings);
      if (Math.abs(got - want) > 1e-9) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %f, want: %f\n",
            Arrays.toString(ratings), got, want));
      }
    }
  }
}

public class P16_03_RestaurantRatings {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
