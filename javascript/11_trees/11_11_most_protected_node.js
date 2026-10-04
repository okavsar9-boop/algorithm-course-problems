// 11.11 - Most Protected Node
// Run: node 11_11_most_protected_node.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function mostProtectedNode(root) {
  if (!root) {
    return 0;
  }

  // Map from node to its current minimum protection level
  const protection = new Map();
  const nodesPerLevel = new Map();
  const nodeToIndexInLevel = new Map();

  // First pass: BFS to get depth and position in level
  const Q = new Queue();
  Q.push([root, 0]);
  while (!Q.empty()) {
    const [node, depth] = Q.pop();
    if (!node) {
      continue;
    }

    // Add node to its level
    nodesPerLevel.set(depth, (nodesPerLevel.get(depth) || 0) + 1);
    // Store node's position in its level
    nodeToIndexInLevel.set(node, nodesPerLevel.get(depth) - 1);

    // Update protection with min of ancestor count (depth) and left count
    protection.set(node, Math.min(depth, nodeToIndexInLevel.get(node)));

    Q.push([node.left, depth + 1]);
    Q.push([node.right, depth + 1]);
  }

  // Second pass: DFS to get descendant heights
  // Passes current depth down the tree.
  // Passes subtree height up the tree.
  // Updates protection level of nodes in global map.
  function dfs(node, depth) {
    if (!node) {
      return -1;
    }
    const height =
      1 + Math.max(dfs(node.left, depth + 1), dfs(node.right, depth + 1));

    protection.set(node, Math.min(protection.get(node), height));

    // Update protection with right counts
    protection.set(
      node,
      Math.min(
        protection.get(node),
        nodesPerLevel.get(depth) - nodeToIndexInLevel.get(node) - 1,
      ),
    );

    return height;
  }

  dfs(root, 0);

  // Return highest protection level
  return Math.max(...protection.values());
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
  const root = new Node(
    1,
    new Node(
      2,
      new Node(
        3,
        new Node(
          4,
          new Node(5, new Node(6)),
          new Node(7, new Node(8), new Node(9)),
        ),
        new Node(10, new Node(11)),
      ),
      new Node(12, new Node(13, new Node(14, new Node(15), new Node(16)))),
    ),
    new Node(
      17,
      new Node(18, new Node(19, new Node(20, new Node(21)))),
      new Node(22, new Node(23, new Node(24, new Node(25)))),
    ),
  );

  function perfectTree(height) {
    if (height === 1) {
      return new Node(1);
    }
    return new Node(1, perfectTree(height - 1), perfectTree(height - 1));
  }

  const tests = [
    [root, 2],
    [new Node(1), 0], // Single node
    [new Node(1, new Node(2, new Node(3, new Node(4)))), 0], // Linear tree
    [perfectTree(1), 0],
    [perfectTree(2), 0],
    [perfectTree(3), 0],
    [perfectTree(4), 1],
    [perfectTree(5), 1],
    [perfectTree(6), 2],
    [perfectTree(7), 3],
    [perfectTree(8), 3],
    [perfectTree(9), 4],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = mostProtectedNode(root);
    if (got !== want) {
      throw new Error(
        `\nExample ${i + 1}: mostProtectedNode(): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
