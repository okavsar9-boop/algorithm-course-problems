// 13.7 - Make Playlist
// Run: javac P13_07_MakePlaylist.java && java P13_07_MakePlaylist

import java.util.*;
import java.util.function.*;

record Song(String name, String artist) {
}

record HeapEntry(String artist, List<String> songs) {
}

class MakePlaylistHeap {
  public List<String> solve(List<Song> songs) {
    // Group songs by artist
    Map<String, List<String>> artistToSongs = new HashMap<>();
    for (Song song : songs) {
      artistToSongs.computeIfAbsent(song.artist(), k -> new ArrayList<>())
      .add(song.name());
    }

    // Create max heap of (artist, songs)
    PriorityQueue<HeapEntry> maxHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(b.songs().size(), a.songs().size()));

    for (Map.Entry<String, List<String>> entry : artistToSongs.entrySet()) {
      maxHeap.offer(new HeapEntry(entry.getKey(), entry.getValue()));
    }

    List<String> res = new ArrayList<>();
    String lastArtist = "";
    while (!maxHeap.isEmpty()) {
      HeapEntry entry = maxHeap.poll();
      String artist = entry.artist();
      List<String> songList = entry.songs();

      if (!artist.equals(lastArtist)) {
        res.add(songList.get(songList.size() - 1));
        songList.remove(songList.size() - 1);
        lastArtist = artist;
        if (!songList.isEmpty()) {
          maxHeap.offer(new HeapEntry(artist, songList));
        }
      } else {
        // Need to find a different artist
        if (maxHeap.isEmpty()) {
          return new ArrayList<>(); // No valid solution
        }
        HeapEntry entry2 = maxHeap.poll();
        String artist2 = entry2.artist();
        List<String> songList2 = entry2.songs();

        res.add(songList2.get(songList2.size() - 1));
        songList2.remove(songList2.size() - 1);
        lastArtist = artist2;

        // Re-add the artists we popped
        if (!songList2.isEmpty()) {
          maxHeap.offer(new HeapEntry(artist2, songList2));
        }
        maxHeap.offer(new HeapEntry(artist, songList));
      }
    }
    return res;
  }
}

index:  0  1  2  3  4  5  6  7  8  9 ... i-1  i  i+1  i+2 ... n-1
artist: P  ?  P  ?  P  ?  P  ?  P  ? ...  ?   P   ?    ?  ...  ?

index:  0  1  2  3  4  5  6  7  8  9 ... i-1  i  i+1  i+2 ... n-1
artist: P  ?  P  ?  P  ?  P  ?  P  ? ...  ?   P   ?    ?  ...  ?

class MakePlaylistGreedy {
  public List<String> solve(List<Song> songs) {
    if (songs.isEmpty()) {
      return new ArrayList<>();
    }

    // Group songs by artist
    Map<String, List<String>> artistToSongs = new HashMap<>();
    for (Song song : songs) {
      artistToSongs.computeIfAbsent(song.artist(), k -> new ArrayList<>())
      .add(song.name());
    }

    // Find the most popular artist
    String mostPopularArtist = "";
    int maxCount = 0;
    for (Map.Entry<String, List<String>> entry : artistToSongs.entrySet()) {
      if (entry.getValue().size() > maxCount) {
        maxCount = entry.getValue().size();
        mostPopularArtist = entry.getKey();
      }
    }

    // Check if solution is possible
    if (maxCount > Math.ceil(songs.size() / 2.0)) {
      return new ArrayList<>();
    }

    // Initialize result array
    String[] res = new String[songs.size()];

    // Place most popular artist's songs at even indices
    int index = 0;
    for (String song : artistToSongs.get(mostPopularArtist)) {
      res[index] = song;
      index += 2;
    }

    // Continue filling even indices with other artists
    for (Map.Entry<String, List<String>> entry : artistToSongs.entrySet()) {
      if (entry.getKey().equals(mostPopularArtist)) {
        continue;
      }
      for (String song : entry.getValue()) {
        if (index >= songs.size()) {
          index = 1; // Wrap to odd indices
        }
        res[index] = song;
        index += 2;
      }
    }

    return Arrays.asList(res);
  }
}


