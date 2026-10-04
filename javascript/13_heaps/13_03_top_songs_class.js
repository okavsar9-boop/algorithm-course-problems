// 13.3 - Top Songs Class
// Run: node 13_03_top_songs_class.js

class TopSongs {
  constructor(k) {
    this.k = k;
    this.minHeap = new Heap((x, y) => x[1] < y[1]);
  }

  registerPlays(title, plays) {
    this.minHeap.push([title, plays]);
    if (this.minHeap.size() > this.k) {
      this.minHeap.pop();
    }
  }

  topK() {
    const topSongs = [];
    for (const [title, _] of this.minHeap.heap) {
      topSongs.push(title);
    }
    return topSongs;
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
  // Example from the book
  let s = new TopSongs(3);
  s.registerPlays("Boolean Rhapsody", 193);
  s.registerPlays("Coding In The Deep", 146);
  let result = s.topK();
  if (
    JSON.stringify(new Set(result)) !==
    JSON.stringify(new Set(["Boolean Rhapsody", "Coding In The Deep"]))
  ) {
    throw new Error("Test failed for TopSongs with initial songs");
  }

  s.registerPlays("All About That Base Case", 291);
  s.registerPlays("Here Comes The Bug", 223);
  s.registerPlays("Oops! I Broke Prod Again", 274);
  s.registerPlays("All the Single Brackets", 132);
  result = s.topK();
  if (
    JSON.stringify(new Set(result)) !==
    JSON.stringify(
      new Set([
        "All About That Base Case",
        "Here Comes The Bug",
        "Oops! I Broke Prod Again",
      ]),
    )
  ) {
    throw new Error("Test failed for TopSongs after more songs");
  }

  // Additional test cases
  // Test with fewer songs than k
  s = new TopSongs(5);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 200);
  result = s.topK();
  if (
    JSON.stringify(new Set(result)) !==
    JSON.stringify(new Set(["Song A", "Song B"]))
  ) {
    throw new Error("Test failed for TopSongs with fewer songs than k");
  }

  // Test with exact k songs
  s = new TopSongs(3);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 200);
  s.registerPlays("Song C", 300);
  result = s.topK();
  if (
    JSON.stringify(new Set(result)) !==
    JSON.stringify(new Set(["Song A", "Song B", "Song C"]))
  ) {
    throw new Error("Test failed for TopSongs with exact k songs");
  }

  // Test with ties in play counts
  s = new TopSongs(2);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 100);
  s.registerPlays("Song C", 100);
  s.registerPlays("Song D", 100);
  result = s.topK();
  if (result.length !== 2) {
    throw new Error("Test failed for TopSongs with ties in play counts");
  }
}

runTests();
