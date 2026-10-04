// 12.10 - Shortest-Path Queries
// Run: node 12_10_shortest_path_queries.js

function shortestPathQueries(graph, start, queries) {
  const Q = new Queue();
  Q.push(start);
  const predecessors = new Map();
  predecessors.set(start, null);

  while (!Q.empty()) {
    const node = Q.pop();
    for (const nbr of graph[node]) {
      if (!predecessors.has(nbr)) {
        predecessors.set(nbr, node);
        Q.push(nbr);
      }
    }
  }

  const res = [];
  for (const node of queries) {
    if (!predecessors.has(node)) {
      res.push([]);
    } else {
      const path = [node];
      while (path[path.length - 1] !== start) {
        path.push(predecessors.get(path[path.length - 1]));
      }
      path.reverse();
      res.push(path);
    }
  }
  return res;
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
    // Example
    [
      [[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]],
      0,
      [1, 0, 3, 4],
      [[0, 1], [0], [], [0, 1, 4]],
    ],
    // Simple line graph
    [
      [[1], [0, 2], [1]],
      0,
      [1, 2],
      [
        [0, 1],
        [0, 1, 2],
      ],
    ],
    // Disconnected components
    [[[1], [0], [3], [2]], 0, [1, 2, 3], [[0, 1], [], []]],
    // Complete graph
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      0,
      [1, 2],
      [
        [0, 1],
        [0, 2],
      ],
    ],
    // Single node
    [[[]], 0, [0], [[0]]],
    // Empty queries
    [[[1], [0]], 0, [], []],
  ];
  for (const [graph, start, queries, want] of tests) {
    const got = shortestPathQueries(graph, start, queries);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nshortestPathQueries(${JSON.stringify(graph)}, ${start}, ${JSON.stringify(queries)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
