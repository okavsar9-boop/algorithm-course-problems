// 3.10 - Missing Numbers in Range
// Run: javac P03_10_MissingNumbersInRange.java && java P03_10_MissingNumbersInRange

import java.util.*;
import java.util.function.*;

class MissingNumbers {
  public List<Integer> solve(List<Integer> arr, int low, int high) {
    int p1 = 0; // pointer for arr
    int p2 = low; // pointer for virtual arr2
    List<Integer> res = new ArrayList<>();

    while (p1 < arr.size() && p2 <= high) {
      if (arr.get(p1) < p2) {
        p1++;
      } else if (arr.get(p1) == p2) {
        p1++;
        p2++;
      } else {
        res.add(p2);
        p2++;
      }
    }

    if (p2 <= high) {
      for (int i = p2; i <= high; i++) {
        res.add(i);
      }
    }

    return res;

  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { new int[] { 6, 9, 12, 15, 18 }, 9, 13,
        new int[] { 10, 11, 13 } },
      // Example 2 from the book
      { new int[] {}, 9, 9, new int[] { 9 } },
      // Example 3 from the book
      { new int[] { 6, 7, 8, 9 }, 7, 8, new int[] {} },
      // Additional test cases
      { new int[] {}, 1, 5, new int[] { 1, 2, 3, 4, 5 } },
      { new int[] { 1, 2, 3, 4, 5 }, 1, 5, new int[] {} },
      { new int[] { 1, 3, 5 }, 1, 5, new int[] { 2, 4 } },
      { new int[] { 1 }, 1, 1, new int[] {} },
      { new int[] { 2 }, 1, 3, new int[] { 1, 3 } },
    };

    MissingNumbers solution = new MissingNumbers();
    for (Object[] test : tests) {
      int[] arrArray = (int[]) test[0];
      List<Integer> arr = new ArrayList<>();
      for (int num : arrArray) {
        arr.add(num);
      }

      int low = (int) test[1];
      int high = (int) test[2];

      int[] wantArray = (int[]) test[3];
      List<Integer> want = new ArrayList<>();
      for (int num : wantArray) {
        want.add(num);
      }

      List<Integer> got = solution.solve(arr, low, high);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %d, %d): got: %s, want: %s\n",
        arr, low, high, got, want));
      }
    }

  }
}

public class P03_10_MissingNumbersInRange {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
