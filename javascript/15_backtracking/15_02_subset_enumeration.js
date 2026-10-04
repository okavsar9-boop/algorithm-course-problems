// 15.2 - Subset Enumeration
// Run: node 15_02_subset_enumeration.js

function allSubsets(S) {
  const res = []; // Global list of subsets
  const subset = []; // State of the current partial solution

  function visit(i) {
    if (i === S.length) {
      res.push([...subset]);
      return;
    }
    // Choice 1: pick S[i]
    subset.push(S[i]);
    visit(i + 1);
    subset.pop(); // Cleanup work: undo choice 1

    // Choice 2: skip S[i]
    visit(i + 1);
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
        [],
        ["x"],
        ["y"],
        ["z"],
        ["x", "y"],
        ["x", "z"],
        ["y", "z"],
        ["x", "y", "z"],
      ],
    ],
    // Edge case - empty set
    [[], [[]]],
    // Single element
    [["a"], [[], ["a"]]],
    // Two elements
    [
      ["a", "b"],
      [[], ["a"], ["b"], ["a", "b"]],
    ],
    // Larger set
    [
      ["a", "b", "c", "d"],
      [
        [],
        ["a"],
        ["b"],
        ["c"],
        ["d"],
        ["a", "b"],
        ["a", "c"],
        ["a", "d"],
        ["b", "c"],
        ["b", "d"],
        ["c", "d"],
        ["a", "b", "c"],
        ["a", "b", "d"],
        ["a", "c", "d"],
        ["b", "c", "d"],
        ["a", "b", "c", "d"],
      ],
    ],
  ];

  for (const [S, want] of tests) {
    const got = allSubsets(S);
    got.sort((a, b) => {
      if (a.length !== b.length) return a.length - b.length;
      for (let i = 0; i < a.length; i++) {
        if (a[i] < b[i]) return -1;
        if (a[i] > b[i]) return 1;
      }
      return 0;
    });
    const want_sorted = [...want];
    want_sorted.sort((a, b) => {
      if (a.length !== b.length) return a.length - b.length;
      for (let i = 0; i < a.length; i++) {
        if (a[i] < b[i]) return -1;
        if (a[i] > b[i]) return 1;
      }
      return 0;
    });

    if (JSON.stringify(got) !== JSON.stringify(want_sorted)) {
      throw new Error(
        `\nallSubsets(${JSON.stringify(S)}): got: ${JSON.stringify(got)}, ` +
          `want: ${JSON.stringify(want_sorted)}\n`,
      );
    }
  }
}

runTests();
