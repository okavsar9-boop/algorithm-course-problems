// 10.14 - Linked List Block Reversal
// Run: javac P10_14_LinkedListBlockReversal.java && java P10_14_LinkedListBlockReversal

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

class ReverseKGroup {
  private Node reverseList(Node head) {
    Node prev = null;
    Node cur = head;
    while (cur != null) {
      Node nxt = cur.next;
      cur.next = prev;
      prev = cur;
      cur = nxt;
    }
    return prev;
  }

  public Node solve(Node head, int k) {
    Node dummy = new Node(0);
    dummy.next = head;
    Node groupPrev = dummy;

    while (true) {
      // 1. Find the bounds of the current block
      Node kth = groupPrev;
      for (int i = 0; i < k; i++) {
        kth = kth.next;
        if (kth == null) {
          return dummy.next;
        }
      }
      Node groupNext = kth.next;

      // 2. Break the block out from the rest of the list
      kth.next = null;
      Node groupHead = groupPrev.next;

      // 3. Reverse the block
      Node reversedHead = reverseList(groupHead);

      // 4. Reattach the reversed block
      groupPrev.next = reversedHead;
      groupHead.next = groupNext;
      groupPrev = groupHead;
    }
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
    Object[][] tests = {
        // Book examples
        { new int[] { 1, 2, 3, 4 }, 2, new int[] { 2, 1, 4, 3 } },
        { new int[] { 1, 2, 3, 4, 5 }, 3, new int[] { 3, 2, 1, 4, 5 } },

        { new int[] { 1, 2, 3, 4, 5, 6 }, 2, new int[] { 2, 1, 4, 3, 6, 5 } },

        // Test empty list
        { new int[] {}, 2, new int[] {} },
        // Test single element list
        { new int[] { 1 }, 2, new int[] { 1 } },
        // Test k greater than list length
        { new int[] { 1, 2, 3 }, 4, new int[] { 1, 2, 3 } },
        // Test k equal to list length
        { new int[] { 1, 2, 3 }, 3, new int[] { 3, 2, 1 } },
        // Test k less than list length
        { new int[] { 1, 2, 3, 4, 5 }, 2, new int[] { 2, 1, 4, 3, 5 } },
        // Test k is 1 (no change)
        { new int[] { 1, 2, 3, 4, 5 }, 1, new int[] { 1, 2, 3, 4, 5 } },
        // Test list with repeated values
        { new int[] { 1, 1, 1, 2, 2 }, 2, new int[] { 1, 1, 2, 1, 2 } },
        // Test list with negative values
        { new int[] { -1, -2, -3, -4 }, 2, new int[] { -2, -1, -4, -3 } },
        // Test list with zero
        { new int[] { 0, 1, 2 }, 2, new int[] { 1, 0, 2 } },
        // Test longer list
        { new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }, 3,
            new int[] { 3, 2, 1, 6, 5, 4, 7, 8 } },
    };

    ReverseKGroup solution = new ReverseKGroup();
    for (int i = 0; i < tests.length; i++) {
      int[] input = (int[]) tests[i][0];
      int k = (int) tests[i][1];
      int[] want = (int[]) tests[i][2];
      Node head = arrayToLinkedList(input);
      Node result = solution.solve(head, k);
      List<Integer> got = linkedListToList(result);
      List<Integer> wantList = new ArrayList<>();
      for (int w : want)
        wantList.add(w);

      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nTest %d: got: %s, want: %s\n",
            i + 1, got, wantList));
      }
    }
  }
}

public class P10_14_LinkedListBlockReversal {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
