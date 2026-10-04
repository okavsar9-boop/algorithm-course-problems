// 6.6 - Find All Squares
// Run: javac P06_06_FindAllSquares.java && java P06_06_FindAllSquares

import java.util.*;
import java.util.function.*;

class FindSquared {
  public List<List<Integer>> solve(List<Integer> arr) {
    Map<Integer, Integer> numToIndex = new HashMap<>();
    for (int i = 0; i < arr.size(); i++) {
      numToIndex.put(arr.get(i), i);
    }

    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < arr.size(); i++) {
      int square = arr.get(i) * arr.get(i);
      if (numToIndex.containsKey(square)) {
        res.add(Arrays.asList(i, numToIndex.get(square)));
      }
    }
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        {
            new Integer[] { 4, 10, 3, 100, 5, 2, 10000 },
            new Integer[][] {
                { 5, 0 },
                { 1, 3 },
                { 3, 6 }
            }
        },
        // Additional test cases
        {
            new Integer[] {},
            new Integer[][] {}
        },
        {
            new Integer[] { 1 },
            new Integer[][] { { 0, 0 } }
        },
        {
            new Integer[] { 2, 4 },
            new Integer[][] { { 0, 1 } }
        }
    };

    FindSquared solution = new FindSquared();
    for (Object[] test : tests) {
      Integer[] arrArray = (Integer[]) test[0];
      Integer[][] wantArray = (Integer[][]) test[1];

      List<Integer> arr = Arrays.asList(arrArray);
      List<List<Integer>> want = Arrays.stream(wantArray)
          .map(Arrays::asList)
          .collect(Collectors.toList());

      List<List<Integer>> got = solution.solve(arr);
      // Sort both lists for comparison
      got.sort((a, b) -> {
        int cmp = a.get(0).compareTo(b.get(0));
        return cmp != 0 ? cmp : a.get(1).compareTo(b.get(1));
      });
      want.sort((a, b) -> {
        int cmp = a.get(0).compareTo(b.get(0));
        return cmp != 0 ? cmp : a.get(1).compareTo(b.get(1));
      });
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            arr, got, want));
      }
    }
  }
}

public class P06_06_FindAllSquares {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
