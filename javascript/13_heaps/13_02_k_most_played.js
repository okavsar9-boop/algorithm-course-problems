// 13.2 - K Most Played
// Run: node 13_02_k_most_played.js

function kMostPlayedSort(songs, k) {
  // Sort by plays in descending order, then take first k titles
  const sortedSongs = songs.sort((a, b) => b[1] - a[1]);
  return sortedSongs.slice(0, k).map((song) => song[0]);
}

function kMostPlayedMaxHeap(songs, k) {
  const maxHeap = new Heap((a, b) => a[1] > b[1], songs);
  const res = [];
  for (let i = 0; i < Math.min(k, songs.length); i++) {
    const song = maxHeap.pop();
    res.push(song[0]);
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

function kMostPlayedMinHeap(songs, k) {
  const minHeap = new Heap((a, b) => a[1] < b[1]);
  for (const song of songs) {
    minHeap.push(song);
    if (minHeap.size() > k) {
      minHeap.pop();
    }
  }
  return minHeap.heap.map((song) => song[0]);
}

function quickselect(nums, k) {
  if (nums.length === 1) {
    return nums[0];
  }

  const pivot = nums[Math.floor(Math.random() * nums.length)];
  const larger = [];
  const equal = [];
  const smaller = [];

  for (const x of nums) {
    if (x < pivot) {
      smaller.push(x);
    } else if (x === pivot) {
      equal.push(x);
    } else {
      larger.push(x);
    }
  }

  const S = smaller.length;
  const E = equal.length;
  if (k <= S) {
    return quickselect(smaller, k);
  } else if (k <= S + E) {
    return pivot;
  } else {
    return quickselect(larger, k - S - E);
  }
}

function kMostPlayedQuickselect(songs, k) {
  if (songs.length === 0) {
    return [];
  }

  if (k >= songs.length) {
    return songs.map((song) => song[0]);
  }

  // Extract play counts
  const playCounts = songs.map((song) => song[1]);

  // Find the kth largest play count
  const kthLargestPlays = quickselect(playCounts, songs.length - k);

  // Collect all songs with play counts > kthLargestPlays
  const res = [];
  for (const song of songs) {
    if (song[1] > kthLargestPlays) {
      res.push(song[0]);
    }
  }

  // Add songs with exactly kthLargestPlays until we have k songs
  let remaining = k - res.length;
  if (remaining > 0) {
    for (const song of songs) {
      if (song[1] === kthLargestPlays) {
        res.push(song[0]);
        remaining--;
        if (remaining === 0) {
          break;
        }
      }
    }
  }
  return res;
}


function runTests() {
  const testCases = [
    // Example from the book
    [
      [
        ["All the Single Brackets", 132],
        ["Oops! I Broke Prod Again", 274],
        ["Coding In The Deep", 146],
        ["Boolean Rhapsody", 193],
        ["Here Comes The Bug", 291],
        ["All About That Base Case", 291],
      ],
      3,
      [
        "All About That Base Case",
        "Here Comes The Bug",
        "Oops! I Broke Prod Again",
      ],
    ],

    // Test with fewer songs than k
    [
      [
        ["Song A", 100],
        ["Song B", 200],
      ],
      5,
      ["Song A", "Song B"],
    ],

    // Test with exact k songs
    [
      [
        ["Song A", 100],
        ["Song B", 200],
        ["Song C", 300],
      ],
      3,
      ["Song A", "Song B", "Song C"],
    ],

    // Test with k = 1
    [
      [
        ["Song A", 100],
        ["Song B", 200],
        ["Song C", 300],
      ],
      1,
      ["Song C"],
    ],

    // Test with ties in play counts
    [
      [
        ["Song A", 100],
        ["Song B", 100],
        ["Song C", 200],
        ["Song D", 200],
      ],
      2,
      ["Song C", "Song D"],
    ],

    // Test empty input
    [[], 3, []],
  ];

  // Test all implementations
  const implementations = [
    ["sort", kMostPlayedSort],
    ["maxHeap", kMostPlayedMaxHeap],
    ["minHeap", kMostPlayedMinHeap],
    ["quickselect", kMostPlayedQuickselect],
  ];

  for (const [solutionName, solutionFunc] of implementations) {
    for (const [songs, k, want] of testCases) {
      const got = solutionFunc(songs, k);
      got.sort();
      want.sort();
      if (JSON.stringify(got) !== JSON.stringify(want)) {
        throw new Error(
          `\n${solutionName}(${JSON.stringify(songs)}, ${k}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
        );
      }
    }

    // Also test tie breaking, any possible result is accepted
    const got = solutionFunc(
      [
        ["Song A", 100],
        ["Song B", 100],
      ],
      1,
    );
    if (
      JSON.stringify(got) !== JSON.stringify(["Song A"]) &&
      JSON.stringify(got) !== JSON.stringify(["Song B"])
    ) {
      throw new Error(
        `\n${solutionName}: got: ${JSON.stringify(got)}, want: ["Song A"] or ["Song B"]\n`,
      );
    }
  }
}

runTests();
