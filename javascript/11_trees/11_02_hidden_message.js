// 11.2 - Hidden Message
// Run: node 11_02_hidden_message.js

class Node {
  constructor(text, left = null, right = null) {
    this.text = text;
    this.left = left;
    this.right = right;
  }
}

function hiddenMessage(root) {
  const message = [];

  function visit(node) {
    if (!node) {
      return;
    }
    if (node.text[0] === "b") {
      message.push(node.text[1]);
      visit(node.left);
      visit(node.right);
    } else if (node.text[0] === "i") {
      visit(node.left);
      message.push(node.text[1]);
      visit(node.right);
    } else {
      visit(node.left);
      visit(node.right);
      message.push(node.text[1]);
    }
  }

  visit(root);
  return message.join("");
}


function runTests() {
  // Test 1: Example from the book - "nice_try!"
  const root1 = new Node("bn");
  root1.left = new Node("i_");
  root1.left.left = new Node("ae");
  root1.left.right = new Node("it");
  root1.left.left.left = new Node("bi");
  root1.left.left.right = new Node("bc");
  root1.right = new Node("a!");
  root1.right.left = new Node("br");
  root1.right.right = new Node("ay");

  // Test 2: Empty tree
  const root2 = null;

  // Test 3: Single TreeNode with before order
  const root3 = new Node("bx");

  // Test 4: Single TreeNode with in order
  const root4 = new Node("ix");

  // Test 5: Single TreeNode with after order
  const root5 = new Node("ax");

  // Test 6: All before order TreeNodes
  const root6 = new Node(
    "b1",
    new Node("b2", new Node("b4", null, null), new Node("b5", null, null)),
    new Node("b3", new Node("b6", null, null), new Node("b7", null, null)),
  );

  // Test 7: All in order TreeNodes
  const root7 = new Node(
    "i1",
    new Node("i2", new Node("i4", null, null), new Node("i5", null, null)),
    new Node("i3", new Node("i6", null, null), new Node("i7", null, null)),
  );

  // Test 8: All after order TreeNodes
  const root8 = new Node(
    "a1",
    new Node("a2", new Node("a4", null, null), new Node("a5", null, null)),
    new Node("a3", new Node("a6", null, null), new Node("a7", null, null)),
  );

  // Test 9: Mixed orders forming "hello"
  const root9 = new Node(
    "bh",
    new Node("be", new Node("bl", null, null), new Node("il", null, null)),
    new Node("ao", null, null),
  );

  const tests = [
    [root1, "nice_try!"], // Example from book
    [root2, ""], // Empty tree
    [root3, "x"], // Single TreeNode before
    [root4, "x"], // Single TreeNode in
    [root5, "x"], // Single TreeNode after
    [root6, "1245367"], // All before order
    [root7, "4251637"], // All in order
    [root8, "4526731"], // All after order
    [root9, "hello"], // Mixed orders spelling "hello"
  ];

  // Run normal test cases
  for (let i = 0; i < tests.length; i++) {
    const [root, want] = tests[i];
    const got = hiddenMessage(root);
    if (got !== want) {
      throw new Error(`\nhiddenMessage(): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
