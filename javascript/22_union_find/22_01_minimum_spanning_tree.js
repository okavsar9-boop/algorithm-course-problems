// 22.1 - Minimum Spanning Tree
// Run: node 22_01_minimum_spanning_tree.js

function buildAdjacencyList(edges, V) {
  const adjList = Array(V).fill(null).map(() => []);
  for (const [u, v, w] of edges) {
    adjList[u].push([v, w]);
    adjList[v].push([u, w]);
  }
  return adjList;
}

function prim(V, edges) {
  // Assumes the graph is connected.
  const adjList = buildAdjacencyList(edges, V);
  const minEdge = Array(V).fill(Infinity);
  minEdge[0] = 0;
  const vis = Array(V).fill(false);
  const PQ = new Heap((a, b) => a[0] < b[0]);
  PQ.push([0, 0]);
  let mstCost = 0;
  while (PQ.size() > 0) {
    const [, u] = PQ.pop();  // Only need the node, not the edge weight
    if (vis[u]) continue;  // Not first extraction -- obsolete copy
    vis[u] = true;
    mstCost += minEdge[u];
    for (const [v, w] of adjList[u]) {
      if (!vis[v] && w < minEdge[v]) {
        minEdge[v] = w;
        PQ.push([w, v]);
      }
    }
  }
  return mstCost;
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
function kruskal(V, edges) {
  // Assumes the graph is connected.
  const uf = new UnionFind();
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
    }
  }
  return mstCost;
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
    // Example from book
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
      18,
    ],
    // Single edge
    [2, [[0, 1, 5]], 5],
    // Triangle graph
    [
      3,
      [
        [0, 1, 1],
        [1, 2, 2],
        [0, 2, 3],
      ],
      3,
    ],
    // Square graph
    [
      4,
      [
        [0, 1, 1],
        [1, 2, 2],
        [2, 3, 3],
        [3, 0, 4],
      ],
      6,
    ],
    // Negative weights
    [
      3,
      [
        [0, 1, -2],
        [1, 2, -3],
        [0, 2, 1],
      ],
      -5,
    ],
  ];

  for (const [n, edges, want] of tests) {
    const gotPrim = prim(n, edges);
    if (gotPrim !== want) {
      throw new Error(
        `\nprim(${n}, ${JSON.stringify(edges)}): got: ${gotPrim}, want: ${want}\n`,
      );
    }
    const gotKruskal = kruskal(n, edges);
    if (gotKruskal !== want) {
      throw new Error(
        `\nkruskal(${n}, ${JSON.stringify(edges)}): got: ${gotKruskal}, want: ${want}\n`,
      );
    }
  }
}

runTests();
