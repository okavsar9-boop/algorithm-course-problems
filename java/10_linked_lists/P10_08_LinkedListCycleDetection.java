// 10.8 - Linked-List Cycle Detection
// Run: javac P10_08_LinkedListCycleDetection.java && java P10_08_LinkedListCycleDetection

import java.util.*;
import java.util.function.*;

class Node {
  int val;
  Node next;

  Node(int x) {
    val = x;
    next = null;
  }
}

class HasCycle {
  public boolean solve(Node head) {
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        return true;
      }
    }
    return false;
  }
}


class RunTests {
  // arr: non-empty array representing the linked list
  // finalPointerIndex: index of the node that the last pointer's next pointer
  // should point to.
  // If finalPointerIndex is -1, then the last pointer's next pointer should
  // point to null.
  //
  // Returns the head of the list
  private Node createCyclicList(int[] arr, int finalPointerIndex) {
    // Build list and store cycle start node
    Node dummyHead = new Node(0);
    Node current = dummyHead;
    Node cycleStartNode = null;
    for (int i = 0; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
      if (i == finalPointerIndex) {
        cycleStartNode = current;
      }
    }

    // Create cycle if needed
    if (cycleStartNode != null) {
      current.next = cycleStartNode;
    }

    return dummyHead.next;
  }

  public void runTests() {
    Object[][] tests = {
        // Test: (list, finalPointerIndex, want)

        // Single node no cycle
        { new int[] { 1 }, -1, false },
        // Single node with cycle
        { new int[] { 1 }, 0, true },
        // Multiple nodes with no cycle
        { new int[] { 1, 2, 3, 4, 5 }, -1, false },
        // Multiple nodes all in a cycle
        { new int[] { 1, 2, 3, 4, 5 }, 0, true },
        // Multiple nodes with cycle in the middle
        { new int[] { 1, 2, 3, 4, 5 }, 2, true },
        // Multiple nodes with cycle at the end
        { new int[] { 1, 2, 3, 4, 5 }, 4, true },
        // The length of the cycle is equal to the distance from the
        // head to the start of the cycle (both are 5)
        { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 5, true },
        // The length of the cycle is greater than the distance from the
        // head to the start of the cycle
        { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 4, true },
        // The length of the cycle is less than the distance from the
        // head to the start of the cycle
        { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 6, true },
    };

    HasCycle solution = new HasCycle();
    for (int i = 0; i < tests.length; i++) {
      int[] arr = (int[]) tests[i][0];
      int finalPointerIndex = (int) tests[i][1];
      boolean want = (boolean) tests[i][2];
      Node head = createCyclicList(arr, finalPointerIndex);
      boolean got = solution.solve(head);

      String cycleDesc;
      if (finalPointerIndex == -1) {
        cycleDesc = "no cycle";
      } else {
        cycleDesc = "cycle starting at index " + finalPointerIndex;
      }
      String testCaseStr = String.format("Test %d: hasCycle(list %s with %s)",
          i + 1, Arrays.toString(arr), cycleDesc);

      if (got != want) {
        throw new RuntimeException(String.format(
            "\n%s: got: %b, want: %b",
            testCaseStr, got, want));
      }
    }
  }
}

public class P10_08_LinkedListCycleDetection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
