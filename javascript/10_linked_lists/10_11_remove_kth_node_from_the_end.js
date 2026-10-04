// 10.11 - Remove Kth Node From the End
// Run: node 10_11_remove_kth_node_from_the_end.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function removeKthNodeTwoPass(head, k) {
  // First pass: compute the length of the list
  let n = 0;
  let current = head;
  while (current) {
    n += 1;
    current = current.next;
  }

  // Second pass: walk n-k steps from the head and remove the element
  if (k === n) {
    return head.next; // Remove the first element
  }

  current = head;
  for (let i = 0; i < n - k - 1; i++) {
    current = current.next;
  }

  current.next = current.next.next;
  return head;
}

function removeKthNode(head, k) {
  const dummy = new Node(0);
  dummy.next = head;
  let fast = dummy;
  let slow = dummy;

  for (let i = 0; i < k; i++) {
    fast = fast.next;
  }

  while (fast && fast.next) {
    fast = fast.next;
    slow = slow.next;
  }

  slow.next = slow.next.next;
  return dummy.next;
}


function runTests() {
  function arrayToLinkedList(arr) {
    if (!arr.length) {
      return null;
    }
    const head = new Node(arr[0]);
    let current = head;
    for (let i = 1; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
    }
    return head;
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
    // Test single element list
    [[1], 1, []],
    // Test removing first element (k = length)
    [[1, 2, 3], 3, [2, 3]],
    // Test removing last element (k = 1)
    [[1, 2, 3], 1, [1, 2]],
    // Test removing middle element
    [[1, 2, 3], 2, [1, 3]],
    // Test longer list removing first
    [[1, 2, 3, 4, 5], 5, [2, 3, 4, 5]],
    // Test longer list removing last
    [[1, 2, 3, 4, 5], 1, [1, 2, 3, 4]],
    // Test longer list removing middle
    [[1, 2, 3, 4, 5], 3, [1, 2, 4, 5]],
    // Test with repeated values
    [[1, 1, 1], 2, [1, 1]],
    // Test with negative values
    [[-1, -2, -3], 2, [-1, -3]],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, k, want] = tests[i];

    // Test the fast/slow pointer solution
    const result = removeKthNode(arrayToLinkedList(arr), k);
    const got = linkedListToArray(result);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nTest ${i + 1} (fast/slow): removeKthNode(${JSON.stringify(arr)}, ${k}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }

    // Test the two pass solution
    const resultTwoPass = removeKthNodeTwoPass(arrayToLinkedList(arr), k);
    const gotTwoPass = linkedListToArray(resultTwoPass);
    if (JSON.stringify(gotTwoPass) !== JSON.stringify(want)) {
      throw new Error(
        `\nTest ${i + 1} (two pass): removeKthNodeTwoPass(${JSON.stringify(arr)}, ${k}): got: ${JSON.stringify(gotTwoPass)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
