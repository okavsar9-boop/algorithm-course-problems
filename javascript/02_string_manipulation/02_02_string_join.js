// 2.2 - String Join
// Run: node 02_02_string_join.js

function join(arr, s) {
  const res = [];
  for (let i = 0; i < arr.length; i++) {
    if (i !== 0) {
      for (const c of s) {
        res.push(c);
      }
    }
    for (const c of arr[i]) {
      res.push(c);
    }
  }
  return arrayToString(res);
}

function arrayToString(arr) {
  // Function allowed by the problem statement.
  return arr.join("");
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [["join", "by", "space"], " ", "join by space"],
  // Example 2 from the book
  [
  ["b", "", "k", "", "p", "r n", "", "d", "d!!"],
  "ee",
  "beeeekeeeepeer neeeedeed!!",
  ],
  // Edge case - empty arrays
  [[], "x", ""],
  [[], "", ""],
  [[], "long separator", ""],
  // Edge case - single element arrays
  [["a"], "x", "a"],
  [[""], "x", ""],
  [["multiple words"], "x", "multiple words"],
  // two element arrays
  [["a", "b"], "", "ab"],
  [["a", "b"], " ", "a b"],
  [["", ""], ",", ","],
  // Edge case - empty strings in array
  [["", "", ""], ",", ",,"],
  [["hello", "", "world"], " ", "hello  world"],
  // special characters
  [["\n", "\t"], ",", "\n,\t"],
  [["tab", "separated"], "\t", "tab\tseparated"],
  // long separators
  [
  ["short", "strings"],
  "very long separator",
  "shortvery long separatorstrings",
  ],
  // mixed content
  [["123", "abc", "!@#", " "], "|", "123|abc|!@#| "],
  // whitespace handling
  [
  [" leading", "trailing ", " both "],
  "|",
  " leading|trailing | both ",
  ],
  // numbers and special chars
  [["123", "456"], "-", "123-456"],
  [["!@#", "$%^"], "&", "!@#&$%^"],
  ];

  for (const [arr, s, want] of tests) {
    const got = join(arr, s);
    if (got !== want) {
      throw new Error(
      `\njoin(${JSON.stringify(arr)}, ${JSON.stringify(s)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
