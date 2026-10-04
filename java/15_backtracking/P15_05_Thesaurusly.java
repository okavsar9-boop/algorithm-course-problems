// 15.5 - Thesaurusly
// Run: javac P15_05_Thesaurusly.java && java P15_05_Thesaurusly

import java.util.*;
import java.util.function.*;

class GenerateSentences {
  private String[] words;
  private List<String> res;
  private List<String> curSentence;
  private Map<String, List<String>> synonyms;

  private void visit(int i) {
    if (i == words.length) {
      res.add(String.join(" ", curSentence));
      return;
    }

    List<String> choices = synonyms.containsKey(words[i])
        ? synonyms.get(words[i])
        : Arrays.asList(words[i]);

    for (String choice : choices) {
      curSentence.add(choice);
      visit(i + 1);
      curSentence.remove(curSentence.size() - 1); // Undo change.
    }
  }

  public List<String> solve(String sentence, Map<String, List<String>> syns) {
    words = sentence.split(" ");
    res = new ArrayList<>();
    curSentence = new ArrayList<>();
    synonyms = syns;

    visit(0);
    return res;
  }
}


class RunTests {
  private static class TestCase {
    String sentence;
    Map<String, List<String>> synonyms;
    String[] want;

    TestCase(String sentence, Object[][] synonymPairs, String[] want) {
      this.sentence = sentence;
      this.synonyms = Arrays.stream(synonymPairs)
          .collect(Collectors.toMap(
              pair -> (String) pair[0],
              pair -> Arrays.stream((String[]) pair[1])
                  .collect(Collectors.toList())));
      this.want = want;
    }
  }

  public void runTests() {
    TestCase[] tests = {
        // Example from the book
        new TestCase(
            "one does not simply walk into mordor",
            new Object[][] {
                { "walk", new String[] { "stroll", "hike", "wander" } },
                { "simply", new String[] { "just", "merely" } }
            },
            new String[] {
                "one does not just stroll into mordor",
                "one does not just hike into mordor",
                "one does not just wander into mordor",
                "one does not merely stroll into mordor",
                "one does not merely hike into mordor",
                "one does not merely wander into mordor"
            }),
        // Edge case - no synonyms
        new TestCase(
            "hello world",
            new Object[][] {},
            new String[] { "hello world" }),
        // Single word with synonyms
        new TestCase(
            "walk",
            new Object[][] {
                { "walk", new String[] { "stroll", "hike" } }
            },
            new String[] { "stroll", "hike" }),
        // Multiple words, some with synonyms
        new TestCase(
            "I walk to the park",
            new Object[][] {
                { "walk", new String[] { "stroll", "hike" } }
            },
            new String[] {
                "I stroll to the park",
                "I hike to the park"
            })
    };

    GenerateSentences solution = new GenerateSentences();
    for (TestCase test : tests) {
      List<String> got = solution.solve(test.sentence, test.synonyms);
      List<String> wantList = Arrays.asList(test.want);
      Collections.sort(got);
      Collections.sort(wantList);

      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            test.sentence, test.synonyms, got, wantList));
      }
    }
  }
}

public class P15_05_Thesaurusly {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
