// 11.10 - Zig-Zag Order
// Run: node 11_10_zig_zag_order.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function zigZagOrder(root) {
  const res = [];
  const Q = new Queue();
  Q.push([root, 0]);
  let curLevel = [];
  let curDepth = 0;
  while (!Q.empty()) {
    const [node, depth] = Q.pop();
    if (!node) {
      continue;
    }
    if (depth > curDepth) {
      if (curDepth % 2 === 0) {
        res.push(...curLevel);
      } else {
        res.push(...curLevel.reverse()); // Reverse order
      }
      curLevel = [];
      curDepth = depth;
    }
    curLevel.push(node.val);
    Q.push([node.left, depth + 1]);
    Q.push([node.right, depth + 1]);
  }
  if (curDepth % 2 === 0) {
    // Add the last level
    res.push(...curLevel);
  } else {
    res.push(...curLevel.reverse());
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
  // Example 1 from the book
  const root1 = new Node(1);
  root1.left = new Node(2);
  root1.right = new Node(3);
  root1.left.left = new Node(4);
  root1.left.right = new Node(5);
  root1.right.left = new Node(6);
  root1.right.right = new Node(7);

  // Example 2 - empty tree
  const root2 = null;

  // Example 3 - single node
  const root3 = new Node(1);

  // Example 4 - unbalanced tree
  const root4 = new Node(1);
  root4.left = new Node(2);
  root4.left.left = new Node(3);
  root4.left.left.left = new Node(4);

  // Example 5 - complete binary tree
  const root5 = new Node(1);
  root5.left = new Node(2);
  root5.right = new Node(3);
  root5.left.left = new Node(4);
  root5.left.right = new Node(5);
  root5.right.left = new Node(6);
  root5.right.right = new Node(7);
  root5.left.left.left = new Node(8);
  root5.left.left.right = new Node(9);
  root5.left.right.left = new Node(10);
  root5.left.right.right = new Node(11);
  root5.right.left.left = new Node(12);
  root5.right.right.left = new Node(14);
  root5.right.right.right = new Node(15);

  const tests = [
    // Example 1 - basic tree
    [root1, [1, 3, 2, 4, 5, 6, 7]],
    // Example 2 - empty tree
    [root2, []],
    // Example 3 - single node
    [root3, [1]],
    // Example 4 - unbalanced tree
    [root4, [1, 2, 3, 4]],
    // Example 5 - complete binary tree
    [root5, [1, 3, 2, 4, 5, 6, 7, 15, 14, 12, 11, 10, 9, 8]],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = zigZagOrder(root);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nExample ${i + 1}: zigZagOrder(): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
