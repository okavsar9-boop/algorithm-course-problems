// 5.9 - Min-Subarray-Sum Split
// Run: node 05_09_min_subarray_sum_split.js

// "Is it impossible to split arr into at most k subarrays,

// each with sum <= max_sum?"

function is_before(arr, k, max_sum) {
  const splits_required = get_splits_required(arr, max_sum);
  return splits_required > k;
}

// Returns the minimum number of subarrays with a given maximum sum.

// Assumes that max_sum >= max(arr).

function get_splits_required(arr, max_sum) {
  let splits_required = 1;
  let current_sum = 0;

  for (const num of arr) {
    if (current_sum + num > max_sum) {
      splits_required++;
      current_sum = num; // Start a new subarray with the current number.
    } else {
      current_sum += num;
    }
  }
  return splits_required;
}

function min_subarray_sum_split(arr, k) {
  if (!arr.length) {
    return 0;
  }
  let l = Math.max(...arr);
  let r = arr.reduce((a, b) => a + b, 0);
  if (!is_before(arr, k, l)) {
    return l;
  }
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (is_before(arr, k, mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }
  return r;
}

function min_subarray_sum_split_memoization(arr, k) {
  const n = arr.length;
  const memo = new Map();

  function min_split_rec(i, x) {
    const key = `${i},${x}`;
    if (memo.has(key)) {
      return memo.get(key);
    }

    // Base cases
    if (n - i === x) {
      // Put each element in its own subarray
      memo.set(key, Math.max(...arr.slice(i)));
    } else if (x === 1) {
      // Put all elements in one subarray
      memo.set(
        key,
        arr.slice(i).reduce((a, b) => a + b, 0),
      );
    } else {
      // General case
      let current_sum = 0;
      let res = Infinity;
      for (let p = i; p <= n - x; p++) {
        current_sum += arr[p];
        res = Math.min(res, Math.max(current_sum, min_split_rec(p + 1, x - 1)));
      }
      memo.set(key, res);
    }

    return memo.get(key);
  }

  return min_split_rec(0, k);
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[10, 5, 8, 9, 11], 3, 17],
    // Example 2 from the book
    [[10, 10, 10, 10, 10], 2, 30],
    // Example 2 from the book
    [[9, 12, 13], 3, 13],
    // Edge case - k=1
    [[1, 2, 3], 1, 6],
    // Edge case - k=length
    [[1, 2, 3], 3, 3],
    // Edge case - single element
    [[5], 1, 5],
  ];

  for (const [arr, k, want] of tests) {
    const got = min_subarray_sum_split(arr, k);
    if (got !== want) {
      throw new Error(
        `\nmin_subarray_sum_split(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }

  for (const [arr, k, want] of tests) {
    const got = min_subarray_sum_split_memoization(arr, k);
    if (got !== want) {
      throw new Error(
        `\nmin_subarray_sum_split_memoization(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
