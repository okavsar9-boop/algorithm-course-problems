// 11.13 - BST Nearest Value
// Run: node 11_13_bst_nearest_value.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function findClosest(root, target) {
  let curNode = root;
  let nextAbove = Infinity;
  let nextBelow = -Infinity;
  while (curNode) {
    if (curNode.val === target) {
      return curNode.val;
    } else if (curNode.val > target) {
      nextAbove = curNode.val;
      curNode = curNode.left;
    } else {
      nextBelow = curNode.val;
      curNode = curNode.right;
    }
  }
  if (nextAbove - target < target - nextBelow) {
    return nextAbove;
  }
  return nextBelow;
}


function runTests() {
  const root1 = new Node(
    5,
    new Node(2, null, new Node(4)),
    new Node(9, new Node(9, null, new Node(9)), new Node(11)),
  );

  // Single node
  const root2 = null;

  // Perfect BST
  const root3 = new Node(1);

  // Unbalanced BST
  const root4 = new Node(
    4,
    new Node(2, new Node(1), new Node(3)),
    new Node(6, new Node(5), new Node(7)),
  );

  // Unbalanced BST
  const root5 = new Node(
    5,
    new Node(3, new Node(2, new Node(1), null), new Node(4)),
    null,
  );

  // Example from the book
  const root6 = new Node(
    8,
    new Node(
      6,
      new Node(5, new Node(2), new Node(6)),
      new Node(8, new Node(8), new Node(8)),
    ),
    new Node(12, new Node(10, new Node(9), null), null),
  );

  const tests = [
    [root1, 6, 5], // Closest to 6 is 5
    [root1, 9, 9], // Exact match
    [root1, 3, 2], // Closest to 3 is 2
    [root1, 4, 4], // Exact match
    [root3, 1, 1], // Single node, exact match
    [root3, 2, 1], // Single node, closest is 1
    [root4, 5, 5], // Perfect BST, exact match
    [root4, 8, 7], // Perfect BST, closest is 7
    [root5, 1, 1], // Unbalanced BST, exact match at leaf
    [root5, 5, 5], // Unbalanced BST, exact match at root
    [root5, 6, 5], // Unbalanced BST, closest is 5
    [root6, 9, 9],
    [root6, 13, 12],
    [root6, 1, 2],
    [root6, 8, 8],
    [root6, 6, 6],
    [root6, 7, 6],
    [root6, 11, 10],
    [root6, 4, 5],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, target, want] = tests[i];
    const got = findClosest(root, target);
    if (got !== want) {
      throw new Error(
        `\nfindClosest(root${i + 1}, ${target}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
