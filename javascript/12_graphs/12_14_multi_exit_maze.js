// 12.14 - Multi-Exit Maze
// Run: node 12_14_multi_exit_maze.js

function exitDistances(maze) {
  const R = maze.length;
  const C = maze[0].length;
  const directions = [
    [-1, 0],
    [1, 0],
    [0, 1],
    [0, -1],
  ];
  const distances = Array(R)
    .fill()
    .map(() => Array(C).fill(-1));
  const Q = new Queue();

  for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
      if (maze[r][c] === "O") {
        distances[r][c] = 0;
        Q.push([r, c]);
      }
    }
  }

  while (!Q.empty()) {
    const [r, c] = Q.pop();
    for (const [dirR, dirC] of directions) {
      const nbrR = r + dirR;
      const nbrC = c + dirC;
      if (
        0 <= nbrR &&
        nbrR < R &&
        0 <= nbrC &&
        nbrC < C &&
        maze[nbrR][nbrC] !== "X" &&
        distances[nbrR][nbrC] === -1
      ) {
        distances[nbrR][nbrC] = distances[r][c] + 1;
        Q.push([nbrR, nbrC]);
      }
    }
  }
  return distances;
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
    // Example from book
    [
      ["...X.O", "OX.X..", "...X..", ".X....", "XOX.XX"].map((row) =>
        row.split(""),
      ),
      [
        [1, 2, 3, -1, 1, 0],
        [0, -1, 4, -1, 2, 1],
        [1, 2, 3, -1, 3, 2],
        [2, -1, 4, 5, 4, 3],
        [-1, 0, -1, 6, -1, -1],
      ],
    ],
    // Single exit
    [
      ["...", ".O.", "..."].map((row) => row.split("")),
      [
        [2, 1, 2],
        [1, 0, 1],
        [2, 1, 2],
      ],
    ],
    // Multiple exits
    [
      ["O.O", "...", "O.O"].map((row) => row.split("")),
      [
        [0, 1, 0],
        [1, 2, 1],
        [0, 1, 0],
      ],
    ],
    // Walls blocking direct paths
    [
      ["O.X.", "XX..", "...O"].map((row) => row.split("")),
      [
        [0, 1, -1, 2],
        [-1, -1, 2, 1],
        [3, 2, 1, 0],
      ],
    ],
    // Single cell
    [["O"].map((row) => row.split("")), [[0]]],
  ];

  for (const [maze, want] of tests) {
    const got = exitDistances(maze);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nexitDistances(${JSON.stringify(maze)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
