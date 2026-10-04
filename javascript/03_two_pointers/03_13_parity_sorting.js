// 3.13 - Parity Sorting
// Run: node 03_13_parity_sorting.js

function sortEven(arr) {
  let l = 0,
    r = arr.length - 1;
  while (l < r) {
    if (arr[l] % 2 === 0) {
      l++;
    } else if (arr[r] % 2 === 1) {
      r--;
    } else {
      [arr[l], arr[r]] = [arr[r], arr[l]];
      l++;
      r--;
    }
  }
}


function isValidSolution(arr, original) {

  // Check that we have the same elements
  if (
  JSON.stringify([...arr].sort()) !== JSON.stringify([...original].sort())
  ) {
    return false;
  }

  // Find the boundary between even and odd numbers
  let boundary = 0;
  while (boundary < arr.length && arr[boundary] % 2 === 0) {
    boundary++;
  }

  // Check that all numbers before boundary are even
  // and all numbers after are odd
  for (let i = 0; i < boundary; i++) {
    if (arr[i] % 2 !== 0) {
      return false;
    }
  }
  for (let i = boundary; i < arr.length; i++) {
    if (arr[i] % 2 !== 1) {
      return false;
    }
  }
  return true;
}

function runTests() {
  const tests = [
  // Example 1 from the book
  [
  [1, 2, 3, 4, 5],
  [2, 4, 1, 3, 5],
  ],
  // Example 2 from the book
  [
  [5, 1, 3, 1, 5],
  [5, 1, 3, 1, 5],
  ],
  // Additional test cases
  [[], []],
  [[1], [1]],
  [[2], [2]],
  [
  [1, 2],
  [2, 1],
  ],
  [
  [2, 1],
  [2, 1],
  ],
  [
  [1, 3, 2, 4],
  [2, 4, 1, 3],
  ],
  ];
  for (const [arr, exampleSolution] of tests) {
    const arrCopy = [...arr]; // Make a copy since sortEven modifies in place
    sortEven(arrCopy);
    if (!isValidSolution(arrCopy, arr)) {
      throw new Error(
      `\nsortEven(${JSON.stringify(arr)}): got: ${JSON.stringify(arrCopy)}, example solution: ${JSON.stringify(exampleSolution)}\n`,
      );
    }
  }
}

runTests();
