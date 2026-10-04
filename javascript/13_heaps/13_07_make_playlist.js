// 13.7 - Make Playlist
// Run: node 13_07_make_playlist.js

function makePlaylistHeap(songs) {
  // Group songs by artist
  const artistToSongs = {};
  for (const [song, artist] of songs) {
    if (!(artist in artistToSongs)) {
      artistToSongs[artist] = [];
    }
    artistToSongs[artist].push(song);
  }

  // Use negative length for max heap behavior
  const heap = new Heap((a, b) => a[1].length > b[1].length);
  for (const [artist, songsList] of Object.entries(artistToSongs)) {
    heap.push([artist, songsList]);
  }

  const res = [];
  let lastArtist = null;
  while (heap.size() > 0) {
    let [artist, songList] = heap.pop();
    if (artist !== lastArtist) {
      res.push(songList.pop());
      lastArtist = artist;
      if (songList.length > 0) {
        // If the artist has more songs, re-add it
        heap.push([artist, songList]);
      }
    } else {
      // We need to find a different artist
      if (heap.size() === 0) {
        return []; // No valid solution
      }
      let [artist2, songList2] = heap.pop();
      res.push(songList2.pop());
      lastArtist = artist2;
      // Re-add the artists we popped
      if (songList2.length > 0) {
        heap.push([artist2, songList2]);
      }
      heap.push([artist, songList]);
    }
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

function makePlaylistGreedy(songs) {
  if (songs.length === 0) {
    return [];
  }

  // Group songs by artist
  const artistToSongs = {};
  for (const [song, artist] of songs) {
    if (!(artist in artistToSongs)) {
      artistToSongs[artist] = [];
    }
    artistToSongs[artist].push(song);
  }

  // Find the most popular artist
  let mostPopularArtist = Object.keys(artistToSongs)[0];
  let maxCount = artistToSongs[mostPopularArtist].length;
  for (const [artist, songsList] of Object.entries(artistToSongs)) {
    if (songsList.length > maxCount) {
      maxCount = songsList.length;
      mostPopularArtist = artist;
    }
  }

  // Check if solution is possible
  if (maxCount > Math.ceil(songs.length / 2)) {
    return [];
  }

  // Initialize result array
  const res = new Array(songs.length);

  // Place most popular artist's songs at even indices
  let index = 0;
  for (const song of artistToSongs[mostPopularArtist]) {
    res[index] = song;
    index += 2;
  }

  // Continue filling even indices with other artists
  for (const [artist, songsList] of Object.entries(artistToSongs)) {
    if (artist === mostPopularArtist) {
      continue;
    }
    for (const song of songsList) {
      if (index >= songs.length) {
        index = 1; // Wrap to odd indices
      }
      res[index] = song;
      index += 2;
    }
  }

  return res;
}


function runTests() {

  function validateSolution(songs, got, expectedEmpty = false) {

    function getArtistForSong(songName) {
      for (const [song, artist] of songs) {
        if (song === songName) {
          return artist;
        }
      }
      return null;
    }

    if (expectedEmpty) {
      if (got.length !== 0) {
        return `Expected empty result, got: ${JSON.stringify(got)}`;
      }
      return "";
    }

    // Check length
    if (got.length !== songs.length) {
      return `Expected length ${songs.length}, got length ${got.length}`;
    }

    // Check no consecutive songs by same artist
    for (let i = 1; i < got.length; i++) {
      const gotArtist = getArtistForSong(got[i]);
      const prevArtist = getArtistForSong(got[i - 1]);
      if (gotArtist === prevArtist) {
        return `Consecutive songs by same artist '${gotArtist}' at indices ${i - 1} and ${i}`;
      }
    }

    // Check all songs are present
    const gotSongs = new Set(got);
    const expectedSongs = new Set(songs.map(([song, _]) => song));
    if (
      gotSongs.size !== expectedSongs.size ||
      ![...gotSongs].every((song) => expectedSongs.has(song))
    ) {
      return `Song mismatch. Got: ${[...gotSongs]}, Expected: ${[...expectedSongs]}`;
    }

    return "";
  }

  const testCases = [
    // Example from the book
    [
      [
        ["Coding In The Deep", "A Dell"],
        ["Hello World", "A Dell"],
        ["Someone Like GNU", "A Dell"],
        ["Make You Read My Logs", "A Dell"],
        ["Hey Queue", "The Bugs"],
        ["Here Comes the Bug", "The Bugs"],
        ["Merge Together", "The Bugs"],
        ["Dirty Data", "Michael JSON"],
        ["Man in the Middle Attack", "Michael JSON"],
        ["Ring Of Firewall", "Johnny Cache"],
      ],
      false,
    ],

    // Test with no songs
    [[], false],

    // Test with one song
    [[["Single Song", "Solo Artist"]], false],

    // Test with two songs by different artists
    [
      [
        ["Song A", "Artist 1"],
        ["Song B", "Artist 2"],
      ],
      false,
    ],

    // Test with two songs by the same artist (impossible)
    [
      [
        ["Song A", "Artist 1"],
        ["Song B", "Artist 1"],
      ],
      true,
    ],

    // Test with more songs by one artist than ceiling(n/2)
    [
      [
        ["Song 1", "Artist 1"],
        ["Song 2", "Artist 1"],
        ["Song 3", "Artist 1"],
        ["Song 4", "Artist 2"],
      ],
      true,
    ],

    // Test with exactly ceiling(n/2) songs by one artist (should work)
    [
      [
        ["Song 1", "Artist 1"],
        ["Song 2", "Artist 1"],
        ["Song 3", "Artist 1"],
        ["Song 4", "Artist 2"],
        ["Song 5", "Artist 3"],
      ],
      false,
    ],
  ];

  for (let i = 0; i < testCases.length; i++) {
    const [songs, expectedEmpty] = testCases[i];
    const got1 = makePlaylistHeap(songs);
    const got2 = makePlaylistGreedy(songs);

    const error1 = validateSolution(songs, got1, expectedEmpty);
    if (error1) {
      throw new Error(
        `\nTest case ${i + 1} failed (heap): ${error1}\nInput: ${JSON.stringify(songs)}\nGot: ${JSON.stringify(got1)}\n`,
      );
    }

    const error2 = validateSolution(songs, got2, expectedEmpty);
    if (error2) {
      throw new Error(
        `\nTest case ${i + 1} failed (greedy): ${error2}\nInput: ${JSON.stringify(songs)}\nGot: ${JSON.stringify(got2)}\n`,
      );
    }
  }
}

runTests();
