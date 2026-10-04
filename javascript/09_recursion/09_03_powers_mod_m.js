// 9.3 - Powers Mod M
// Run: node 09_03_powers_mod_m.js

function power(a, p, m) {
  if (p === 0) {
    return 1;
  }
  if (p % 2 === 0) {
    const half = power(a, Math.floor(p / 2), m);
    return (half * half) % m;
  }
  return ((a % m) * power(a, p - 1, m)) % m;
}


function runTests() {
  const tests = [
    // Example 1 from book
    [[2, 5, 100], 32],
    // Example 2 from book
    [[2, 5, 30], 2],
    // Edge cases
    [[2, 0, 10], 1],
    [[3, 1, 5], 3],
    [[5, 3, 7], 6],
  ];

  for (const [[a, p, m], want] of tests) {
    const got = power(a, p, m);
    if (got !== want) {
      throw new Error(
        `\npower(${a}, ${p}, ${m}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
