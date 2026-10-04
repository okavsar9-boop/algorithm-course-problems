# 5.11 - Min Pages Per Day
# Run: python3 05_11_min_pages_per_day.py

import math

def min_pages_per_day(page_counts, days):

  # How many days it takes to finish the book with a given daily page limit.
  def days_to_finish(daily_limit):
    d = 0
    for pages in page_counts:
      # Ceiling division to handle leftover pages.
      d += math.ceil(pages / daily_limit)
    return d

  # Defines a transition point over the range of # of pages per day (daily_limit).
  # In the 'before' region, the daily limit is not enough to finish the book in time.
  # In the 'after' region, the daily limit is enough to finish the book in time.
  def is_before(daily_limit):
    return days_to_finish(daily_limit) > days

  l = 0
  # In case we have more days than max pages in any chapter,
  # we might need to read as little as 1 page per day.
  r = max(page_counts)
  
  # Binary search for the transition point from 'before' to 'after' region.
  while r - l > 1:
    mid = (l + r) // 2
    if is_before(mid):
      l = mid
    else:
      r = mid

  # Return the first value in the 'after' region, i.e., the smallest daily limit
  # that allows us to finish the book in time.
  return r


def run_tests():
  tests = [
      # Example from book
      ([20, 15, 17, 10], 5, 17),

      ([20, 15, 17, 10], 14, 5),
      ([20, 15, 17, 10], 17, 4),
      # Edge case - single chapter
      ([10], 5, 2),
      # Edge case - days = chapters
      ([1, 2, 3], 3, 3),
      # Edge case - more days than max chapter pages
      ([20], 21, 1)
  ]

  for page_counts, days, want in tests:
    got = min_pages_per_day(page_counts, days)
    assert got == want, f"\nmin_pages_per_day({page_counts}, {days}): got: {        got}, want: {want}\n"

run_tests()
