// 15.6 - Jumping Numbers
// Run: node 15_06_jumping_numbers.js

function jumpingNumbers(n) {
  const res = [];

  function visit(num) {
    if (num >= n) {
      return;
    }
    res.push(num);
    const lastDigit = num % 10;
    if (lastDigit > 0) {
      visit(num * 10 + (lastDigit - 1));
    }
    if (lastDigit < 9) {
      visit(num * 10 + (lastDigit + 1));
    }
  }

  for (let num = 1; num < 10; num++) {
    visit(num);
  }
  return res.sort((a, b) => a - b);
}


function runTests() {
  const tests = [
    // Example from the book
    [34, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32]],
    // Edge case - n is 1
    [1, []],
    [10, [1, 2, 3, 4, 5, 6, 7, 8, 9]],
    // Larger n
    [50, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32, 34, 43, 45]],
    [
      102,
      [
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21, 23, 32, 34, 43, 45, 54, 56, 65,
        67, 76, 78, 87, 89, 98, 101,
      ],
    ],
  ];
  for (const [n, want] of tests) {
    const got = jumpingNumbers(n);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\njumpingNumbers(${n}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
