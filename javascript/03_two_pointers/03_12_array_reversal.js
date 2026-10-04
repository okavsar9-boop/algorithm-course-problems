// 3.12 - Array Reversal
// Run: node 03_12_array_reversal.js

function reverse(arr) {
  let l = 0,
  r = arr.length - 1;
  while (l < r) {
    [arr[l], arr[r]] = [arr[r], arr[l]];
    l++;
    r--;
  }
}


function runTests() {
  const tests = [
  // Test cases
  [Array.from("hello"), Array.from("olleh")],
  [Array.from(""), Array.from("")],
  [Array.from("a"), Array.from("a")],
  [Array.from("ab"), Array.from("ba")],
  [Array.from("abc"), Array.from("cba")],
  [Array.from("abcd"), Array.from("dcba")],
  ];
  for (const [arr, want] of tests) {
    const arrCopy = [...arr]; // Make a copy since reverse modifies in place
    reverse(arrCopy);
    if (JSON.stringify(arrCopy) !== JSON.stringify(want)) {
      throw new Error(
      `\nreverse(${JSON.stringify(arr)}): got: ${JSON.stringify(arrCopy)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
