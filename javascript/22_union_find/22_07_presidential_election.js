// 22.7 - Presidential Election
// Run: node 22_07_presidential_election.js

function winner(candidates, votes) {
  const totalVotes = votes.reduce((a, b) => a + b, 0);
  const minHeap = new Heap(
    (x, y) => x[1] < y[1] || (x[1] === y[1] && x[0] > y[0]),
  );

  for (let i = 0; i < candidates.length; i++) {
    if (votes[i] > totalVotes / 2) {
      return candidates[i];
    }
    minHeap.push([candidates[i], votes[i]]);
  }

  while (true) {
    const [_, minVotes] = minHeap.pop();
    let [cand, secondMinVotes] = minHeap.pop();
    let votes = minVotes + secondMinVotes;

    while (minHeap.size() > 0 && minHeap.top()[1] === secondMinVotes) {
      cand = cand < minHeap.top()[0] ? cand : minHeap.top()[0];
      votes += minHeap.top()[1];
      minHeap.pop();
    }

    if (votes > totalVotes / 2) {
      return cand;
    }
    minHeap.push([cand, votes]);
  }
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
    // Example from the book
    [["Ale", "Bloop", "Chip", "Dart", "Zing"], [10, 20, 30, 15, 25], "Dart"],

    // Two parties
    [["Alice", "Bob"], [40, 50], "Bob"],
    [["Alice", "Bob"], [60, 60], "Alice"],

    // Single party
    [["Alice"], [10], "Alice"],

    // Three way tie for second lowest
    [["A", "E", "C", "D"], [20, 5, 5, 5], "A"],
    [["A", "E", "C", "D"], [10, 5, 5, 5], "C"],

    // All parties have equal votes
    [["X", "Y", "Z"], [10, 10, 10], "X"],
  ];

  for (const [candidates, votes, want] of tests) {
    const got = winner(candidates, votes);
    if (got !== want) {
      throw new Error(
        `\nwinner(${JSON.stringify(candidates)}, ${JSON.stringify(votes)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
