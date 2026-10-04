// 10.13 - Duplicate Removal in Sorted Linked List
// Run: node 10_13_duplicate_removal_in_sorted_linked_list.js

class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

function removeDuplicates(head) {
  let cur = head;
  while (cur && cur.next) {
    if (cur.val === cur.next.val) {
      cur.next = cur.next.next;
    } else {
      cur = cur.next;
    }
  }
  return head;
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

  const tests = [
    // Book example
    [
      [1, 1, 1, 3, 5, 5],
      [1, 3, 5],
    ],

    // Test empty list
    [[], []],
    // Test single node
    [[1], [1]],
    // Test no duplicates
    [
      [1, 2, 3],
      [1, 2, 3],
    ],
    // Test all duplicates
    [[1, 1, 1, 1, 1], [1]],
    // Test some duplicates
    [
      [1, 1, 2, 3, 3],
      [1, 2, 3],
    ],
    // Test duplicates at start
    [
      [1, 1, 2, 3],
      [1, 2, 3],
    ],
    // Test duplicates at end
    [
      [1, 2, 3, 3],
      [1, 2, 3],
    ],
    // Test duplicates in middle
    [
      [1, 2, 2, 3],
      [1, 2, 3],
    ],
    // Test with negative numbers
    [
      [-3, -3, -2, -1, -1],
      [-3, -2, -1],
    ],
    // Test with zeros
    [
      [0, 0, 0, 1, 1],
      [0, 1],
    ],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [input, want] = tests[i];
    const head = arrayToLinkedList(input);
    const got = removeDuplicates(head);
    const gotList = linkedListToArray(got);
    if (JSON.stringify(gotList) !== JSON.stringify(want)) {
      throw new Error(
        `\nTest ${i + 1}: removeDuplicates(${JSON.stringify(input)}): got: ${gotList}, want: ${want}\n`,
      );
    }
  }
}

runTests();
