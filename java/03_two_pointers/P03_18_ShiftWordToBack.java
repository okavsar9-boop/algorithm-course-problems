// 3.18 - Shift Word to Back
// Run: javac P03_18_ShiftWordToBack.java && java P03_18_ShiftWordToBack

import java.util.*;
import java.util.function.*;

class MoveWord {
  public void solve(char[] arr, String word) {
    int seeker = 0, writer = 0;
    int i = 0;
    while (seeker < arr.length) {
      if (i < word.length() && arr[seeker] == word.charAt(i)) {
        seeker++;
        i++;
      } else {
        arr[writer] = arr[seeker];
        seeker++;
        writer++;
      }
    }
    for (char c : word.toCharArray()) {
      arr[writer] = c;
      writer++;
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book
      { "seekerandwriter".toCharArray(), "edit",
        "sekeranwreredit".toCharArray() },
      // Example 2 from the book
      { "bacb".toCharArray(), "ab", "bcab".toCharArray() },
      // Example 3 from the book
      { "babc".toCharArray(), "b", "abcb".toCharArray() },
      // Additional test cases
      { "".toCharArray(), "", "".toCharArray() },
      { "a".toCharArray(), "a", "a".toCharArray() },
      { "abc".toCharArray(), "", "abc".toCharArray() },
      { "hello".toCharArray(), "ho", "ellho".toCharArray() },
      { "abcabc".toCharArray(), "abc", "abcabc".toCharArray() },
    };

    MoveWord solution = new MoveWord();
    for (Object[] test : tests) {
      char[] arr = Arrays.copyOf((char[]) test[0], ((char[]) test[0]).length);
      String word = (String) test[1];
      char[] want = (char[]) test[2];
      solution.solve(arr, word);
      if (!Arrays.equals(arr, want)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %s): got: %s, want: %s\n",
        Arrays.toString((char[]) test[0]), word, Arrays.toString(arr),
        Arrays.toString(want)));
      }
    }

  }
}

public class P03_18_ShiftWordToBack {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
