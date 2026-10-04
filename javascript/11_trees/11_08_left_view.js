// 11.8 - Left View
// Run: node 11_08_left_view.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function leftView(root) {
  if (!root) {
    return [];
  }
  const Q = new Queue();
  Q.push([root, 0]);
  const res = [root.val];
  let currentDepth = 0;
  while (!Q.empty()) {
    const [node, depth] = Q.pop();
    if (!node) {
      continue;
    }
    if (depth === currentDepth + 1) {
      res.push(node.val);
      currentDepth += 1;
    }
    Q.push([node.left, depth + 1]);
    Q.push([node.right, depth + 1]);
  }
  return res;
}

class QueueNode {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

class Queue {
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
    const newNode = new QueueNode(val);
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
      throw new Error("empty queue");
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
  // Test 1
  const root1 = new Node(
    1,
    new Node(2, new Node(4), new Node(5)),
    new Node(3, null, new Node(6)),
  );

  // Test 2: Empty tree
  const root2 = null;

  // Test 3: Single node
  const root3 = new Node(1);

  // Test 4: Only right children
  const root4 = new Node(1, null, new Node(2, null, new Node(3)));

  // Test 5: Only left children
  const root5 = new Node(1, new Node(2, new Node(3), null), null);

  // Test 6: Example from the book
  const root6 = new Node(
    5,
    new Node(2, null, new Node(6)),
    new Node(9, new Node(9, null, new Node(1)), new Node(8)),
  );

  const tests = [
    [root1, [1, 2, 4]], // Example
    [root2, []], // Empty tree
    [root3, [1]], // Single node
    [root4, [1, 2, 3]], // Only right children
    [root5, [1, 2, 3]], // Only left children
    [root6, [5, 2, 6, 1]], // Example from the book
  ];

  for (const [i, [root, want]] of tests.entries()) {
    const got = leftView(root);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(`\nleftView(root${i + 1}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
