// 19.10 - All Overbooked Slots
// Run: node 19_10_all_overbooked_slots.js

function allOverbookedSlots(slots, bookings, cap) {
  const n = slots.length;
  const diff = new Array(n).fill(0);
  for (const [l, r, c] of bookings) {
    diff[l] += c;
    if (r + 1 < n) {
      diff[r + 1] -= c;
    }
  }

  // Recipe 1.
  const prefixSum = new Array(n).fill(0);
  prefixSum[0] = diff[0];
  for (let i = 1; i < n; i++) {
    prefixSum[i] = prefixSum[i - 1] + diff[i];
  }

  let overbookedCount = 0;
  for (let i = 0; i < n; i++) {
    const totalBookings = prefixSum[i] + slots[i];
    if (totalBookings > cap) {
      overbookedCount++;
    }
  }
  return overbookedCount;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [0, 0, 0, 0, 0, 0],
      [
        [0, 3, 4],
        [2, 5, 1],
        [4, 4, 3],
      ],
      5,
      0,
    ],
    // Edge case: Single slot overbooked
    [
      [1, 1, 0, 0, 2, 3],
      [
        [0, 3, 4],
        [2, 5, 1],
        [4, 4, 3],
      ],
      4,
      5,
    ],
    // Edge case: No bookings
    [[1, 1, 1, 1, 1, 1], [], 1, 0],
    // Edge case: All slots overbooked
    [[0, 0, 0, 0, 0, 0], [[0, 5, 6]], 5, 6],
  ];

  for (const [slots, bookings, cap, want] of tests) {
    const got = allOverbookedSlots(slots, bookings, cap);
    if (got !== want) {
      throw new Error(
        `\nallOverbookedSlots(${JSON.stringify(slots)}, ${JSON.stringify(bookings)}, ${cap}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
