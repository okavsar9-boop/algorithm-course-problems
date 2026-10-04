// 13.8 - Sum of First K Prime Powers
// Run: node 13_08_sum_of_first_k_prime_powers.js

function sumOfPowers(primes, k) {
  const m = 10 ** 9 + 7;
  // Initialize the heap with the first power of each prime.
  // Each element is a tuple [power, base]
  const elems = [];
  for (const p of primes) {
    elems.push([p, p]);
  }
  const minHeap = new Heap((a, b) => a[0] < b[0], elems);

  let res = 0;
  for (let i = 0; i < k; i++) {
    const [power, base] = minHeap.pop();
    res = (res + power) % m;
    minHeap.push([(power * base) % m, base]);
  }
  return res;
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


function runTests() {
  const tests = [
    // Example 1 from the book
    [[2], 1, 2],
    // Example 2 from the book
    [[5], 3, 155],
    // Example 3 from the book
    [[2, 3], 7, 69],
    // k is 0
    [[2, 3], 0, 0],
    // k < primes.length
    [[5, 7, 11, 13, 17, 19], 4, 36],
    // prime order doesn't matter
    [[19, 17, 13, 11, 7, 5], 4, 36],
  ];
  for (const [primes, n, want] of tests) {
    const got = sumOfPowers(primes, n);
    if (got !== want) {
      throw new Error(
        `\nsumOfPowers(${JSON.stringify(primes)}, ${n}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
