// 21.1 - Max Queue
// Run: node 21_01_max_queue.js

class MaxQueue {
  constructor() {
    this.queue = new Deque();
    this.monoDecrDeque = new Deque();
  }

  peek() {
    return this.queue.peekFront();
  }

  size() {
    return this.queue.size();
  }

  max() {
    return this.monoDecrDeque.peekFront();
  }

  pop() {
    const val = this.queue.popFront();
    // Check if we are popping the max
    if (val === this.monoDecrDeque.peekFront()) {
      this.monoDecrDeque.popFront();
    }
    return val;
  }

  push(val) {
    this.queue.pushBack(val);
    // Remove elements from the monotonic deque that can never be the max
    while (!this.monoDecrDeque.empty() && this.monoDecrDeque.peekBack() < val) {
      this.monoDecrDeque.popBack();
    }
    this.monoDecrDeque.pushBack(val);
  }
}

class DequeNode {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

class Deque {
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

  peekFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.head.val;
  }

  peekBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.tail.val;
  }

  pushBack(val) {
    const newNode = new DequeNode(val);
    if (this.tail) {
      this.tail.next = newNode;
      newNode.prev = this.tail;
    }
    this.tail = newNode;
    if (!this.head) {
      this.head = newNode;
    }
    this._size++;
  }

  pushFront(val) {
    const newNode = new DequeNode(val);
    if (this.head) {
      this.head.prev = newNode;
      newNode.next = this.head;
    }
    this.head = newNode;
    if (!this.tail) {
      this.tail = newNode;
    }
    this._size++;
  }

  popBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.tail.val;
    this.tail = this.tail.prev;
    if (this.tail) {
      this.tail.next = null;
    } else {
      this.head = null;
    }
    this._size--;
    return val;
  }

  popFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.head.val;
    this.head = this.head.next;
    if (this.head) {
      this.head.prev = null;
    } else {
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
      ["push", 10],
      ["push", 30],
      ["push", 20],
      ["max", 30],
      ["pop", 10],
      ["max", 30],
      ["pop", 30],
      ["max", 20],
      ["push", 50],
      ["push", 30],
      ["push", 20],
      ["push", 10],
      ["max", 50],
      ["push", 50],
      ["max", 50],
      ["pop", 20],
      ["pop", 50],
      ["max", 50],
    ],
    // Edge cases
    [
      ["push", 1],
      ["max", 1],
      ["pop", 1],
      ["push", 2],
      ["max", 2],
    ],
    // Multiple equal values
    [
      ["push", 5],
      ["push", 5],
      ["max", 5],
      ["pop", 5],
      ["max", 5],
    ],
  ];

  for (const ops of tests) {
    const q = new MaxQueue();
    for (const [cmd, val] of ops) {
      if (cmd === "push") {
        q.push(val);
      } else if (cmd === "pop") {
        const got = q.pop();
        if (got !== val) {
          throw new Error(`\npop(): got: ${got}, want: ${val}\n`);
        }
      } else if (cmd === "max") {
        const got = q.max();
        if (got !== val) {
          throw new Error(`\nmax(): got: ${got}, want: ${val}\n`);
        }
      }
    }
  }
}

runTests();
