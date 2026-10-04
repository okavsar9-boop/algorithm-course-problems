// 10.11 - Remove Kth Node From the End
// Run: javac P10_11_RemoveKthNodeFromTheEnd.java && java P10_11_RemoveKthNodeFromTheEnd

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

class RemoveKthNodeTwoPass {
  public Node solve(Node head, int k) {
    // First pass: compute the length of the list
    int n = 0;
    Node current = head;
    while (current != null) {
      n += 1;
      current = current.next;
    }

    // Second pass: walk n-k steps from the head and remove the element
    if (k == n) {
      return head.next; // Remove the first element
    }

    current = head;
    for (int i = 0; i < n - k - 1; i++) {
      current = current.next;
    }

    current.next = current.next.next;
    return head;
  }
}

class RemoveKthNode {
  public Node solve(Node head, int k) {
    Node dummy = new Node(0);
    dummy.next = head;
    Node fast = dummy;
    Node slow = dummy;

    for (int i = 0; i < k; i++) {
      fast = fast.next;
    }

    while (fast != null && fast.next != null) {
      fast = fast.next;
      slow = slow.next;
    }

    slow.next = slow.next.next;
    return dummy.next;
  }
}


class RunTests {
  private Node arrayToLinkedList(int[] arr) {
    if (arr.length == 0)
      return null;
    Node head = new Node(arr[0]);
    Node cur = head;
    for (int i = 1; i < arr.length; i++) {
      cur.next = new Node(arr[i]);
      cur = cur.next;
    }
    return head;
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
        // Test single element list
        { new int[] { 1 }, 1, new int[] {} },
        // Test removing first element (k = length)
        { new int[] { 1, 2, 3 }, 3, new int[] { 2, 3 } },
        // Test removing last element (k = 1)
        { new int[] { 1, 2, 3 }, 1, new int[] { 1, 2 } },
        // Test removing middle element
        { new int[] { 1, 2, 3 }, 2, new int[] { 1, 3 } },
        // Test longer list removing first
        { new int[] { 1, 2, 3, 4, 5 }, 5, new int[] { 2, 3, 4, 5 } },
        // Test longer list removing last
        { new int[] { 1, 2, 3, 4, 5 }, 1, new int[] { 1, 2, 3, 4 } },
        // Test longer list removing middle
        { new int[] { 1, 2, 3, 4, 5 }, 3, new int[] { 1, 2, 4, 5 } },
        // Test with repeated values
        { new int[] { 1, 1, 1 }, 2, new int[] { 1, 1 } },
        // Test with negative values
        { new int[] { -1, -2, -3 }, 2, new int[] { -1, -3 } },
    };

    RemoveKthNode solution = new RemoveKthNode();
    RemoveKthNodeTwoPass solutionTwoPass = new RemoveKthNodeTwoPass();
    for (int i = 0; i < tests.length; i++) {
      int[] arr = (int[]) tests[i][0];
      int k = (int) tests[i][1];
      int[] want = (int[]) tests[i][2];

      // Test the fast/slow pointer solution
      Node result1 = solution.solve(arrayToLinkedList(arr), k);
      List<Integer> got1 = linkedListToList(result1);
      List<Integer> wantList = new ArrayList<>();
      for (int w : want)
        wantList.add(w);

      if (!got1.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nTest %d (fast/slow): solve(%s, %d): got: %s, want: %s\n",
            i + 1, Arrays.toString(arr), k, got1, wantList));
      }

      // Test the two pass solution
      Node result2 = solutionTwoPass.solve(arrayToLinkedList(arr), k);
      List<Integer> got2 = linkedListToList(result2);

      if (!got2.equals(wantList)) {
        throw new RuntimeException(String.format(
            "\nTest %d (two pass): solve(%s, %d): got: %s, want: %s\n",
            i + 1, Arrays.toString(arr), k, got2, wantList));
      }
    }
  }
}

public class P10_11_RemoveKthNodeFromTheEnd {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
