// 22.7 - Presidential Election
// Run: javac P22_07_PresidentialElection.java && java P22_07_PresidentialElection

import java.util.*;
import java.util.function.*;

class Party implements Comparable<Party> {
  String candidate;
  int votes;

  Party(String candidate, int votes) {
    this.candidate = candidate;
    this.votes = votes;
  }

  @Override
  public int compareTo(Party other) {
    if (this.votes != other.votes) {
      return Integer.compare(this.votes, other.votes);
    }
    return other.candidate.compareTo(this.candidate); // Reverse for min heap
  }
}

class Winner {
  public String solve(String[] candidates, int[] votes) {
    int totalVotes = Arrays.stream(votes).sum();

    // Min heap using PriorityQueue
    PriorityQueue<Party> minHeap = new PriorityQueue<>();

    for (int i = 0; i < candidates.length; i++) {
      if (votes[i] > totalVotes / 2) {
        return candidates[i];
      }
      minHeap.offer(new Party(candidates[i], votes[i]));
    }

    while (true) {
      Party smallest = minHeap.poll();
      Party secondSmallest = minHeap.poll();

      int coalitionVotes = smallest.votes + secondSmallest.votes;
      String coalitionCandidate = secondSmallest.candidate;

      // Handle ties
      while (!minHeap.isEmpty()
      && minHeap.peek().votes == secondSmallest.votes) {
        coalitionCandidate = coalitionCandidate
        .compareTo(minHeap.peek().candidate) < 0
        ? coalitionCandidate
        : minHeap.peek().candidate;
        coalitionVotes += minHeap.poll().votes;
      }

      if (coalitionVotes > totalVotes / 2) {
        return coalitionCandidate;
      }
      minHeap.offer(new Party(coalitionCandidate, coalitionVotes));
    }
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new String[] { "Ale", "Bloop", "Chip", "Dart", "Zing" },
            new int[] { 10, 20, 30, 15, 25 },
            "Dart" },
        // Two parties
        { new String[] { "Alice", "Bob" },
            new int[] { 40, 50 },
            "Bob" },
        { new String[] { "Alice", "Bob" },
            new int[] { 60, 60 },
            "Alice" },
        // Single party
        { new String[] { "Alice" },
            new int[] { 10 },
            "Alice" },
        // Three way tie for second lowest
        { new String[] { "A", "E", "C", "D" },
            new int[] { 20, 5, 5, 5 },
            "A" },
        { new String[] { "A", "E", "C", "D" },
            new int[] { 10, 5, 5, 5 },
            "C" },
        // All parties have equal votes
        { new String[] { "X", "Y", "Z" },
            new int[] { 10, 10, 10 },
            "X" },
    };

    Winner solution = new Winner();
    for (Object[] test : tests) {
      String[] candidates = (String[]) test[0];
      int[] votes = (int[]) test[1];
      String want = (String) test[2];
      String got = solution.solve(candidates, votes);

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %s): got: %s, want: %s\n",
            Arrays.toString(candidates), Arrays.toString(votes), got, want));
      }
    }
  }
}

public class P22_07_PresidentialElection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
