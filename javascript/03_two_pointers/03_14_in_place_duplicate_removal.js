// 3.14 - In-Place Duplicate Removal
// Run: node 03_14_in_place_duplicate_removal.js

function removeDuplicates(arr) {
  let s = 0,
    w = 0;
  while (s < arr.length) {
    const mustKeep = s === 0 || arr[s] !== arr[s - 1];
    if (mustKeep) {
      arr[w] = arr[s];
      w++;
    }
    s++;
  }
  return w;
}


function runTests() {
  const tests = [
    // Example from the book
    [[1, 2, 2, 3, 3, 3, 5], 4, [1, 2, 3, 5]],
    // Additional test cases
    [[], 0, []],
    [[1], 1, [1]],
    [[1, 1], 1, [1]],
    [[1, 2], 2, [1, 2]],
    [[1, 1, 1], 1, [1]],
    [[1, 2, 2, 2, 3], 3, [1, 2, 3]],
  ];
  for (const [arr, wantLen, wantPrefix] of tests) {
    const arrCopy = [...arr]; // Make a copy since removeDuplicates modifies in place
    const gotLen = removeDuplicates(arrCopy);
    if (gotLen !== wantLen) {
      throw new Error(
        `\nremoveDuplicates(${JSON.stringify(arr)}): got length: ${gotLen}, want length: ${wantLen}\n`,
      );
    }
    if (
      JSON.stringify(arrCopy.slice(0, wantLen)) !== JSON.stringify(wantPrefix)
    ) {
      throw new Error(
        `\nremoveDuplicates(${JSON.stringify(arr)}): got prefix: ${JSON.stringify(arrCopy.slice(0, wantLen))}, want prefix: ${JSON.stringify(wantPrefix)}\n`,
      );
    }
  }
}

runTests();
