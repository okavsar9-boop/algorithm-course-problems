// 6.9 - Product of Alphabetical Sums
// Run: javac P06_09_ProductOfAlphabeticalSums.java && java P06_09_ProductOfAlphabeticalSums

import java.util.*;
import java.util.function.*;

class AlphabeticSumProduct {
  private int alphabeticalSum(String word) {
    int sum = 0;
    for (char c : word.toCharArray()) {
      sum += c - 'a' + 1;
    }
    return sum;
  }

  public boolean solve(List<String> words, int target) {
    Set<Integer> sums = new HashSet<>();
    for (String word : words) {
      sums.add(alphabeticalSum(word));
    }

    for (int i : sums) {
      if (target % i != 0) {
        continue;
      }
      for (int j : sums) {
        if (target % (i * j) != 0) {
          continue;
        }
        int k = target / (i * j);
        if (sums.contains(k)) {
          return true;
        }
      }
    }
    return false;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        {
            new String[] { "abc", "fg", "hij", "klm", "nop", "qrs", "vwx" },
            1620,
            true
        },
        // Example 2
        {
            new String[] { "a", "b" },
            2,
            true
        },
        // Additional test cases
        {
            new String[] {},
            1,
            false
        },
        {
            new String[] { "a" },
            1,
            true
        },
        {
            new String[] { "a", "b", "c" },
            6,
            true
        },
        {
            new String[] { "a", "b", "c" },
            7,
            false
        }
    };

    AlphabeticSumProduct solution = new AlphabeticSumProduct();
    for (Object[] test : tests) {
      String[] wordsArray = (String[]) test[0];
      List<String> words = Arrays.stream(wordsArray)
          .collect(Collectors.toList());
      int target = (Integer) test[1];
      boolean want = (Boolean) test[2];
      boolean got = solution.solve(words, target);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d): got: %b, want: %b\n",
            words, target, got, want));
      }
    }
  }
}

public class P06_09_ProductOfAlphabeticalSums {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
