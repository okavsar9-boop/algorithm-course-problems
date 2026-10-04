// 21.7 - King Kong Vs Godzilla
// Run: node 21_07_king_kong_vs_godzilla.js

class MaxQueue {
  constructor() {
    this.queue = new Deque();
    this.monoDecrDeque = new Deque();
  }

  max() {
    return this.monoDecrDeque.peekFront();
  }

  empty() {
    return this.queue.empty();
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

function sparedByKingKong(street) {
  const n = street.length;
  const spared = new Array(n).fill(false);

  // Add all buildings to the queue (except the first one)
  const maxQueue = new MaxQueue();
  for (let i = 1; i < n; i++) {
    maxQueue.push(street[i]);
  }

  // Process each building (except the last one, which is always destroyed)
  for (let i = 0; i < n - 1; i++) {
    if (!maxQueue.empty() && street[i] < maxQueue.max()) {
      spared[i] = true;
    }
    maxQueue.pop();
  }
  return spared;
}

function sparedByKingKongNge(street) {
  const n = street.length;

  // Build NGE array using recipe
  const nge = new Array(n).fill(-1);
  const stack = new Deque();
  for (let i = n - 1; i >= 0; i--) {
    while (!stack.empty() && street[stack.peekBack()] <= street[i]) {
      stack.popBack();
    }
    if (!stack.empty()) {
      nge[i] = stack.peekBack();
    }
    stack.pushBack(i);
  }

  // Building i is spared if it has a next greater element
  const spared = new Array(n).fill(false);
  for (let i = 0; i < n; i++) {
    if (nge[i] !== -1) {
      spared[i] = true;
    }
  }
  return spared;
}

function sparedByGodzilla(street) {
  const newStreet = street.map((h) => -h).reverse();
  const res = sparedByKingKong(newStreet);
  return res.reverse();
}

function spared(street) {
  const res1 = sparedByKingKong(street);
  const res2 = sparedByGodzilla(street);
  return res1.map((v, i) => v && res2[i]);
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [10, 20, 30, 15, 5],
      [true, true, false, false, false], // King Kong
      [false, true, true, true, false], // Godzilla
      [false, true, false, false, false],
    ], // Combined
    // Example 2 from the book
    [
      [10, 20, 30, 40, 50],
      [true, true, true, true, false], // King Kong
      [false, true, true, true, true], // Godzilla
      [false, true, true, true, false],
    ], // Combined
    // Example 3 from the book
    [
      [50, 40, 30, 20, 10],
      [false, false, false, false, false], // King Kong
      [false, false, false, false, false], // Godzilla
      [false, false, false, false, false],
    ], // Combined
    // Edge case - single element
    [
      [1],
      [false], // King Kong
      [false], // Godzilla
      [false],
    ], // Combined
    // Edge case - all same height
    [
      [5, 5, 5, 5, 5, 5],
      [false, false, false, false, false, false], // King Kong
      [false, false, false, false, false, false], // Godzilla
      [false, false, false, false, false, false],
    ], // Combined
  ];

  for (const [street, wantKong, wantGodzilla, want] of tests) {
    const gotKong = sparedByKingKong(street);
    const gotKongNge = sparedByKingKongNge(street);
    if (JSON.stringify(gotKong) !== JSON.stringify(gotKongNge)) {
      throw new Error(
        `\nsparedByKingKong(${JSON.stringify(street)}): using sliding window max: ${JSON.stringify(gotKong)}, using NGE: ${JSON.stringify(gotKongNge)}\n`,
      );
    }

    const gotGodzilla = sparedByGodzilla(street);
    const got = spared(street);

    if (
      JSON.stringify(gotKong) === JSON.stringify(wantKong) &&
      JSON.stringify(gotGodzilla) === JSON.stringify(wantGodzilla) &&
      JSON.stringify(got) !== JSON.stringify(want)
    ) {
      throw new Error(
        `\nspared(${JSON.stringify(street)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