class RunTests {
  private String validateSolution(List<Song> songs, List<String> got,
  boolean expectedEmpty) {
    Function<String, String> getArtistForSong = songName -> {
      for (Song song : songs) {
        if (song.name().equals(songName)) {
          return song.artist();
        }
      }
      return null;
    };

    if (expectedEmpty) {
      if (!got.isEmpty()) {
        return "Expected empty result, got: " + got;
      }
      return "";
    }

    // Check length
    if (got.size() != songs.size()) {
      return "Expected length " + songs.size() + ", got length " + got.size();
    }

    // Check no consecutive songs by same artist
    for (int i = 1; i < got.size(); i++) {
      String gotArtist = getArtistForSong.apply(got.get(i));
      String prevArtist = getArtistForSong.apply(got.get(i - 1));
      if (gotArtist != null && gotArtist.equals(prevArtist)) {
        return "Consecutive songs by same artist '" + gotArtist +
        "' at indices " + (i - 1) + " and " + i;
      }
    }

    // Check all songs are present
    Set<String> gotSongs = new HashSet<>(got);
    Set<String> expectedSongs = new HashSet<>();
    for (Song song : songs) {
      expectedSongs.add(song.name());
    }
    if (!gotSongs.equals(expectedSongs)) {
      return "Song mismatch. Got: " + gotSongs + ", Expected: " + expectedSongs;
    }

    return "";
  }

  public void runTests() {
    TestCase[] testCases = {
      // Example from the book
      new TestCase(new Song[] {
        new Song("Coding In The Deep", "A Dell"),
        new Song("Hello World", "A Dell"),
        new Song("Someone Like GNU", "A Dell"),
        new Song("Make You Read My Logs", "A Dell"),
        new Song("Hey Queue", "The Bugs"),
        new Song("Here Comes the Bug", "The Bugs"),
        new Song("Merge Together", "The Bugs"),
        new Song("Dirty Data", "Michael JSON"),
        new Song("Man in the Middle Attack", "Michael JSON"),
        new Song("Ring Of Firewall", "Johnny Cache")
      }, false),

      // Test with no songs
      new TestCase(new Song[] {}, false),

      // Test with one song
      new TestCase(new Song[] {
        new Song("Single Song", "Solo Artist")
      }, false),

      // Test with two songs by different artists
      new TestCase(new Song[] {
        new Song("Song A", "Artist 1"),
        new Song("Song B", "Artist 2")
      }, false),

      // Test with two songs by the same artist (impossible)
      new TestCase(new Song[] {
        new Song("Song A", "Artist 1"),
        new Song("Song B", "Artist 1")
      }, true),

      // Test with more songs by one artist than ceiling(n/2)
      new TestCase(new Song[] {
        new Song("Song 1", "Artist 1"),
        new Song("Song 2", "Artist 1"),
        new Song("Song 3", "Artist 1"),
        new Song("Song 4", "Artist 2")
      }, true),

      // Test with exactly ceiling(n/2) songs by one artist (should work)
      new TestCase(new Song[] {
        new Song("Song 1", "Artist 1"),
        new Song("Song 2", "Artist 1"),
        new Song("Song 3", "Artist 1"),
        new Song("Song 4", "Artist 2"),
        new Song("Song 5", "Artist 3")
      }, false)
    };

    MakePlaylistHeap heapSolution = new MakePlaylistHeap();
    MakePlaylistGreedy greedySolution = new MakePlaylistGreedy();

    for (int i = 0; i < testCases.length; i++) {
      List<Song> songs = Arrays.asList(testCases[i].songs());
      boolean expectedEmpty = testCases[i].expectedEmpty();

      List<String> got1 = heapSolution.solve(songs);
      List<String> got2 = greedySolution.solve(songs);

      String error1 = validateSolution(songs, got1, expectedEmpty);
      if (!error1.isEmpty()) {
        throw new RuntimeException("\nTest case " + (i + 1) +
        " failed (heap): " + error1 + "\n");
      }

      String error2 = validateSolution(songs, got2, expectedEmpty);
      if (!error2.isEmpty()) {
        throw new RuntimeException("\nTest case " + (i + 1) +
        " failed (greedy): " + error2 + "\n");
      }
    }
  }
}

public class P13_07_MakePlaylist {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
