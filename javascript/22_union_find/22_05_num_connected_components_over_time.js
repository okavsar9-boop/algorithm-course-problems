// 22.5 - Num Connected Components Over Time
// Run: node 22_05_num_connected_components_over_time.js

function numCcsAtTimes(n, edges, times) {
  edges.sort((a, b) => a[2] - b[2]);
  const uf = new UnionFind();
  for (let u = 0; u < n; u++) {
    uf.add(u);
  }
  let numCcs = n;
  let timesI = 0;
  const res = new Array(times.length).fill(0);

  for (const [u, v, time] of edges) {
    while (timesI < times.length && time > times[timesI]) {
      res[timesI] = numCcs;
      timesI++;
    }
    const reprU = uf.find(u);
    const reprV = uf.find(v);
    if (reprU === reprV) {
      continue;
    }
    uf.union(u, v);
    numCcs--;
  }

  while (timesI < times.length) {
    res[timesI] = numCcs;
    timesI++;
  }
  return res;
}

class UnionFind {
  constructor() {
    this.parent = {};
    this.size = {};
  }

  // Assumes x is not already in the UnionFind.
  add(x) {
    this.parent[x] = x;
    this.size[x] = 1;
  }

  // Assumes x is already in the UnionFind.
  find(x) {
    let root = this.parent[x];
    while (this.parent[root] !== root) {
      root = this.parent[root];
    }
    while (x !== root) {
      const parent = this.parent[x];
      this.parent[x] = root;
      x = parent;
    }
    return root;
  }

  // Assumes x and y are already in the UnionFind.
  union(x, y) {
    const reprX = this.find(x);
    const reprY = this.find(y);
    if (reprX === reprY) return; // They are already in the same set.

    if (this.size[reprX] < this.size[reprY]) {
      this.size[reprY] += this.size[reprX];
      this.parent[reprX] = reprY;
    } else {
      this.size[reprX] += this.size[reprY];
      this.parent[reprY] = reprX;
    }
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [
      4,
      [
        [0, 1, 60],
        [0, 3, 180],
        [2, 3, 120],
      ],
      [30, 120, 210],
      [4, 2, 1],
    ],
    // Edge case - no edges
    [3, [], [10, 20], [3, 3]],
    // Edge case - single node
    [1, [], [5], [1]],
    // Multiple edges at same time
    [
      4,
      [
        [0, 1, 10],
        [2, 3, 10],
        [1, 2, 20],
      ],
      [5, 15, 25],
      [4, 2, 1],
    ],
    // All edges after last query time
    [
      3,
      [
        [0, 1, 100],
        [1, 2, 200],
      ],
      [10, 20],
      [3, 3],
    ],
  ];

  for (const [V, edges, times, want] of tests) {
    const got = numCcsAtTimes(V, edges, times);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nnumCcsAtTimes(${V}, ${JSON.stringify(edges)}, ${JSON.stringify(times)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
