// 13.1 - Implement a Heap
// Run: node 13_01_implement_a_heap.js

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
  // Test min heap
  const minHeap = new Heap();
  const values = [4, 8, 2, 6, 1, 7, 3, 5];
  for (const val of values) {
    minHeap.push(val);
  }

  // Should pop in ascending order
  const sortedValues1 = [];
  while (minHeap.size() > 0) {
    sortedValues1.push(minHeap.pop());
  }
  if (
    JSON.stringify(sortedValues1) !== JSON.stringify([1, 2, 3, 4, 5, 6, 7, 8])
  ) {
    throw new Error(
      `\nmin_heap popped values: got: ${sortedValues1}, want: [1, 2, 3, 4, 5, 6, 7, 8]\n`,
    );
  }

  // Test max heap
  const maxHeap = new Heap((x, y) => x > y);
  for (const val of values) {
    maxHeap.push(val);
  }

  // Should pop in descending order
  const sortedValues2 = [];
  while (maxHeap.size() > 0) {
    sortedValues2.push(maxHeap.pop());
  }
  if (
    JSON.stringify(sortedValues2) !== JSON.stringify([8, 7, 6, 5, 4, 3, 2, 1])
  ) {
    throw new Error(
      `\nmax_heap popped values: got: ${sortedValues2}, want: [8, 7, 6, 5, 4, 3, 2, 1]\n`,
    );
  }

  // Test heapify
  const heap = new Heap((x, y) => x < y, [4, 8, 2, 6, 1, 7, 3, 5]);
  if (heap.pop() !== 1) {
    throw new Error(`\nheap.pop(): want: 1\n`);
  }
  if (heap.pop() !== 2) {
    throw new Error(`\nheap.pop(): want: 2\n`);
  }
  if (heap.pop() !== 3) {
    throw new Error(`\nheap.pop(): want: 3\n`);
  }
}

runTests();
