// 15.3 - Permutation Enumeration
// Run: javac P15_03_PermutationEnumeration.java && java P15_03_PermutationEnumeration

import java.util.*;
import java.util.function.*;

class GeneratePermutations {
  private List<List<Character>> res;
  private List<Character> perm;

  private void visit(int i) {
    if (i == perm.size() - 1) {
      res.add(new ArrayList<>(perm));
      return;
    }
    for (int j = i; j < perm.size(); j++) {
      Collections.swap(perm, i, j); // Pick perm[j]
      visit(i + 1);
      Collections.swap(perm, i, j); // Cleanup work: undo change
    }
  }

  public List<List<Character>> solve(char[] arr) {
    res = new ArrayList<>();
    perm = new String(arr).chars()
        .mapToObj(ch -> (char) ch)
        .collect(Collectors.toList());
    visit(0);
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new char[] { 'x', 'y', 'z' },
            new char[][] {
                { 'x', 'y', 'z' }, { 'x', 'z', 'y' },
                { 'y', 'x', 'z' }, { 'y', 'z', 'x' },
                { 'z', 'x', 'y' }, { 'z', 'y', 'x' } } },
        // Single element
        { new char[] { 'a' },
            new char[][] { { 'a' } } },
        // Two elements
        { new char[] { 'a', 'b' },
            new char[][] { { 'a', 'b' }, { 'b', 'a' } } },
        // Larger set
        { new char[] { 'a', 'b', 'c' },
            new char[][] {
                { 'a', 'b', 'c' }, { 'a', 'c', 'b' },
                { 'b', 'a', 'c' }, { 'b', 'c', 'a' },
                { 'c', 'a', 'b' }, { 'c', 'b', 'a' } } }
    };

    GeneratePermutations solution = new GeneratePermutations();
    for (Object[] test : tests) {
      char[] arr = (char[]) test[0];
      char[][] wantArray = (char[][]) test[1];

      List<List<Character>> want = Arrays.stream(wantArray)
          .map(row -> new String(row).chars()
              .mapToObj(ch -> (char) ch)
              .collect(Collectors.toList()))
          .collect(Collectors.toList());

      List<List<Character>> got = solution.solve(arr);
      Collections.sort(got, (a, b) -> {
        for (int i = 0; i < a.size(); i++) {
          int cmp = a.get(i).compareTo(b.get(i));
          if (cmp != 0)
            return cmp;
        }
        return 0;
      });
      Collections.sort(want, (a, b) -> {
        for (int i = 0; i < a.size(); i++) {
          int cmp = a.get(i).compareTo(b.get(i));
          if (cmp != 0)
            return cmp;
        }
        return 0;
      });

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %d permutations, want: %d permutations\n",
            Arrays.toString(arr), got.size(), want.size()));
      }
    }
  }
}

public class P15_03_PermutationEnumeration {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
