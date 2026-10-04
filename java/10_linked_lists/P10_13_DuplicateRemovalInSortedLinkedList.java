// 10.13 - Duplicate Removal in Sorted Linked List
// Run: javac P10_13_DuplicateRemovalInSortedLinkedList.java && java P10_13_DuplicateRemovalInSortedLinkedList

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

class RemoveDuplicates {
  public Node solve(Node head) {
    Node cur = head;
    while (cur != null && cur.next != null) {
      if (cur.val == cur.next.val) {
        cur.next = cur.next.next;
      } else {
        cur = cur.next;
      }
    }
    return head;
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
        // Book example
        { new int[] { 1, 1, 1, 3, 5, 5 }, new int[] { 1, 3, 5 } },

        // Test empty list
        { new int[] {}, new int[] {} },
        // Test single node
        { new int[] { 1 }, new int[] { 1 } },
        // Test no duplicates
        { new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 } },
        // Test all duplicates
        { new int[] { 1, 1, 1, 1, 1 }, new int[] { 1 } },
        // Test some duplicates
        { new int[] { 1, 1, 2, 3, 3 }, new int[] { 1, 2, 3 } },
        // Test duplicates at start
        { new int[] { 1, 1, 2, 3 }, new int[] { 1, 2, 3 } },
        // Test duplicates at end
        { new int[] { 1, 2, 3, 3 }, new int[] { 1, 2, 3 } },
        // Test duplicates in middle
        { new int[] { 1, 2, 2, 3 }, new int[] { 1, 2, 3 } },
        // Test with negative numbers
        { new int[] { -3, -3, -2, -1, -1 }, new int[] { -3, -2, -1 } },
        // Test with zeros
        { new int[] { 0, 0, 0, 1, 1 }, new int[] { 0, 1 } },
    };

    RemoveDuplicates solution = new RemoveDuplicates();
    for (int i = 0; i < tests.length; i++) {
      int[] input = (int[]) tests[i][0];
      int[] want = (int[]) tests[i][1];
      Node head = arrayToLinkedList(input);
      Node result = solution.solve(head);
      List<Integer> got = linkedListToList(result);
      List<Integer> wantList = new ArrayList<>();
      for (int w : want)
        wantList.add(w);

      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nTest %d: removeDuplicates(%s): got: %s, want: %s\n",
            i + 1, Arrays.toString(input), got, wantList));
      }
    }
  }
}

public class P10_13_DuplicateRemovalInSortedLinkedList {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
