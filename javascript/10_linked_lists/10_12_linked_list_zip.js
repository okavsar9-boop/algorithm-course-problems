// 10.12 - Linked-List Zip
// Run: node 10_12_linked_list_zip.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function merge(head1, head2) {
  const dummy = new Node(0);
  let cur = dummy;

  let p1 = head1,
    p2 = head2;
  while (p1 && p2) {
    cur.next = p1;
    cur = cur.next;
    p1 = p1.next;

    cur.next = p2;
    p2 = p2.next;
    cur = cur.next;
  }

  if (p1) {
    cur.next = p1;
  } else {
    cur.next = p2;
  }

  return dummy.next;
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
    // Book examples
    [
      [1, 3, 5],
      [2, 4, 6],
      [1, 2, 3, 4, 5, 6],
    ],
    [
      [1, 2, 3, 4],
      [8, 7],
      [1, 8, 2, 7, 3, 4],
    ],

    // Test empty lists
    [[], [], []],
    // Test one empty list
    [[1, 2], [], [1, 2]],
    [[], [1, 2], [1, 2]],
    // Test equal length lists
    [
      [1, 3],
      [2, 4],
      [1, 2, 3, 4],
    ],
    // Test different length lists
    [
      [1, 3, 5],
      [2, 4],
      [1, 2, 3, 4, 5],
    ],
    [
      [1, 3],
      [2, 4, 6],
      [1, 2, 3, 4, 6],
    ],
    // Test with negative numbers
    [
      [-1, -3],
      [-2, -4],
      [-1, -2, -3, -4],
    ],
    // Test with zeros
    [
      [0, 0],
      [0, 0],
      [0, 0, 0, 0],
    ],
    // Test longer lists
    [
      [1, 3, 5, 7, 9],
      [2, 4, 6, 8, 10],
      [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    ],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [list1, list2, want] = tests[i];
    const head1 = arrayToLinkedList(list1);
    const head2 = arrayToLinkedList(list2);
    const got = merge(head1, head2);
    const gotList = linkedListToArray(got);
    if (JSON.stringify(gotList) !== JSON.stringify(want)) {
      throw new Error(
        `\nmerge(${JSON.stringify(list1)}, ${JSON.stringify(list2)}): got: ${JSON.stringify(gotList)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
