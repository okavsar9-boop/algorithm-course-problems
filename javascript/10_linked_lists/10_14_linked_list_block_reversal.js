// 10.14 - Linked List Block Reversal
// Run: node 10_14_linked_list_block_reversal.js

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

function reverseKGroup(head, k) {
  const dummy = new Node(0);
  dummy.next = head;
  let groupPrev = dummy;

  while (true) {
    // 1. Find the bounds of the current block
    let kth = groupPrev;
    for (let i = 0; i < k; i++) {
      kth = kth.next;
      if (!kth) {
        return dummy.next;
      }
    }
    const groupNext = kth.next;

    // 2. Break the block out from the rest of the list
    kth.next = null;
    const groupHead = groupPrev.next;

    // 3. Reverse the block
    const reversedHead = reverseList(groupHead);

    // 4. Reattach the reversed block
    groupPrev.next = reversedHead;
    groupHead.next = groupNext;
    groupPrev = groupHead;
  }
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
    [[1, 2, 3, 4], 2, [2, 1, 4, 3]],
    [[1, 2, 3, 4, 5], 3, [3, 2, 1, 4, 5]],

    [[1, 2, 3, 4, 5, 6], 2, [2, 1, 4, 3, 6, 5]],
    // Test empty list
    [[], 2, []],
    // Test single element list
    [[1], 2, [1]],
    // Test k greater than list length
    [[1, 2, 3], 4, [1, 2, 3]],
    // Test k equal to list length
    [[1, 2, 3], 3, [3, 2, 1]],
    // Test k less than list length
    [[1, 2, 3, 4, 5], 2, [2, 1, 4, 3, 5]],
    // Test k is 1 (no change)
    [[1, 2, 3, 4, 5], 1, [1, 2, 3, 4, 5]],
    // Test list with repeated values
    [[1, 1, 1, 2, 2], 2, [1, 1, 2, 1, 2]],
    // Test list with negative values
    [[-1, -2, -3, -4], 2, [-2, -1, -4, -3]],
    // Test list with zero
    [[0, 1, 2], 2, [1, 0, 2]],
    // Test longer list
    [[1, 2, 3, 4, 5, 6, 7, 8], 3, [3, 2, 1, 6, 5, 4, 7, 8]],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, k, expected] = tests[i];
    const head = arrayToLinkedList(arr);
    const reversedHead = reverseKGroup(head, k);
    const got = linkedListToArray(reversedHead);
    if (JSON.stringify(got) !== JSON.stringify(expected)) {
      throw new Error(`\nTest ${i + 1}: got: ${got}, want: ${expected}\n`);
    }
  }
}

runTests();
