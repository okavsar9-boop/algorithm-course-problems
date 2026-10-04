// 10.1 - Singly Linked List Design
// Run: node 10_01_singly_linked_list_design.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

class SinglyLinkedList {
  constructor() {
    this.head = null;
    this._size = 0;
  }

  size() {
    return this._size;
  }

  pushFront(val) {
    const newNode = new Node(val);
    newNode.next = this.head;
    this.head = newNode;
    this._size++;
  }

  popFront() {
    if (!this.head) {
      return null;
    }
    const val = this.head.val;
    this.head = this.head.next;
    this._size--;
    return val;
  }

  pushBack(val) {
    const newNode = new Node(val);
    this._size++;
    if (!this.head) {
      this.head = newNode;
      return;
    }
    let cur = this.head;
    while (cur.next) {
      cur = cur.next;
    }
    cur.next = newNode;
  }

  popBack() {
    if (!this.head) {
      return null;
    }
    this._size--;
    if (!this.head.next) {
      const val = this.head.val;
      this.head = null;
      return val;
    }
    let cur = this.head;
    while (cur.next && cur.next.next) {
      cur = cur.next;
    }
    const val = cur.next.val;
    cur.next = null;
    return val;
  }

  contains(val) {
    let cur = this.head;
    while (cur) {
      if (cur.val === val) {
        return cur;
      }
      cur = cur.next;
    }
    return null;
  }
}


function runTests() {
  const sll = new SinglyLinkedList();

  // Test size on empty list
  if (sll.size() !== 0) {
    throw new Error(`\nsize(): got: ${sll.size()}, want: 0\n`);
  }
  // Test pop_front on empty list
  if (sll.popFront() !== null) {
    throw new Error("\npopFront() on empty list should return null\n");
  }

  // Test pop_back on empty list
  if (sll.popBack() !== null) {
    throw new Error("\npopBack() on empty list should return null\n");
  }

  // Test push_front and size
  sll.pushFront(10);
  if (sll.size() !== 1) {
    throw new Error(`\nsize(): got: ${sll.size()}, want: 1\n`);
  }

  // Test push_back and size
  sll.pushBack(20);
  if (sll.size() !== 2) {
    throw new Error(`\nsize(): got: ${sll.size()}, want: 2\n`);
  }

  // Test contains
  if (sll.contains(10) === null) {
    throw new Error("\ncontains(10) should find the node\n");
  }
  if (sll.contains(30) !== null) {
    throw new Error("\ncontains(30) should not find the node\n");
  }

  // Test pop_front
  if (sll.popFront() !== 10) {
    throw new Error("\npopFront() should return 10\n");
  }
  if (sll.size() !== 1) {
    throw new Error(`\nsize(): got: ${sll.size()}, want: 1\n`);
  }

  // Test pop_back
  if (sll.popBack() !== 20) {
    throw new Error("\npopBack() should return 20\n");
  }
  if (sll.size() !== 0) {
    throw new Error(`\nsize(): got: ${sll.size()}, want: 0\n`);
  }

  // Test push_back and pop_back
  sll.pushBack(30);
  if (sll.popBack() !== 30) {
    throw new Error("\npopBack() should return 30\n");
  }

  // Test push_front and pop_front
  sll.pushFront(40);
  if (sll.popFront() !== 40) {
    throw new Error("\npopFront() should return 40\n");
  }
}

runTests();
