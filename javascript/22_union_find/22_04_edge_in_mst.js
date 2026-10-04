// 22.4 - Edge In MST
// Run: node 22_04_edge_in_mst.js

function edgeInMst(V, edges, i) {
  const mstCost = kruskal(V, edges);
  const mstCostWithoutI = kruskal(V, [
    ...edges.slice(0, i),
    ...edges.slice(i + 1),
  ]);
  return mstCost !== mstCostWithoutI;
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

function kruskal(V, edges) {
  const uf = new UnionFind();
  let numCcs = V;
  for (let u = 0; u < V; u++) {
    uf.add(u);
  }
  let mstCost = 0;
  edges.sort((a, b) => a[2] - b[2]);

  for (const [u, v, weight] of edges) {
    const reprU = uf.find(u);
    const reprV = uf.find(v);
    if (reprU !== reprV) {
      uf.union(u, v);
      mstCost += weight;
      numCcs--;
    }
  }

  if (numCcs !== 1) {
    return Number.POSITIVE_INFINITY;
  }
  return mstCost;
}


function runTests() {
  const tests = [
    // Graph from the book
    [
      4,
      [
        [0, 1, 5],
        [1, 2, 5],
        [2, 3, 20],
        [3, 0, 20],
      ],
      0,
      true,
    ],
    [
      4,
      [
        [0, 1, 5],
        [1, 2, 5],
        [2, 3, 20],
        [3, 0, 20],
      ],
      1,
      true,
    ],
    [
      4,
      [
        [0, 1, 5],
        [1, 2, 5],
        [2, 3, 20],
        [3, 0, 20],
      ],
      2,
      false,
    ],
    [
      4,
      [
        [0, 1, 5],
        [1, 2, 5],
        [2, 3, 20],
        [3, 0, 20],
      ],
      3,
      false,
    ],
    // Edge case - single edge
    [2, [[0, 1, 5]], 0, true],
    // Triangle graph - all edges same weight
    [
      3,
      [
        [0, 1, 1],
        [1, 2, 1],
        [2, 0, 1],
      ],
      0,
      false,
    ],
    // Square graph - one edge much heavier
    [
      4,
      [
        [0, 1, 1],
        [1, 2, 1],
        [2, 3, 1],
        [3, 0, 10],
      ],
      3,
      false,
    ],
    // Negative weights
    [
      3,
      [
        [0, 1, -2],
        [1, 2, 1],
        [2, 0, 1],
      ],
      0,
      true,
    ],
  ];

  for (const [V, edges, i, want] of tests) {
    const got = edgeInMst(V, edges, i);
    if (got !== want) {
      throw new Error(
        `\nedgeInMst(${V}, ${JSON.stringify(edges)}, ${i}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
