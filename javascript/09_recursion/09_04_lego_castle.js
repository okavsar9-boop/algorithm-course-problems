// 9.4 - Lego Castle
// Run: node 09_04_lego_castle.js

function blocksRec(n) {
  if (n === 1) {
    return 1;
  }

  function roof(i) {
    if (i === 1) {
      return 1;
    }
    return roof(i - 1) * 2 + 1;
  }

  return blocksRec(n - 1) * 2 + roof(n);
}

function blocksMemoized(n) {
  const memo = new Map();

  function roof(i) {
    if (i === 1) {
      return 1;
    }
    if (memo.has(i)) {
      return memo.get(i);
    }
    const result = roof(i - 1) * 2 + 1;
    memo.set(i, result);
    return result;
  }

  function blocksRec(n) {
    if (n === 1) {
      return 1;
    }
    return blocksRec(n - 1) * 2 + roof(n);
  }

  return blocksRec(n);
}

function blocksIterative(n) {
  let blocks = 1;
  for (let i = 2; i <= n; i++) {
    const roof = 2 ** i - 1;
    blocks = blocks * 2 + roof;
  }
  return blocks;
}

function blocksMath(n) {
  return n * 2 ** n - (2 ** n - 1);
}


function runTests() {
  const tests = [
    [1, 1],
    [2, 5],
    [3, 17],
    [4, 49],
    [5, 129],
    [6, 321],
    [7, 769],
    [8, 1793],
    [9, 4097],
    [10, 9217],
  ];

  for (const [n, want] of tests) {
    const gotRec = blocksRec(n);
    const gotMemoized = blocksMemoized(n);
    const gotIterative = blocksIterative(n);
    const gotMath = blocksMath(n);
    if (gotRec !== want) {
      throw new Error(`\nblocksRec(${n}): got: ${gotRec}, want: ${want}\n`);
    }
    if (gotMemoized !== want) {
      throw new Error(`\nblocksMemoized(${n}): got: ${gotMemoized}, want: ${want}\n`);
    }
    if (gotIterative !== want) {
      throw new Error(`\nblocksIterative(${n}): got: ${gotIterative}, want: ${want}\n`);
    }
    if (gotMath !== want) {
      throw new Error(`\nblocksMath(${n}): got: ${gotMath}, want: ${want}\n`);
    }
  }
}

runTests();
