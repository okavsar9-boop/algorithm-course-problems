// 11.7 - Evaluate Expression Tree
// Run: node 11_07_evaluate_expression_tree.js

class Node {
  constructor(kind, num, children) {
    this.kind = kind; // One of "sum", "product", "max", "min", or "num".
    this.num = num; // Only valid when kind is "num".
    this.children = children; // Only valid when kind is not "num".
  }
}

function product(vals) {
  return vals.reduce((acc, val) => acc * val, 1);
}

function evaluate(root) {
  if (root.kind === "num") {
    return root.num;
  }
  const childrenEvals = root.children.map((child) => evaluate(child));
  if (root.kind === "sum") {
    return childrenEvals.reduce((acc, val) => acc + val, 0);
  }
  if (root.kind === "product") {
    return product(childrenEvals);
  }
  if (root.kind === "max") {
    return Math.max(...childrenEvals);
  }
  if (root.kind === "min") {
    return Math.min(...childrenEvals);
  }
  throw new Error("Invalid node kind");
}


function runTests() {
  // Test 0: Example from the book
  const root0 = new Node("min", null, [
    new Node("max", null, [
      new Node("num", 4, null),
      new Node("num", 6, null),
      new Node("sum", null, [
        new Node("num", 5, null),
        new Node("num", 7, null),
      ]),
    ]),
    new Node("sum", null, [
      new Node("product", null, [
        new Node("num", 6, null),
        new Node("num", 8, null),
      ]),
    ]),
  ]);

  // Test 1: Example - (2 + 3) * 4
  const root1 = new Node("product", null, [
    new Node("sum", null, [new Node("num", 2, null), new Node("num", 3, null)]),
    new Node("num", 4, null),
  ]);

  // Test 2: Single number node
  const root2 = new Node("num", 5, null);

  // Test 3: Empty sum node
  const root3 = new Node("sum", null, []);

  // Test 4: Empty product node
  const root4 = new Node("product", null, []);

  // Test 5: Complex expression with all operations
  // min(2, max(3,4)) + product(1,2,3)
  const root5 = new Node("sum", null, [
    new Node("min", null, [
      new Node("num", 2, null),
      new Node("max", null, [
        new Node("num", 3, null),
        new Node("num", 4, null),
      ]),
    ]),
    new Node("product", null, [
      new Node("num", 1, null),
      new Node("num", 2, null),
      new Node("num", 3, null),
    ]),
  ]);

  const tests = [
    [root0, 12],
    [root1, 20], // (2 + 3) * 4 = 20
    [root2, 5], // Single number
    [root3, 0], // Empty sum = 0
    [root4, 1], // Empty product = 1
    [root5, 8], // min(2,max(3,4)) + product(1,2,3) = 2 + 6 = 8
    [root0.children[0], 12],
    [root0.children[1], 48],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = evaluate(root);
    if (got !== want) {
      throw new Error(`\nevaluate(root${i + 1}): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
