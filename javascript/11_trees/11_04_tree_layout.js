// 11.4 - Tree Layout
// Run: node 11_04_tree_layout.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function mostStacked(root) {
  const posToCount = new Map();

  function visit(node, r, c) {
    if (!node) {
      return;
    }
    const key = `${r},${c}`;
    posToCount.set(key, (posToCount.get(key) || 0) + 1);
    visit(node.left, r + 1, c);
    visit(node.right, r, c + 1);
  }

  visit(root, 0, 0);
  return Math.max(...posToCount.values());
}


function runTests() {
  // Test 1: Example from the book - two nodes stacked
  const root1 = new Node(1);
  root1.left = new Node(2);
  root1.right = new Node(3);
  root1.left.left = new Node(4);
  root1.left.right = new Node(5);
  root1.left.left.right = new Node(7);
  root1.right.left = new Node(6);
  root1.right.left.left = new Node(8);
  root1.right.left.right = new Node(9);

  const root2 = new Node(1);

  const root3 = new Node(1, new Node(2), new Node(3));

  // Test 4: Perfect binary tree of depth 4
  const root4 = new Node(
    1,
    new Node(
      2,
      new Node(4, new Node(8), new Node(9, null, new Node(16))),
      new Node(
        5,
        new Node(10, null, new Node(17)),
        new Node(11, new Node(18), null),
      ),
    ),
    new Node(
      3,
      new Node(6, new Node(12), new Node(13)),
      new Node(
        7,
        new Node(14, new Node(19), null),
        new Node(15, new Node(20), null),
      ),
    ),
  );

  const tests = [
    [root1, 2], // Example from book
    [root2, 1], // Single node
    [root3, 1],
    [root4, 4],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = mostStacked(root);
    if (got !== want) {
      throw new Error(`\nmostStacked(): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
