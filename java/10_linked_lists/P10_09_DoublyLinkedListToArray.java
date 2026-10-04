// 10.9 - Doubly Linked List To Array
// Run: javac P10_09_DoublyLinkedListToArray.java && java P10_09_DoublyLinkedListToArray

import java.util.*;
import java.util.function.*;

class Node {
  int val;
  Node next;
  Node prev;

  Node(int x) {
    val = x;
    next = null;
    prev = null;
  }
}

class ConvertToArray {
  public List<Integer> solve(Node node) {
    Node cur = node;
    while (cur.prev != null) {
      cur = cur.prev;
    }
    List<Integer> res = new ArrayList<>();
    while (cur != null) {
      res.add(cur.val);
      cur = cur.next;
    }
    return res;
  }
}


class RunTests {
  private Node createDoublyLinkedList(int[] arr) {
    Node head = new Node(arr[0]);
    Node cur = head;
    for (int i = 1; i < arr.length; i++) {
      Node newNode = new Node(arr[i]);
      cur.next = newNode;
      newNode.prev = cur;
      cur = newNode;
    }
    return head;
  }

  private Node nodeAtIndex(Node head, int index) {
    Node cur = head;
    for (int i = 0; i < index; i++) {
      cur = cur.next;
    }
    return cur;
  }

  public void runTests() {
    Object[][] tests = {
        // Examples from the book
        { new int[] { 1, 2, 3, 4 }, 2 },
        { new int[] { 1, 2, 3, 4 }, 0 },

        { new int[] { 1, 2, 3, 4, 5 }, 0 },
        { new int[] { 1, 2, 3, 4, 5 }, 1 },
        { new int[] { 1, 2, 3, 4, 5 }, 2 },
        { new int[] { 1, 2, 3, 4, 5 }, 3 },
        { new int[] { 1, 2, 3, 4, 5 }, 4 },
        // Test single node
        { new int[] { 1 }, 0 },
    };

    ConvertToArray solution = new ConvertToArray();
    for (int i = 0; i < tests.length; i++) {
      int[] arr = (int[]) tests[i][0];
      int index = (int) tests[i][1];
      Node head = createDoublyLinkedList(arr);
      Node node = nodeAtIndex(head, index);
      List<Integer> got = solution.solve(node);

      List<Integer> want = new ArrayList<>();
      for (int w : arr) {
        want.add(w);
      }

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nTest %d: got: %s, want: %s\n",
            i + 1, got, Arrays.toString(arr)));
      }
    }
  }
}

public class P10_09_DoublyLinkedListToArray {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
