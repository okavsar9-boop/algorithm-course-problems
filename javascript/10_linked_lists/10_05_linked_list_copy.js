// 10.5 - Linked-List Copy
// Run: node 10_05_linked_list_copy.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function copyList(head) {
  if (!head) {
    return null;
  }
  const newHead = new Node(head.val);
  let curNew = newHead;
  let curOld = head.next;
  while (curOld) {
    curNew.next = new Node(curOld.val);
    curNew = curNew.next;
    curOld = curOld.next;
  }
  return newHead;
}

function copyListWithDummy(head) {
  const dummy = new Node(0);
  let curNew = dummy;
  let curOld = head;
  while (curOld) {
    curNew.next = new Node(curOld.val);
    curNew = curNew.next;
    curOld = curOld.next;
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
    // Test empty list
    [],
    // Test single element list
    [1],
    // Test multiple elements list
    [1, 2, 3],
    // Test list with repeated values
    [1, 1, 1],
    // Test list with negative values
    [-1, -2, -3],
    // Test list with zero
    [0],
    // Test longer list
    [1, 2, 3, 4, 5],
    // Test list with mixed values
    [-1, 0, 1],
  ];

  for (let i = 0; i < tests.length; i++) {
    const arr = tests[i];
    const head = arrayToLinkedList(arr);

    // Test first copyList function
    const copiedHead1 = copyList(head);
    const got1 = linkedListToArray(copiedHead1);
    if (JSON.stringify(got1) !== JSON.stringify(arr)) {
      throw new Error(
        `\nTest ${i + 1} (copyList 1): got: ${got1}, want: ${arr}\n`,
      );
    }

    // Test second copyList function
    const copiedHead2 = copyListWithDummy(head);
    const got2 = linkedListToArray(copiedHead2);
    if (JSON.stringify(got2) !== JSON.stringify(arr)) {
      throw new Error(
        `\nTest ${i + 1} (copyList 2): got: ${got2}, want: ${arr}\n`,
      );
    }
  }
}

runTests();
