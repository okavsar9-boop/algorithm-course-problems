// 5.4 - 2-Array 2-Sum
// Run: node 05_04_2_array_2_sum.js

function twoArrayTwoSum(sortedArr, unsortedArr) {

  function binarySearch(arr, target) {

    function isBefore(i) {
      return arr[i] < target;
    }

    let l = 0,
      r = arr.length - 1;
    if (arr[l] > target || arr[r] < target) {
      return -1;
    }

    if (arr[l] === target) {
      return l;
    }

    while (r - l > 1) {
      const mid = Math.floor((l + r) / 2);
      if (isBefore(mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }

    if (arr[r] === target) {
      return r;
    }
    return -1;
  }

  for (let i = 0; i < unsortedArr.length; i++) {
    const idx = binarySearch(sortedArr, -unsortedArr[i]);
    if (idx !== -1) {
      return [idx, i];
    }
  }
  return [-1, -1];
}


function runTests() {
  const tests = [
    // Example from book
    [
      [-5, -4, -1, 4, 6, 6, 7],
      [-3, 7, 18, 4, 6],
      [1, 3],
    ],
    // no solution
    [
      [1, 2, 3],
      [1, 2, 3],
      [-1, -1],
    ],
    [[1], [-1], [0, 0]],
    [
      [1, 2],
      [-2, -1],
      [1, 0],
    ],
    [
      [0, 1, 2, 3],
      [3, 2, 1, 0],
      [0, 3],
    ],
  ];

  for (const [sortedArr, unsortedArr, want] of tests) {
    const got = twoArrayTwoSum(sortedArr, unsortedArr);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ntwoArrayTwoSum(${JSON.stringify(sortedArr)}, ${JSON.stringify(unsortedArr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
