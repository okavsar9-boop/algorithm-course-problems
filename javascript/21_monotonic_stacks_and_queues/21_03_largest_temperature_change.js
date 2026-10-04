// 21.3 - Largest Temperature Change
// Run: node 21_03_largest_temperature_change.js

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

function largestTemperatureChange(arr, k) {
  let l = 0,
    r = 0;
  const maxMinQueue = new MaxMinQueue();
  let res = -Infinity;
  while (r < arr.length) {
    maxMinQueue.push(arr[r]);
    r++;
    if (r - l === k) {
      res = Math.max(res, maxMinQueue.max() - maxMinQueue.min());
      maxMinQueue.pop();
      l++;
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[12, 13, 12, 13, 13, 12, 11, 12], 3, 2],
    // Example 2 from the book
    [[10, 30], 2, 20],
    // all same temperature
    [[10, 10, 10, 10], 2, 0],
    // strictly increasing
    [[10, 20, 30, 40], 3, 20],
    // strictly decreasing
    [[40, 30, 20, 10], 3, 20],
    // k equals length
    [[15, 10, 25], 3, 15],
    // Mixed sequence
    [[22, 18, 25, 20, 15, 21, 16], 4, 10],
  ];

  for (const [arr, k, want] of tests) {
    const got = largestTemperatureChange(arr, k);
    if (got !== want) {
      throw new Error(
        `\nlargestTemperatureChange(${JSON.stringify(arr)}, ${k}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
