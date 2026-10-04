// 13.4 - Top Songs Class With Updates
// Run: javac P13_04_TopSongsClassWithUpdates.java && java P13_04_TopSongsClassWithUpdates

import java.util.*;
import java.util.function.*;

record Song(int plays, String title) {
}

class TopSongs {
  private final int k;
  private final PriorityQueue<Song> maxHeap;
  private final Map<String, Integer> totalPlays;

  public TopSongs(int k) {
    this.k = k;
    this.maxHeap = new PriorityQueue<>((a, b) -> b.plays() - a.plays());
    this.totalPlays = new HashMap<>();
  }

  public void registerPlays(String title, int plays) {
    int newTotalPlays = plays;
    if (totalPlays.containsKey(title)) {
      newTotalPlays += totalPlays.get(title);
    }
    totalPlays.put(title, newTotalPlays);

    maxHeap.add(new Song(newTotalPlays, title));
  }

  public List<String> topK() {
    List<String> topSongs = new ArrayList<>();

    while (topSongs.size() < k && !maxHeap.isEmpty()) {
      Song song = maxHeap.poll();
      if (totalPlays.get(song.title()) == song.plays()) { // Not stale
        topSongs.add(song.title());
      }
    }

    // Restore the max-heap
    for (String title : topSongs) {
      maxHeap.add(new Song(totalPlays.get(title), title));
    }
    return topSongs;
  }
}


class RunTests {
  public void runTests() {
    // Example from the book
    TopSongs s = new TopSongs(3);
    s.registerPlays("Boolean Rhapsody", 100);
    s.registerPlays("Boolean Rhapsody", 193); // Total 293
    s.registerPlays("Coding In The Deep", 75);
    s.registerPlays("Coding In The Deep", 75); // Total 150
    s.registerPlays("All About That Base Case", 200);
    s.registerPlays("All About That Base Case", 90); // Total 290
    s.registerPlays("All About That Base Case", 1); // Total 291
    s.registerPlays("Here Comes The Bug", 223);
    s.registerPlays("Oops! I Broke Prod Again", 274);
    s.registerPlays("All the Single Brackets", 132);

    List<String> result = s.topK();
    Set<String> got = new HashSet<>(result);
    Set<String> want = new HashSet<>(Arrays.asList(
        "All About That Base Case", "Boolean Rhapsody",
        "Oops! I Broke Prod Again"));
    if (!got.equals(want)) {
      throw new RuntimeException("\ntopK(): got: " + got +
          ", want: " + want + "\n");
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
      throw new RuntimeException("\ntopK() with fewer songs than k: got: " +
          got + ", want: " + want + "\n");
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
      throw new RuntimeException("\ntopK() with exactly k songs: got: " +
          got + ", want: " + want + "\n");
    }

    // Test with ties in play counts
    TopSongs s4 = new TopSongs(2);
    s4.registerPlays("Song A", 100);
    s4.registerPlays("Song B", 100);
    s4.registerPlays("Song C", 100);
    s4.registerPlays("Song D", 100);
    result = s4.topK();
    if (result.size() != 2) {
      throw new RuntimeException(String.format(
          "\ntopK() with tied play counts: got length %d, want length 2\n",
          result.size()));
    }
  }
}

public class P13_04_TopSongsClassWithUpdates {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
