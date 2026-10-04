// 11.15 - BST Duplicate Detection
// Run: node 11_15_bst_duplicate_detection.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function hasDuplicate(root) {
  let prevValue = -Infinity;
  let res = false;

  function visit(node) {
    if (!node || res) {
      return;
    }
    visit(node.left);
    if (node.val === prevValue) {
      res = true;
    }
    prevValue = node.val;
    visit(node.right);
  }

  visit(root);
  return res;
}


function runTests() {
  // Example 1 - BST with duplicates
  const root1 = new Node(
    5,
    new Node(2, null, new Node(4)),
    new Node(9, new Node(9, null, new Node(9)), new Node(11)),
  );

  // Example 2 - empty tree
  const root2 = null;

  // Example 3 - single node
  const root3 = new Node(1);

  // Example 4 - BST without duplicates
  const root4 = new Node(
    5,
    new Node(2, new Node(1), new Node(4)),
    new Node(8, new Node(6), new Node(9)),
  );

  const tests = [
    [root1, true], // Has duplicates (9s)
    [root2, false], // Empty tree has no duplicates
    [root3, false], // Single node has no duplicates
    [root4, false], // No duplicates
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = hasDuplicate(root);
    if (got !== want) {
      throw new Error(
        `\nhasDuplicate(root${i + 1}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
