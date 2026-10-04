// 13.6 - Most Listened Across Genres
// Run: javac P13_06_MostListenedAcrossGenres.java && java P13_06_MostListenedAcrossGenres

import java.util.*;
import java.util.function.*;

record Song(String name, int playCount) {
}

record HeapEntry(int genreIndex, int songIndex) {
}

class TopKAcrossGenres {
  public List<String> solve(List<List<Song>> genres, int k) {
    // Create max heap of (genre_index, song_index)
    PriorityQueue<HeapEntry> maxHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(
    genres.get(b.genreIndex()).get(b.songIndex()).playCount(),
    genres.get(a.genreIndex()).get(a.songIndex()).playCount()));

    for (int genreIndex = 0; genreIndex < genres.size(); genreIndex++) {
      if (!genres.get(genreIndex).isEmpty()) {
        maxHeap.offer(new HeapEntry(genreIndex, 0));
      }
    }

    List<String> topK = new ArrayList<>();
    while (topK.size() < k && !maxHeap.isEmpty()) {
      HeapEntry entry = maxHeap.poll();
      String songName = genres.get(entry.genreIndex())
      .get(entry.songIndex()).name();
      topK.add(songName);

      int nextSongIndex = entry.songIndex() + 1;
      if (nextSongIndex < genres.get(entry.genreIndex()).size()) {
        maxHeap.offer(new HeapEntry(entry.genreIndex(), nextSongIndex));
      }
    }

    return topK;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example from the book
        new TestCase(
            new Song[][][] {
                {
                    { new Song("Coding In The Deep", 123),
                        new Song("Someone Like GNU", 99),
                        new Song("Hello World", 98) },
                },
                {
                    { new Song("Ring Of Firewalls", 217) }
                },
                {
                    { new Song("Boolean Rhapsody", 184),
                        new Song("Merge Together", 119),
                        new Song("Hey Queue", 102) }
                }
            },
            5,
            new String[] { "Ring Of Firewalls", "Boolean Rhapsody",
                "Coding In The Deep",
                "Merge Together", "Hey Queue" },
            null),

        // Test with fewer songs than k
        new TestCase(
            new Song[][][] {
                { { new Song("Song A", 100) } },
                { { new Song("Song B", 200) } }
            },
            5,
            new String[] { "Song B", "Song A" },
            null),

        // Test with exact k songs
        new TestCase(
            new Song[][][] {
                { { new Song("Song A", 100) } },
                { { new Song("Song B", 200) } },
                { { new Song("Song C", 300) } }
            },
            3,
            new String[] { "Song C", "Song B", "Song A" },
            null),

        // Test with ties in play counts
        new TestCase(
            new Song[][][] {
                { { new Song("Song A", 100) } },
                { { new Song("Song B", 100) } },
                { { new Song("Song C", 100) } },
                { { new Song("Song D", 100) } }
            },
            2,
            null,
            2),

        // Test with empty genres
        new TestCase(
            new Song[0][][],
            3,
            new String[] {},
            null),

        // Test with k=1
        new TestCase(
            new Song[][][] {
                { { new Song("Song A", 50),
                    new Song("Song B", 30) } },
                { { new Song("Song C", 100),
                    new Song("Song D", 80) } },
                { { new Song("Song E", 75) } }
            },
            1,
            new String[] { "Song C" },
            null),

        // Test with descending play counts within genres
        new TestCase(
            new Song[][][] {
                { { new Song("Song A", 300),
                    new Song("Song B", 200),
                    new Song("Song C", 100) } },
                { { new Song("Song D", 250),
                    new Song("Song E", 150),
                    new Song("Song F", 50) } }
            },
            4,
            new String[] { "Song A", "Song D", "Song B", "Song E" },
            null)
    };

    TopKAcrossGenres solution = new TopKAcrossGenres();
    for (TestCase test : tests) {
      List<List<Song>> genresList = Arrays.stream(test.genres())
          .map(genre -> Arrays.stream(genre[0])
              .collect(Collectors.toList()))
          .collect(Collectors.toList());

      List<String> got = solution.solve(genresList, test.k());

      if (test.wantLength() != null) {
        if (got.size() != test.wantLength()) {
          throw new RuntimeException(String.format(
              "\nsolve(): got length %d, want length %d\n",
              got.size(), test.wantLength()));
        }
      } else {
        List<String> want = Arrays.asList(test.want());
        if (!got.equals(want)) {
          throw new RuntimeException(String.format(
              "\nsolve(): got: %s, want: %s\n",
              got, want));
        }
      }
    }
  }
}

public class P13_06_MostListenedAcrossGenres {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
