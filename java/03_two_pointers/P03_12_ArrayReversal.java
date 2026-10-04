// 3.12 - Array Reversal
// Run: javac P03_12_ArrayReversal.java && java P03_12_ArrayReversal

import java.util.*;
import java.util.function.*;

class Reverse {
  public void solve(char[] arr) {
    int l = 0, r = arr.length - 1;
    while (l < r) {
      char temp = arr[l];
      arr[l] = arr[r];
      arr[r] = temp;
      l++;
      r--;
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Test cases
      { "hello".toCharArray(), "olleh".toCharArray() },
      { "".toCharArray(), "".toCharArray() },
      { "a".toCharArray(), "a".toCharArray() },
      { "ab".toCharArray(), "ba".toCharArray() },
      { "abc".toCharArray(), "cba".toCharArray() },
      { "abcd".toCharArray(), "dcba".toCharArray() },
    };

    Reverse solution = new Reverse();
    for (Object[] test : tests) {
      char[] arr = Arrays.copyOf((char[]) test[0], ((char[]) test[0]).length);
      char[] want = (char[]) test[1];
      solution.solve(arr);
      if (!Arrays.equals(arr, want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s): got: %s, want: %s\n",
        Arrays.toString((char[]) test[0]), Arrays.toString(arr),
        Arrays.toString(want)));
      }
    }

  }
}

public class P03_12_ArrayReversal {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
