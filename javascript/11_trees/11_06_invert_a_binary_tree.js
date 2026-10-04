// 11.6 - Invert a Binary Tree
// Run: node 11_06_invert_a_binary_tree.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function invert(root) {
  if (!root) {
    return null;
  }
  [root.left, root.right] = [invert(root.right), invert(root.left)];
  return root;
}


function runTests() {
  // Test 1: Example from the book - tree with 4 triangles
  const root1a = new Node(
    1,
    new Node(6, new Node(4, null, new Node(5)), new Node(11)),
    new Node(7, new Node(2, null, new Node(9)), null),
  );
  const root1b = new Node(1);
  root1b.left = new Node(7);
  root1b.right = new Node(6);
  root1b.left.right = new Node(2);
  root1b.left.right.left = new Node(9);
  root1b.right.left = new Node(11);
  root1b.right.right = new Node(4);
  root1b.right.right.left = new Node(5);

  // Test 2: Empty tree
  const root2 = null;

  // Test 3: Single node
  const root3 = new Node(1);

  const root4a = new Node(1, new Node(2, new Node(3), null), null);
  const root4b = new Node(1, null, new Node(2, null, new Node(3)));

  const tests = [
    [root1a, root1b], // Example from book
    [root2, null], // Empty tree
    [root3, root3], // Single node
    [root4a, root4b],
  ];

  function sameValues(t1, t2) {
    if (!t1 && !t2) {
      return true;
    }
    if (!t1 || !t2) {
      return false;
    }
    return (
      t1.val === t2.val &&
      sameValues(t1.left, t2.left) &&
      sameValues(t1.right, t2.right)
    );
  }

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = invert(root);
    if (!sameValues(got, want)) {
      throw new Error(`\ninvert(): got != want\n`);
    }
  }
}

runTests();
