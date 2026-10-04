// 12.2 - Graph Path
// Run: node 12_02_graph_path.js

function path(graph, node1, node2) {
  const predecessors = new Map();
  predecessors.set(node2, null); // The starting node doesn't have a predecessor.

  function visit(node) {
    for (const nbr of graph[node]) {
      if (!predecessors.has(nbr)) {
        predecessors.set(nbr, node);
        visit(nbr);
      }
    }
  }

  visit(node2);
  if (!predecessors.has(node1)) {
    return []; // node1 and node2 are disconnected.
  }
  const path = [node1];
  while (path[path.length - 1] !== node2) {
    path.push(predecessors.get(path[path.length - 1]));
  }
  return path;
}

function pathBfs(graph, node1, node2) {
  const Q = new Queue();
  Q.push(node2);
  const predecessors = new Map();
  predecessors.set(node2, null);
  while (!Q.empty()) {
    const node = Q.pop();
    for (const nbr of graph[node]) {
      if (!predecessors.has(nbr)) {
        predecessors.set(nbr, node);
        Q.push(nbr);
      }
    }
  }
  if (!predecessors.has(node1)) {
    return [];
  }
  const path = [node1];
  while (path[path.length - 1] !== node2) {
    path.push(predecessors.get(path[path.length - 1]));
  }
  return path;
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
    // Example 1 from book - graph from Figure 8
    [[[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]], 0, 4, [0, 1, 4]],
    // Example 2 from book - graph from Figure 8, no path exists
    [[[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]], 0, 3, []],
    // Simple line graph
    [[[1], [0, 2], [1]], 0, 2, [0, 1, 2]],
    // Cycle graph
    [
      [
        [1, 3],
        [0, 2],
        [1, 3],
        [0, 2],
      ],
      0,
      2,
      [0, 1, 2],
    ],
    // Disconnected graph
    [[[1], [0], [3], [2]], 0, 2, []],
    // Complete graph
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      0,
      2,
      [0, 2],
    ],
  ];
  for (const [graph, node1, node2, want] of tests) {
    const got = path(graph, node1, node2);
    const gotBfs = pathBfs(graph, node1, node2);
    // For this problem, there can be multiple valid paths
    // So we need to verify:
    // 1. If want is empty, got should be empty
    // 2. If want is not empty:
    //    - got should start with node1 and end with node2
    //    - got should be a valid path in the graph
    //    - got should not have duplicates
    if (!want.length) {
      if (!!got.length) {
        throw new Error(
          `\npath(${JSON.stringify(graph)}, ${node1}, ${node2}): got: ${got}, want empty path\n`,
        );
      }
      if (!!gotBfs.length) {
        throw new Error(
          `\npathBfs(${JSON.stringify(graph)}, ${node1}, ${node2}): got: ${gotBfs}, want empty path\n`,
        );
      }
      continue;
    }

    if (got[0] !== node1 || got[got.length - 1] !== node2) {
      throw new Error(
        `\npath(${JSON.stringify(graph)}, ${node1}, ${node2}): path ${got} should start with ${node1} and end with ${node2}\n`,
      );
    }
    if (gotBfs[0] !== node1 || gotBfs[gotBfs.length - 1] !== node2) {
      throw new Error(
        `\npathBfs(${JSON.stringify(graph)}, ${node1}, ${node2}): path ${gotBfs} should start with ${node1} and end with ${node2}\n`,
      );
    }

    // Verify path is valid
    for (let i = 0; i < got.length - 1; i++) {
      if (!graph[got[i]].includes(got[i + 1])) {
        throw new Error(
          `\npath(${JSON.stringify(graph)}, ${node1}, ${node2}): invalid path ${got} - no edge between ${got[i]} and ${got[i + 1]}\n`,
        );
      }
    }
    for (let i = 0; i < gotBfs.length - 1; i++) {
      if (!graph[gotBfs[i]].includes(gotBfs[i + 1])) {
        throw new Error(
          `\npathBfs(${JSON.stringify(graph)}, ${node1}, ${node2}): invalid path ${gotBfs} - no edge between ${gotBfs[i]} and ${gotBfs[i + 1]}\n`,
        );
      }
    }

    // Verify no duplicates
    if (got.length !== new Set(got).size) {
      throw new Error(
        `\npath(${JSON.stringify(graph)}, ${node1}, ${node2}): path ${got} contains duplicates\n`,
      );
    }
    if (gotBfs.length !== new Set(gotBfs).size) {
      throw new Error(
        `\npathBfs(${JSON.stringify(graph)}, ${node1}, ${node2}): path ${gotBfs} contains duplicates\n`,
      );
    }
  }
}

runTests();
