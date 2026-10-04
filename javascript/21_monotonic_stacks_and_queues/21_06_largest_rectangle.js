// 21.6 - Largest Rectangle
// Run: node 21_06_largest_rectangle.js

// JS has no built-in deque, so a linked-list one is included.
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

function nextSmallerElement(arr) {
  const n = arr.length;
  const nse = new Array(n).fill(n);
  const stack = new Deque();

  for (let i = n - 1; i >= 0; i--) {
    while (!stack.empty() && arr[stack.peekBack()] >= arr[i]) {
      stack.popBack();
    }
    if (!stack.empty()) {
      nse[i] = stack.peekBack();
    }
    stack.pushBack(i);
  }
  return nse;
}

function prevSmallerElement(arr) {
  const n = arr.length;
  const pse = new Array(n).fill(-1);
  const stack = new Deque();

  for (let i = 0; i < n; i++) {
    while (!stack.empty() && arr[stack.peekBack()] >= arr[i]) {
      stack.popBack();
    }
    if (!stack.empty()) {
      pse[i] = stack.peekBack();
    }
    stack.pushBack(i);
  }
  return pse;
}

function largestRectangle(tiles) {
  const n = tiles.length;
  const nse = nextSmallerElement(tiles);
  const pse = prevSmallerElement(tiles);

  let maxArea = 0;
  for (let i = 0; i < n; i++) {
    const width = nse[i] - pse[i] - 1;
    const area = width * tiles[i];
    maxArea = Math.max(maxArea, area);
  }
  return maxArea;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [[1, 2, 3], 4],
    // Example 2 from the book
    [[2, 1, 2], 3],
    // Example 3 from the book
    [[1, 2, 5, 2, 1], 6],
    // Edge cases
    [[1], 1],
    [[0], 0],
    [[5, 5, 5, 5, 5], 25],
    [[0, 0, 0, 0, 0], 0],
    [[1, 0, 1], 1],
  ];

  for (const [tiles, want] of tests) {
    const got = largestRectangle(tiles);
    if (got !== want) {
      throw new Error(
        `\nlargestRectangle(${JSON.stringify(tiles)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
