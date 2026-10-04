// 8.1 - Compress Array
// Run: node 08_01_compress_array.js

function compressArray(arr) {
  const stack = [];
  for (let num of arr) {
    while (stack.length && stack[stack.length - 1] === num) {
      num += stack.pop();
    }
    stack.push(num);
  }
  return stack;
}


function runTests() {
  const tests = [
    [
      [8, 4, 2, 2, 2, 4],
      [16, 2, 4],
    ],
    [[4, 4, 4, 4], [16]],
    [
      [1, 2, 3, 4],
      [1, 2, 3, 4],
    ],
    [[], []],
    [[0, 0, 0, 0], [0]],
  ];
  for (const [arr, want] of tests) {
    const got = compressArray(arr);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ncompressArray(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
