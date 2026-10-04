// 13.6 - Most Listened Across Genres
// Run: node 13_06_most_listened_across_genres.js

function topKAcrossGenres(genres, k) {
  const initialElems = new Heap((a, b) => {
    const playsA = genres[a[0]][a[1]][1];
    const playsB = genres[b[0]][b[1]][1];
    return playsA > playsB;
  }); // (genreIndex, songIndex)
  for (let genreIndex = 0; genreIndex < genres.length; genreIndex++) {
    initialElems.push([genreIndex, 0]);
  }

  const topK = [];
  while (topK.length < k && initialElems.size() > 0) {
    const [genreIndex, songIndex] = initialElems.pop();
    const songName = genres[genreIndex][songIndex][0];
    topK.push(songName);

    const nextSongIndex = songIndex + 1;
    if (nextSongIndex < genres[genreIndex].length) {
      initialElems.push([genreIndex, nextSongIndex]);
    }
  }

  return topK;
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
    {
      genres: [
        [
          ["Coding In The Deep", 123],
          ["Someone Like GNU", 99],
          ["Hello World", 98],
        ],
        [["Ring Of Firewalls", 217]],
        [
          ["Boolean Rhapsody", 184],
          ["Merge Together", 119],
          ["Hey Queue", 102],
        ],
      ],
      k: 5,
      want: [
        "Ring Of Firewalls",
        "Boolean Rhapsody",
        "Coding In The Deep",
        "Merge Together",
        "Hey Queue",
      ],
    },

    // Test with fewer songs than k
    {
      genres: [[["Song A", 100]], [["Song B", 200]]],
      k: 5,
      want: ["Song B", "Song A"],
    },

    // Test with exact k songs
    {
      genres: [[["Song A", 100]], [["Song B", 200]], [["Song C", 300]]],
      k: 3,
      want: ["Song C", "Song B", "Song A"],
    },

    // Test with ties in play counts
    {
      genres: [
        [["Song A", 100]],
        [["Song B", 100]],
        [["Song C", 100]],
        [["Song D", 100]],
      ],
      k: 2,
      want_length: 2,
    },

    // Test with empty genres
    {
      genres: [],
      k: 3,
      want: [],
    },

    // Test with k=1
    {
      genres: [
        [
          ["Song A", 50],
          ["Song B", 30],
        ],
        [
          ["Song C", 100],
          ["Song D", 80],
        ],
        [["Song E", 75]],
      ],
      k: 1,
      want: ["Song C"],
    },

    // Test with descending play counts within genres
    {
      genres: [
        [
          ["Song A", 300],
          ["Song B", 200],
          ["Song C", 100],
        ],
        [
          ["Song D", 250],
          ["Song E", 150],
          ["Song F", 50],
        ],
      ],
      k: 4,
      want: ["Song A", "Song D", "Song B", "Song E"],
    },
  ];

  for (const test of tests) {
    const got = topKAcrossGenres(test.genres, test.k);

    if ("want_length" in test) {
      if (got.length !== test.want_length) {
        throw new Error(
          `\ntopKAcrossGenres() with tied play counts: got length ${got.length}, want length ${test.want_length}\n`,
        );
      }
    } else {
      if (JSON.stringify(got) !== JSON.stringify(test.want)) {
        throw new Error(
          `\ntopKAcrossGenres(): got: ${got}, want: ${test.want}\n`,
        );
      }
    }
  }
}

runTests();
