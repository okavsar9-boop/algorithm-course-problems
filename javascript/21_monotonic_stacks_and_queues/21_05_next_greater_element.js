// 21.5 - Next Greater Element
// Run: node 21_05_next_greater_element.js

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

function nextGreaterElement(arr) {
  const n = arr.length;
  const nge = new Array(n).fill(-1);
  const stack = new Deque();

  // Iterate right to left
  for (let i = n - 1; i >= 0; i--) {
    // Pop all NGE candidates from stack that are <= arr[i]
    while (!stack.empty() && arr[stack.peekBack()] <= arr[i]) {
      stack.popBack();
    }

    // If stack not empty, top is NGE of i
    if (!stack.empty()) {
      nge[i] = stack.peekBack();
    }

    // Add i to stack as candidate for future elements
    stack.pushBack(i);
  }

  return nge;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [5, 3, 10, 8, 8, 10],
      [2, 2, -1, 5, 5, -1],
    ],
    // Example 2 from the book
    [
      [4, 2, 6, 4, 5, 2, 4, 7, 3, 7],
      [2, 2, 7, 4, 7, 6, 7, -1, 9, -1],
    ],
    // Example 3 from the book
    [
      [5, 5, 5, 5, 5],
      [-1, -1, -1, -1, -1],
    ],
    // Example 4 from the book
    [
      [5, 6, 7, 8, 9],
      [1, 2, 3, 4, -1],
    ],
    // Edge case - empty array
    [[], []],
    // Edge case - single element
    [[1], [-1]],
  ];

  for (const [arr, want] of tests) {
    const got = nextGreaterElement(arr);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nnextGreaterElement(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
