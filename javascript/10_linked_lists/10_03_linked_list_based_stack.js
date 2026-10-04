// 10.3 - Linked-List-Based Stack
// Run: node 10_03_linked_list_based_stack.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

class LinkedListStack {
  constructor() {
    this.head = null;
    this._size = 0;
  }

  push(val) {
    const newNode = new Node(val);
    newNode.next = this.head;
    this.head = newNode;
    this._size++;
  }

  pop() {
    if (!this.head) {
      return null;
    }
    const val = this.head.val;
    this.head = this.head.next;
    this._size--;
    return val;
  }

  peek() {
    if (!this.head) {
      return null;
    }
    return this.head.val;
  }

  size() {
    return this._size;
  }

  empty() {
    return this._size === 0;
  }
}


function runTests() {
  const stack = new LinkedListStack();

  // Test size on empty stack
  if (stack.size() !== 0) {
    throw new Error(`\nsize(): got: ${stack.size()}, want: 0\n`);
  }
  // Test pop on empty stack
  if (stack.pop() !== null) {
    throw new Error("\npop() on empty stack should return null\n");
  }

  // Test peek on empty stack
  if (stack.peek() !== null) {
    throw new Error("\npeek() on empty stack should return null\n");
  }

  // Test push and size
  stack.push(10);
  if (stack.size() !== 1) {
    throw new Error(`\nsize(): got: ${stack.size()}, want: 1\n`);
  }

  // Test peek
  if (stack.peek() !== 10) {
    throw new Error("\npeek() should return 10\n");
  }

  // Test push and pop
  stack.push(20);
  if (stack.pop() !== 20) {
    throw new Error("\npop() should return 20\n");
  }
  if (stack.size() !== 1) {
    throw new Error(`\nsize(): got: ${stack.size()}, want: 1\n`);
  }

  // Test empty
  if (stack.empty()) {
    throw new Error("\nempty() should return false\n");
  }
  stack.pop();
  if (!stack.empty()) {
    throw new Error("\nempty() should return true\n");
  }
}

runTests();
