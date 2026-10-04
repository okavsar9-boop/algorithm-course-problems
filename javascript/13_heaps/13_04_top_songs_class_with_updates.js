// 13.4 - Top Songs Class With Updates
// Run: node 13_04_top_songs_class_with_updates.js

class TopSongs {
  constructor(k) {
    this.k = k;
    // Will store [plays, title] pairs with custom comparator for max heap
    this.maxHeap = new Heap((a, b) => a[0] > b[0]);
    this.totalPlays = new Map();
  }

  registerPlays(title, plays) {
    let newTotalPlays = plays;
    if (this.totalPlays.has(title)) {
      newTotalPlays += this.totalPlays.get(title);
    }
    this.totalPlays.set(title, newTotalPlays);

    this.maxHeap.push([newTotalPlays, title]);
  }

  topK() {
    const topSongs = [];
    while (topSongs.length < this.k && this.maxHeap.size() > 0) {
      const [plays, title] = this.maxHeap.pop();
      if (this.totalPlays.get(title) === plays) {
        // Not stale
        topSongs.push(title);
      }
    }

    // Restore the max-heap
    for (const title of topSongs) {
      this.maxHeap.push([this.totalPlays.get(title), title]);
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
  function setsEqual(a, b) {
    const sa = new Set(a);
    const sb = new Set(b);
    if (sa.size !== sb.size) return false;
    for (const x of sa) {
      if (!sb.has(x)) return false;
    }
    return true;
  }

  // Example from the book
  let s = new TopSongs(3);
  s.registerPlays("Boolean Rhapsody", 100);
  s.registerPlays("Boolean Rhapsody", 193); // Total 293
  s.registerPlays("Coding In The Deep", 75);
  s.registerPlays("Coding In The Deep", 75); // Total 150
  s.registerPlays("All About That Base Case", 200);
  s.registerPlays("All About That Base Case", 90); // Total 290
  s.registerPlays("All About That Base Case", 1); // Total 291
  s.registerPlays("Here Comes The Bug", 223);
  s.registerPlays("Oops! I Broke Prod Again", 274);
  s.registerPlays("All the Single Brackets", 132);
  let got = s.topK();
  let want = [
    "All About That Base Case",
    "Boolean Rhapsody",
    "Oops! I Broke Prod Again",
  ];
  if (!setsEqual(got, want)) {
    throw new Error(`\ntopK(): got: ${got}, want: ${want}\n`);
  }

  // Additional test cases
  // Test with fewer songs than k
  s = new TopSongs(5);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 200);
  got = s.topK();
  want = ["Song A", "Song B"];
  if (!setsEqual(got, want)) {
    throw new Error(`\ntopK() with fewer songs than k: got: ${got}, ` +
      `want: ${want}\n`);
  }

  // Test with exact k songs
  s = new TopSongs(3);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 200);
  s.registerPlays("Song C", 300);
  got = s.topK();
  want = ["Song A", "Song B", "Song C"];
  if (!setsEqual(got, want)) {
    throw new Error(`\ntopK() with exactly k songs: got: ${got}, ` +
      `want: ${want}\n`);
  }

  // Test with ties in play counts
  s = new TopSongs(2);
  s.registerPlays("Song A", 100);
  s.registerPlays("Song B", 100);
  s.registerPlays("Song C", 100);
  s.registerPlays("Song D", 100);
  got = s.topK();
  if (got.length !== 2) {
    throw new Error(
      `\ntopK() with tied play counts: got length ${got.length}, want length 2\n`,
    );
  }
}

runTests();
