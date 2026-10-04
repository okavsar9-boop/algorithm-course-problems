// 21.4 - Longest Stable Period
// Run: node 21_04_longest_stable_period.js

class MaxMinQueue {
  constructor() {
    this.queue = new Deque();
    this.monoDecrDeque = new Deque(); // For max
    this.monoIncrDeque = new Deque(); // For min
  }

  max() {
    return this.monoDecrDeque.peekFront();
  }

  min() {
    return this.monoIncrDeque.peekFront();
  }

  pop() {
    // Check if we are popping the max
    if (this.queue.peekFront() === this.monoDecrDeque.peekFront()) {
      this.monoDecrDeque.popFront();
    }
    // Check if we are popping the min
    if (this.queue.peekFront() === this.monoIncrDeque.peekFront()) {
      this.monoIncrDeque.popFront();
    }
    this.queue.popFront();
  }

  push(val) {
    this.queue.pushBack(val);
    // Remove elements from the decreasing deque that can never be the max
    while (!this.monoDecrDeque.empty() && this.monoDecrDeque.peekBack() < val) {
      this.monoDecrDeque.popBack();
    }
    this.monoDecrDeque.pushBack(val);
    // Remove elements from the increasing deque that can never be the min
    while (!this.monoIncrDeque.empty() && this.monoIncrDeque.peekBack() > val) {
      this.monoIncrDeque.popBack();
    }
    this.monoIncrDeque.pushBack(val);
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

function longestStablePeriod(temperatures, t) {
  let l = 0,
    r = 0;
  const maxMinQueue = new MaxMinQueue();
  let curBest = 0;
  while (r < temperatures.length) {
    const canGrow =
      l === r ||
      Math.max(maxMinQueue.max(), temperatures[r]) -
      Math.min(maxMinQueue.min(), temperatures[r]) <=
      t;
    if (canGrow) {
      maxMinQueue.push(temperatures[r]);
      r++;
      curBest = Math.max(curBest, r - l);
    } else {
      maxMinQueue.pop();
      l++;
    }
  }
  return curBest;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[12, 16, 14, 15, 13, 17], 3, 4],
    // Example 2 from the book
    [[30, 10], 100, 2],
    // Example 3 from the book
    [[30, 10], 1, 1],
    // All same temperature
    [[10, 10, 10, 10], 0, 4],
    // Strictly increasing
    [[10, 20, 30, 40], 5, 1],
    // Strictly decreasing
    [[40, 30, 20, 10], 5, 1],
    // Mixed sequence
    [[22, 18, 25, 20, 15, 21, 16], 4, 2],
  ];

  for (const [temperatures, t, want] of tests) {
    const got = longestStablePeriod(temperatures, t);
    if (got !== want) {
      throw new Error(
        `\nlongestStablePeriod(${JSON.stringify(temperatures)}, ${t}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
