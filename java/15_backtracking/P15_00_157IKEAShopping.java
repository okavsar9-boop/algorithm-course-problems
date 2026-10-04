//  - 15.7 IKEA Shopping
// Run: javac P15_00_157IKEAShopping.java && java P15_00_157IKEAShopping

import java.util.*;
import java.util.function.*;

class MaximizeStyle {
  // Inputs
  private int budget;
  private int[] prices;
  private double[] ratings;
  private int n;

  // Backtracking state
  private int bestRatingSum;
  private List<Integer> bestItems;
  private List<Integer> items;

  public MaximizeStyle(int budget, int[] prices, double[] ratings) {
    this.budget = budget;
    this.prices = prices;
    this.ratings = ratings;
    this.bestRatingSum = 0;
    this.bestItems = new ArrayList<>();
    this.items = new ArrayList<>();
    this.n = prices.length;
  }

  private void visit(int i, int curCost, double curRatingSum) {
    if (i == n) {
      if (curRatingSum > bestRatingSum) {
        bestRatingSum = (int) curRatingSum;
        bestItems = new ArrayList<>(items);
      }
      return;
    }

    // Choice 1: skip item i.
    visit(i + 1, curCost, curRatingSum);
    // Choice 2: pick item i (if within budget).
    if (curCost + prices[i] <= budget) {
      items.add(i);
      visit(i + 1, curCost + prices[i], curRatingSum + ratings[i]);
      items.remove(items.size() - 1);
    }
  }

  public List<Integer> solve() {
    visit(0, 0, 0);
    return bestItems;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        {
            20,
            new int[] { 10, 5, 15, 8, 3 },
            new double[] { 7.0, 3.5, 9.0, 6.0, 2.0 },
            new int[] { 0, 3 }
        },
        // Example 2 from the book
        {
            10,
            new int[] { 2, 3, 4, 5 },
            new double[] { 1.0, 2.0, 3.5, 4.0 },
            new int[] { 2, 3 }
        },
        // Edge case - budget is 0
        {
            0,
            new int[] { 1, 2, 3 },
            new double[] { 1.0, 2.0, 3.0 },
            new int[] {}
        },
        // Edge case - no items
        {
            10,
            new int[] {},
            new double[] {},
            new int[] {}
        },
        // Larger budget
        {
            50,
            new int[] { 10, 20, 30 },
            new double[] { 10.0, 20.0, 30.0 },
            new int[] { 1, 2 }
        },
    };

    for (Object[] test : tests) {
      int budget = (int) test[0];
      int[] prices = (int[]) test[1];
      double[] ratings = (double[]) test[2];
      int[] wantArray = (int[]) test[3];
      List<Integer> want = Arrays.stream(wantArray).boxed()
          .collect(Collectors.toList());

      MaximizeStyle solution = new MaximizeStyle(budget, prices, ratings);
      List<Integer> got = solution.solve();

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%d, %s, %s): got: %s, want: %s\n",
            budget, Arrays.toString(prices), Arrays.toString(ratings), got,
            want));
      }
    }
  }
}

public class P15_00_157IKEAShopping {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
