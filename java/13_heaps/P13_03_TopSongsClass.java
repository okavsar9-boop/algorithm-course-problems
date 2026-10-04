// 13.3 - Top Songs Class
// Run: javac P13_03_TopSongsClass.java && java P13_03_TopSongsClass

import java.util.*;
import java.util.function.*;

record Song(String title, int plays) {
}

class TopSongs {
  private final int k;
  private final PriorityQueue<Song> minHeap;

  public TopSongs(int k) {
    this.k = k;
    this.minHeap = new PriorityQueue<>(Comparator.comparingInt(Song::plays));
  }

  public void registerPlays(String title, int plays) {
    minHeap.add(new Song(title, plays));
    if (minHeap.size() > k) {
      minHeap.poll();
    }
  }

  public List<String> topK() {
    List<String> topSongs = new ArrayList<>();
    PriorityQueue<Song> temp = new PriorityQueue<>(minHeap);
    while (!temp.isEmpty()) {
      topSongs.add(temp.poll().title());
    }
    return topSongs;
  }
}


class RunTests {
  public void runTests() {
    // Example from the book
    TopSongs s = new TopSongs(3);
    s.registerPlays("Boolean Rhapsody", 193);
    s.registerPlays("Coding In The Deep", 146);
    List<String> result = s.topK();
    Set<String> got = new HashSet<>(result);
    Set<String> want = new HashSet<>(Arrays.asList(
        "Boolean Rhapsody", "Coding In The Deep"));
    if (!got.equals(want)) {
      throw new RuntimeException("Test failed for TopSongs with initial songs");
    }

    s.registerPlays("All About That Base Case", 291);
    s.registerPlays("Here Comes The Bug", 223);
    s.registerPlays("Oops! I Broke Prod Again", 274);
    s.registerPlays("All the Single Brackets", 132);
    result = s.topK();
    got = new HashSet<>(result);
    want = new HashSet<>(Arrays.asList(
        "All About That Base Case", "Here Comes The Bug",
        "Oops! I Broke Prod Again"));
    if (!got.equals(want)) {
      throw new RuntimeException("Test failed for TopSongs after more songs");
    }

    // Additional test cases
    // Test with fewer songs than k
    TopSongs s2 = new TopSongs(5);
    s2.registerPlays("Song A", 100);
    s2.registerPlays("Song B", 200);
    result = s2.topK();
    got = new HashSet<>(result);
    want = new HashSet<>(Arrays.asList("Song A", "Song B"));
    if (!got.equals(want)) {
      throw new RuntimeException(
          "Test failed for TopSongs with fewer songs than k");
    }

    // Test with exact k songs
    TopSongs s3 = new TopSongs(3);
    s3.registerPlays("Song A", 100);
    s3.registerPlays("Song B", 200);
    s3.registerPlays("Song C", 300);
    result = s3.topK();
    got = new HashSet<>(result);
    want = new HashSet<>(Arrays.asList("Song A", "Song B", "Song C"));
    if (!got.equals(want)) {
      throw new RuntimeException("Test failed for TopSongs with exact k songs");
    }

    // Test with ties in play counts
    TopSongs s4 = new TopSongs(2);
    s4.registerPlays("Song A", 100);
    s4.registerPlays("Song B", 100);
    s4.registerPlays("Song C", 100);
    s4.registerPlays("Song D", 100);
    result = s4.topK();
    if (result.size() != 2) {
      throw new RuntimeException(
          "Test failed for TopSongs with ties in play counts");
    }
  }
}

public class P13_03_TopSongsClass {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
