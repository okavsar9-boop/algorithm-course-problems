// 13.5 - Popular Songs Class
// Run: javac P13_05_PopularSongsClass.java && java P13_05_PopularSongsClass

import java.util.*;
import java.util.function.*;

class PopularSongs {
  // Max-heap for lower half
  private PriorityQueue<Integer> lowerMaxHeap = new PriorityQueue<>(
      Collections.reverseOrder());
  // Min-heap for upper half
  private PriorityQueue<Integer> upperMinHeap = new PriorityQueue<>();
  private Map<String, Integer> playCounts = new HashMap<>();

  public void registerPlays(String title, int plays) {
    playCounts.put(title, plays);
    if (upperMinHeap.isEmpty() || plays >= upperMinHeap.peek()) {
      upperMinHeap.add(plays);
    } else {
      lowerMaxHeap.add(plays);
    }

    // Distribute elements if they are off by more than one
    if (lowerMaxHeap.size() > upperMinHeap.size()) {
      upperMinHeap.add(lowerMaxHeap.poll());
    } else if (upperMinHeap.size() > lowerMaxHeap.size() + 1) {
      lowerMaxHeap.add(upperMinHeap.poll());
    }
  }

  public boolean isPopular(String title) {
    if (!playCounts.containsKey(title)) {
      return false;
    }
    double median;
    if (lowerMaxHeap.size() == upperMinHeap.size()) {
      median = (upperMinHeap.peek() + lowerMaxHeap.peek()) / 2.0;
    } else {
      median = upperMinHeap.peek();
    }
    return playCounts.get(title) > median;
  }
}


class RunTests {
  public void runTests() {
    // Example from the book
    PopularSongs p = new PopularSongs();
    p.registerPlays("Boolean Rhapsody", 193);
    if (p.isPopular("Boolean Rhapsody")) {
      throw new RuntimeException("Fail: Boolean Rhapsody");
    }
    p.registerPlays("Coding In The Deep", 140);
    p.registerPlays("All the Single Brackets", 132);
    if (!p.isPopular("Boolean Rhapsody")) {
      throw new RuntimeException("Fail: Boolean Rhapsody");
    }
    if (p.isPopular("Coding In The Deep")) {
      throw new RuntimeException("Fail: Coding In The Deep");
    }
    if (p.isPopular("All the Single Brackets")) {
      throw new RuntimeException("Fail: All the Single Brackets");
    }

    p.registerPlays("All About That Base Case", 291);
    p.registerPlays("Oops! I Broke Prod Again", 274);
    p.registerPlays("Here Comes The Bug", 223);
    if (p.isPopular("Boolean Rhapsody")) {
      throw new RuntimeException(
          "Fail: Boolean Rhapsody after more plays");
    }
    if (!p.isPopular("Here Comes The Bug")) {
      throw new RuntimeException("Fail: Here Comes The Bug");
    }

    // Additional test cases
    // Test with no songs
    PopularSongs p2 = new PopularSongs();
    if (p2.isPopular("Nonexistent Song")) {
      throw new RuntimeException("Fail: nonexistent song");
    }

    // Test with one song
    p2.registerPlays("Single Song", 100);
    if (p2.isPopular("Single Song")) {
      throw new RuntimeException("Fail: single song should not be popular");
    }

    // Test with two songs
    p2.registerPlays("Song A", 100);
    p2.registerPlays("Song B", 200);
    if (p2.isPopular("Song A")) {
      throw new RuntimeException("Fail: Song A");
    }
    if (!p2.isPopular("Song B")) {
      throw new RuntimeException("Fail: Song B");
    }

    // Test with three songs
    p2.registerPlays("Song C", 150);
    if (p2.isPopular("Song A")) {
      throw new RuntimeException("Fail: Song A with three songs");
    }
    if (!p2.isPopular("Song B")) {
      throw new RuntimeException("Fail: Song B with three songs");
    }
    if (!p2.isPopular("Song C")) {
      throw new RuntimeException("Fail: Song C with three songs");
    }
  }
}

public class P13_05_PopularSongsClass {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
