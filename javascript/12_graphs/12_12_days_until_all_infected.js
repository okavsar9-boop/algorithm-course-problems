// 12.12 - Days Until All Infected
// Run: node 12_12_days_until_all_infected.js

function allInfected(graph, infected) {
  const Q = new Queue();
  const distances = new Map();

  // Initialize queue with infected nodes
  for (const start of infected) {
    Q.push(start);
    distances.set(start, 0);
  }

  // Multisource BFS
  while (!Q.empty()) {
    const node = Q.pop();
    for (const nbr of graph[node]) {
      if (!distances.has(nbr)) {
        distances.set(nbr, distances.get(node) + 1);
        Q.push(nbr);
      }
    }
  }

  // If any node wasn't reached, graph is disconnected
  if (distances.size < graph.length) {
    return -1;
  }

  // Return max distance - that's how many days it takes
  return Math.max(...distances.values());
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
      [0, 8, 17], // infected
      3,
    ],
    [
      [[1, 2], [0, 2], [0, 1, 3], [2]], // graph
      [0], // infected
      2, // want
    ],
    // Single node graph
    [[[]], [0], 0],
    // Line graph
    [[[1], [0, 2], [1, 3], [2]], [0], 3],
    // Multiple initial infected nodes
    [
      [
        [1, 2],
        [0, 3],
        [0, 3],
        [1, 2],
      ],
      [0, 3],
      1,
    ],
  ];

  for (const [graph, infected, want] of tests) {
    const got = allInfected(graph, infected);
    if (got !== want) {
      throw new Error(
        `\nallInfected(${JSON.stringify(graph)}, ${JSON.stringify(infected)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
