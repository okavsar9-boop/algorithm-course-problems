// 7.5 - Sort By Publication Year
// Run: node 07_05_sort_by_publication_year.js

class Book {
  constructor(title, author, pageCount, genre, yearPublished) {
    this.title = title;
    this.author = author;
    this.pageCount = pageCount;
    this.genre = genre;
    this.yearPublished = yearPublished;
  }
}

function bucketSort(books) {
  if (!books.length) return [];
  const minYear = Math.min(...books.map((book) => book.yearPublished));
  const maxYear = Math.max(...books.map((book) => book.yearPublished));
  const buckets = Array.from({ length: maxYear - minYear + 1 }, () => []);
  for (const book of books) {
    buckets[book.yearPublished - minYear].push(book);
  }
  const res = [];
  for (const bucket of buckets) {
    for (const book of bucket) {
      res.push(book);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [
        new Book(
          "Shadow of Tomorrow",
          "Elliot Greyson",
          350,
          "Science Fiction",
          2020,
        ),
        new Book("Whispers in the Wind", "Lila Hart", 280, "Romance", 2018),
        new Book("Echoes of Eternity", "Mara Vance", 420, "Fantasy", 2018),
        new Book("Fragments of Dawn", "Cora Blake", 310, "Mystery", 2019),
        new Book("Beneath the Starlit Sky", "Aria Monroe", 270, "Drama", 2020),
      ],
      [2018, 2018, 2019, 2020, 2020],
    ],
    // Edge case - empty list
    [[], []],
    // Edge case - single book
    [[new Book("Solo", "Author", 100, "Genre", 2000)], [2000]],
    // Multiple books with the same year
    [
      [
        new Book("A", "Author1", 100, "Genre", 2000),
        new Book("B", "Author2", 200, "Genre", 2000),
      ],
      [2000, 2000],
    ],
    // Reverse sorted years
    [
      [
        new Book("A", "Author1", 100, "Genre", 2020),
        new Book("B", "Author2", 200, "Genre", 2019),
        new Book("C", "Author3", 300, "Genre", 2018),
      ],
      [2018, 2019, 2020],
    ],
    // Large gap between years
    [
      [
        new Book("A", "Author1", 100, "Genre", 1000),
        new Book("B", "Author2", 200, "Genre", 2025),
      ],
      [1000, 2025],
    ],
    // Many books same year
    [
      Array.from(
        { length: 10 },
        (_, i) => new Book(`Book${i}`, `Author${i}`, 100, "Genre", 2000),
      ),
      Array(10).fill(2000),
    ],
  ];

  for (const [books, wantYears] of tests) {
    const got = bucketSort(books);
    const gotYears = got.map((book) => book.yearPublished);
    if (JSON.stringify(gotYears) !== JSON.stringify(wantYears)) {
      throw new Error(
        `\nbucketSort(${books.map((b) => b.title)}): got years: ${gotYears}, want years: ${wantYears}\n`,
      );
    }
    // Verify that all books are preserved
    if (got.length !== books.length) {
      throw new Error(
        `\nbucketSort: got length ${got.length}, want length ${books.length}\n`,
      );
    }
    if (
      !(
        JSON.stringify(new Set(got.map((b) => b.title))) ===
        JSON.stringify(new Set(books.map((b) => b.title)))
      )
    ) {
      throw new Error(`\nbucketSort: some books were lost or duplicated\n`);
    }
  }
}

runTests();
