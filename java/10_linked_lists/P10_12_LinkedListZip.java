// 10.12 - Linked-List Zip
// Run: javac P10_12_LinkedListZip.java && java P10_12_LinkedListZip

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

class Merge {
  public Node solve(Node head1, Node head2) {
    Node dummy = new Node(0);
    Node cur = dummy;

    Node p1 = head1;
    Node p2 = head2;
    while (p1 != null && p2 != null) {
      cur.next = p1;
      cur = cur.next;
      p1 = p1.next;

      cur.next = p2;
      p2 = p2.next;
      cur = cur.next;
    }

    if (p1 != null) {
      cur.next = p1;
    } else {
      cur.next = p2;
    }

    return dummy.next;
  }
}


class RunTests {
  private Node arrayToLinkedList(int[] arr) {
    Node dummy = new Node(0);
    Node curr = dummy;
    for (int i = 0; i < arr.length; i++) {
      curr.next = new Node(arr[i]);
      curr = curr.next;
    }
    return dummy.next;
  }

  private List<Integer> linkedListToList(Node head) {
    List<Integer> result = new ArrayList<>();
    while (head != null) {
      result.add(head.val);
      head = head.next;
    }
    return result;
  }

  public void runTests() {
    Object[][] tests = {
        // Book examples
        { new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 },
            new int[] { 1, 2, 3, 4, 5, 6 } },
        { new int[] { 1, 2, 3, 4 }, new int[] { 8, 7 },
            new int[] { 1, 8, 2, 7, 3, 4 } },

        // Test empty lists
        { new int[] {}, new int[] {}, new int[] {} },
        // Test one empty list
        { new int[] { 1, 2 }, new int[] {}, new int[] { 1, 2 } },
        { new int[] {}, new int[] { 1, 2 }, new int[] { 1, 2 } },
        // Test equal length lists
        { new int[] { 1, 3 }, new int[] { 2, 4 }, new int[] { 1, 2, 3, 4 } },
        // Test different length lists
        { new int[] { 1, 3, 5 }, new int[] { 2, 4 },
            new int[] { 1, 2, 3, 4, 5 } },
        { new int[] { 1, 3 }, new int[] { 2, 4, 6 },
            new int[] { 1, 2, 3, 4, 6 } },
        // Test with negative numbers
        { new int[] { -1, -3 }, new int[] { -2, -4 },
            new int[] { -1, -2, -3, -4 } },
        // Test with zeros
        { new int[] { 0, 0 }, new int[] { 0, 0 }, new int[] { 0, 0, 0, 0 } },
        // Test longer lists
        { new int[] { 1, 3, 5, 7, 9 }, new int[] { 2, 4, 6, 8, 10 },
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } },
    };

    Merge solution = new Merge();
    for (int i = 0; i < tests.length; i++) {
      int[] input1 = (int[]) tests[i][0];
      int[] input2 = (int[]) tests[i][1];
      int[] want = (int[]) tests[i][2];
      Node head1 = arrayToLinkedList(input1);
      Node head2 = arrayToLinkedList(input2);
      Node result = solution.solve(head1, head2);
      List<Integer> got = linkedListToList(result);
      List<Integer> wantList = new ArrayList<>();
      for (int w : want)
        wantList.add(w);

      if (!got.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nTest %d: merge(%s, %s): got: %s, want: %s\n",
            i + 1, Arrays.toString(input1), Arrays.toString(input2), got,
            wantList));
      }
    }
  }
}

public class P10_12_LinkedListZip {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
