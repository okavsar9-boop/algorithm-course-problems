// 11.17 - BST Merge Into Array
// Run: node 11_17_bst_merge_into_array.js

class Node {
  constructor(val, left = null, right = null) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

function mergeIntoArray(root1, root2) {

  function inorder(root, arr) {
    if (!root) {
      return;
    }
    inorder(root.left, arr);
    arr.push(root.val);
    inorder(root.right, arr);
  }

  const arr1 = [],
    arr2 = [];
  inorder(root1, arr1);
  inorder(root2, arr2);

  // Merge sorted arrays
  const res = [];
  let i = 0,
    j = 0;
  while (i < arr1.length && j < arr2.length) {
    if (arr1[i] <= arr2[j]) {
      res.push(arr1[i]);
      i++;
    } else {
      res.push(arr2[j]);
      j++;
    }
  }

  res.push(...arr1.slice(i));
  res.push(...arr2.slice(j));
  return res;
}


function runTests() {
  const root1 = new Node(
    5,
    new Node(2, null, new Node(4)),
    new Node(9, new Node(9), new Node(11)),
  );

  const root2 = new Node(
    3,
    new Node(2, new Node(1)),
    new Node(7, new Node(6), new Node(8)),
  );

  const root3 = new Node(2, new Node(2), new Node(2));

  const root4 = new Node(2, new Node(2), new Node(2));

  const tests = [
    // Example 1 from the book
    [root1, root2, [1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9, 11]],
    // Example 2 from the book
    [root3, root4, [2, 2, 2, 2, 2, 2]],
    // Edge cases
    [null, null, []],
    [new Node(1), null, [1]],
    [null, new Node(1), [1]],
    [new Node(1), new Node(2), [1, 2]],
  ];

  for (const [root1, root2, want] of tests) {
    const got = mergeIntoArray(root1, root2);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(`\nmergeIntoArray(): got: ${got}, want: ${want}\n`);
    }
  }
}

runTests();
