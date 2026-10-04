// 10.10 - Linked-List Midpoint
// Run: node 10_10_linked_list_midpoint.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function getMiddleTwoPass(head) {
  // First pass: count the nodes
  let count = 0;
  let current = head;
  while (current) {
    count += 1;
    current = current.next;
  }

  // Second pass: stop at half of the count
  let middleIndex = Math.floor(count / 2);
  current = head;
  for (let i = 0; i < middleIndex; i++) {
    current = current.next;
  }

  return current.val;
}

function getMiddle(head) {
  let slow = head,
    fast = head;
  while (fast && fast.next) {
    slow = slow.next;
    fast = fast.next.next;
  }
  return slow.val;
}


function runTests() {
  function arrayToLinkedList(arr) {
    const head = new Node(arr[0]);
    let current = head;
    for (let i = 1; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
    }
    return head;
  }

  const tests = [
    // Test single node
    [[10], 10],
    // Test two nodes
    [[10, 20], 20],
    // Test odd number of nodes
    [[10, 20, 30], 20],
    // Test even number of nodes
    [[10, 20, 30, 40], 30],
    // Test longer odd list
    [[10, 20, 30, 40, 50], 30],
    // Test longer even list
    [[10, 20, 30, 40, 50, 60], 40],
    // Test with negative values
    [[-10, -20, -30], -20],
    // Test with zeros
    [[0, 0, 0], 0],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [inputArr, want] = tests[i];

    // Test the fast/slow pointer solution
    const head = arrayToLinkedList(inputArr);
    const got = getMiddle(head);
    if (got !== want) {
      throw new Error(
        `\nTest ${i + 1} (fast/slow): got: ${got}, want: ${want}\n`,
      );
    }

    // Test the two pass solution
    const gotTwoPass = getMiddleTwoPass(head);
    if (gotTwoPass !== want) {
      throw new Error(
        `\nTest ${i + 1} (two pass): got: ${gotTwoPass}, want: ${want}\n`,
      );
    }
  }
}

runTests();
