// 10.6 - Linked-List Reversal
// Run: javac P10_06_LinkedListReversal.java && java P10_06_LinkedListReversal

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

class ReverseList {
  public Node solve(Node head) {
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
}


class RunTests {
  private Node arrayToLinkedList(int[] arr) {
    Node dummy = new Node(0);
    Node current = dummy;
    for (int i = 0; i < arr.length; i++) {
      current.next = new Node(arr[i]);
      current = current.next;
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
        // Test empty list
        { new int[] {}, new int[] {} },
        // Test single element list
        { new int[] { 1 }, new int[] { 1 } },
        // Test multiple elements list
        { new int[] { 1, 2, 3 }, new int[] { 3, 2, 1 } },
        // Test list with repeated values
        { new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 } },
        // Test list with negative values
        { new int[] { -1, -2, -3 }, new int[] { -3, -2, -1 } },
        // Test list with zero
        { new int[] { 0 }, new int[] { 0 } },
        // Test longer list
        { new int[] { 1, 2, 3, 4, 5 }, new int[] { 5, 4, 3, 2, 1 } },
        // Test list with mixed values
        { new int[] { -1, 0, 1 }, new int[] { 1, 0, -1 } },
    };

    ReverseList solution = new ReverseList();
    for (int i = 0; i < tests.length; i++) {
      int[] input = (int[]) tests[i][0];
      int[] want = (int[]) tests[i][1];
      Node head = arrayToLinkedList(input);
      Node reversedHead = solution.solve(head);
      List<Integer> got = linkedListToList(reversedHead);
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

public class P10_06_LinkedListReversal {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
