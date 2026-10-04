// 11.1 - Aligned Chain
// Run: node 11_01_aligned_chain.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function longestAlignedChain(root) {
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
      res = Math.max(res, currentChain);
    }
    return currentChain;
  }

  visit(root, 0);
  return res;
}


function runTests() {
  const tests = [
    // Test 1: from the book
    [
      new Node(
        7,
        new Node(1, new Node(2, new Node(4), new Node(3)), new Node(8)),
        new Node(3, new Node(2, new Node(3))),
      ),
      3,
    ],
    // Test 2
    [
      new Node(
        0,
        new Node(1, new Node(2, new Node(3), null), new Node(4)),
        new Node(5),
      ),
      4,
    ],
    // Test 3: Empty tree
    [null, 0],
    // Test 4: Single node aligned at root
    [new Node(0), 1],
    // Test 5: Single node not aligned
    [new Node(1), 0],
    // Test 6: Multiple valid chains, should return longest
    [
      new Node(
        0,
        new Node(
          1,
          new Node(2, new Node(4), null),
          new Node(2, new Node(3), null),
        ),
      ),
      4,
    ],
    // Test 7: No aligned nodes
    [new Node(5, new Node(4, new Node(3), new Node(3)), new Node(2)), 0],
    // Test 8
    [new Node(0, new Node(1), new Node(1)), 2],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = longestAlignedChain(root);
    if (got !== want) {
      throw new Error(`\nTest ${i + 1} failed! Got: ${got}, Want: ${want}`);
    }
  }
}

runTests();
