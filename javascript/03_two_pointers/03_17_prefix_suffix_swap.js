// 3.17 - Prefix-Suffix Swap
// Run: node 03_17_prefix_suffix_swap.js

function swapPrefixSuffix(arr) {
  if (arr.length === 0) {
    return;
  }

  const n = arr.length;

  // Reverse the whole array
  let l = 0,
    r = n - 1;
  while (l < r) {
    [arr[l], arr[r]] = [arr[r], arr[l]];
    l++;
    r--;
  }

  // Reverse the last n/3 elements
  l = Math.floor((2 * n) / 3);
  r = n - 1;
  while (l < r) {
    [arr[l], arr[r]] = [arr[r], arr[l]];
    l++;
    r--;
  }

  // Reverse the first 2n/3 elements
  l = 0;
  r = Math.floor((2 * n) / 3) - 1;
  while (l < r) {
    [arr[l], arr[r]] = [arr[r], arr[l]];
    l++;
    r--;
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [Array.from("badreview"), Array.from("reviewbad")],
    // Additional test cases
    [[], []],
    [Array.from("abc"), Array.from("bca")],
    [Array.from("abcdef"), Array.from("cdefab")],
    [Array.from("123456789"), Array.from("456789123")],
    [Array.from("aaabbbccc"), Array.from("bbbcccaaa")],
  ];
  for (const [arr, want] of tests) {
    const arrCopy = [...arr]; // Make a copy since swapPrefixSuffix modifies in place
    swapPrefixSuffix(arrCopy);
    if (JSON.stringify(arrCopy) !== JSON.stringify(want)) {
      throw new Error(
        `\nswapPrefixSuffix(${JSON.stringify(arr)}): got: ${JSON.stringify(arrCopy)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
