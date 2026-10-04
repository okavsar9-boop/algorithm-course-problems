// 3.16 - Dutch Flag Problem
// Run: node 03_16_dutch_flag_problem.js

function sortColors(arr) {
  // Count occurrences of each color
  const rCount = arr.filter((c) => c === "R").length;
  const wCount = arr.filter((c) => c === "W").length;

  // Rewrite array with the right number of each color
  let i = 0;
  for (let j = 0; j < rCount; j++) {
    arr[i] = "R";
    i++;
  }
  for (let j = 0; j < wCount; j++) {
    arr[i] = "W";
    i++;
  }
  while (i < arr.length) {
    arr[i] = "B";
    i++;
  }
}


function runTests() {
  const tests = [
  // Example from the book
  [Array.from("RWBBWRW"), Array.from("RRWWWBB")],
  // Additional test cases
  [[], []],
  [Array.from("R"), Array.from("R")],
  [Array.from("W"), Array.from("W")],
  [Array.from("B"), Array.from("B")],
  [Array.from("RW"), Array.from("RW")],
  [Array.from("WR"), Array.from("RW")],
  [Array.from("RWB"), Array.from("RWB")],
  [Array.from("RRRWWBBB"), Array.from("RRRWWBBB")],
  [Array.from("BBBWWRRR"), Array.from("RRRWWBBB")],
  ];
  for (const [arr, want] of tests) {
    const arrCopy = [...arr]; // Make a copy since sortColors modifies in place
    sortColors(arrCopy);
    if (JSON.stringify(arrCopy) !== JSON.stringify(want)) {
      throw new Error(
      `\nsortColors(${JSON.stringify(arr)}): got: ${JSON.stringify(arrCopy)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
