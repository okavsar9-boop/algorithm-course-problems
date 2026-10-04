// 16.6 - Minimum Steps to One
// Run: node 16_06_minimum_steps_to_one.js

function minimumStepsToOne(n) {
  const memo = new Map();

  function numSteps(i) {
    if (i === 1) {
      return 0;
    }
    if (memo.has(i)) {
      return memo.get(i);
    }
    let steps = numSteps(i - 1);
    if (i % 2 === 0) {
      steps = Math.min(steps, numSteps(i / 2));
    }
    if (i % 3 === 0) {
      steps = Math.min(steps, numSteps(i / 3));
    }
    memo.set(i, 1 + steps);
    return memo.get(i);
  }

  return numSteps(n);
}


function runTests() {
  const tests = [
    [10, 3],
    [1, 0],
    [15, 4],
    [6, 2],
    [7, 3],
    [100, 7], // larger test case
  ];
  for (const [n, want] of tests) {
    const got = minimumStepsToOne(n);
    if (got !== want) {
      throw new Error(
        `\nminimumStepsToOne(${n}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
