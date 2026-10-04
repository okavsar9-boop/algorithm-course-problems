// 7.3 - Delete Operations
// Run: javac P07_03_DeleteOperations.java && java P07_03_DeleteOperations

import java.util.*;
import java.util.function.*;

class ProcessOperations {
  public List<Integer> solve(int[] nums, int[] operations) {
    int n = nums.length;
    Set<Integer> deleted = new HashSet<>();
    List<Integer> sortedIndices = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      sortedIndices.add(i);
    }

    // Since the indices start in order and the sort is stable, we break ties by
    // smallest index, as required by the problem.
    sortedIndices.sort((a, b) -> Integer.compare(nums[a], nums[b]));

    int smallestIdx = 0;
    for (int op : operations) {
      if (0 <= op && op < n) {
        deleted.add(op);
      } else {
        // Skip until the next non-deleted smallest index.
        while (smallestIdx < n
        && deleted.contains(sortedIndices.get(smallestIdx))) {
          smallestIdx++;
        }
        if (smallestIdx < n) {
          deleted.add(sortedIndices.get(smallestIdx));
          smallestIdx++;
        }
      }
    }

    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (!deleted.contains(i)) {
        res.add(nums[i]);
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 50, 30, 70, 20, 80 }, new int[] { 2, -1, 4, -1 },
            new Integer[] { 50 } },
        // Edge case - empty operations
        { new int[] { 1, 2, 3 }, new int[] {},
            new Integer[] { 1, 2, 3 } },
        // Edge case - delete all
        { new int[] { 1, 2, 3 }, new int[] { -1, -1, -1 },
            new Integer[] {} },
        // Edge case - delete all indices
        { new int[] { 1, 2, 3 }, new int[] { 0, 1, 2 },
            new Integer[] {} },
        // Edge case - single element
        { new int[] { 1 }, new int[] { -1 },
            new Integer[] {} },
        // Edge case - duplicates
        { new int[] { 5, 5, 5 }, new int[] { -1, -1 },
            new Integer[] { 5 } },
        // Edge case - negative numbers
        { new int[] { -3, -2, -1 }, new int[] { -1, -1 },
            new Integer[] { -1 } },
        // Mixed operations with duplicates
        { new int[] { 10, 10, 20, 20 }, new int[] { 1, -1, -1 },
            new Integer[] { 20 } },
        // Operations targeting same index
        { new int[] { 1, 2, 3 }, new int[] { 0, 0, 0 },
            new Integer[] { 2, 3 } },
        // Alternating index and min operations
        { new int[] { 5, 4, 3, 2, 1 }, new int[] { 2, -1, 0, -1 },
            new Integer[] { 4 } },
        // Large numbers within constraints
        { new int[] { 1000000000, -1000000000, 0 }, new int[] { -1, -1 },
            new Integer[] { 1000000000 } }
    };

    ProcessOperations solution = new ProcessOperations();
    for (Object[] test : tests) {
      int[] nums = (int[]) test[0];
      int[] operations = (int[]) test[1];
      Integer[] wantArray = (Integer[]) test[2];
      List<Integer> want = Arrays.stream(wantArray)
          .collect(Collectors.toList());
      List<Integer> got = solution.solve(nums.clone(), operations); // Create a
                                                                    // copy
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            Arrays.toString(nums), Arrays.toString(operations), got, want));
      }
    }
  }
}

public class P07_03_DeleteOperations {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
