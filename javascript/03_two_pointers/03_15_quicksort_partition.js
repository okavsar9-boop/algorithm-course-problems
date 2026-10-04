// 3.15 - Quicksort Partition
// Run: node 03_15_quicksort_partition.js

function partition(arr, pivot) {
  // First pass: partition into <= pivot and > pivot
  let l = 0,
  r = arr.length - 1;
  while (l < r) {
    if (arr[l] <= pivot) {
      l++;
    } else if (arr[r] > pivot) {
      r--;
    } else {
      [arr[l], arr[r]] = [arr[r], arr[l]];
      l++;
      r--;
    }
  }

  // Find the boundary between <= pivot and > pivot
  let boundary = 0;
  while (boundary < arr.length && arr[boundary] <= pivot) {
    boundary++;
  }

  // Second pass: partition the <= pivot section into < pivot and = pivot
  l = 0;
  r = boundary - 1;
  while (l < r) {
    if (arr[l] < pivot) {
      l++;
    } else if (arr[r] === pivot) {
      r--;
    } else {
      [arr[l], arr[r]] = [arr[r], arr[l]];
      l++;
      r--;
    }
  }
}


function runTests() {

  function isValidPartition(arr, pivot) {
    // Find boundaries between sections
    let first = 0;
    while (first < arr.length && arr[first] < pivot) {
      first++;
    }
    let second = first;
    while (second < arr.length && arr[second] === pivot) {
      second++;
    }

    // Check that all elements are in their correct sections
    for (let i = 0; i < first; i++) {
      if (arr[i] >= pivot) {
        return false;
      }
    }
    for (let i = first; i < second; i++) {
      if (arr[i] !== pivot) {
        return false;
      }
    }
    for (let i = second; i < arr.length; i++) {
      if (arr[i] <= pivot) {
        return false;
      }
    }
    return true;

  }

  const tests = [
  // Example 1 from the book
  [[1, 7, 2, 3, 3, 5, 3], 4],
  // Example 2 from the book
  [[1, 7, 2, 3, 3, 5, 3], 3],
  // Additional test cases
  [[], 1],
  [[1], 1],
  [[1, 2], 1],
  [[2, 1], 1],
  [[3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5], 4],
  ];
  for (const [arr, pivot] of tests) {
    const arrCopy = [...arr]; // Make a copy since partition modifies in place
    partition(arrCopy, pivot);
    if (!isValidPartition(arrCopy, pivot)) {
      throw new Error(
      `\npartition(${JSON.stringify(arr)}, ${pivot}): got: ${JSON.stringify(arrCopy)}\n`,
      );
    }
  }
}

runTests();
