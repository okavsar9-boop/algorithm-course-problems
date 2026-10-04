// 11.16 - BST Kth Element
// Run: node 11_16_bst_kth_element.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function kthElement(root, k) {
  let steps = 0;
  let res = null;

  function visit(node) {
    if (!node || res !== null) {
      return;
    }
    visit(node.left);
    if (steps === k) {
      res = node.val;
    }
    steps++;
    visit(node.right);
  }

  visit(root);
  return res;
}


function runTests() {
  const root = new Node(
    5,
    new Node(2, new Node(1), new Node(4)),
    new Node(8, new Node(6), new Node(9)),
  );

  const tests = [
    [
      new Node(
        5,
        new Node(2, null, new Node(4)),
        new Node(9, new Node(9), new Node(11)),
      ),
      4,
      9,
    ],
    [new Node(1), 0, 1], // Single node
    [root, 0, 1],
    [root, 1, 2],
    [root, 2, 4],
    [root, 3, 5],
    [root, 4, 6],
    [root, 5, 8],
    [root, 6, 9],
  ];

  for (const [root, k, want] of tests) {
    const got = kthElement(root, k);
    if (got !== want) {
      throw new Error(`\nkthElement(root, ${k}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
