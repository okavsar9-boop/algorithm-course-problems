// 7.1 - Sorting by Frequency
// Run: javac P07_01_SortingByFrequency.java && java P07_01_SortingByFrequency

import java.util.*;
import java.util.function.*;

class LetterOccurrences {
  public List<Character> solve(String word) {
    Map<Character, Integer> letterToCount = new HashMap<>();
    for (char c : word.toCharArray()) {
      letterToCount.put(c, letterToCount.getOrDefault(c, 0) + 1);
    }

    List<Map.Entry<Character, Integer>> tuples = new ArrayList<>(
        letterToCount.entrySet());
    tuples.sort((a, b) -> {
      if (!a.getValue().equals(b.getValue())) {
        return b.getValue() - a.getValue(); // Sort by frequency descending
      }
      return a.getKey().compareTo(b.getKey()); // Break ties by letter ascending
    });

    List<Character> res = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : tuples) {
      res.add(entry.getKey());
    }
    return res;
  }
}

class LetterOccurrencesLambda {
  public List<Character> solve(String word) {
    Map<Character, Integer> letterToCount = new HashMap<>();
    List<Character> res = new ArrayList<>();
    for (char c : word.toCharArray()) {
      if (!letterToCount.containsKey(c)) {
        letterToCount.put(c, 0);
        res.add(c);
      }
      letterToCount.put(c, letterToCount.get(c) + 1);
    }

    res.sort((a, b) -> {
      int countA = letterToCount.get(a);
      int countB = letterToCount.get(b);
      if (countA != countB) {
        return Integer.compare(countB, countA);
      }
      return Character.compare(a, b);
    });
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { "supercalifragilisticexpialidocious",
            new Character[] {
                'i', 'a', 'c', 'l', 's',
                'e', 'o', 'p', 'r', 'u',
                'd', 'f', 'g', 't', 'x' } },
        // Edge case - empty string
        { "", new Character[] {} },
        // Edge case - single character
        { "a", new Character[] { 'a' } },
        // Edge case - all same frequency
        { "abc", new Character[] { 'a', 'b', 'c' } },
        // Multiple frequencies with ties
        { "aabbbcccc", new Character[] { 'c', 'b', 'a' } },
        // All same character
        { "zzzzz", new Character[] { 'z' } },
        // Alternating characters
        { "ababab", new Character[] { 'a', 'b' } },
        // Reverse alphabetical order but same frequency
        { "zyxwv", new Character[] { 'v', 'w', 'x', 'y', 'z' } },
        // Long string with many frequencies
        { "aaaaabbbbbbbcccccccccdddddddddddeeeeeeeeeeee",
            new Character[] { 'e', 'd', 'c', 'b', 'a' } },
    };

    LetterOccurrences solution = new LetterOccurrences();
    LetterOccurrencesLambda lambdaSolution = new LetterOccurrencesLambda();
    for (Object[] test : tests) {
      String word = (String) test[0];
      List<Character> want = Arrays.asList((Character[]) test[1]);
      List<Character> got = solution.solve(word);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n", word, got, want));
      }
      got = lambdaSolution.solve(word);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolveLambda(%s): got: %s, want: %s\n", word, got, want));
      }
    }
  }
}

public class P07_01_SortingByFrequency {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
