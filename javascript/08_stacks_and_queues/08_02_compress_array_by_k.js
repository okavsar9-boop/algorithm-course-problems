// 8.2 - Compress Array By K
// Run: node 08_02_compress_array_by_k.js

function compressArrayK(arr, k) {
  const stack = [];

  function merge(num) {
    if (!stack.length || stack[stack.length - 1][0] !== num) {
      stack.push([num, 1]);
    } else if (stack[stack.length - 1][1] < k - 1) {
      stack[stack.length - 1][1] += 1;
    } else {
      stack.pop();
      merge(num * k);
    }
  }

  for (let num of arr) {
    merge(num);
  }

  const res = [];
  for (const [num, count] of stack) {
    for (let i = 0; i < count; i++) {
      res.push(num);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    [[1, 9, 9, 3, 3, 3, 4], 3, [1, 27, 4]],
    [[8, 4, 2, 2], 2, [16]],
    [[4, 4, 4, 4], 5, [4, 4, 4, 4]],
    [[], 2, []],
    [[0, 0, 0, 0], 2, [0]],
  ];
  for (const [arr, k, want] of tests) {
    const got = compressArrayK(arr, k);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ncompressArrayK(${JSON.stringify(arr)}, ${k}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
