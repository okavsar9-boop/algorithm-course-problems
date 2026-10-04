# 19.9 - Most Booked Slot
# Run: python3 19_09_most_booked_slot.py

def most_booked_slot(slots, bookings):
  n = len(slots)
  diff = [0] * n
  for l, r, c in bookings:
    diff[l] += c
    if r + 1 < n:
      diff[r + 1] -= c

  # Recipe 1.
  prefix_sum = [0] * n
  prefix_sum[0] = diff[0]
  for i in range(1, n):
    prefix_sum[i] = prefix_sum[i - 1] + diff[i]

  max_bookings, max_index = 0, -1
  for i in range(n):
    total_bookings = prefix_sum[i] + slots[i]
    if total_bookings > max_bookings:
      max_bookings, max_index = total_bookings, i
  return max_index


def run_tests():
  tests = [
    # Example from the book
    ([0, 0, 0, 0, 0, 0], [[0, 3, 4], [2, 5, 1], [4, 4, 3]], 2),
    ([1, 1, 0, 0, 2, 3], [[0, 3, 4], [2, 5, 1], [4, 4, 3]], 4),
    # Edge case: No bookings
    ([1, 1, 1, 1, 1, 1], [], 0),
    # Edge case: All slots booked equally
    ([0, 0, 0, 0, 0, 0], [[0, 5, 1]], 0),
  ]

  for slots, bookings, want in tests:
    got = most_booked_slot(slots, bookings)
    assert got == want, f"\nmost_booked_slot({slots}, {bookings}): got: {got}, want: {want}\n"

run_tests()
