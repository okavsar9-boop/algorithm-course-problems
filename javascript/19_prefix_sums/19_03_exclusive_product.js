// 19.3 - Exclusive Product
// Run: node 19_03_exclusive_product.js

function exclusiveProductArray(arr) {
  const m = 10 ** 9 + 7;
  const n = arr.length;
  const prefixProduct = new Array(n).fill(1);
  prefixProduct[0] = arr[0];
  for (let i = 1; i < n; i++) {
    prefixProduct[i] = (prefixProduct[i - 1] * arr[i]) % m;
  }

  const postfixProduct = new Array(n).fill(1);
  postfixProduct[n - 1] = arr[n - 1];
  for (let i = n - 2; i >= 0; i--) {
    postfixProduct[i] = (postfixProduct[i + 1] * arr[i]) % m;
  }

  const res = new Array(n).fill(1);
  res[0] = postfixProduct[1];
  res[n - 1] = prefixProduct[n - 2];
  for (let i = 1; i < n - 1; i++) {
    res[i] = (prefixProduct[i - 1] * postfixProduct[i + 1]) % m;
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [1, 3, 2, 1],
      [6, 2, 3, 6],
    ],
    // Edge case: Contains zero
    [
      [0, 1, 2, 3],
      [6, 0, 0, 0],
    ],
    // Edge case: All ones
    [
      [1, 1, 1, 1],
      [1, 1, 1, 1],
    ],
    // Edge case: Large numbers
    [
      [10000, 10000],
      [10000, 10000],
    ],
  ];

  for (const [arr, want] of tests) {
    const got = exclusiveProductArray(arr);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nexclusiveProductArray(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
