// 11.5 - Triangle Count
// Run: node 11_05_triangle_count.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function triangleCount(root) {
  let res = 0;

  function visit(node) {
    if (!node) {
      return [0, 0]; // left_side, right_side counts
    }

    const [leftSide, unused1] = visit(node.left); // Only care about left descendants
    const [unused2, rightSide] = visit(node.right); // Only care about right descendants

    // Number of triangles with this node at the top is min of left and right sides
    res += Math.min(leftSide, rightSide);

    // Return counts of consecutive left/right descendants
    return [leftSide + 1, rightSide + 1];
  }

  visit(root);
  return res;
}


function runTests() {
  const tests = [
    // Example
    [
      new Node(
        1,
        new Node(2, new Node(4), new Node(5)),
        new Node(3, new Node(6), new Node(7)),
      ),
      4,
    ],
    [null, 0], // Empty tree
    [new Node(1), 0], // Single node
    // No triangles - only left children
    [new Node(1, new Node(2, new Node(3), null), null), 0],
    // No triangles - only right children
    [new Node(1, null, new Node(2, null, new Node(3))), 0],
    [new Node(1, new Node(2), new Node(3)), 1],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = triangleCount(root);
    if (got !== want) {
      throw new Error(`\ntriangleCount(): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
