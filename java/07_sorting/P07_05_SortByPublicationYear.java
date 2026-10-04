// 7.5 - Sort By Publication Year
// Run: javac P07_05_SortByPublicationYear.java && java P07_05_SortByPublicationYear

import java.util.*;
import java.util.function.*;

class Book {
  public final String title;
  public final String author;
  public final int pageCount;
  public final String genre;
  public final int yearPublished;

  public Book(String title, String author, int pageCount, String genre,
  int yearPublished) {
    this.title = title;
    this.author = author;
    this.pageCount = pageCount;
    this.genre = genre;
    this.yearPublished = yearPublished;
  }

  @Override
  public String toString() {
    return String.format("Book(%s, %d)", title, yearPublished);
  }
}

class BucketSort {
  public List<Book> solve(List<Book> books) {
    if (books.isEmpty()) {
      return new ArrayList<>();
    }

    int minYear = Integer.MAX_VALUE;
    int maxYear = Integer.MIN_VALUE;
    for (Book book : books) {
      minYear = Math.min(minYear, book.yearPublished);
      maxYear = Math.max(maxYear, book.yearPublished);
    }

    int numBuckets = maxYear - minYear + 1;
    List<List<Book>> buckets = new ArrayList<>(numBuckets);
    for (int i = 0; i < numBuckets; i++) {
      buckets.add(new ArrayList<>());
    }

    for (Book book : books) {
      buckets.get(book.yearPublished - minYear).add(book);
    }

    List<Book> result = new ArrayList<>();
    for (List<Book> bucket : buckets) {
      result.addAll(bucket);
    }

    return result;
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        new TestCase(
            new Book[] {
                new Book("Shadow of Tomorrow", "Elliot Greyson", 350,
                    "Science Fiction", 2020),
                new Book("Whispers in the Wind", "Lila Hart", 280, "Romance",
                    2018),
                new Book("Echoes of Eternity", "Mara Vance", 420, "Fantasy",
                    2018),
                new Book("Fragments of Dawn", "Cora Blake", 310, "Mystery",
                    2019),
                new Book("Beneath the Starlit Sky", "Aria Monroe", 270,
                    "Drama", 2020) },
            new int[] { 2018, 2018, 2019, 2020, 2020 }),
        new TestCase(
            new Book[] {},
            new int[] {}),
        new TestCase(
            new Book[] { new Book("Solo", "Author", 100, "Genre", 2000) },
            new int[] { 2000 }),
        new TestCase(
            new Book[] {
                new Book("A", "Author1", 100, "Genre", 2000),
                new Book("B", "Author2", 200, "Genre", 2000) },
            new int[] { 2000, 2000 }),
        new TestCase(
            new Book[] {
                new Book("A", "Author1", 100, "Genre", 2020),
                new Book("B", "Author2", 200, "Genre", 2019),
                new Book("C", "Author3", 300, "Genre", 2018) },
            new int[] { 2018, 2019, 2020 }),
        new TestCase(
            new Book[] {
                new Book("A", "Author1", 100, "Genre", 1000),
                new Book("B", "Author2", 200, "Genre", 2025) },
            new int[] { 1000, 2025 }),
        new TestCase(
            new Book[] {
                new Book("Book0", "Author0", 100, "Genre", 2000),
                new Book("Book1", "Author1", 100, "Genre", 2000),
                new Book("Book2", "Author2", 100, "Genre", 2000),
                new Book("Book3", "Author3", 100, "Genre", 2000),
                new Book("Book4", "Author4", 100, "Genre", 2000),
                new Book("Book5", "Author5", 100, "Genre", 2000),
                new Book("Book6", "Author6", 100, "Genre", 2000),
                new Book("Book7", "Author7", 100, "Genre", 2000),
                new Book("Book8", "Author8", 100, "Genre", 2000),
                new Book("Book9", "Author9", 100, "Genre", 2000) },
            new int[] { 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000,
                2000 })
    };

    BucketSort solution = new BucketSort();
    for (TestCase test : tests) {
      List<Book> got = solution.solve(Arrays.asList(test.books()));
      int[] gotYears = got.stream().mapToInt(b -> b.yearPublished).toArray();
      if (!Arrays.equals(gotYears, test.wantYears())) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            Arrays.asList(test.books()),
            Arrays.toString(gotYears),
            Arrays.toString(test.wantYears())));
      }

      if (got.size() != test.books().length) {
        throw new RuntimeException(String.format(
            "\nsolve length: got: %d, want: %d\n",
            got.size(), test.books().length));
      }

      Set<String> gotTitles = new HashSet<>();
      for (Book book : got)
        gotTitles.add(book.title);
      Set<String> wantTitles = new HashSet<>();
      for (Book book : test.books())
        wantTitles.add(book.title);
      if (!gotTitles.equals(wantTitles)) {
        throw new RuntimeException(
            "\nsolve: some books were lost or duplicated\n");
      }
    }
  }
}

public class P07_05_SortByPublicationYear {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
