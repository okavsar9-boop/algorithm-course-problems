// 10.9 - Doubly Linked List To Array
// Run: node 10_09_doubly_linked_list_to_array.js

class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

function convertToArray(node) {
  let cur = node;
  while (cur.prev) {
    cur = cur.prev;
  }
  const res = [];
  while (cur) {
    res.push(cur.val);
    cur = cur.next;
  }
  return res;
}


function runTests() {

  function createDoublyLinkedList(arr) {
    const head = new Node(arr[0]);
    let cur = head;
    for (let i = 1; i < arr.length; i++) {
      const newNode = new Node(arr[i]);
      cur.next = newNode;
      newNode.prev = cur;
      cur = newNode;
    }
    return head;
  }

  function nodeAtIndex(head, index) {
    let cur = head;
    for (let i = 0; i < index; i++) {
      cur = cur.next;
    }
    return cur;
  }

  const tests = [
    // Examples from the book
    [[1, 2, 3, 4], 2],
    [[1, 2, 3, 4], 0],

    [[1, 2, 3, 4, 5], 0],
    [[1, 2, 3, 4, 5], 1],
    [[1, 2, 3, 4, 5], 2],
    [[1, 2, 3, 4, 5], 3],
    [[1, 2, 3, 4, 5], 4],
    // Test single node
    [[1], 0],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, index] = tests[i];
    const head = createDoublyLinkedList(arr);
    const node = nodeAtIndex(head, index);
    const got = convertToArray(node);
    if (JSON.stringify(got) !== JSON.stringify(arr)) {
      throw new Error(
        `\nTest ${i + 1}: got: ${JSON.stringify(got)}, want: ${JSON.stringify(arr)}\n`,
      );
    }
  }
}

runTests();
