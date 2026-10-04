// 12.15 - RGB Distances
// Run: node 12_15_rgb_distances.js

function getSources(screen, target) {
  const sources = [];
  for (let i = 0; i < screen.length; i++) {
    for (let j = 0; j < screen[0].length; j++) {
      if (screen[i][j] === target) {
        sources.push([i, j]);
      }
    }
  }
  return sources;
}

function multisourceBfs(screen, sources) {
  const rows = screen.length;
  const cols = screen[0].length;
  const distances = new Map();
  const Q = new Queue();

  // Initialize with sources
  for (const [r, c] of sources) {
    Q.push([r, c]);
    distances.set(`${r},${c}`, 0);
  }

  // BFS
  while (!Q.empty()) {
    const [r, c] = Q.pop();
    for (const [nr, nc] of [
      [r + 1, c],
      [r - 1, c],
      [r, c + 1],
      [r, c - 1],
    ]) {
      if (
        0 <= nr &&
        nr < rows &&
        0 <= nc &&
        nc < cols &&
        !distances.has(`${nr},${nc}`)
      ) {
        distances.set(`${nr},${nc}`, distances.get(`${r},${c}`) + 1);
        Q.push([nr, nc]);
      }
    }
  }

  return distances;
}

function rgbDistances(screen) {
  const rows = screen.length;
  const cols = screen[0].length;
  const output = Array(rows)
    .fill()
    .map(() => Array(cols).fill(0));

  // Map each color to its target
  const targets = { R: "G", G: "B", B: "R" };

  // For each color, do multisource BFS from its target color
  for (const [color, target] of Object.entries(targets)) {
    const sources = getSources(screen, target);
    const distances = multisourceBfs(screen, sources);

    // Fill in distances for cells of current color
    for (let i = 0; i < rows; i++) {
      for (let j = 0; j < cols; j++) {
        if (screen[i][j] === color) {
          output[i][j] = distances.get(`${i},${j}`);
        }
      }
    }
  }

  return output;
}

class QueueNode {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

class Queue {
  constructor() {
    this.head = null;
    this.tail = null;
    this._size = 0;
  }

  empty() {
    return !this.head;
  }

  size() {
    return this._size;
  }

  push(val) {
    const newNode = new QueueNode(val);
    if (this.tail) {
      this.tail.next = newNode;
    }

    this.tail = newNode;
    if (!this.head) {
      this.head = newNode;
    }
    this._size++;
  }

  pop() {
    if (this.empty()) {
      throw new Error("empty queue");
    }
    const val = this.head.val;
    this.head = this.head.next;
    if (!this.head) {
      this.tail = null;
    }
    this._size--;
    return val;
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [
      ["RRRGRB", "BGRGRR", "RRRGRR", "RGRRRR", "GBGRGG"].map((row) =>
        row.split(""),
      ),
      [
        [2, 1, 1, 2, 1, 1],
        [1, 1, 1, 3, 1, 2],
        [2, 1, 1, 4, 1, 2],
        [1, 1, 1, 1, 1, 1],
        [1, 2, 1, 1, 3, 4],
      ],
    ],
    // Single row
    [["RGB"].map((row) => row.split("")), [[1, 1, 2]]],
    // Single column
    [["R", "G", "B"].map((row) => row.split("")), [[1], [1], [2]]],
    // All colors adjacent
    [
      ["RGB", "BGR"].map((row) => row.split("")),
      [
        [1, 1, 1],
        [1, 1, 1],
      ],
    ],
  ];

  for (const [screen, want] of tests) {
    const got = rgbDistances(screen);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nrgbDistances(${JSON.stringify(screen)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
