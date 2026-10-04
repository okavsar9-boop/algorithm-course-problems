// 13.2 - K Most Played
// Run: javac P13_02_KMostPlayed.java && java P13_02_KMostPlayed

import java.util.*;
import java.util.function.*;

record Song(String title, int plays) {
}

class KMostPlayedSort {
  public List<String> solve(List<Song> songs, int k) {
    // Sort by plays in descending order, then take first k titles
    List<Song> sortedSongs = new ArrayList<>(songs);
    sortedSongs.sort((a, b) -> Integer.compare(b.plays(), a.plays()));

    List<String> result = new ArrayList<>();
    for (int i = 0; i < Math.min(k, sortedSongs.size()); i++) {
      result.add(sortedSongs.get(i).title());
    }
    return result;
  }
}

class KMostPlayedMaxHeap {
  public List<String> solve(List<Song> songs, int k) {
    PriorityQueue<Song> maxHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(b.plays(), a.plays()));

    for (Song song : songs) {
      maxHeap.offer(song);
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < Math.min(k, songs.size()); i++) {
      result.add(maxHeap.poll().title());
    }
    return result;
  }
}

class KMostPlayedMinHeap {
  public List<String> solve(List<Song> songs, int k) {
    PriorityQueue<Song> minHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(a.plays(), b.plays()));

    for (Song song : songs) {
      minHeap.offer(song);
      if (minHeap.size() > k) {
        minHeap.poll();
      }
    }

    List<String> result = new ArrayList<>();
    while (!minHeap.isEmpty()) {
      result.add(minHeap.poll().title());
    }
    return result;
  }
}

class KMostPlayedQuickselect {
  private Random random = new Random();

  private int quickselect(List<Integer> nums, int k) {
    if (nums.size() == 1) {
      return nums.get(0);
    }

    int pivot = nums.get(random.nextInt(nums.size()));
    List<Integer> larger = new ArrayList<>();
    List<Integer> equal = new ArrayList<>();
    List<Integer> smaller = new ArrayList<>();

    for (int x : nums) {
      if (x < pivot) {
        smaller.add(x);
      } else if (x == pivot) {
        equal.add(x);
      } else {
        larger.add(x);
      }
    }

    int S = smaller.size();
    int E = equal.size();

    if (k <= S) {
      return quickselect(smaller, k);
    } else if (k <= S + E) {
      return pivot;
    } else {
      return quickselect(larger, k - S - E);
    }
  }

  public List<String> solve(List<Song> songs, int k) {
    if (songs.isEmpty()) {
      return new ArrayList<>();
    }

    if (k >= songs.size()) {
      List<String> result = new ArrayList<>();
      for (Song song : songs) {
        result.add(song.title());
      }
      return result;
    }

    // Extract play counts
    List<Integer> playCounts = new ArrayList<>();
    for (Song song : songs) {
      playCounts.add(song.plays());
    }

    // Find the kth largest play count
    int kthLargestPlays = quickselect(playCounts, songs.size() - k);

    // Collect all songs with play counts > kthLargestPlays
    List<String> result = new ArrayList<>();
    for (Song song : songs) {
      if (song.plays() > kthLargestPlays) {
        result.add(song.title());
      }
    }

    // Add songs with exactly kthLargestPlays until we have k songs
    int remaining = k - result.size();
    if (remaining > 0) {
      for (Song song : songs) {
        if (song.plays() == kthLargestPlays) {
          result.add(song.title());
          remaining--;
          if (remaining == 0) {
            break;
          }
        }
      }
    }
    return result;
  }
}


class RunTests {
  public void runTests() {
    Object[][] testCases = {
      // Example from the book
      {
        new Song[] {
          new Song("All the Single Brackets", 132),
          new Song("Oops! I Broke Prod Again", 274),
          new Song("Coding In The Deep", 146),
          new Song("Boolean Rhapsody", 193),
          new Song("Here Comes The Bug", 291),
          new Song("All About That Base Case", 291)
        },
        3,
        new String[] {
          "All About That Base Case",
          "Here Comes The Bug",
          "Oops! I Broke Prod Again"
        }
      },

      // Test with fewer songs than k
      {
        new Song[] {
          new Song("Song A", 100),
          new Song("Song B", 200)
        },
        5,
        new String[] { "Song A", "Song B" }
      },

      // Test with exact k songs
      {
        new Song[] {
          new Song("Song A", 100),
          new Song("Song B", 200),
          new Song("Song C", 300)
        },
        3,
        new String[] { "Song A", "Song B", "Song C" }
      },

      // Test with k = 1
      {
        new Song[] {
          new Song("Song A", 100),
          new Song("Song B", 200),
          new Song("Song C", 300)
        },
        1,
        new String[] { "Song C" }
      },

      // Test with ties in play counts
      {
        new Song[] {
          new Song("Song A", 100),
          new Song("Song B", 100),
          new Song("Song C", 200),
          new Song("Song D", 200)
        },
        2,
        new String[] { "Song C", "Song D" }
      },

      // Test empty input
      { new Song[] {}, 3, new String[] {} }
    };

    // Test all implementations
    Object[][] implementations = {
      { "sort", new KMostPlayedSort() },
      { "maxHeap", new KMostPlayedMaxHeap() },
      { "minHeap", new KMostPlayedMinHeap() },
      { "quickselect", new KMostPlayedQuickselect() }
    };

    for (Object[] impl : implementations) {
      String solutionName = (String) impl[0];
      Object solution = impl[1];

      for (Object[] testCase : testCases) {
        List<Song> songs = Arrays.asList((Song[]) testCase[0]);
        int k = (Integer) testCase[1];
        List<String> want = Arrays.asList((String[]) testCase[2]);

        List<String> got = null;
        if (solution instanceof KMostPlayedSort) {
          got = ((KMostPlayedSort) solution).solve(songs, k);
        } else if (solution instanceof KMostPlayedMaxHeap) {
          got = ((KMostPlayedMaxHeap) solution).solve(songs, k);
        } else if (solution instanceof KMostPlayedMinHeap) {
          got = ((KMostPlayedMinHeap) solution).solve(songs, k);
        } else if (solution instanceof KMostPlayedQuickselect) {
          got = ((KMostPlayedQuickselect) solution).solve(songs, k);
        }

        Collections.sort(got);
        Collections.sort(want);

        if (!got.equals(want)) {
          throw new RuntimeException(String.format(
          "\n%s(%s, %d): got: %s, want: %s\n",
          solutionName, songs.toString(), k, got.toString(),
          want.toString()));
        }
      }

      // Also test tie breaking, any possible result is accepted
      List<Song> tieTest = Arrays.asList(
      new Song("Song A", 100),
      new Song("Song B", 100));

      List<String> got = null;
      if (solution instanceof KMostPlayedSort) {
        got = ((KMostPlayedSort) solution).solve(tieTest, 1);
      } else if (solution instanceof KMostPlayedMaxHeap) {
        got = ((KMostPlayedMaxHeap) solution).solve(tieTest, 1);
      } else if (solution instanceof KMostPlayedMinHeap) {
        got = ((KMostPlayedMinHeap) solution).solve(tieTest, 1);
      } else if (solution instanceof KMostPlayedQuickselect) {
        got = ((KMostPlayedQuickselect) solution).solve(tieTest, 1);
      }

      if (!got.equals(Arrays.asList("Song A"))
      && !got.equals(Arrays.asList("Song B"))) {
        throw new RuntimeException(String.format(
        "\n%s: got: %s, want: [Song A] or [Song B]\n",
        solutionName, got.toString()));
      }
    }
  }
}

public class P13_02_KMostPlayed {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
