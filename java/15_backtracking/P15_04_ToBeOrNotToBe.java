// 15.4 - To Be or Not to Be
// Run: javac P15_04_ToBeOrNotToBe.java && java P15_04_ToBeOrNotToBe

import java.util.*;
import java.util.function.*;

def visit(partial_solution):
  if full_solution(partial_solution):
    # Process leaf/full solution.
  else:
    for choice in choices(partial_solution):
      # Prune children where possible.
      child = apply_choice(partial_solution)
      visit(child)

class Shakespearify {
  private List<String> words;
  private List<String> currentSentence;
  private List<String> res;

  private void visit(int i) {
    if (i == words.size()) {
      res.add(String.join(" ", currentSentence));
      return;
    }
    // Choice 1: include the word
    currentSentence.add(words.get(i));
    visit(i + 1);
    currentSentence.remove(currentSentence.size() - 1); // Cleanup work: undo
                                                        // choice 1
    // Choice 2: exclude the word
    visit(i + 1);
  }

  public List<String> solve(String sentence) {
    res = new ArrayList<>();
    currentSentence = new ArrayList<>();
    words = sentence.isEmpty() ? new ArrayList<>()
        : Arrays.asList(sentence.split(" "));

    visit(0);
    return res;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { "I love dogs",
            new String[] { "", "I", "love", "dogs", "I love", "I dogs",
                "love dogs", "I love dogs" } },
        // Edge case - empty sentence
        { "", new String[] { "" } },
        // Single word
        { "hello", new String[] { "", "hello" } },
        // Two words
        { "hello world", new String[] { "", "hello", "world", "hello world" } },
    };

    Shakespearify solution = new Shakespearify();
    for (Object[] test : tests) {
      String sentence = (String) test[0];
      String[] want = (String[]) test[1];
      List<String> got = solution.solve(sentence);
      Collections.sort(got);
      List<String> wantList = Arrays.asList(want);
      Collections.sort(wantList);

      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nsolve(\"%s\"): got: %s, want: %s\n",
            sentence, got, wantList));
      }
    }
  }
}

public class P15_04_ToBeOrNotToBe {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
