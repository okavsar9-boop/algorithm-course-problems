// 12.9 - First Time All Connected
// Run: node 12_09_first_time_all_connected.js

function firstTimeAllConnected(V, cables) {

  function visit(graph, visited, node) {
    for (const nbr of graph[node]) {
      if (!visited.has(nbr)) {
        visited.add(nbr);
        visit(graph, visited, nbr);
      }
    }
  }

  function isBefore(cableIndex) {
    const graph = Array(V)
      .fill()
      .map(() => []);
    for (let i = 0; i <= cableIndex; i++) {
      const [node1, node2] = cables[i];
      graph[node1].push(node2);
      graph[node2].push(node1);
    }
    const visited = new Set([0]);
    visit(graph, visited, 0);
    return visited.size < V;
  }

  let l = 0,
    r = cables.length - 1;
  if (isBefore(r)) {
    return -1;
  }
  while (r - l > 1) {
    const mid = l + Math.floor((r - l) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }
  return r;
}

function firstTimeAllConnectedUnionFind(V, cables) {
  const uf = new UnionFind();
  for (let x = 0; x < V; x++) {
    uf.add(x);
  }
  let groups = V;
  for (let i = 0; i < cables.length; i++) {
    const [x, y] = cables[i];

    // If x and y are not in the same group yet, union their groups.
    if (uf.find(x) !== uf.find(y)) {
      uf.union(x, y);
      groups--;
      if (groups === 1) {
        return i;
      }
    }
  }
  return -1;
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
    // Case from picture - becomes connected after cables[2].
    [
      4,
      [
        [0, 2],
        [1, 3],
        [0, 1],
        [1, 2],
      ],
      2,
    ],
    // Edge case - never gets fully connected
    [3, [[0, 1]], -1],
    // Edge case - gets connected with final cable
    [
      3,
      [
        [0, 1],
        [1, 2],
      ],
      1,
    ],
    // Larger test case
    [
      5,
      [
        [0, 1],
        [2, 3],
        [1, 2],
        [3, 4],
        [0, 4],
      ],
      3,
    ],
    // Edge case - redundant cables don't affect result
    [
      4,
      [
        [0, 1],
        [1, 2],
        [2, 0],
        [2, 3],
        [3, 0],
      ],
      3,
    ],
    // No edges added.
    [4, [], -1],
    // One edge added.
    [4, [[0, 1]], -1],
  ];
  for (const [V, cables, want] of tests) {
    let got = firstTimeAllConnected(V, cables);
    if (got !== want) {
      throw new Error(
        `\nfirstTimeAllConnected(${V}, ${JSON.stringify(cables)}): got: ${got}, want: ${want}\n`,
      );
    }
    got = firstTimeAllConnectedUnionFind(V, cables);
    if (got !== want) {
      throw new Error(
        `\nfirstTimeAllConnectedUnionFind(${V}, ${JSON.stringify(cables)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
