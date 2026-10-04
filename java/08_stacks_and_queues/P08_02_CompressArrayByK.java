// 8.2 - Compress Array By K
// Run: javac P08_02_CompressArrayByK.java && java P08_02_CompressArrayByK

import java.util.*;
import java.util.function.*;

class CompressArrayK {
  public List<Integer> solve(int[] arr, int k) {
    List<int[]> stack = new ArrayList<>();

    for (int num : arr) {
      merge(stack, num, k);
    }

    List<Integer> res = new ArrayList<>();
    for (int[] pair : stack) {
      for (int i = 0; i < pair[1]; i++) {
        res.add(pair[0]);
      }
    }
    return res;
  }

  private void merge(List<int[]> stack, int num, int k) {
    if (stack.isEmpty() || stack.get(stack.size() - 1)[0] != num) {
      stack.add(new int[] { num, 1 });
    } else if (stack.get(stack.size() - 1)[1] < k - 1) {
      stack.get(stack.size() - 1)[1] += 1;
    } else {
      stack.remove(stack.size() - 1);
      merge(stack, num * k, k);
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        {
            new int[] { 1, 9, 9, 3, 3, 3, 4 },
            3,
            new int[] { 1, 27, 4 }
        },
        {
            new int[] { 8, 4, 2, 2 },
            2,
            new int[] { 16 }
        },
        {
            new int[] { 4, 4, 4, 4 },
            5,
            new int[] { 4, 4, 4, 4 }
        },
        {
            new int[] {},
            2,
            new int[] {}
        },
        {
            new int[] { 0, 0, 0, 0 },
            2,
            new int[] { 0 }
        },
    };
    CompressArrayK solution = new CompressArrayK();
    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int k = (int) test[1];
      int[] want = (int[]) test[2];
      List<Integer> got = solution.solve(arr, k);
      List<Integer> wantList = new ArrayList<>();
      for (int num : want) {
        wantList.add(num);
      }
      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %s, want: %s\n",
            java.util.Arrays.toString(arr), k, got, wantList));
      }
    }
  }
}

public class P08_02_CompressArrayByK {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
