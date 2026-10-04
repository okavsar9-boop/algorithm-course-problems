// 11.9 - Most Prolific Level
// Run: node 11_09_most_prolific_level.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function mostProlificLevel(root) {

  function levelCounts(root) {
    const Q = new Queue();
    Q.push([root, 0]);
    const levelCount = new Map();
    while (!Q.empty()) {
      const [node, depth] = Q.pop();
      if (!node) {
        continue;
      }
      levelCount.set(depth, (levelCount.get(depth) || 0) + 1);
      Q.push([node.left, depth + 1]);
      Q.push([node.right, depth + 1]);
    }
    return levelCount;
  }

  const levelCount = levelCounts(root);
  let res = -1;
  let maxProlificness = -1; // Less than any valid prolificness
  for (const [level, count] of levelCount.entries()) {
    const nextLevelCount = levelCount.get(level + 1) || 0;
    const prolificness = nextLevelCount / count;
    if (prolificness > maxProlificness) {
      maxProlificness = prolificness;
      res = level;
    }
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
    5,
    new Node(2, null, new Node(6)),
    new Node(9, new Node(9, null, new Node(1)), new Node(8)),
  );

  // Test 2: Empty tree
  const root2 = null;

  // Test 3: Single node
  const root3 = new Node(1);

  // Test 4: Perfect binary tree
  const root4 = new Node(
    1,
    new Node(2, new Node(4), new Node(5)),
    new Node(3, new Node(6), new Node(7)),
  );

  // Test 5: Unbalanced tree
  const root5 = new Node(
    1,
    new Node(2, new Node(4, new Node(8), new Node(9)), new Node(5)),
    new Node(3),
  );

  // Test 6: Example from the book
  const root6 = new Node(
    1,
    new Node(
      2,
      new Node(4, new Node(8), new Node(9)),
      new Node(5, null, new Node(11)),
    ),
    null,
  );

  // Test 7
  const root7 = new Node(1, new Node(2, new Node(4, new Node(8), new Node(9))));

  const tests = [
    [root1, [0]],
    [root2, [-1]], // Empty tree
    [root3, [0]], // Single node: level 0 has prolificness 0
    // Level 0->1 and 1->2 both have prolificness 2
    [root4, [0, 1]], // Both level 0 and 1 are valid answers
    [root5, [0]],
    [root6, [1]],
    [root7, [2]],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, validWants] = tests[i];
    const got = mostProlificLevel(root);
    if (!validWants.includes(got)) {
      throw new Error(
        `\nmostProlificLevel(root${i + 1}): got: ${got}, valid_wants: ${JSON.stringify(validWants)}\n`,
      );
    }
  }
}

runTests();
