// 10.7 - Sublist Reversal
// Run: javac P10_07_SublistReversal.java && java P10_07_SublistReversal

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

class ReverseSection {
  private Node nodeAtIndex(Node head, int index) {
    // Retrieves the node at the specified zero-based index in a singly linked
    // list.
    // Returns null if:
    // - The index is negative.
    // - The index is out of bounds (greater than or equal to the list length).
    // - The linked list is empty (head is null).
    // Iterates through the list, returning the node when the index matches.
    if (index < 0) {
      // Invalid index
      return null;
    }

    Node cur = head;
    int i = 0;

    while (cur != null) {
      if (i == index) {
        return cur;
      }
      cur = cur.next;
      i++;
    }

    // If we traverse the whole list and don't find the index
    return null;
  }

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

  public Node solve(Node head, int left, int right) {
    Node dummy = new Node(0);
    dummy.next = head;

    // Step 1: find the nodes BEFORE and AFTER the section.
    Node prev;
    if (left == 0) {
      prev = dummy;
    } else {
      prev = nodeAtIndex(head, left - 1);
    }
    if (prev == null || prev.next == null) {
      // Nothing to reverse.
      return head;
    }
    Node nxt = nodeAtIndex(head, right + 1); // May be null.

    // Step 2: break out the section.
    Node sectionHead = prev.next;
    prev.next = null;
    Node sectionTail = sectionHead;
    while (sectionTail.next != nxt) {
      sectionTail = sectionTail.next;
    }
    sectionTail.next = null;

    // Step 3: reverse section.
    Node oldSectionHead = sectionHead;
    Node newSectionHead = reverseList(sectionHead);

    // Step 4: reattach the section.
    prev.next = newSectionHead;
    oldSectionHead.next = nxt;

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
    Object[][] tests = {
        // From book
        { new int[] { 1, 2, 3, 4, 5 }, 1, 3, new int[] { 1, 4, 3, 2, 5 } },
        { new int[] { 1, 2, 3, 4, 5 }, 2, 7, new int[] { 1, 2, 5, 4, 3 } },
        { new int[] { 1, 2 }, 5, 6, new int[] { 1, 2 } },

        // Test empty list
        { new int[] {}, 0, 1, new int[] {} },
        // Test single element list
        { new int[] { 1 }, 0, 1, new int[] { 1 } },
        // Test reversing entire list
        { new int[] { 1, 2, 3 }, 0, 3, new int[] { 3, 2, 1 } },
        // Test reversing sublist with repeated values
        { new int[] { 1, 1, 1, 2, 2 }, 1, 3, new int[] { 1, 2, 1, 1, 2 } },
        // Test reversing sublist with negative values
        { new int[] { -1, -2, -3, -4 }, 1, 3, new int[] { -1, -4, -3, -2 } },
        // Test reversing sublist with zero
        { new int[] { 0, 1, 2 }, 0, 1, new int[] { 1, 0, 2 } },
        // Test reversing sublist at the end
        { new int[] { 1, 2, 3, 4, 5 }, 2, 4, new int[] { 1, 2, 5, 4, 3 } },
        // Test left beyond list length - should not modify
        { new int[] { 1, 2, 3 }, 4, 5, new int[] { 1, 2, 3 } },
        // Test right beyond list length - reverse to end
        { new int[] { 1, 2, 3 }, 1, 5, new int[] { 1, 3, 2 } },
    };

    ReverseSection solution = new ReverseSection();
    for (int i = 0; i < tests.length; i++) {
      int[] input = (int[]) tests[i][0];
      int left = (int) tests[i][1];
      int right = (int) tests[i][2];
      int[] want = (int[]) tests[i][3];
      Node head = arrayToLinkedList(input);
      Node result = solution.solve(head, left, right);
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

public class P10_07_SublistReversal {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
