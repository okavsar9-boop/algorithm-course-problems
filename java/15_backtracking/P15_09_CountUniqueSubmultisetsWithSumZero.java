// 15.9 - Count Unique Submultisets with Sum Zero
// Run: javac P15_09_CountUniqueSubmultisetsWithSumZero.java && java P15_09_CountUniqueSubmultisetsWithSumZero

import java.util.*;
import java.util.function.*;

def visit(partial_solution):
  if full_solution(partial_solution):
    # Process leaf/full solution.
  else:
    for choice in choices(partial_solution):
      # Prune children where possible.
      child = apply_choice(partial_solution)
      visit(child)

class CountUniqueSubmultisetsWithSumZero {
  private List<Integer> uniqueElements;
  private Map<Integer, Integer> frequency;

  private int visit(int index, int currentSum) {
    if (index == uniqueElements.size()) {
      return currentSum == 0 ? 1 : 0;
    }

    int element = uniqueElements.get(index);
    int count = frequency.get(element);
    int totalCount = 0;

    // Try all possible counts of the current element
    for (int i = 0; i <= count; i++) {
      totalCount += visit(index + 1, currentSum + i * element);
    }

    return totalCount;
  }

  public int solve(int[] S) {
    frequency = new HashMap<>();
    uniqueElements = new ArrayList<>();

    // Build frequency map
    for (int num : S) {
      frequency.put(num, frequency.getOrDefault(num, 0) + 1);
    }

    // Extract unique elements
    uniqueElements.addAll(frequency.keySet());

    return visit(0, 0);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[] { 1, 1, -1, -1 }, 3 },
        // Example 2 from the book
        { new int[] {}, 1 },
        // Example 3 from the book
        { new int[] { -1, 2, 1, 0, 3 }, 4 },
        // Edge case - no zero-sum submultisets
        { new int[] { 1, 2, 3 }, 1 },
        // Edge case - all zeros
        { new int[] { 0, 0, 0 }, 4 }
    };

    CountUniqueSubmultisetsWithSumZero solution = new CountUniqueSubmultisetsWithSumZero();
    for (Object[] test : tests) {
      int[] S = (int[]) test[0];
      int want = (int) test[1];
      int got = solution.solve(S);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d, want: %d\n",
            Arrays.toString(S), got, want));
      }
    }
  }
}

public class P15_09_CountUniqueSubmultisetsWithSumZero {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
