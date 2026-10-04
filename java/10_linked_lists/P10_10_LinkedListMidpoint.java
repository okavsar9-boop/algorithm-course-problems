// 10.10 - Linked-List Midpoint
// Run: javac P10_10_LinkedListMidpoint.java && java P10_10_LinkedListMidpoint

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

class GetMiddleTwoPass {
  public int solve(Node head) {
    // First pass: count the nodes
    int count = 0;
    Node current = head;
    while (current != null) {
      count += 1;
      current = current.next;
    }

    // Second pass: stop at half of the count
    int middleIndex = count / 2;
    current = head;
    for (int i = 0; i < middleIndex; i++) {
      current = current.next;
    }

    return current.val;
  }
}

class GetMiddle {
  public int solve(Node head) {
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow.val;
  }
}


class RunTests {
  private Node arrayToLinkedList(int[] arr) {
    Node head = new Node(arr[0]);
    Node curr = head;
    for (int i = 1; i < arr.length; i++) {
      curr.next = new Node(arr[i]);
      curr = curr.next;
    }
    return head;
  }

  public void runTests() {
    Object[][] tests = {
        // Test single node
        { new int[] { 10 }, 10 },
        // Test two nodes
        { new int[] { 10, 20 }, 20 },
        // Test odd number of nodes
        { new int[] { 10, 20, 30 }, 20 },
        // Test even number of nodes
        { new int[] { 10, 20, 30, 40 }, 30 },
        // Test longer odd list
        { new int[] { 10, 20, 30, 40, 50 }, 30 },
        // Test longer even list
        { new int[] { 10, 20, 30, 40, 50, 60 }, 40 },
        // Test with negative values
        { new int[] { -10, -20, -30 }, -20 },
        // Test with zeros
        { new int[] { 0, 0, 0 }, 0 },
    };

    GetMiddle solution = new GetMiddle();
    GetMiddleTwoPass twoPassSolution = new GetMiddleTwoPass();
    for (int i = 0; i < tests.length; i++) {
      int[] input = (int[]) tests[i][0];
      int want = (int) tests[i][1];
      Node head = arrayToLinkedList(input);

      // Test the fast/slow pointer solution
      int got = solution.solve(head);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nTest %d (fast/slow): got: %s, want: %s\n",
            i + 1, got, want));
      }

      // Test the brute force solution
      int gotBruteForce = twoPassSolution.solve(head);
      if (gotBruteForce != want) {
        throw new RuntimeException(String.format(
            "\nTest %d (brute force): got: %s, want: %s\n",
            i + 1, gotBruteForce, want));
      }
    }
  }
}

public class P10_10_LinkedListMidpoint {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
