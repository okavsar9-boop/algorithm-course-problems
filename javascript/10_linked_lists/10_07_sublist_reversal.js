// 10.7 - Sublist Reversal
// Run: node 10_07_sublist_reversal.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function nodeAtIndex(head, index) {
  /*
    - The index is negative.
    */
  if (index < 0) {
    // Invalid index
    return null;
  }

  let cur = head;
  let i = 0;

  while (cur) {
    if (i === index) {
      return cur;
    }
    cur = cur.next;
    i++;
  }

  // If we traverse the whole list and don't find the index
  return null;
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

function reverseSection(head, left, right) {
  const dummy = new Node(0);
  dummy.next = head;

  // Step 1: find the nodes BEFORE and AFTER the section.
  let prev;
  if (left === 0) {
    prev = dummy;
  } else {
    prev = nodeAtIndex(head, left - 1);
  }
  if (!prev || !prev.next) {
    // Nothing to reverse.
    return head;
  }
  const nxt = nodeAtIndex(head, right + 1); // May be null.

  // Step 2: break out the section.
  const sectionHead = prev.next;
  prev.next = null;
  let sectionTail = sectionHead;
  while (sectionTail.next !== nxt) {
    sectionTail = sectionTail.next;
  }
  sectionTail.next = null;

  // Step 3: reverse section.
  const oldSectionHead = sectionHead;
  const newSectionHead = reverseList(sectionHead);

  // Step 4: reattach the section.
  prev.next = newSectionHead;
  oldSectionHead.next = nxt;

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

  // Test cases
  const tests = [
    // From book
    [[1, 2, 3, 4, 5], 1, 3, [1, 4, 3, 2, 5]],
    [[1, 2, 3, 4, 5], 2, 7, [1, 2, 5, 4, 3]],
    [[1, 2], 5, 6, [1, 2]],

    // Test empty list
    [[], 0, 1, []],
    // Test single element list
    [[1], 0, 1, [1]],
    // Test reversing entire list
    [[1, 2, 3], 0, 3, [3, 2, 1]],
    // Test reversing sublist with repeated values
    [[1, 1, 1, 2, 2], 1, 3, [1, 2, 1, 1, 2]],
    // Test reversing sublist with negative values
    [[-1, -2, -3, -4], 1, 3, [-1, -4, -3, -2]],
    // Test reversing sublist with zero
    [[0, 1, 2], 0, 1, [1, 0, 2]],
    // Test reversing sublist at the end
    [[1, 2, 3, 4, 5], 2, 4, [1, 2, 5, 4, 3]],
    // Test left beyond list length - should not modify
    [[1, 2, 3], 4, 5, [1, 2, 3]],
    // Test right beyond list length - reverse to end
    [[1, 2, 3], 1, 5, [1, 3, 2]],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, left, right, expected] = tests[i];
    const head = arrayToLinkedList(arr);
    const reversedHead = reverseSection(head, left, right);
    const got = linkedListToArray(reversedHead);
    if (JSON.stringify(got) !== JSON.stringify(expected)) {
      throw new Error(
        `\nTest ${i + 1}: got: ${JSON.stringify(got)}, want: ${JSON.stringify(expected)}\n`,
      );
    }
  }
  return true;
}

runTests();
