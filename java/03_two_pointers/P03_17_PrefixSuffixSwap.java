// 3.17 - Prefix-Suffix Swap
// Run: javac P03_17_PrefixSuffixSwap.java && java P03_17_PrefixSuffixSwap

import java.util.*;
import java.util.function.*;

class SwapPrefixSuffix {
  public void solve(char[] arr) {
    if (arr.length == 0) {
      return;
    }

    int n = arr.length;

    // Reverse the whole array
    int l = 0, r = n - 1;
    while (l < r) {
      char temp = arr[l];
      arr[l] = arr[r];
      arr[r] = temp;
      l++;
      r--;
    }
    
    // Reverse the last n/3 elements
    l = 2 * n / 3;
    r = n - 1;
    while (l < r) {
      char temp = arr[l];
      arr[l] = arr[r];
      arr[r] = temp;
      l++;
      r--;
    }

    // Reverse the first 2n/3 elements
    l = 0;
    r = (2 * n / 3) - 1;
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
        // Example from the book
        { "badreview".toCharArray(), "reviewbad".toCharArray() },
        // Additional test cases
        { "".toCharArray(), "".toCharArray() },
        { "abc".toCharArray(), "bca".toCharArray() },
        { "abcdef".toCharArray(), "cdefab".toCharArray() },
        { "123456789".toCharArray(), "456789123".toCharArray() },
        { "aaabbbccc".toCharArray(), "bbbcccaaa".toCharArray() },
    };

    SwapPrefixSuffix solution = new SwapPrefixSuffix();
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

public class P03_17_PrefixSuffixSwap {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
