// 10.6 - Linked-List Reversal
// Run: node 10_06_linked_list_reversal.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function reverseList(head) {
  let prev = null;
  let cur = head;
  while (cur) {
    const nxt = cur.next;
    cur.next = prev;
    prev = cur;
    cur = nxt;
  }
  return prev;
}


function runTests() {
  function arrayToLinkedList(arr) {
    const dummy = new Node(0);
    let current = dummy;
    for (let i = 0; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
    }
    return dummy.next;
  }

  function linkedListToArray(head) {
    const result = [];
    let current = head;
    while (current) {
      result.push(current.val);
      current = current.next;
    }
    return result;
  }

  // Test cases
  const tests = [
    // Test empty list
    [[], []],
    // Test single element list
    [[1], [1]],
    // Test multiple elements list
    [
      [1, 2, 3],
      [3, 2, 1],
    ],
    // Test list with repeated values
    [
      [1, 1, 1],
      [1, 1, 1],
    ],
    // Test list with negative values
    [
      [-1, -2, -3],
      [-3, -2, -1],
    ],
    // Test list with zero
    [[0], [0]],
    // Test longer list
    [
      [1, 2, 3, 4, 5],
      [5, 4, 3, 2, 1],
    ],
    // Test list with mixed values
    [
      [-1, 0, 1],
      [1, 0, -1],
    ],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, expected] = tests[i];
    const head = arrayToLinkedList(arr);
    const reversedHead = reverseList(head);
    const got = linkedListToArray(reversedHead);
    if (JSON.stringify(got) !== JSON.stringify(expected)) {
      throw new Error(
        `\nreverseList(${JSON.stringify(arr)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(expected)}\n`,
      );
    }
  }
}

runTests();
