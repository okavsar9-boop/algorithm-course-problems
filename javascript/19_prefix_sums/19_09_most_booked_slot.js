// 19.9 - Most Booked Slot
// Run: node 19_09_most_booked_slot.js

function mostBookedSlot(slots, bookings) {
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

  let maxBookings = 0,
    maxIndex = -1;
  for (let i = 0; i < n; i++) {
    const totalBookings = prefixSum[i] + slots[i];
    if (totalBookings > maxBookings) {
      maxBookings = totalBookings;
      maxIndex = i;
    }
  }
  return maxIndex;
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
      2,
    ],
    [
      [1, 1, 0, 0, 2, 3],
      [
        [0, 3, 4],
        [2, 5, 1],
        [4, 4, 3],
      ],
      4,
    ],
    // Edge case: No bookings
    [[1, 1, 1, 1, 1, 1], [], 0],
    // Edge case: All slots booked equally
    [[0, 0, 0, 0, 0, 0], [[0, 5, 1]], 0],
  ];

  for (const [slots, bookings, want] of tests) {
    const got = mostBookedSlot(slots, bookings);
    if (got !== want) {
      throw new Error(
        `\nmostBookedSlot(${JSON.stringify(slots)}, ${JSON.stringify(bookings)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
