// 5.3 - Valley Bottom
// Run: node 05_03_valley_bottom.js

function valleyBottom(arr) {

  function isBefore(i) {
    return i === 0 || arr[i] < arr[i - 1];
  }

  let l = 0,
    r = arr.length - 1;
  if (isBefore(r)) {
    return arr[r];
  }

  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }
  return arr[l];
}


function runTests() {
  const tests = [
    // Example 1 from book
    [[6, 5, 4, 7, 9], 4],
    // Example 2 from book
    [[5, 6, 7], 5],
    // Example 3 from book
    [[7, 6, 5], 5],
    // Edge case - 2 elements
    [[2, 1], 1],
    // Edge case - 3 elements
    [[3, 2, 4], 2],
  ];

  for (const [arr, want] of tests) {
    const got = valleyBottom(arr);
    if (got !== want) {
      throw new Error(
        `\nvalleyBottom(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
