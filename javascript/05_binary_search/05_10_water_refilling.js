// 5.10 - Water Refilling
// Run: node 05_10_water_refilling.js

function numRefills(a, b) {
  // "Can we pour 'numPours' times?"
  function isBefore(numPours) {
    return numPours * b <= a;
  }

  // Exponential search (repeated doubling until we find an upper bound).
  let k = 1;
  while (isBefore(k * 2)) {
    k *= 2;
  }

  // Binary search between k and k*2
  let l = k;
  let r = k * 2;
  while (r - l > 1) {
    const gap = r - l;
    const halfGap = gap >> 1; // Bit shift instead of division
    const mid = l + halfGap;
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }
  return l;
}


function runTests() {
  const tests = [
    // Basic cases
    [10, 2, 5],
    [10, 3, 3],
    [10, 4, 2],
    [10, 5, 2],
    // Large numbers
    [1000000, 1, 1000000],
    // Large numbers with multiple refills
    [1000000, 500000, 2],
    // Random cases
    [18, 5, 3],
    [182983, 90, 2033],
  ];

  for (const [a, b, expected] of tests) {
    const result = numRefills(a, b);
    if (result !== expected) {
      throw new Error(
        `\nnumRefills(${a}, ${b}): got ${result}, want ${expected}\n`,
      );
    }
  }
}

runTests();
