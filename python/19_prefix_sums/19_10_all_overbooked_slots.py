# 19.10 - All Overbooked Slots
# Run: python3 19_10_all_overbooked_slots.py

def all_overbooked_slots(slots, bookings, cap):
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

  overbooked_count = 0
  for i in range(n):
    total_bookings = prefix_sum[i] + slots[i]
    if total_bookings > cap:
      overbooked_count += 1
  return overbooked_count


def run_tests():
  tests = [
    # Example from the book
    ([0, 0, 0, 0, 0, 0], [[0, 3, 4], [2, 5, 1], [4, 4, 3]], 5, 0),
    # Edge case: Single slot overbooked
    ([1, 1, 0, 0, 2, 3], [[0, 3, 4], [2, 5, 1], [4, 4, 3]], 4, 5),
    # Edge case: No bookings
    ([1, 1, 1, 1, 1, 1], [], 1, 0),
    # Edge case: All slots overbooked
    ([0, 0, 0, 0, 0, 0], [[0, 5, 6]], 5, 6),
  ]

  for slots, bookings, cap, want in tests:
    got = all_overbooked_slots(slots, bookings, cap)
    assert got == want, f"\nall_overbooked_slots({slots}, {bookings}, {cap}): got: {got}, want: {want}\n"

run_tests()
