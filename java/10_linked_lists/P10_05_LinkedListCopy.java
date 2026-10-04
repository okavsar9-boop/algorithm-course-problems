// 10.5 - Linked-List Copy
// Run: javac P10_05_LinkedListCopy.java && java P10_05_LinkedListCopy

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

class CopyList {
  public Node solve(Node head) {
    if (head == null)
      return null;
    Node newHead = new Node(head.val);
    Node curNew = newHead;
    Node curOld = head.next;
    while (curOld != null) {
      curNew.next = new Node(curOld.val);
      curNew = curNew.next;
      curOld = curOld.next;
    }
    return newHead;
  }
}

class CopyListWithDummy {
  public Node solve(Node head) {
    Node dummy = new Node(0); // New list's dummy head
    Node curNew = dummy;
    Node curOld = head;
    while (curOld != null) {
      curNew.next = new Node(curOld.val);
      curNew = curNew.next;
      curOld = curOld.next;
    }
    return dummy.next;
  }
}


class RunTests {
  private Node arrayToLinkedList(int[] arr) {
    Node dummy = new Node(0);
    Node cur = dummy;
    for (int i = 0; i < arr.length; i++) {
      cur.next = new Node(arr[i]);
      cur = cur.next;
    }
    return dummy.next;
  }

  private List<Integer> linkedListToList(Node head) {
    List<Integer> result = new ArrayList<>();
    Node cur = head;
    while (cur != null) {
      result.add(cur.val);
      cur = cur.next;
    }
    return result;
  }

  public void runTests() {
    int[][] tests = {
        // Test empty list
        {},
        // Test single element list
        { 1 },
        // Test multiple elements list
        { 1, 2, 3 },
        // Test list with repeated values
        { 1, 1, 1 },
        // Test list with negative values
        { -1, -2, -3 },
        // Test list with zero
        { 0 },
        // Test longer list
        { 1, 2, 3, 4, 5 },
        // Test list with mixed values
        { -1, 0, 1 },
    };

    CopyList solution1 = new CopyList();
    CopyListWithDummy solution2 = new CopyListWithDummy();

    for (int i = 0; i < tests.length; i++) {
      int[] arr = tests[i];
      Node head = arrayToLinkedList(arr);

      // Test first copyList function
      Node copiedHead1 = solution1.solve(head);
      List<Integer> got1 = linkedListToList(copiedHead1);
      List<Integer> want = new ArrayList<>();
      for (int w : arr)
        want.add(w);

      if (!got1.equals(want)) {
        throw new RuntimeException(String.format(
            "\nTest %d (copyList 1): got: %s, want: %s\n",
            i + 1, got1, want));
      }

      // Test second copyList function
      Node copiedHead2 = solution2.solve(head);
      List<Integer> got2 = linkedListToList(copiedHead2);

      if (!got2.equals(want)) {
        throw new RuntimeException(String.format(
            "\nTest %d (copyList 2): got: %s, want: %s\n",
            i + 1, got2, want));
      }
    }
  }
}

public class P10_05_LinkedListCopy {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
