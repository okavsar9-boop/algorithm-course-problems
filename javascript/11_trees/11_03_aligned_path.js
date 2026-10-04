// 11.3 - Aligned Path
// Run: node 11_03_aligned_path.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function alignedPath(root) {
  let res = 0;

  function visit(node, depth) {
    if (!node) {
      return 0;
    }
    const leftChain = visit(node.left, depth + 1);
    const rightChain = visit(node.right, depth + 1);
    let currentChain = 0;
    if (node.val === depth) {
      currentChain = 1 + Math.max(leftChain, rightChain);
      // For each aligned node, try using it as the highest node in the path
      res = Math.max(res, leftChain + rightChain + 1);
    }
    return currentChain;
  }

  visit(root, 0);
  return res;
}


function runTests() {
  const tests = [
    // Test 1: Example from the book
    [
      new Node(
        7,
        new Node(1, new Node(2, new Node(4), new Node(3)), new Node(8)),
        new Node(3, new Node(2, new Node(3), new Node(3))),
      ),
      3,
    ],
    // Variation 1
    [
      new Node(
        7,
        new Node(1, new Node(20, new Node(4), new Node(3)), new Node(8)),
        new Node(3, new Node(2, new Node(3), new Node(3))),
      ),
      3,
    ],
    // Variation 2
    [
      new Node(
        7,
        new Node(1, new Node(2, new Node(4), new Node(3)), new Node(8)),
        new Node(3, new Node(20, new Node(3), new Node(3))),
      ),
      3,
    ],
    // Variation 3
    [
      new Node(
        7,
        new Node(1, new Node(20, new Node(4), new Node(3)), new Node(8)),
        new Node(3, new Node(20, new Node(3), new Node(3))),
      ),
      1,
    ],
    // Test 2: Empty tree
    [null, 0],
    // Test 3: Single aligned node
    [new Node(0), 1],
    // Test 4: Single unaligned node
    [new Node(1), 0],
    // Test 5: Path through root
    [new Node(0, new Node(1), new Node(1)), 3],
    // Test 6: No aligned nodes
    [new Node(5, new Node(4), new Node(2)), 0],
    // Test 7
    [new Node(0, new Node(1, new Node(2), new Node(2)), new Node(1)), 4],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = alignedPath(root);
    if (got !== want) {
      throw new Error(`\nalignedPath(): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
