// 10.2 - Doubly Linked List Design
// Run: node 10_02_doubly_linked_list_design.js

class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

class DoublyLinkedList {
  constructor() {
    this.head = null;
    this.tail = null;
    this._size = 0;
  }

  size() {
    return this._size;
  }

  pushFront(val) {
    const newNode = new Node(val);
    if (!this.head) {
      this.head = this.tail = newNode;
    } else {
      newNode.next = this.head;
      this.head.prev = newNode;
      this.head = newNode;
    }
    this._size++;
  }

  popFront() {
    if (!this.head) {
      return null;
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

  pushBack(val) {
    const newNode = new Node(val);
    if (!this.tail) {
      this.head = this.tail = newNode;
    } else {
      newNode.prev = this.tail;
      this.tail.next = newNode;
      this.tail = newNode;
    }
    this._size++;
  }

  popBack() {
    if (!this.tail) {
      return null;
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
  const dll = new DoublyLinkedList();

  // Test size on empty list
  if (dll.size() !== 0) {
    throw new Error(`\nsize(): got: ${dll.size()}, want: 0\n`);
  }

  // Test pop_front on empty list
  if (dll.popFront() !== null) {
    throw new Error("\npopFront() on empty list should return null\n");
  }

  // Test pop_back on empty list
  if (dll.popBack() !== null) {
    throw new Error("\npopBack() on empty list should return null\n");
  }

  // Test push_front and size
  dll.pushFront(10);
  if (dll.size() !== 1) {
    throw new Error(`\nsize(): got: ${dll.size()}, want: 1\n`);
  }

  // Test push_back and size
  dll.pushBack(20);
  if (dll.size() !== 2) {
    throw new Error(`\nsize(): got: ${dll.size()}, want: 2\n`);
  }

  // Test contains
  if (dll.contains(10) === null) {
    throw new Error("\ncontains(10) should find the node\n");
  }
  if (dll.contains(30) !== null) {
    throw new Error("\ncontains(30) should not find the node\n");
  }

  // Test pop_front
  if (dll.popFront() !== 10) {
    throw new Error("\npopFront() should return 10\n");
  }
  if (dll.size() !== 1) {
    throw new Error(`\nsize(): got: ${dll.size()}, want: 1\n`);
  }

  // Test pop_back
  if (dll.popBack() !== 20) {
    throw new Error("\npopBack() should return 20\n");
  }
  if (dll.size() !== 0) {
    throw new Error(`\nsize(): got: ${dll.size()}, want: 0\n`);
  }

  // Test push_back and pop_back
  dll.pushBack(30);
  if (dll.popBack() !== 30) {
    throw new Error("\npopBack() should return 30\n");
  }

  // Test push_front and pop_front
  dll.pushFront(40);
  if (dll.popFront() !== 40) {
    throw new Error("\npopFront() should return 40\n");
  }
}

runTests();
