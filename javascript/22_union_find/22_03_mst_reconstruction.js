// 22.3 - MST Reconstruction
// Run: node 22_03_mst_reconstruction.js

function kruskal(V, edges) {
  const uf = new UnionFind();
  for (let u = 0; u < V; u++) {
    uf.add(u);
  }
  const mstEdges = [];
  edges.sort((a, b) => a[2] - b[2]);

  for (const [u, v, weight] of edges) {
    const reprU = uf.find(u);
    const reprV = uf.find(v);
    if (reprU !== reprV) {
      uf.union(u, v);
      mstEdges.push([u, v, weight]);
    }
  }

  if (mstEdges.length === V - 1) {
    return mstEdges;
  }
  return []; // The graph was not connected.
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
    // Example 1 from book
    [
      9,
      [
        [0, 1, 3],
        [1, 8, 9],
        [8, 7, 5],
        [7, 4, 13],
        [4, 3, 4],
        [3, 0, 5],
        [1, 5, 8],
        [5, 4, 2],
        [4, 2, 3],
        [2, 1, -1],
        [2, 5, 10],
        [5, 6, 11],
        [6, 8, 0],
        [6, 7, -2],
      ],
      [
        [0, 1, 3],
        [1, 8, 9],
        [4, 3, 4],
        [5, 4, 2],
        [4, 2, 3],
        [2, 1, -1],
        [6, 8, 0],
        [6, 7, -2],
      ],
    ],
    // Example 2 - not connected
    [3, [[0, 1, 1]], []],
    // Example 3 - not unique solution
    [
      3,
      [
        [0, 1, 1],
        [1, 2, 1],
        [2, 0, 1],
      ],
      [
        [0, 1, 1],
        [1, 2, 1],
      ],
    ],
    // Single edge
    [2, [[0, 1, 5]], [[0, 1, 5]]],
    // Triangle graph
    [
      3,
      [
        [0, 1, 1],
        [1, 2, 2],
        [0, 2, 3],
      ],
      [
        [0, 1, 1],
        [1, 2, 2],
      ],
    ],
    // Empty graph
    [0, [], []],
  ];

  for (const [V, edges, want] of tests) {
    const got = kruskal(V, edges);
    // We check that 'got' has (1) the right number of edges, (2) the right cost,
    // and (3) no cycles.
    if (got.length !== want.length) {
      throw new Error(
        `\nkruskal(${V}, ${JSON.stringify(edges)}): got:\n${JSON.stringify(got)}, want: ${want.length} edges`,
      );
    }

    const gotCost = got.reduce((sum, [_, __, w]) => sum + w, 0);
    const wantCost = want.reduce((sum, [_, __, w]) => sum + w, 0);
    if (gotCost !== wantCost) {
      throw new Error(
        `\nkruskal(${V}, ${JSON.stringify(edges)}): got:\n${JSON.stringify(got)}, want cost ${wantCost}`,
      );
    }

    if (!got.length) {
      continue;
    }

    // Check that 'got' has no cycles with BFS
    const graph = Array(V)
      .fill()
      .map(() => []);
    for (const [u, v] of got) {
      graph[u].push(v);
      graph[v].push(u);
    }

    const visited = new Set([0]);
    const parent = new Map([[0, -1]]);
    const Q = [0];

    while (Q.length) {
      // Be mindful that shift() may take linear time, so in an interview,
      // we may not want to code BFS like this.
      const u = Q.shift();
      for (const v of graph[u]) {
        if (!visited.has(v)) {
          visited.add(v);
          parent.set(v, u);
          Q.push(v);
        } else if (parent.get(u) !== v) {
          // Found a cycle
          throw new Error(
            `\nkruskal(${V}, ${JSON.stringify(edges)}): got contains a cycle`,
          );
        }
      }
    }
  }
  return true;
}

runTests();
