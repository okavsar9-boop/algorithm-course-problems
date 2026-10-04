# 7.5 - Sort By Publication Year
# Run: python3 07_05_sort_by_publication_year.py

class Book:
  def __init__(self, title, author, page_count, genre, year_published):
    self.title = title
    self.author = author
    self.page_count = page_count
    self.genre = genre
    self.year_published = year_published

def bucket_sort(books):
  if not books: return []
  min_year = min(book.year_published for book in books)
  max_year = max(book.year_published for book in books)
  buckets = [[] for _ in range(max_year - min_year + 1)]
  for book in books:
    buckets[book.year_published - min_year].append(book)
  res = []
  for bucket in buckets:
    for book in bucket:
      res.append(book)
  return res


def run_tests():
  tests = [
    # Example from the book
    ([
      Book("Shadow of Tomorrow", "Elliot Greyson", 350, "Science Fiction", 2020),
      Book("Whispers in the Wind", "Lila Hart", 280, "Romance", 2018),
      Book("Echoes of Eternity", "Mara Vance", 420, "Fantasy", 2018),
      Book("Fragments of Dawn", "Cora Blake", 310, "Mystery", 2019),
      Book("Beneath the Starlit Sky", "Aria Monroe", 270, "Drama", 2020)
    ], [2018, 2018, 2019, 2020, 2020]),
    # Edge case - empty list
    ([], []),
    # Edge case - single book
    ([Book("Solo", "Author", 100, "Genre", 2000)], [2000]),
    # Multiple books with the same year
    ([
      Book("A", "Author1", 100, "Genre", 2000),
      Book("B", "Author2", 200, "Genre", 2000),
    ], [2000, 2000]),
    # Reverse sorted years
    ([
      Book("A", "Author1", 100, "Genre", 2020),
      Book("B", "Author2", 200, "Genre", 2019),
      Book("C", "Author3", 300, "Genre", 2018),
    ], [2018, 2019, 2020]),
    # Large gap between years
    ([
      Book("A", "Author1", 100, "Genre", 1000),
      Book("B", "Author2", 200, "Genre", 2025),
    ], [1000, 2025]),
    # Many books same year
    ([Book(f"Book{i}", f"Author{i}", 100, "Genre", 2000) for i in range(10)],
     [2000] * 10),
  ]

  for books, want_years in tests:
    got = bucket_sort(books)
    got_years = [book.year_published for book in got]
    assert got_years == want_years, f"\nbucket_sort({[b.title for b in books]}): got years: {got_years}, want years: {want_years}\n"
    # Verify that all books are preserved
    assert len(got) == len(books), f"\nbucket_sort: got length {len(got)}, want length {len(books)}\n"
    assert set(b.title for b in got) == set(b.title for b in books), f"\nbucket_sort: some books were lost or duplicated\n"

run_tests()
