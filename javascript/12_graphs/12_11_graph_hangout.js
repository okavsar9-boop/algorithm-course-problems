// 12.11 - Graph Hangout
// Run: node 12_11_graph_hangout.js

function bfs(graph, start) {
  const Q = new Queue();
  Q.push(start);
  const distances = new Map();
  distances.set(start, 0);
  while (!Q.empty()) {
    const node = Q.pop();
    for (const nbr of graph[node]) {
      if (!distances.has(nbr)) {
        distances.set(nbr, distances.get(node) + 1);
        Q.push(nbr);
      }
    }
  }
  return distances;
}

function walkingDistanceToCoffee(graph, node1, node2, node3) {
  const distances1 = bfs(graph, node1);
  const distances2 = bfs(graph, node2);
  const distances3 = bfs(graph, node3);
  let res = Infinity;
  for (let i = 0; i < graph.length; i++) {
    res = Math.min(
      res,
      distances1.get(i) + distances2.get(i) + distances3.get(i),
    );
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
    // Example from the book
    [
      [
        [1, 14], // 0: Outer ring connections
        [0, 2], // 1
        [1, 3], // 2
        [2, 4], // 3
        [3, 5, 19], // 4: Connector from outer to inner ring
        [4, 6], // 5
        [5, 7], // 6
        [6, 8], // 7
        [7, 9, 21], // 8: Connector from outer to inner ring
        [8, 10], // 9
        [9, 11], // 10
        [10, 12], // 11
        [11, 13], // 12
        [12, 14], // 13
        [0, 13, 15], // 14: Connector from outer to inner ring
        [14, 16], // 15
        [15, 17], // 16
        [16, 18, 20], // 17: Center node connections
        [17, 19], // 18
        [18, 4], // 19
        [17, 21], // 20
        [8, 20], // 21
      ],
      14,
      4,
      8,
      9,
    ],
    // Cycle with 5 nodes
    [
      [
        [1, 4],
        [0, 2],
        [1, 3],
        [2, 4],
        [0, 3],
      ],
      0,
      2,
      4,
      3,
    ],
    // Simple line graph
    [[[1], [0, 2], [1]], 0, 1, 2, 2],
    // Star graph - optimal meeting point is center
    [[[1], [0, 2, 3, 4], [1], [1], [1]], 0, 2, 3, 3],
    // Complete graph - can meet at any node
    [
      [
        [1, 2, 3],
        [0, 2, 3],
        [0, 1, 3],
        [0, 1, 2],
      ],
      0,
      1,
      2,
      2,
    ],
    // Edge case - all start at same node
    [[[1], [0]], 0, 0, 0, 0],
    // Edge case - two start at same node
    [[[1], [0, 2], [1]], 0, 0, 2, 2],
  ];
  for (const [graph, node1, node2, node3, want] of tests) {
    const got = walkingDistanceToCoffee(graph, node1, node2, node3);
    if (got !== want) {
      throw new Error(
        `\nwalkingDistanceToCoffee(${JSON.stringify(graph)}, ${node1}, ${node2}, ${node3}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
