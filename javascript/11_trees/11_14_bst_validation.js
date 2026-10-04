// 11.14 - BST Validation
// Run: node 11_14_bst_validation.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function isBst(root) {
  let prevValue = -Infinity;
  let res = true;

  function visit(node) {
    if (!node || !res) {
      return;
    }
    visit(node.left);
    if (node.val < prevValue) {
      res = false;
    } else {
      prevValue = node.val;
    }
    visit(node.right);
  }

  visit(root);
  return res;
}


function runTests() {
  // Example 1 - valid BST
  const root1 = new Node(
    5,
    new Node(2, null, new Node(4)),
    new Node(9, new Node(9, null, new Node(9)), new Node(11)),
  );

  // Example 2 - empty tree
  const root2 = null;

  // Example 3 - single node
  const root3 = new Node(1);

  // Example 4 - invalid BST (right child smaller than parent)
  const root4 = new Node(5, new Node(2), new Node(4));

  // Example 5 - invalid BST (left child larger than parent)
  const root5 = new Node(5, new Node(6), new Node(7));

  const tests = [
    [root1, true], // Valid BST
    [root2, true], // Empty tree is valid
    [root3, true], // Single node is valid
    [root4, false], // Invalid - right child smaller than parent
    [root5, false], // Invalid - left child larger than parent
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = isBst(root);
    if (got !== want) {
      throw new Error(`\nisBst(root${i + 1}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
