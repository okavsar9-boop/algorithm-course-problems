// 3.2 - Smaller Prefixes
// Run: node 03_02_smaller_prefixes.js

function smallerPrefixes(arr) {
  let sp = 0,
  fp = 0;
  let slowSum = 0,
  fastSum = 0;
  while (fp < arr.length) {
    slowSum += arr[sp];
    fastSum += arr[fp] + arr[fp + 1];
    if (slowSum >= fastSum) {
      return false;
    }
    sp++;
    fp += 2;
  }
  return true;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [[1, 2, 2, -1], true],
  // Example 2 from the book
  [[1, 2, -2, 1, 3, 5], false],
  // Additional test cases
  [[0, 3, 7, 12, 10, 5, 0, 1], true],
  [[], true],
  [[1, 2], true],
  [[2, 1], true],
  [[-2, 1, -4, 5, -3, 7], true],
  [[-2, 1, -14, 8, -3, 2], false],
  ];
  for (const [arr, want] of tests) {
    const got = smallerPrefixes(arr);
    if (got !== want) {
      throw new Error(
      `\nsmallerPrefixes(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
