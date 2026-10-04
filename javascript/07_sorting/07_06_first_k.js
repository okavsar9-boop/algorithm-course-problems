// 7.6 - First K
// Run: node 07_06_first_k.js

function firstKSorting(arr, k) {
  const sorted = arr.sort((a, b) => a - b);
  return sorted.slice(0, k);
}

function firstKMinHeap(arr, k) {
  const heap = new Heap((x, y) => x < y, arr);
  const result = [];
  for (let i = 0; i < k; i++) {
    result.push(heap.pop());
  }
  return result;
}

class Heap {
  // A binary heap implementation that can act as either min-heap or max-heap.
  // By default, it creates a min-heap (the smallest element has highest priority).
  // For max-heap behavior, provide a custom 'higherPriority' function.
  // higherPriority: Function that returns True if x has higher priority than y.
  // heap:           Optional list of initial elements to heapify.
  constructor(higherPriority = (x, y) => x < y, heap = null) {
    this.higherPriority = higherPriority;
    this.heap = [];
    if (heap) {
      this.heap = [...heap];
      this.heapify();
    }
  }

  // Returns the number of elements in the heap.
  size() {
    return this.heap.length;
  }

  // Returns the highest priority element without removing it.
  top() {
    if (this.heap.length === 0) {
      return null;
    }
    return this.heap[0];
  }

  // Adds an element to the heap.
  push(elem) {
    this.heap.push(elem);
    this._bubbleUp(this.heap.length - 1);
  }

  // Removes and returns the highest priority element.
  pop() {
    if (this.heap.length === 0) {
      return null;
    }

    const top = this.heap[0];
    if (this.heap.length === 1) {
      this.heap = [];
      return top;
    }

    // Move last element to root and bubble down
    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap.pop();
    this._bubbleDown(0);

    return top;
  }

  // Converts an array into a valid heap in O(n) time.
  heapify() {
    for (let idx = Math.floor(this.heap.length / 2); idx >= 0; idx--) {
      this._bubbleDown(idx);
    }
  }

  // Get parent index.
  _parent(idx) {
    if (idx === 0) {
      return -1; // The root has no parent.
    }
    return Math.floor((idx - 1) / 2);
  }

  // Get left child index.
  _leftChild(idx) {
    return 2 * idx + 1;
  }

  // Get right child index.
  _rightChild(idx) {
    return 2 * idx + 2;
  }

  // Move element up until heap property is restored.
  _bubbleUp(idx) {
    if (idx === 0) {
      return;
    }

    const parentIdx = this._parent(idx);
    if (
      parentIdx >= 0 &&
      this.higherPriority(this.heap[idx], this.heap[parentIdx])
    ) {
      [this.heap[idx], this.heap[parentIdx]] = [
        this.heap[parentIdx],
        this.heap[idx],
      ];
      this._bubbleUp(parentIdx);
    }
  }

  // Move element down until heap property is restored.
  _bubbleDown(idx) {
    const leftIdx = this._leftChild(idx);
    const isLeaf = leftIdx >= this.heap.length;
    if (isLeaf) {
      return;
    }

    // Find child with higher priority
    let childIdx = leftIdx;
    const rightIdx = this._rightChild(idx);
    if (
      rightIdx < this.heap.length &&
      this.higherPriority(this.heap[rightIdx], this.heap[leftIdx])
    ) {
      childIdx = rightIdx;
    }

    // Swap with child if it has higher priority
    if (this.higherPriority(this.heap[childIdx], this.heap[idx])) {
      [this.heap[idx], this.heap[childIdx]] = [
        this.heap[childIdx],
        this.heap[idx],
      ];
      this._bubbleDown(childIdx);
    }
  }
}

function firstKMaxHeap(arr, k) {
  const maxHeap = new Heap((x, y) => x > y);
  for (const num of arr) {
    maxHeap.push(num);
    if (maxHeap.size() > k) {
      maxHeap.pop();
    }
  }

  const result = [];
  while (maxHeap.size() > 0) {
    result.push(maxHeap.pop());
  }
  return result;
}

function firstKQuickselect(arr, k) {
  if (arr.length === 0) {
    return [];
  }
  const kthVal = quickselect(arr, k);
  return arr.filter((x) => x <= kthVal);
}

function partition(arr) {
  const pivot = arr[Math.floor(Math.random() * arr.length)];
  const smaller = [];
  const equal = [];
  const larger = [];
  for (const x of arr) {
    if (x < pivot) {
      smaller.push(x);
    } else if (x === pivot) {
      equal.push(x);
    } else {
      larger.push(x);
    }
  }
  return [smaller, equal, larger];
}

function quickselect(arr, k) {
  const [smaller, equal, larger] = partition(arr);
  const S = smaller.length;
  const E = equal.length;

  if (k <= S) {
    return quickselect(smaller, k);
  } else if (k <= S + E) {
    return equal[0];
  } else {
    return quickselect(larger, k - S - E);
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [[15, 4, 13, 8, 10, 5, 2, 20, 3, 9, 11, 27], 5, [2, 3, 4, 5, 8]],
    // Edge case - k = 1
    [[5, 2, 1, 3, 4], 1, [1]],
    // Edge case - k = length of array
    [[3, 1, 2], 3, [1, 2, 3]],
    // Edge case - array of length 1
    [[42], 1, [42]],
    // Reverse sorted array
    [[5, 4, 3, 2, 1], 4, [1, 2, 3, 4]],
    // Already sorted array
    [[1, 2, 3, 4, 5], 3, [1, 2, 3]],
    // Edge case - empty array
    [[], 0, []],
    // Array with negative numbers
    [[-3, -1, -4, -2], 3, [-4, -3, -2]],
    // Mix of positive and negative
    [[-5, 3, -2, 8, -1], 4, [-5, -2, -1, 3]],
    // Large numbers
    [[10 ** 9, -(10 ** 9), 0], 2, [-(10 ** 9), 0]],
  ];

  const solutions = [
    ["firstKSorting", firstKSorting],
    ["firstKMaxHeap", firstKMaxHeap],
    ["firstKMinHeap", firstKMinHeap],
    ["firstKQuickselect", firstKQuickselect],
  ];

  for (const [name, solution] of solutions) {
    for (const [arr, k, want] of tests) {
      const got = solution([...arr], k);
      const gotSorted = [...got].sort((a, b) => a - b);
      const wantSorted = [...want].sort((a, b) => a - b);
      if (JSON.stringify(gotSorted) !== JSON.stringify(wantSorted)) {
        throw new Error(
          `\n${name}(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want} (in any order)\n`,
        );
      }
    }
  }
}

runTests();
