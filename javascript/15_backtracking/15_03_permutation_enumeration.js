// 15.3 - Permutation Enumeration
// Run: node 15_03_permutation_enumeration.js

function generatePermutations(arr) {
  const res = [];
  const perm = [...arr];

  function visit(i) {
    if (i === perm.length - 1) {
      res.push([...perm]);
      return;
    }
    for (let j = i; j < perm.length; j++) {
      [perm[i], perm[j]] = [perm[j], perm[i]]; // Pick perm[j]
      visit(i + 1);
      [perm[i], perm[j]] = [perm[j], perm[i]]; // Cleanup work: undo change
    }
  }

  visit(0);
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      ["x", "y", "z"],
      [
        ["x", "y", "z"],
        ["x", "z", "y"],
        ["y", "x", "z"],
        ["y", "z", "x"],
        ["z", "x", "y"],
        ["z", "y", "x"],
      ],
    ],
    // Single element
    [["a"], [["a"]]],
    // Two elements
    [
      ["a", "b"],
      [
        ["a", "b"],
        ["b", "a"],
      ],
    ],
    // Larger set
    [
      ["a", "b", "c"],
      [
        ["a", "b", "c"],
        ["a", "c", "b"],
        ["b", "a", "c"],
        ["b", "c", "a"],
        ["c", "a", "b"],
        ["c", "b", "a"],
      ],
    ],
  ];

  for (const [arr, want] of tests) {
    const got = generatePermutations(arr);
    got.sort();
    want.sort();
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ngeneratePermutations(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
