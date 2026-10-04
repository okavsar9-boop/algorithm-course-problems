// 13.5 - Popular Songs Class
// Run: node 13_05_popular_songs_class.js

class Heap {
  // A binary heap that is a min-heap by default; pass higherPriority for a max-heap.
  constructor(higherPriority = (x, y) => x < y, heap = null) {
    this.higherPriority = higherPriority;
    this.heap = [];
    if (heap) {
      this.heap = [...heap];
      this.heapify();
    }
  }

  size() {
    return this.heap.length;
  }

  top() {
    if (this.heap.length === 0) {
      return null;
    }
    return this.heap[0];
  }

  push(elem) {
    this.heap.push(elem);
    this._bubbleUp(this.heap.length - 1);
  }

  pop() {
    if (this.heap.length === 0) {
      return null;
    }

    const top = this.heap[0];
    if (this.heap.length === 1) {
      this.heap = [];
      return top;
    }

    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap.pop();
    this._bubbleDown(0);

    return top;
  }

  heapify() {
    for (let idx = Math.floor(this.heap.length / 2); idx >= 0; idx--) {
      this._bubbleDown(idx);
    }
  }

  _parent(idx) {
    if (idx === 0) {
      return -1;
    }
    return Math.floor((idx - 1) / 2);
  }

  _leftChild(idx) {
    return 2 * idx + 1;
  }

  _rightChild(idx) {
    return 2 * idx + 2;
  }

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

  _bubbleDown(idx) {
    const leftIdx = this._leftChild(idx);
    const isLeaf = leftIdx >= this.heap.length;
    if (isLeaf) {
      return;
    }

    let childIdx = leftIdx;
    const rightIdx = this._rightChild(idx);
    if (
      rightIdx < this.heap.length &&
      this.higherPriority(this.heap[rightIdx], this.heap[leftIdx])
    ) {
      childIdx = rightIdx;
    }

    if (this.higherPriority(this.heap[childIdx], this.heap[idx])) {
      [this.heap[idx], this.heap[childIdx]] = [
        this.heap[childIdx],
        this.heap[idx],
      ];
      this._bubbleDown(childIdx);
    }
  }
}

class PopularSongs {
  constructor() {
    // Max-heap for the lower half
    this.lowerMaxHeap = new Heap((x, y) => x > y);
    // Min-heap for the upper half
    this.upperMinHeap = new Heap();
    this.playCounts = new Map();
  }

  registerPlays(title, plays) {
    this.playCounts.set(title, plays);
    if (!this.upperMinHeap.size() || plays >= this.upperMinHeap.top()) {
      this.upperMinHeap.push(plays);
    } else {
      this.lowerMaxHeap.push(plays);
    }

    // Distribute elements if they are off by more than one
    if (this.lowerMaxHeap.size() > this.upperMinHeap.size()) {
      this.upperMinHeap.push(this.lowerMaxHeap.pop());
    } else if (this.upperMinHeap.size() > this.lowerMaxHeap.size() + 1) {
      this.lowerMaxHeap.push(this.upperMinHeap.pop());
    }
  }

  isPopular(title) {
    if (!this.playCounts.has(title)) {
      return false;
    }
    let median;
    if (this.lowerMaxHeap.size() === this.upperMinHeap.size()) {
      median = (this.upperMinHeap.top() + this.lowerMaxHeap.top()) / 2;
    } else {
      median = this.upperMinHeap.top();
    }
    return this.playCounts.get(title) > median;
  }
}


function runTests() {
  // Example from the book
  let p = new PopularSongs();
  p.registerPlays("Boolean Rhapsody", 193);
  if (p.isPopular("Boolean Rhapsody")) {
    throw new Error("Fail: Boolean Rhapsody");
  }
  p.registerPlays("Coding In The Deep", 140);
  p.registerPlays("All the Single Brackets", 132);
  if (!p.isPopular("Boolean Rhapsody")) {
    throw new Error("Fail: Boolean Rhapsody");
  }
  if (p.isPopular("Coding In The Deep")) {
    throw new Error("Fail: Coding In The Deep");
  }
  if (p.isPopular("All the Single Brackets")) {
    throw new Error("Fail: All the Single Brackets");
  }

  p.registerPlays("All About That Base Case", 291);
  p.registerPlays("Oops! I Broke Prod Again", 274);
  p.registerPlays("Here Comes The Bug", 223);
  if (p.isPopular("Boolean Rhapsody")) {
    throw new Error("Fail: Boolean Rhapsody after more plays");
  }
  if (!p.isPopular("Here Comes The Bug")) {
    throw new Error("Fail: Here Comes The Bug");
  }

  // Additional test cases
  // Test with no songs
  p = new PopularSongs();
  if (p.isPopular("Nonexistent Song")) {
    throw new Error("Fail: nonexistent song");
  }

  // Test with one song
  p.registerPlays("Single Song", 100);
  if (p.isPopular("Single Song")) {
    throw new Error("Fail: single song should not be popular");
  }

  // Test with two songs
  p.registerPlays("Song A", 100);
  p.registerPlays("Song B", 200);
  if (p.isPopular("Song A")) {
    throw new Error("Fail: Song A");
  }
  if (!p.isPopular("Song B")) {
    throw new Error("Fail: Song B");
  }

  // Test with three songs
  p.registerPlays("Song C", 150);
  if (p.isPopular("Song A")) {
    throw new Error("Fail: Song A with three songs");
  }
  if (!p.isPopular("Song B")) {
    throw new Error("Fail: Song B with three songs");
  }
  if (!p.isPopular("Song C")) {
    throw new Error("Fail: Song C with three songs");
  }
}

runTests();
