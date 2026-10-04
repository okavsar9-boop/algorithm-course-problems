// 3.10 - Missing Numbers in Range
// Run: node 03_10_missing_numbers_in_range.js

function missingNumbers(arr, low, high) {
  let p1 = 0; // pointer for arr
  let p2 = low; // pointer for virtual arr2
  const res = [];

  while (p1 < arr.length && p2 <= high) {
    if (arr[p1] < p2) {
      p1++;
    } else if (arr[p1] === p2) {
      p1++;
      p2++;
    } else {
      res.push(p2);
      p2++;
    }
  }

  if (p2 <= high) {
    for (let i = p2; i <= high; i++) {
      res.push(i);
    }
  }
  return res;
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [[6, 9, 12, 15, 18], 9, 13, [10, 11, 13]],
  // Example 2 from the book
  [[], 9, 9, [9]],
  // Example 3 from the book
  [[6, 7, 8, 9], 7, 8, []],
  // Additional test cases
  [[], 1, 5, [1, 2, 3, 4, 5]],
  [[1, 2, 3, 4, 5], 1, 5, []],
  [[1, 3, 5], 1, 5, [2, 4]],
  [[1], 1, 1, []],
  [[2], 1, 3, [1, 3]],
  ];
  for (const [arr, low, high, want] of tests) {
    const got = missingNumbers(arr, low, high);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
      `\nmissingNumbers(${JSON.stringify(arr)}, ${low}, ${high}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
