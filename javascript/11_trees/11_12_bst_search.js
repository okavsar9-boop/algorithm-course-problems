// 11.12 - BST Search
// Run: node 11_12_bst_search.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function containsTarget(root, target) {
  let curNode = root;
  while (curNode) {
    if (curNode.val === target) {
      return true;
    } else if (curNode.val > target) {
      curNode = curNode.left;
    } else {
      curNode = curNode.right;
    }
  }
  return false;
}


function runTests() {
  // Test 1
  const root1 = new Node(
    5,
    new Node(2, null, new Node(4)),
    new Node(9, new Node(9, null, new Node(9)), new Node(11)),
  );

  // Test 2: Empty tree
  const root2 = null;

  // Test 3: Single node
  const root3 = new Node(1);

  // Test 4: Perfect BST
  const root4 = new Node(
    4,
    new Node(2, new Node(1), new Node(3)),
    new Node(6, new Node(5), new Node(7)),
  );

  // Test 5: Unbalanced BST
  const root5 = new Node(
    5,
    new Node(3, new Node(2, new Node(1), null), new Node(4)),
    null,
  );

  const tests = [
    [root1, 6, false],
    [root1, 9, true],
    [root1, 3, false],
    [root1, 4, true],
    [root2, 1, false], // Empty tree
    [root3, 1, true], // Single node, target exists
    [root3, 2, false], // Single node, target doesn't exist
    [root4, 5, true], // Perfect BST, target exists
    [root4, 8, false], // Perfect BST, target doesn't exist
    [root5, 1, true], // Unbalanced BST, target exists at leaf
    [root5, 5, true], // Unbalanced BST, target exists at root
    [root5, 6, false], // Unbalanced BST, target doesn't exist
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, target, want] = tests[i];
    const got = containsTarget(root, target);
    if (got !== want) {
      throw new Error(
        `\nfind(root${i + 1}, ${target}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
