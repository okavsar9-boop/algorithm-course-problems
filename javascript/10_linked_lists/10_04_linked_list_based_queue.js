// 10.4 - Linked-List-Based Queue
// Run: node 10_04_linked_list_based_queue.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

class LinkedListQueue {
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
    const newNode = new Node(val);
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
      return null;
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
  const queue = new LinkedListQueue();

  // Test size on empty queue
  if (queue.size() !== 0) {
    throw new Error(`\nsize(): got: ${queue.size()}, want: 0\n`);
  }

  // Test pop on empty queue
  if (queue.pop() !== null) {
    throw new Error("\npop() on empty queue should return null\n");
  }

  // Test push and size
  queue.push(10);
  if (queue.size() !== 1) {
    throw new Error(`\nsize(): got: ${queue.size()}, want: 1\n`);
  }

  // Test push and pop
  queue.push(20);
  if (queue.pop() !== 10) {
    throw new Error("\npop() should return 10\n");
  }
  if (queue.size() !== 1) {
    throw new Error(`\nsize(): got: ${queue.size()}, want: 1\n`);
  }

  // Test empty
  if (queue.empty()) {
    throw new Error("\nempty() should return false\n");
  }
  queue.pop();
  if (!queue.empty()) {
    throw new Error("\nempty() should return true\n");
  }
}

runTests();
