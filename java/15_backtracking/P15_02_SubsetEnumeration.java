// 15.2 - Subset Enumeration
// Run: javac P15_02_SubsetEnumeration.java && java P15_02_SubsetEnumeration

import java.util.*;
import java.util.function.*;

class AllSubsets {
  private List<List<Character>> res;
  private List<Character> subset;
  private List<Character> S;

  private void visit(int i) {
    if (i == S.size()) {
      res.add(new ArrayList<>(subset));
      return;
    }
    // Choice 1: pick S[i]
    subset.add(S.get(i));
    visit(i + 1);
    subset.remove(subset.size() - 1); // Cleanup work: undo choice 1

    // Choice 2: skip S[i]
    visit(i + 1);
  }

  public List<List<Character>> solve(char[] input) {
    res = new ArrayList<>();
    subset = new ArrayList<>();
    S = new String(input).chars()
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
        {
            new char[] { 'x', 'y', 'z' },
            new char[][] {
                {},
                { 'x' },
                { 'y' },
                { 'z' },
                { 'x', 'y' },
                { 'x', 'z' },
                { 'y', 'z' },
                { 'x', 'y', 'z' }
            }
        },
        // Edge case - empty set
        {
            new char[] {},
            new char[][] { {} }
        },
        // Single element
        {
            new char[] { 'a' },
            new char[][] { {}, { 'a' } }
        },
        // Two elements
        {
            new char[] { 'a', 'b' },
            new char[][] {
                {},
                { 'a' },
                { 'b' },
                { 'a', 'b' }
            }
        },
        // Larger set
        {
            new char[] { 'a', 'b', 'c', 'd' },
            new char[][] {
                {},
                { 'a' },
                { 'b' },
                { 'c' },
                { 'd' },
                { 'a', 'b' },
                { 'a', 'c' },
                { 'a', 'd' },
                { 'b', 'c' },
                { 'b', 'd' },
                { 'c', 'd' },
                { 'a', 'b', 'c' },
                { 'a', 'b', 'd' },
                { 'a', 'c', 'd' },
                { 'b', 'c', 'd' },
                { 'a', 'b', 'c', 'd' }
            }
        }
    };

    AllSubsets solution = new AllSubsets();
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
        if (a.size() != b.size())
          return a.size() - b.size();
        for (int i = 0; i < a.size(); i++) {
          int cmp = a.get(i).compareTo(b.get(i));
          if (cmp != 0)
            return cmp;
        }
        return 0;
      });
      Collections.sort(want, (a, b) -> {
        if (a.size() != b.size())
          return a.size() - b.size();
        for (int i = 0; i < a.size(); i++) {
          int cmp = a.get(i).compareTo(b.get(i));
          if (cmp != 0)
            return cmp;
        }
        return 0;
      });

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            Arrays.toString(arr), got, want));
      }
    }
  }
}

public class P15_02_SubsetEnumeration {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
