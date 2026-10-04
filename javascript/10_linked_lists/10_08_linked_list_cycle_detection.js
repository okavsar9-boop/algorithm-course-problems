// 10.8 - Linked-List Cycle Detection
// Run: node 10_08_linked_list_cycle_detection.js

class Node {
  constructor(val = 0, next = null) {
    this.val = val;
    this.next = next;
  }
}

function hasCycle(head) {
  let slow = head,
    fast = head;
  while (fast && fast.next) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow === fast) {
      return true;
    }
  }
  return false;
}


function runTests() {
  // arr: non-empty array representing the linked list
  // finalPointerIndex: index of the node that the last pointer's next pointer
  // should point to.
  // If finalPointerIndex is -1, then the last pointer's next pointer should
  // point to null.
  //
  // Returns the head of the list
  function createCyclicList(arr, finalPointerIndex) {
    // Build list and store cycle start node
    const dummyHead = new Node(0);
    let current = dummyHead;
    let cycleStartNode = null;
    for (let i = 0; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
      if (i === finalPointerIndex) {
        cycleStartNode = current;
      }
    }

    // Create cycle if needed
    if (cycleStartNode) {
      current.next = cycleStartNode;
    }

    return dummyHead.next;
  }

  const tests = [
    // Test: (list, finalPointerIndex, want)

    // Single node no cycle
    [[1], -1, false],
    // Single node with cycle
    [[1], 0, true],
    // Multiple nodes with no cycle
    [[1, 2, 3, 4, 5], -1, false],
    // Multiple nodes all in a cycle
    [[1, 2, 3, 4, 5], 0, true],
    // Multiple nodes with cycle in the middle
    [[1, 2, 3, 4, 5], 2, true],
    // Multiple nodes with cycle at the end
    [[1, 2, 3, 4, 5], 4, true],
    // The length of the cycle is equal to the distance from the
    // head to the start of the cycle (both are 5)
    [[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 5, true],
    // The length of the cycle is greater than the distance from the
    // head to the start of the cycle
    [[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 4, true],
    // The length of the cycle is less than the distance from the
    // head to the start of the cycle
    [[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 6, true],
  ];

  for (let i = 0; i < tests.length; i++) {
    const [arr, finalPointerIndex, want] = tests[i];
    const head = createCyclicList(arr, finalPointerIndex);
    const got = hasCycle(head);

    let cycleDesc;
    if (finalPointerIndex === -1) {
      cycleDesc = "no cycle";
    } else {
      cycleDesc = `cycle starting at index ${finalPointerIndex}`;
    }
    const testCaseStr = `Test ${i + 1}: hasCycle(list ${JSON.stringify(arr)} with ${cycleDesc})`;

    if (got !== want) {
      throw new Error(`\n${testCaseStr}: got: ${got}, want: ${want}`);
    }
  }
}

runTests();
