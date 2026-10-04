// 21.2 - Sliding Window Maximum
// Run: node 21_02_sliding_window_maximum.js

class MaxQueue {
  constructor() {
    this.queue = new Deque();
    this.monoDecrDeque = new Deque();
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

function slidingWindowMax(arr, k) {
  let l = 0,
    r = 0;
  const maxQueue = new MaxQueue();
  const res = [];
  while (r < arr.length) {
    maxQueue.push(arr[r]);
    r++;
    if (r - l === k) {
      res.push(maxQueue.max());
      maxQueue.pop();
      l++;
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[10, 20, 30, 40, 30, 20, 10], 2, [20, 30, 40, 40, 30, 20]],
    // Example 2 from the book
    [[10, 20, 30, 40, 30, 20, 10], 3, [30, 40, 40, 40, 30]],
    // Window size 1 just returns the array
    [[1, 2, 3], 1, [1, 2, 3]],
    // Window size equals array length
    [[5, 2, 1], 3, [5]],
    // Array with duplicates
    [[1, 1, 1, 2, 2, 2], 2, [1, 1, 2, 2, 2]],
    // Decreasing sequence
    [[5, 4, 3, 2, 1], 3, [5, 4, 3]],
    // Increasing sequence
    [[1, 2, 3, 4, 5], 3, [3, 4, 5]],
    // Mixed sequence
    [[1, 5, 2, 6, 3], 3, [5, 6, 6]],
  ];

  for (const [arr, k, want] of tests) {
    const got = slidingWindowMax(arr, k);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nslidingWindowMax(${JSON.stringify(arr)}, ${k}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
