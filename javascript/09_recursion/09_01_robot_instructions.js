// 9.1 - Robot Instructions
// Run: node 09_01_robot_instructions.js

function moves(seq) {
  const res = [];

  function movesRec(pos) {
    if (pos === seq.length) {
      return;
    }
    if (seq[pos] === "2") {
      movesRec(pos + 1);
      movesRec(pos + 2);
    } else {
      res.push(seq[pos]);
      movesRec(pos + 1);
    }
  }

  movesRec(0);
  return res.join("");
}


function runTests() {
  const tests = [
    // Example 1 from book
    ["LL", "LL"],
    // Example 2 from book
    ["2LR", "LRR"],
    // Example 3 from book
    ["2L", "L"],
    // Example 4 from book
    ["22LR", "LRRLR"],
    // Example 5 from book
    ["LL2R2L", "LLRLL"],
    // Edge case - empty string
    ["", ""],
    // Edge case - single character
    ["L", "L"],
    // Multiple 2s in a row
    ["2222LR", "LRRLRLRRLRRLR"],
  ];

  for (const [seq, want] of tests) {
    const got = moves(seq);
    if (got !== want) {
      throw new Error(`\nmoves(${seq}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
